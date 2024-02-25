package sk.myProject.school.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sk.myProject.school.mappers.PersonMapper;
import sk.myProject.school.model.*;
import sk.myProject.school.repository.LogCisRepository;
import sk.myProject.school.repository.LogRepository;
import sk.myProject.school.request.LoginForm;
import sk.myProject.school.request.PersonRequest;
import sk.myProject.school.service.GroupService;
import sk.myProject.school.service.PersonCisService;
import sk.myProject.school.service.PersonServiceImp;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/acc")
public class AccountController {

    @Autowired
    private PersonServiceImp personService;
    @Autowired
    private GroupService groupService;
    @Autowired
    private PersonCisService personCisService;
    @Autowired
    private LogRepository logRepository;
    @Autowired
    private LogCisRepository logCisRepository;
    protected final ObjectMapper json = new ObjectMapper();

    @PostMapping(value = "/register", produces = "application/json")
    @Procedure(MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> register(@RequestBody PersonRequest personRequest) {
        try {
            if (personService.getPersonByUserName(personRequest.getUserName()) != null) {
                throw new Exception("Username is already used");
            }
            if (personService.getPersonByEmail(personRequest.getEmail()) != null) {
                throw new Exception("Email is already used");
            }
            MyUtils.validateEmail(personRequest.getEmail());
            personRequest.setPassword(MyUtils.encryption(personRequest.getPassword()));
            PersonBean newPersonBean = new PersonBean(personRequest);
            newPersonBean.setGroupBean(groupService.getGroupByName(personRequest.getGroupName()));
            newPersonBean.setPersonCisBean(personCisService.getPersonCis(personRequest.getPersonCis()));
            return new ResponseEntity<>(json.writeValueAsString(personService.createPerson(newPersonBean)), HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
        }
    }

    @PostMapping(value = "/login", produces = "application/json")
    @Procedure(MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> login(@RequestBody LoginForm loginForm) {
        try {
            PersonBean personBean = personService.getPersonByUserName(loginForm.getUsername());
            if (personBean == null || !MyUtils.decryption(personBean.getPassword()).equals(loginForm.getPassword())) {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }
            new LogBean(personBean, LogTypeCis.LOG_IN, new Date(), logCisRepository).log(logRepository);
            return new ResponseEntity<>(json.writeValueAsString(PersonMapper.INSTANCE.personToPersonDTO(personBean)), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
        }
    }

    @PostMapping(value = "/resendPassword", produces = "application/json")
    @Procedure(MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> resendPassword(@RequestParam("email") String email) {
        try {
            MyUtils.validateEmail(email);
            PersonBean personBean = personService.getPersonByEmail(email);
            if (personBean == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            personBean.setPassword(MyUtils.encryption(MyUtils.generateRandomString(5)));
            personService.savePerson(personBean);
            MyUtils.sendEmail(email, "Resend password for " + personBean.getName() + " " + personBean.getSurname(), "Your new password is: " + MyUtils.decryption(personBean.getPassword()));
            return new ResponseEntity<>(json.writeValueAsString(PersonMapper.INSTANCE.personToPersonDTO(personBean)), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
        }
    }

    @PutMapping(value = "/resetPassword", produces = "application/json")
    @Procedure(MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> resetPassword(@RequestParam("id") Long id, @RequestParam("oldPassword") String oldPassword, @RequestParam("newPassword") String newPassword) {
        try {
            PersonBean personBean = personService.getPersonById(id);
            if (personBean == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            if (!MyUtils.decryption(personBean.getPassword()).equals(oldPassword)) {
                return new ResponseEntity<>("Invalid password", HttpStatus.FORBIDDEN);
            }
            personBean.setPassword(MyUtils.encryption(newPassword));
            personService.savePerson(personBean);
            return new ResponseEntity<>(json.writeValueAsString(PersonMapper.INSTANCE.personToPersonDTO(personBean)), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
        }
    }
    @PostMapping(value = "/logActivity", produces = "application/json")
    @Procedure(MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> logActivity(@RequestBody String uuid) {
        try {
            new LogBean(personService.getPersonByUUid(uuid), LogTypeCis.LOGGED, new Date(), logCisRepository).log(logRepository);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
        }
    }
    @PostMapping(value = "/getActivities", produces = MediaType.APPLICATION_JSON_VALUE)
    @Procedure(MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Boolean>> getActivities(@RequestBody List<String> uuids) {
        try {
            Map<String, Boolean> map = new HashMap<>();
            LocalDateTime currentTime = LocalDateTime.now();

            for (String uuid : uuids) {
                PersonBean personBean = personService.getPersonByUUid(uuid);
                List<LogBean> logs = logRepository.findAllByPersonBeanOrderByCreateDateDesc(personBean);

                if (!logs.isEmpty()) {
                    LogBean logBean = logs.get(0);
                    LocalDateTime createDate = logBean.getCreateDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
                    boolean isActive = Duration.between(createDate, currentTime).toMinutes() <= 1;
                    map.put(uuid, isActive);
                } else {
                    map.put(uuid, false); // No logs found, considered as inactive
                }
            }
            return new ResponseEntity<>(map, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
