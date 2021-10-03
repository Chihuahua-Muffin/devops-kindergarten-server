package devops.kindergarten.server.dto.post;

import devops.kindergarten.server.domain.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class PostResponseDto {
	private Long id;
	private String title;
	private String content;
	private String username;
	private String category;
	private int hit;
	private LocalDateTime createdDate;
	private LocalDateTime updatedDate;

	public PostResponseDto(Post entity) {
		this.id = entity.getId();
		this.title = entity.getTitle();
		this.content = entity.getContent();
		this.username = entity.getUsername();
		this.category = entity.getCategory();
		this.hit = entity.getHit();
		this.createdDate = entity.getCreatedDate();
		this.updatedDate = entity.getUpdatedDate();
	}
}
