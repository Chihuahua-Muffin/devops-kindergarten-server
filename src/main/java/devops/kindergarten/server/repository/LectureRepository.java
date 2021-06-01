package devops.kindergarten.server.repository;

import devops.kindergarten.server.domain.Lecture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LectureRepository extends JpaRepository<Lecture, Long> {
    @Query(value = "SELECT * FROM Lecture l ORDER BY l.lecture_id DESC LIMIT 10 OFFSET :offset", nativeQuery = true)
    List<Lecture> findAllByCustomQuery(int offset);
}
