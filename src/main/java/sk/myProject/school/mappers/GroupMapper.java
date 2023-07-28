package sk.myProject.school.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import sk.myProject.school.dto.GroupDTO;
import sk.myProject.school.model.GroupBean;

@Mapper
public interface GroupMapper {
    GroupMapper INSTANCE = Mappers.getMapper(GroupMapper.class);

    GroupDTO groupToGroupDTO(GroupBean groupBean);
}
