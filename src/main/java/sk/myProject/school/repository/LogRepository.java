package sk.myProject.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sk.myProject.school.model.GroupBean;
import sk.myProject.school.model.LogBean;
import sk.myProject.school.model.PersonBean;

import java.util.List;

public interface LogRepository extends JpaRepository<LogBean,Long> {
    List<LogBean> findAllByPersonBeanOrderByCreateDateDesc(PersonBean personBean);
}
