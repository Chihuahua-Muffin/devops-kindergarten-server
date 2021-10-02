package devops.kindergarten.server.dto.comment;

import devops.kindergarten.server.domain.Comment;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
public class CommentResponseDto {
    private Long id;
    private String content;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
    private List<CommentResponseDto> recommentList = new ArrayList<>();

    public CommentResponseDto(Comment entity) {
       this.id = entity.getId();
       this.content = entity.getContent();
       this.createdDate = entity.getCreatedDate();
       this.updatedDate = entity.getUpdatedDate();
   }
}
