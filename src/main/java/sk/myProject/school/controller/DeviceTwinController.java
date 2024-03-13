package sk.myProject.school.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sk.myProject.school.model.PersonBean;
import sk.myProject.school.service.DeviceTwinService;
import sk.myProject.school.service.PersonServiceImp;

import java.util.Map;

@RestController
@RequestMapping("/azure")
public class DeviceTwinController {
    @Autowired
    private PersonServiceImp personService;

    @PostMapping(value = "/getTwinData", produces = "application/json")
    @Procedure(MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getTwinData(@RequestParam("uuid") String uuid,
                                              @RequestBody Map<String, String> requestData) {
        String azureConnString = requestData.get("azureConnString");
        String azureDeviceId = requestData.get("azureDeviceId");
        try {
            PersonBean personBean = personService.getPersonByUUid(uuid);
            boolean savePerson = false;
            if(personBean.getAzureConnString() == null || !personBean.getAzureConnString().equals(azureConnString)){
                personBean.setAzureConnString(azureConnString);
                savePerson = true;
            }
            if(personBean.getAzureDeviceId() == null || !personBean.getAzureDeviceId().equals(azureDeviceId)){
                personBean.setAzureDeviceId(azureDeviceId);
                savePerson = true;
            }
            if(savePerson){
                personService.savePerson(personBean);
            }
            DeviceTwinService deviceTwinService = new DeviceTwinService(personBean.getAzureConnString(), azureDeviceId);
            return new ResponseEntity<>(deviceTwinService.getData(), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
        }
    }
}
