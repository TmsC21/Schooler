package com.myProject.school.service;

import com.myProject.school.model.PersonBean;

public interface PersonService {
    public PersonBean createPerson(PersonBean personBean);
    public PersonBean getPersonById(Long id);
}
