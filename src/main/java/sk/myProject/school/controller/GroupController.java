package sk.myProject.school.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sk.myProject.school.service.GroupService;

@RestController
@RequestMapping("/group")
public class GroupController {
    @Autowired
    private GroupService groupService;

    protected final ObjectMapper json = new ObjectMapper();

    @PostMapping(value = "/create", produces = "application/json")
    @Procedure(MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> createGroup(@RequestParam("groupName") String groupName) {
        try {
            return new ResponseEntity<>(json.writeValueAsString(groupService.createGroup(groupName)), HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
        }
    }

    @GetMapping(value = "/findByGroupName/{groupName}", produces = "application/json")
    @Procedure(MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> findByGroupName(@PathVariable String groupName) {
        try {
            return new ResponseEntity<>(json.writeValueAsString(groupService.getGroupByName(groupName)), HttpStatus.FOUND);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "/findAll", produces = "application/json")
    @Procedure(MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> findAll() {
        try {
            return new ResponseEntity<>(json.writeValueAsString(groupService.getAllGroupDTO()), HttpStatus.FOUND);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
