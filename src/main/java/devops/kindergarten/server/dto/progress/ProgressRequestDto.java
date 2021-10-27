package devops.kindergarten.server.dto.progress;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProgressRequestDto {
	private Long lectureId;
	private int progressRate;
	private int count;

	public ProgressRequestDto(Long lectureId, int progressRate, int count) {
		this.lectureId = lectureId;
		this.progressRate = progressRate;
		this.count = count;
	}
}
