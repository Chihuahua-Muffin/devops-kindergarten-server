package devops.kindergarten.server.dto.lecture;

import devops.kindergarten.server.domain.ImageFile;
import devops.kindergarten.server.domain.Lecture;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Getter
@NoArgsConstructor
public class LectureRequestDto {
    private String title;
    private MultipartFile thumbnail;
    private String description;
    private List<String> tagList;

    @Builder
    public LectureRequestDto(String title, MultipartFile thumbnail, String description, List<String> tagList) {
        this.title = title;
        this.thumbnail = thumbnail;
        this.description = description;
        this.tagList = tagList;
    }

    public Lecture toLectureEntity() {
        return Lecture.builder()
                .title(title)
                .description(description)
                .tagList(tagList)
                .build();
    }
}
