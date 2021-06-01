package devops.kindergarten.server.dto.comment;

import lombok.Getter;
import lombok.NoArgsConstructor;
@Getter
@NoArgsConstructor
public class CommentLikeResponseDto {

    boolean viewerHasLike;
    int likeCount;

    public CommentLikeResponseDto(boolean viewerHasLike,int likeCount) {
        this.likeCount = likeCount;
        this.viewerHasLike = viewerHasLike;
    }
}
