package devops.kindergarten.server.repository;

import devops.kindergarten.server.domain.Dictionary;
import devops.kindergarten.server.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepository extends JpaRepository<Post,Long> {

    @Query(value = "SELECT * from Post p WHERE p.category = :category ORDER BY p.post_id desc LIMIT 10 OFFSET :offset", nativeQuery = true)
    List<Post> findAllByCategoryCustomQuery(@Param("category") String category, @Param("offset") int offset);

    @Query(value = "SELECT * from Post p ORDER BY p.post_id desc LIMIT 10 OFFSET :offset", nativeQuery = true)
    List<Post> findAllByCustomQuery(@Param("offset") int offset);

    @Query(value = "SELECT p from Post p WHERE p.title LIKE %:tc% OR p.content LIKE %:tc%")
    List<Post> searchByTitleOrContent(@Param("tc") String tc);
}
