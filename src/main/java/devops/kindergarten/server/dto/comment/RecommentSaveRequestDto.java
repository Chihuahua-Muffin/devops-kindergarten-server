package devops.kindergarten.server.dto.comment;

import devops.kindergarten.server.domain.Comment;
import devops.kindergarten.server.domain.Post;
import devops.kindergarten.server.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RecommentSaveRequestDto {
    private String content;
    private String username;
    private Long postId;
    private Long parentId;

    @Builder
    public RecommentSaveRequestDto(String content, String username, Long postId, Long parentId){
        this.content = content;
        this.username = username;
        this.postId = postId;
        this.parentId = parentId;
    }

    public Comment toEntity(User writer,Post post,Comment parent){
        return Comment.createChildComment(writer,post,parent,content,username);
    }
}
