package com.myProject.school.service;

import com.myProject.school.model.GroupBean;
import com.myProject.school.repository.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GroupServiceImp implements GroupService{

    @Autowired
    GroupRepository groupRepository;

    @Override
    public GroupBean getGroupById(Long id) {
        return groupRepository.getReferenceById(id);
    }
}
