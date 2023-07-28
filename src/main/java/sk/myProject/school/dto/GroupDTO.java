package sk.myProject.school.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import sk.myProject.school.model.GroupBean;
@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class GroupDTO {

    private Long id;
    private String groupName;


    public GroupDTO(GroupBean groupBean) {
        this.id = groupBean.getId();
        this.groupName = groupBean.getGroupName();
    }
}
