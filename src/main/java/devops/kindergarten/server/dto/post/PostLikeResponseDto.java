package devops.kindergarten.server.dto.post;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostLikeResponseDto {
    private int likeCount;
    private boolean viewerHasLike;

    public PostLikeResponseDto(boolean viewerHasLike, int likeCount){
        this.likeCount = likeCount;
        this.viewerHasLike = viewerHasLike;
    }
}
