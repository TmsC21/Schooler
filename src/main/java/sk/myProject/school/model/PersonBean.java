package sk.myProject.school.model;

import sk.myProject.school.request.PersonRequest;
import lombok.*;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Setter
@Getter
@RequiredArgsConstructor
@AllArgsConstructor
public class PersonBean {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "uuid")
    private String uuid;

    @Column(name = "name")
    private String name;
    @Column(name = "surname")
    private String surname;
    @Column(name = "email",unique = true)
    private String email;
    @Column(name = "password")
    private String password;

    @Column(name = "username",unique = true)
    private String username;

    @ManyToOne
    @JoinColumn(name="group_id")
    private GroupBean groupBean;

    @ManyToOne
    @JoinColumn(name="person_cis_id")
    private PersonCisBean personCisBean;

    public PersonBean(PersonRequest personRequest){
        this.uuid = UUID.randomUUID().toString();
        this.name = personRequest.getName();
        this.surname = personRequest.getSurname();
        this.email = personRequest.getEmail();
        this.password = personRequest.getPassword();
        this.username = personRequest.getUserName();
    }

}
