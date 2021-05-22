package devops.kindergarten.server.dto.post;

import devops.kindergarten.server.domain.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostSaveRequestDto {
    private String title;
    private String content;
    private String author;
    private String category;

    @Builder
    public PostSaveRequestDto(String title, String content,String author,String category){
        this.title = title;
        this.content = content;
        this.author = author;
        this.category = category;
    }

    public Post toEntity(){
        return Post.builder()
                .title(title)
                .content(content)
                .author(author)
                .category(category)
                .build();
    }
}
