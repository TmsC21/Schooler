package sk.myProject.school.service;

import sk.myProject.school.dto.GroupDTO;
import sk.myProject.school.model.GroupBean;

import java.util.List;

public interface GroupService {
    GroupBean getGroupById(Long id) throws Exception;

    GroupBean getGroupByName(String groupName) throws Exception;
    GroupDTO getGroupDTOById(Long id);
    GroupDTO createGroup(String groupName);

    List<GroupDTO> getAllGroupDTO();
}
