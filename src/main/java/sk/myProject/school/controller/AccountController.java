package sk.myProject.school.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sk.myProject.school.mappers.PersonMapper;
import sk.myProject.school.model.MyUtils;
import sk.myProject.school.model.PersonBean;
import sk.myProject.school.request.PersonRequest;
import sk.myProject.school.service.GroupService;
import sk.myProject.school.service.PersonCisService;
import sk.myProject.school.service.PersonServiceImp;

@RestController
@RequestMapping("/acc")
public class AccountController {

    @Autowired
    private PersonServiceImp personService;
    @Autowired
    private GroupService groupService;
    @Autowired
    private PersonCisService personCisService;
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
    public ResponseEntity<String> login(@RequestParam("username") String username, @RequestParam("password") String password) {
        try {
            PersonBean personBean = personService.getPersonByUserName(username);
            if (personBean == null || !MyUtils.decryption(personBean.getPassword()).equals(password)) {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }
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
}
