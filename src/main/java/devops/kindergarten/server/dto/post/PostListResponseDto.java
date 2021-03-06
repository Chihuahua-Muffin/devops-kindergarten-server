package devops.kindergarten.server.dto.post;

import devops.kindergarten.server.domain.Post;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PostListResponseDto {
	private Long id;
	private String title;
	private String username;
	private String category;
	private LocalDateTime createDate;

	public PostListResponseDto(Post entity) {
		this.id = entity.getId();
		this.title = entity.getTitle();
		this.username = entity.getUsername();
		this.category = entity.getCategory();
		this.createDate = entity.getCreatedDate();
	}
}
