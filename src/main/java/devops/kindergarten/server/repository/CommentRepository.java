package devops.kindergarten.server.repository;

import devops.kindergarten.server.domain.TheoryPageName;
import devops.kindergarten.server.domain.Comment;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
	List<Comment> findAllByLectureId(Long pageId);

	List<Comment> findAllByTheoryPage(TheoryPageName pageName);

	List<Comment> findAllByUsername(String username);
}
