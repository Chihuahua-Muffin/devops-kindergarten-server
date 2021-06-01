package devops.kindergarten.server.dto.lecture;

import devops.kindergarten.server.domain.Lecture;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class LectureRequestDto {
    private String title;
    private String thumbnail;
    private String description;
    private List<String> tagList;

    @Builder
    public LectureRequestDto(String title, String thumbnail, String description, List<String> tagList) {
        this.title = title;
        this.thumbnail = thumbnail;
        this.description = description;
        this.tagList = tagList;
    }

    public Lecture toEntity() {
        return Lecture.builder()
                .title(title)
                .thumbnail(thumbnail)
                .description(description)
                .tagList(tagList)
                .build();
    }
}
