package me.lkimilhol.inflearnthejavatest.study;

import me.lkimilhol.inflearnthejavatest.domain.Study;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudyRepository extends JpaRepository<Study, Long> {
}
