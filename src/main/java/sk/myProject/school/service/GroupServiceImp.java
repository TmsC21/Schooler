package sk.myProject.school.service;

import sk.myProject.school.dto.GroupDTO;
import sk.myProject.school.mappers.GroupMapper;
import sk.myProject.school.model.GroupBean;
import sk.myProject.school.model.MyUtils;
import sk.myProject.school.repository.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GroupServiceImp implements GroupService{

    @Autowired
    GroupRepository groupRepository;

    @Override
    public GroupBean getGroupById(Long id) throws Exception {
        return groupRepository.findById(id).orElseThrow(() -> new Exception("Group not found"));
    }

    @Override
    public GroupDTO getGroupDTOById(Long id) {
        GroupBean groupBean = groupRepository.findById(id).orElse(null);
        if(groupBean == null){
            return null;
        }
        return GroupMapper.INSTANCE.groupToGroupDTO(groupBean);
    }

    @Override
    public GroupBean getGroupByName(String groupName) throws Exception {
        return groupRepository.findGroupBeanByGroupName(groupName).orElseThrow(() -> new Exception("Group not found"));
    }

    @Override
    public GroupDTO createGroup(String groupName) {
        return GroupMapper.INSTANCE.groupToGroupDTO(groupRepository.save(new GroupBean(groupName, MyUtils.generateRandomString(5))));
    }

    @Override
    public List<GroupDTO> getAllGroupDTO() {
        return groupRepository.findAll().stream().map(GroupMapper.INSTANCE::groupToGroupDTO).collect(Collectors.toList());
    }
}
