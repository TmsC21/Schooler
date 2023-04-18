package com.myProject.school.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.myProject.school.model.PasswordUtil;
import com.myProject.school.model.PersonBean;
import com.myProject.school.request.PersonRequest;
import com.myProject.school.response.PersonResponse;
import com.myProject.school.service.GroupService;
import com.myProject.school.service.PersonServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;

@RestController
@RequestMapping("/person")
public class PersonController {

    @Autowired
    private PersonServiceImp personService;
    @Autowired
    private GroupService groupService;

    protected final ObjectMapper json = new ObjectMapper();

    @PostMapping("/create")
    public ResponseEntity<String> createPerson(@RequestBody PersonRequest personRequest){
        try {
//            personService.validateEmail(personRequest.getEmail());
            personRequest.setPassword(PasswordUtil.encryption(personRequest.getPassword()));
            PersonBean newPersonBean = new PersonBean(personRequest);
            newPersonBean.setGroubBean(groupService.getGroupById(personRequest.getGroupId()));
            return new ResponseEntity<>(json.writeValueAsString(new PersonResponse(personService.createPerson(newPersonBean))),HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @GetMapping("/findById/{id}")
    @Procedure(MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> createPerson(@PathVariable Long id){
        try {
            PersonBean personBean = personService.getPersonById(id);
            personBean.setPassword(PasswordUtil.decryption(personBean.getPassword()));
            String response = json.writeValueAsString(new PersonResponse(personBean));
            return new ResponseEntity<>(response,HttpStatus.FOUND);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
