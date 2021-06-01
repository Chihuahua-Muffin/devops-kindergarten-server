package devops.kindergarten.server.repository;

import devops.kindergarten.server.domain.Dictionary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DictionaryRepository extends JpaRepository<Dictionary,Long> {
    @Query(value = "SELECT * from Dictionary d ORDER BY d.dictionary_id desc LIMIT 10 OFFSET :offset", nativeQuery = true)
    List<Dictionary> findAllByCustomQuery(@Param("offset") int offset);
}
