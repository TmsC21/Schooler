package sk.myProject.school.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import sk.myProject.school.dto.GroupDTO;
import sk.myProject.school.dto.PersonDTO;
import sk.myProject.school.model.GroupBean;
import sk.myProject.school.model.PersonBean;
import sk.myProject.school.model.PersonCisBean;

@Mapper
public interface PersonMapper {
    PersonMapper INSTANCE = Mappers.getMapper( PersonMapper.class );

    @Mapping(target = "group",source = "groupBean")
    @Mapping(target = "role",source = "personCisBean")
    PersonDTO personToPersonDTO(PersonBean person);
    default GroupDTO getGroupDTOById(GroupBean groupBean) {
        return GroupMapper.INSTANCE.groupToGroupDTO(groupBean);
    }
}
