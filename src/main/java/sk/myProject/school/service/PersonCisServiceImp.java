package sk.myProject.school.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sk.myProject.school.model.PersonCis;
import sk.myProject.school.model.PersonCisBean;
import sk.myProject.school.repository.PersonCisRepository;
@Service
public class PersonCisServiceImp implements PersonCisService{

    @Autowired
    PersonCisRepository personCisRepository;
    @Override
    public PersonCisBean getPersonCis(String personCis) {
        return personCisRepository.findPersonCisBeanByPersonCis(PersonCis.valueOf(personCis));
    }
}
