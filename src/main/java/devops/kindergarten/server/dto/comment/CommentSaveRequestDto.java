package devops.kindergarten.server.dto.comment;

import devops.kindergarten.server.domain.Comment;
import devops.kindergarten.server.domain.Post;
import devops.kindergarten.server.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentSaveRequestDto {
    private String content;
    private String username;
    private Long postId;

    @Builder
    public CommentSaveRequestDto(String content, String username, Long postId){
        this.content = content;
        this.username = username;
        this.postId = postId;
    }

    public Comment toEntity(User writer,Post post){
        return Comment.createComment(writer,post,content,username);
    }

}

