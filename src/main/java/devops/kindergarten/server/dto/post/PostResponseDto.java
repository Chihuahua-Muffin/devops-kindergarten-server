package devops.kindergarten.server.dto.post;

import devops.kindergarten.server.domain.Post;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PostResponseDto {
    private Long id;
    private String title;
    private String content;
    private String username;
    private String category;
    private int like;
    private int hit;
    private String createdDate;
    private String updatedDate;

    public PostResponseDto(Post entity) {
        this.id = entity.getId();
        this.title =entity.getTitle();
        this.content = entity.getContent();
        this.username = entity.getUsername();
        this.category = entity.getCategory();
        this.like = entity.getLike();
        this.hit = entity.getHit();
        this.createdDate = entity.getCreatedDate().toString();
        this.updatedDate = entity.getUpdatedDate().toString();
    }
}
