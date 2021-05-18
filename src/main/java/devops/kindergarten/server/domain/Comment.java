package devops.kindergarten.server.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Comment {
    @Id
    @GeneratedValue
    private Long idx;

    private String content;

    private String writer;

    private int like;

    @ElementCollection
    private List<Integer> commentList = new ArrayList<>();

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

}
