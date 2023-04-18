package com.myProject.school.model;


import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Setter
@Getter
@RequiredArgsConstructor
@AllArgsConstructor
public class GroupBean {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "group_name")
    private String groupName;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "groubBean")
    private List<PersonBean> personBeanList;

}
