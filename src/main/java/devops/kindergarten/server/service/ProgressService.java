package devops.kindergarten.server.service;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import devops.kindergarten.server.domain.Progress;
import devops.kindergarten.server.domain.User;
import devops.kindergarten.server.dto.progress.ProgressRequestDto;
import devops.kindergarten.server.dto.progress.ProgressResponseDto;
import devops.kindergarten.server.exception.custom.UserNotFoundException;
import devops.kindergarten.server.repository.ProgressRepository;
import devops.kindergarten.server.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProgressService {
	private final UserRepository userRepository;
	private final ProgressRepository progressRepository;

	@Transactional
	public ProgressResponseDto updateLectureProgress(Long userId, ProgressRequestDto progressRequestDto) {
		User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
		Optional<Progress> optionalProgress = progressRepository.findByUserAndLectureId(user,
			progressRequestDto.getLectureId());
		Progress progress;
		if (optionalProgress.isEmpty()) {
			progress = Progress.createProgress(user, progressRequestDto.getLectureId(),
				progressRequestDto.getProgressRate(), progressRequestDto.getCount());
			progressRepository.save(progress);
		} else {
			progress = optionalProgress.get();
			progress.setProgressRate(progressRequestDto.getProgressRate());
			progress.setCount(progressRequestDto.getCount());
		}
		return new ProgressResponseDto(progress);
	}

	@Transactional(readOnly = true)
	public Map<Long, ProgressResponseDto> getLectureProgresses(Long userId) {
		User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
		return progressRepository
			.findAllByUser(user)
			.stream()
			.collect(Collectors.toMap(Progress::getLectureId, ProgressResponseDto::new));
	}
}
