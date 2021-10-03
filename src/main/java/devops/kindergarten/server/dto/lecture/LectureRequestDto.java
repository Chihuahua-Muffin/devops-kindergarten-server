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
	private String content;

	@Builder
	public LectureRequestDto(String title, MultipartFile thumbnail, String content) {
		this.title = title;
		this.thumbnail = thumbnail;
		this.content = content;
	}

	public Lecture toLectureEntity() {
		return Lecture.builder()
			.title(title)
			.content(content)
			.build();
	}
}
