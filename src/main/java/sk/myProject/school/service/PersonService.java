package sk.myProject.school.service;

import sk.myProject.school.dto.PersonDTO;
import sk.myProject.school.model.PersonBean;

import java.util.List;

public interface PersonService {
    PersonDTO createPerson(PersonBean personBean);

    PersonDTO getPersonDTOById(Long id);
    PersonBean getPersonById(Long id);

    List<PersonDTO> getPersonsList();

    List<PersonDTO> getPersonsListByGroupCode(String groupCode);
    List<PersonDTO> getPersonsListByGroupName(String groupName);

    PersonBean getPersonByUserName(String userName);

    PersonBean getPersonByEmail(String email);

    PersonBean savePerson(PersonBean personBean);
}
