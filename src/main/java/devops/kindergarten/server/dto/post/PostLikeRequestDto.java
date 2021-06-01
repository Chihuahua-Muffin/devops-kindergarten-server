package devops.kindergarten.server.dto.post;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostLikeRequestDto {
    private String username;
    private Long postId;

    @Builder
    public PostLikeRequestDto(String username, Long postId) {
        this.username = username;
        this.postId = postId;
    }
}
