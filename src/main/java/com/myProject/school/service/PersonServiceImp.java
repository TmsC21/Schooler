package com.myProject.school.service;

import com.myProject.school.model.PersonBean;
import com.myProject.school.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

@Service
public class PersonServiceImp implements PersonService{

    @Autowired
    private PersonRepository personRepository;


    public void validateEmail(String email) throws Exception{
        String regex = "^[a-zA-Z0-9_!#$%&amp;'*+/=?`{|}~^-]+(?:\\.[a-zA-Z0-9_!#$%&amp;'*+/=?`{|}~^-]+)*@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$";
        if(!Pattern.compile(regex).matcher(regex).matches()){
            throw new Exception("Email is not valid!");
       }
    }
    @Override
    public PersonBean createPerson(PersonBean personBean) {
        return personRepository.save(personBean);
    }

    @Override
    public PersonBean getPersonById(Long id) {
        return personRepository.findById(id).orElse(null);
    }

}
