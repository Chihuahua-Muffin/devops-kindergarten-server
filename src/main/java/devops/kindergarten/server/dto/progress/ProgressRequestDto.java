package devops.kindergarten.server.dto.progress;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProgressRequestDto {
	private Long lectureId;
	private int progressRate;

	public ProgressRequestDto(Long lectureId, int progressRate) {
		this.lectureId = lectureId;
		this.progressRate = progressRate;
	}
}
