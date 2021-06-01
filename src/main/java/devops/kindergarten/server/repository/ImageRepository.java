package devops.kindergarten.server.repository;

import devops.kindergarten.server.domain.ImageFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<ImageFile,Long> {

}
