package devops.kindergarten.server.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "comment")
@NoArgsConstructor
@Getter
public class Comment {
    @Id
    @Column(name = "comment_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "comment",nullable = false,columnDefinition = "TEXT")
    private String comment;

    @Column(nullable = false)
    private String writer;

    @Column(name="like_count")
    private int like;

    private int depth;

    @Column(nullable = false)
    private Long postId;

    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
}
