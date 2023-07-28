package sk.myProject.school.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import sk.myProject.school.model.MyUtils;
import sk.myProject.school.model.PersonBean;
import sk.myProject.school.request.PersonRequest;
import sk.myProject.school.service.GroupService;
import sk.myProject.school.service.PersonServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/person")
public class PersonController {

    @Autowired
    private PersonServiceImp personService;
    @Autowired
    private GroupService groupService;

    protected final ObjectMapper json = new ObjectMapper();

    @PostMapping(value = "/create", produces = "application/json")
    @Procedure(MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> createPerson(@RequestBody PersonRequest personRequest) {
        try {
            personService.validateEmail(personRequest.getEmail());
            personRequest.setPassword(MyUtils.encryption(personRequest.getPassword()));
            PersonBean newPersonBean = new PersonBean(personRequest);
            newPersonBean.setGroupBean(groupService.getGroupByName(personRequest.getGroupName()));
            return new ResponseEntity<>(json.writeValueAsString(personService.createPerson(newPersonBean)), HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
        }
    }

    @GetMapping(value = "/findById/{id}", produces = "application/json")
    @Procedure(MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> findById(@PathVariable Long id) {
        try {
            return new ResponseEntity<>(json.writeValueAsString(personService.getPersonById(id)), HttpStatus.FOUND);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "/findAll", produces = "application/json")
    @Procedure(MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> findAll() {
        try {
            return new ResponseEntity<>(json.writeValueAsString(personService.getPersonsList()), HttpStatus.FOUND);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "/findAllBytGroupCode/{groupCode}", produces = "application/json")
    @Procedure(MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> findAllBytGroupCode(@PathVariable String groupCode) {
        try {
            return new ResponseEntity<>(json.writeValueAsString(personService.getPersonsListByGroupCode(groupCode)), HttpStatus.FOUND);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "/findAllBytGroupName/{groupName}", produces = "application/json")
    @Procedure(MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> findAllBytGroupName(@PathVariable String groupName) {
        try {
            return new ResponseEntity<>(json.writeValueAsString(personService.getPersonsListByGroupName(groupName)), HttpStatus.FOUND);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}