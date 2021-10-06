package devops.kindergarten.server.dto.comment;

import devops.kindergarten.server.domain.Comment;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class CommentResponseDto {
	private Long commentId;
	private String content;
	private String username;
	private String createdDate;
	private String updatedDate;
	private List<CommentResponseDto> recommentList;

	public CommentResponseDto(Comment entity) {
		this.commentId = entity.getId();
		this.content = entity.getContent();
		this.username = entity.getUsername();
		this.createdDate = entity.getCreatedDate().toString();
		this.updatedDate = entity.getUpdatedDate().toString();
		this.recommentList = entity.getChildren().stream().map(CommentResponseDto::new).collect(Collectors.toList());
	}
}
