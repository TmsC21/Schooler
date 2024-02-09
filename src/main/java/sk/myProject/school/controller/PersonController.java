package sk.myProject.school.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import sk.myProject.school.dto.PersonDTO;
import sk.myProject.school.mappers.PersonMapper;
import sk.myProject.school.model.MyUtils;
import sk.myProject.school.model.PersonBean;
import sk.myProject.school.model.PersonCis;
import sk.myProject.school.request.PersonRequest;
import sk.myProject.school.service.GroupService;
import sk.myProject.school.service.PersonCisService;
import sk.myProject.school.service.PersonServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/person")
public class PersonController {

    @Autowired
    private PersonServiceImp personService;
    @Autowired
    private GroupService groupService;
    @Autowired
    private PersonCisService personCisService;
    protected final ObjectMapper json = new ObjectMapper();


    @GetMapping(value = "/findById/{id}", produces = "application/json")
    @Procedure(MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> findById(@PathVariable Long id) {
        try {
            return new ResponseEntity<>(json.writeValueAsString(personService.getPersonDTOById(id)), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "/findByUuid/{uuid}", produces = "application/json")
    @Procedure(MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> findByUuid(@PathVariable String uuid) {
        try {
            return new ResponseEntity<>(json.writeValueAsString(personService.getPersonDTOByUUid(uuid)), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "/findAll", produces = "application/json")
    @Procedure(MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> findAll() {
        try {
            List<PersonDTO> personBeanList = personService.getPersonsList().stream().filter(personDTO -> !personDTO.getRole().getPersonCis().equals(PersonCis.ADMIN)).collect(Collectors.toList());
            return new ResponseEntity<>(json.writeValueAsString(personBeanList), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "/findAllBytGroupCode/{groupCode}", produces = "application/json")
    @Procedure(MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> findAllBytGroupCode(@PathVariable String groupCode) {
        try {
            return new ResponseEntity<>(json.writeValueAsString(personService.getPersonsListByGroupCode(groupCode)), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "/findByUserName/{userName}", produces = "application/json")
    @Procedure(MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> findByUserName(@PathVariable String userName) {
        try {
            return new ResponseEntity<>(json.writeValueAsString(personService.getPersonByUserName(userName)), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "/findAllBytGroupName/{groupName}", produces = "application/json")
    @Procedure(MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> findAllBytGroupName(@PathVariable String groupName) {
        try {
            return new ResponseEntity<>(json.writeValueAsString(personService.getPersonsListByGroupName(groupName)), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "/findAllBytLecturerGroupName/{groupName}", produces = "application/json")
    @Procedure(MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> findAllBytLecturerGroupName(@PathVariable String groupName) {
        try {
            List<PersonDTO> personDTOList = personService.getPersonsListByGroupName(groupName).stream().filter(personDTO -> personDTO.getRole().getPersonCis().equals(PersonCis.STUDENT)).toList();
            return new ResponseEntity<>(json.writeValueAsString(personDTOList), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
