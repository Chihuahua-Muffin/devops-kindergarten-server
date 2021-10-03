package devops.kindergarten.server.dto.post;

import devops.kindergarten.server.domain.Post;
import devops.kindergarten.server.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostSaveRequestDto {
	private String title;
	private String content;
	private String category;
	private String username;

	@Builder
	public PostSaveRequestDto(String title, String content, String username, String category) {
		this.title = title;
		this.content = content;
		this.username = username;
		this.category = category;
	}

	public Post toEntity(User author) {
		return Post.createPost(author, title, content, username, category);
	}
}
