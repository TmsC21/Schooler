package sk.myProject.school.model;


import lombok.*;

import javax.persistence.*;

@Entity
@Setter
@Getter
@RequiredArgsConstructor
@AllArgsConstructor
public class GroupBean {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "group_code")
    private String groupCode;

    @Column(name = "group_name")
    private String groupName;

    public GroupBean(String groupName, String groupCode) {
        this.groupCode = groupCode;
        this.groupName = groupName;
    }
}
