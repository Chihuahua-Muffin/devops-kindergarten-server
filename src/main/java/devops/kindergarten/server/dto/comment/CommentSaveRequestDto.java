package devops.kindergarten.server.dto.comment;

import devops.kindergarten.server.domain.Comment;
import devops.kindergarten.server.domain.Lecture;
import devops.kindergarten.server.domain.Post;
import devops.kindergarten.server.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentSaveRequestDto {
	private String content;
	private Long userId;
	private Long pageId;
	private Long parentId;
	private String pageName;
}

