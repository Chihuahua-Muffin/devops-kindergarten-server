package devops.kindergarten.server.repository;

import devops.kindergarten.server.domain.Comment;
import devops.kindergarten.server.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment,Long> {
    List<Comment> findAllByPostId(Long postId);

    List<Comment> findAllByWriterId(Long userId);
}