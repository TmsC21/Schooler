package sk.myProject.school.repository;

import sk.myProject.school.dto.PersonDTO;
import sk.myProject.school.model.PersonBean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonRepository extends JpaRepository<PersonBean,Long> {
    List<PersonBean> findAllByGroupBean_GroupCode(String groupCode);
    List<PersonBean> findAllByGroupBean_GroupName(String groupName);
}
