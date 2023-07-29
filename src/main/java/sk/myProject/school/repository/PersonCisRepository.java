package sk.myProject.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sk.myProject.school.model.PersonCis;
import sk.myProject.school.model.PersonCisBean;
@Repository
public interface PersonCisRepository extends JpaRepository<PersonCisBean,Long> {
    PersonCisBean findPersonCisBeanByPersonCis(PersonCis personCis);
}
