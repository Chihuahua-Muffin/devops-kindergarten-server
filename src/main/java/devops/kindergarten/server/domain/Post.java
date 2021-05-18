package devops.kindergarten.server.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@Getter @Setter
public class Post {

    @Id
    @GeneratedValue
    private Long idx;

    private String title;

    private String content;

    private String author;

    private int like;

    private LocalDateTime createAt;

    private LocalDateTime updatedAt;

}
