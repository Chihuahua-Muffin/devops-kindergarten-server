package devops.kindergarten.server.dto.lecture;

import devops.kindergarten.server.domain.Lecture;
import lombok.Getter;
import lombok.NoArgsConstructor;

import org.springframework.web.multipart.MultipartFile;

@Getter
@NoArgsConstructor
public class LectureRequestDto {
	private MultipartFile image;
	private String title;
	private String content;

	public LectureRequestDto(MultipartFile image, String title, String content) {
		this.title = title;
		this.image = image;
		this.content = content;
	}

	public Lecture toLectureEntity() {
		return Lecture.builder()
			.title(title)
			.content(content)
			.build();
	}
}
