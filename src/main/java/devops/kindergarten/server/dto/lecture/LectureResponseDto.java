package devops.kindergarten.server.dto.lecture;

import devops.kindergarten.server.domain.ImageFile;
import devops.kindergarten.server.domain.Lecture;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class LectureResponseDto {
    private Long id;
    private String title;
    private ImageFile thumbnail;
    private String content;

    public LectureResponseDto(Lecture entity,ImageFile image) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.content = entity.getContent();
        this.thumbnail = image;
    }
}
