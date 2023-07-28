package sk.myProject.school.model;

import sk.myProject.school.request.PersonRequest;
import lombok.*;

import javax.persistence.*;

@Entity
@Setter
@Getter
@RequiredArgsConstructor
@AllArgsConstructor
public class PersonBean {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;
    @Column(name = "surname")
    private String surname;
    @Column(name = "email")
    private String email;
    @Column(name = "password")
    private String password;

    @Column(name = "username")
    private String username;

    @ManyToOne
    @JoinColumn(name="group_id", nullable=false)
    private GroupBean groupBean;

    public PersonBean(PersonRequest personRequest){
        this.name = personRequest.getName();
        this.surname = personRequest.getSurname();
        this.email = personRequest.getEmail();
        this.password = personRequest.getPassword();
        this.username = personRequest.getUserName();
    }

}
