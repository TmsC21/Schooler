package sk.myProject.school.dto;

import lombok.Getter;
import sk.myProject.school.mappers.GroupMapper;
import sk.myProject.school.model.PersonBean;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class PersonDTO {

    private Long id;

    private String name;

    private String surname;

    private String email;

    @Getter
    private GroupDTO group;

    public PersonDTO(PersonBean personBean){
        this.id = personBean.getId();
        this.name = personBean.getName();
        this.surname = personBean.getSurname();
        this.email = personBean.getEmail();
        this.group = GroupMapper.INSTANCE.groupToGroupDTO(personBean.getGroupBean());
    }

    public void setGroup(GroupDTO group) {
        this.group = group;
    }
}
