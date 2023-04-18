package com.myProject.school.repository;

import com.myProject.school.model.GroupBean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupRepository extends JpaRepository<GroupBean,Long> {
}
