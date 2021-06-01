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
    private String description;
    private List<String> tagList;

    public LectureResponseDto(Lecture entity,ImageFile image) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.description = entity.getDescription();
        this.tagList = entity.getTagList();
        this.thumbnail = image;
    }
}
