package devops.kindergarten.server.dto.progress;

import devops.kindergarten.server.domain.Progress;
import lombok.Getter;

@Getter
public class ProgressResponseDto {
	Long userId;
	Long progressId;
	Long lectureId;
	int progressRate;

	public ProgressResponseDto(Progress progress) {
		userId = progress.getUser().getId();
		progressId = progress.getId();
		lectureId = progress.getLectureId();
		progressRate = progress.getProgressRate();
	}
}
