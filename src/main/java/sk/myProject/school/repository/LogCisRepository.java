package sk.myProject.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sk.myProject.school.model.LogTypeCis;
import sk.myProject.school.model.LogTypeCisBean;

public interface LogCisRepository extends JpaRepository<LogTypeCisBean,Long> {
    LogTypeCisBean findLogTypeCisBeanByLogTypeCis(LogTypeCis logTypeCis);
}
