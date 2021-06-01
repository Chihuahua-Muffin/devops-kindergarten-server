package devops.kindergarten.server.dto.comment;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentLikeRequestDto {
    private Long commentId;
    private String username;

    @Builder
    public CommentLikeRequestDto(Long commentId, String username) {
        this.commentId = commentId;
        this.username = username;
    }
}
