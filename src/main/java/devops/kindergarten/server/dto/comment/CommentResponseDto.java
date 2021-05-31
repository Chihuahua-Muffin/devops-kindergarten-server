package devops.kindergarten.server.dto.comment;

import devops.kindergarten.server.domain.Comment;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
public class CommentResponseDto {
    private Long id;
    private String content;
    private int like;
    private String createdDate;
    private String updatedDate;
    private List<CommentResponseDto> recommentList = new ArrayList<>();

    public CommentResponseDto(Comment entity) {
        this.id = entity.getId();
        this.content = entity.getContent();
        this.like = entity.getLike();
        this.createdDate = entity.getCreatedDate().toString();
        this.updatedDate = entity.getUpdatedDate().toString();
    }
}
