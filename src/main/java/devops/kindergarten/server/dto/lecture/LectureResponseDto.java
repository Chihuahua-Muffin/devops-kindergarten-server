package devops.kindergarten.server.dto.lecture;

import devops.kindergarten.server.domain.Lecture;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class LectureResponseDto {
    private Long id;
    private String title;
    private String thumbnail;
    private String description;
    private List<String> tagList;

    public LectureResponseDto(Lecture entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.thumbnail = getThumbnail();
        this.description = getDescription();
        this.tagList = getTagList();
    }
}
