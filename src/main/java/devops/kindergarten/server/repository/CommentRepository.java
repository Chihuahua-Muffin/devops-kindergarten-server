package devops.kindergarten.server.repository;

import devops.kindergarten.server.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment,Long> {

}
