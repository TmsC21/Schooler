package sk.myProject.school.repository;

import sk.myProject.school.model.GroupBean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GroupRepository extends JpaRepository<GroupBean,Long> {

    Optional<GroupBean> findGroupBeanByGroupName(String groupName);

}
