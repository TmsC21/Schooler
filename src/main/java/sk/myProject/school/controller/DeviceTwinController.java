package sk.myProject.school.controller;

import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sk.myProject.school.service.DeviceTwinService;
@RestController
@RequestMapping("/azure")
public class DeviceTwinController {

    @GetMapping(value = "/getTwinData", produces = "application/json")
    @Procedure(MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getTwinData() {
        try {
            DeviceTwinService deviceTwinService = new DeviceTwinService();
            return new ResponseEntity<>(deviceTwinService.getData(), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
        }
    }
}
