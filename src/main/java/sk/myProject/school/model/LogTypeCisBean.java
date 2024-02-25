package sk.myProject.school.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
@Entity
@Setter
@Getter
@RequiredArgsConstructor
@AllArgsConstructor
public class LogTypeCisBean {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name="log_type")
    private LogTypeCis logTypeCis;

    private String description;
}
