package sk.myProject.school.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import sk.myProject.school.repository.LogCisRepository;
import sk.myProject.school.repository.LogRepository;

import javax.persistence.*;
import java.util.Date;

@Entity
@Setter
@Getter
@RequiredArgsConstructor
@AllArgsConstructor
public class LogBean {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="person_id")
    private PersonBean personBean;

    @ManyToOne
    @JoinColumn(name="log_type_id")
    private LogTypeCisBean logTypeCis;

    @Column(name = "create_date")
    private Date createDate;

    public LogBean(PersonBean personBean, LogTypeCis logTypeCis, Date createDate, LogCisRepository logCisRepository) {
        this.personBean = personBean;
        this.logTypeCis = logCisRepository.findLogTypeCisBeanByLogTypeCis(logTypeCis);
        this.createDate = createDate;
    }

    public void log(LogRepository logRepository){
        try {
            logRepository.save(this);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
