package sk.myProject.school.dto;

import lombok.*;
import sk.myProject.school.mappers.GroupMapper;
import sk.myProject.school.mappers.PersonMapper;
import sk.myProject.school.model.PersonBean;
import sk.myProject.school.model.PersonCis;
import sk.myProject.school.model.PersonCisBean;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class PersonDTO {

    private String uuid;

    private String name;

    private String surname;

    private PersonCisBean role;

    private String email;

    @Setter
    @Getter
    private GroupDTO group;

    public PersonDTO(PersonBean personBean){
        this.uuid = personBean.getUuid();
        this.name = personBean.getName();
        this.surname = personBean.getSurname();
        this.email = personBean.getEmail();
        this.group = GroupMapper.INSTANCE.groupToGroupDTO(personBean.getGroupBean());
        this.role = personBean.getPersonCisBean();
    }

}
