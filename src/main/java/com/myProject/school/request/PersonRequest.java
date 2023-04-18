package com.myProject.school.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class PersonRequest {
    private String name;
    private String surname;
    private String email;
    private String password;
    private Long groupId;
}
