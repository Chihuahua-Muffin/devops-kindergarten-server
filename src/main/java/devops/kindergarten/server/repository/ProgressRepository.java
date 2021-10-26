package devops.kindergarten.server.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import devops.kindergarten.server.domain.Progress;
import devops.kindergarten.server.domain.User;

public interface ProgressRepository extends JpaRepository<Progress, Long> {
	List<Progress> findAllByUser(User user);

	Optional<Progress> findByUserAndLectureId(User user, Long lectureId);
}
