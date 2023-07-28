package sk.myProject.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sk.myProject.school.model.PersonCis;
import sk.myProject.school.model.PersonCisBean;

public interface PersonCisRepository extends JpaRepository<PersonCisBean,Long> {
    PersonCisBean findByPersonCis(PersonCis personCis);
}
