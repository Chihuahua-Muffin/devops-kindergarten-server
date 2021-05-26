package devops.kindergarten.server.dto.comment;

import devops.kindergarten.server.domain.Comment;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
public class CommentResponseDto {
    private Long id;
    private String content;
    private int like;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;

    public CommentResponseDto(Comment entity) {
        this.id = entity.getId();
        this.content = entity.getContent();
        this.like = entity.getLike();
        this.createdDate = entity.getCreatedDate();
        this.updatedDate = entity.getUpdatedDate();
    }
}
