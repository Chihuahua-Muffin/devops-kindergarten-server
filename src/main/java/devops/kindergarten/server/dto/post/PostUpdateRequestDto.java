package devops.kindergarten.server.dto.post;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostUpdateRequestDto {
    private String title;
    private String content;
    private String category;

    @Builder
    public PostUpdateRequestDto(String title,String content,String category){
        this.title = title;
        this.content = content;
        this.category = category;
    }
}
