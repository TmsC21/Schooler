package sk.myProject.school.service;

import sk.myProject.school.dto.PersonDTO;
import sk.myProject.school.model.PersonBean;

import java.util.List;

public interface PersonService {
    PersonDTO createPerson(PersonBean personBean);

    PersonDTO getPersonById(Long id);

    List<PersonDTO> getPersonsList();

    List<PersonDTO> getPersonsListByGroupCode(String groupCode);
    List<PersonDTO> getPersonsListByGroupName(String groupName);
}
