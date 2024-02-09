package sk.myProject.school.service;

import sk.myProject.school.dto.PersonDTO;
import sk.myProject.school.mappers.GroupMapper;
import sk.myProject.school.mappers.PersonMapper;
import sk.myProject.school.model.PersonBean;
import sk.myProject.school.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class PersonServiceImp implements PersonService{

    @Autowired
    private PersonRepository personRepository;

    @Override
    public PersonDTO createPerson(PersonBean personBean) {
        return PersonMapper.INSTANCE.personToPersonDTO(personRepository.save(personBean));
    }

    @Override
    public PersonDTO getPersonDTOById(Long id) {
        PersonBean personBean = personRepository.findById(id).orElse(null);
        if(personBean == null){
            return null;
        }
        return PersonMapper.INSTANCE.personToPersonDTO(personBean);
    }

    @Override
    public PersonBean getPersonById(Long id) {
        return personRepository.findById(id).orElse(null);
    }

    @Override
    public PersonBean getPersonByUUid(String uuid) {
        return personRepository.findPersonBeanByUuid(uuid);
    }

    @Override
    public PersonDTO getPersonDTOByUUid(String uuid) {
        return PersonMapper.INSTANCE.personToPersonDTO(personRepository.findPersonBeanByUuid(uuid));
    }

    @Override
    public List<PersonDTO> getPersonsList() {
        List<PersonBean> personBeanList = personRepository.findAll();
        return personBeanList.stream().map(PersonMapper.INSTANCE::personToPersonDTO).collect(Collectors.toList());
    }

    @Override
    public List<PersonDTO> getPersonsListByGroupCode(String groupCode) {
        List<PersonBean> personBeanList = personRepository.findAllByGroupBean_GroupCode(groupCode);
        return personBeanList.stream().map(PersonMapper.INSTANCE::personToPersonDTO).collect(Collectors.toList());
    }

    @Override
    public List<PersonDTO> getPersonsListByGroupName(String groupName) {
        List<PersonBean> personBeanList = personRepository.findAllByGroupBean_GroupName(groupName);
        return personBeanList.stream().map(PersonMapper.INSTANCE::personToPersonDTO).collect(Collectors.toList());
    }

    @Override
    public PersonBean getPersonByUserName(String userName) {
        return personRepository.findPersonBeanByUsername(userName);
    }

    @Override
    public PersonBean getPersonByEmail(String email) {
        return personRepository.findPersonBeanByEmail(email);
    }

    @Override
    public PersonBean savePerson(PersonBean personBean) {
        return personRepository.save(personBean);
    }
}
