package com.myProject.school.response;

import com.myProject.school.model.GroupBean;
import com.myProject.school.model.PersonBean;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class PersonResponse {

    private String name;

    private String surname;

    private String email;

    private String password;

    private String groupName;

    public PersonResponse(PersonBean personBean){
        this.name = personBean.getName();
        this.surname = personBean.getSurname();
        this.email = personBean.getEmail();
        this.password = personBean.getPassword();
        this.groupName = personBean.getGroubBean().getGroupName();
    }


}
