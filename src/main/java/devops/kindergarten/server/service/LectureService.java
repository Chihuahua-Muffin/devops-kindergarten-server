package devops.kindergarten.server.service;

import devops.kindergarten.server.domain.ImageFile;
import devops.kindergarten.server.domain.Lecture;
import devops.kindergarten.server.dto.lecture.LectureRequestDto;
import devops.kindergarten.server.dto.lecture.LectureResponseDto;
import devops.kindergarten.server.exception.custom.ImageNotFoundException;
import devops.kindergarten.server.exception.custom.LectureNotFoundException;
import devops.kindergarten.server.repository.ImageRepository;
import devops.kindergarten.server.repository.LectureRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LectureService {
	private final LectureRepository lectureRepository;
	private final ImageRepository imageRepository;

	@Transactional
	public Long save(LectureRequestDto request) throws IOException {
		Lecture lecture = request.toLectureEntity();
		MultipartFile image = request.getImage();
		ImageFile imageFile = ImageFile.createImageFile(lecture, image.getOriginalFilename(), image.getContentType(),
			image.getBytes());
		imageRepository.save(imageFile);
		return lectureRepository.save(lecture).getId();
	}

	@Transactional
	public Long update(Long id, LectureRequestDto request) throws IOException {
		Lecture lecture = lectureRepository.findById(id)
			.orElseThrow(() -> new LectureNotFoundException("해당 강의가 없습니다. id=" + id));
		ImageFile imageFile = imageRepository.findById(lecture.getThumbnail().getId())
			.orElseThrow(() -> new ImageNotFoundException("해당 사진이 존재하지 않습니다."));

		MultipartFile image = request.getImage();
		imageFile.update(image.getOriginalFilename(), image.getContentType(), image.getBytes());
		lecture.update(request.getTitle(), imageFile, request.getContent());
		return id;
	}

	@Transactional(readOnly = true)
	public LectureResponseDto findById(Long id) {
		Lecture lecture = lectureRepository.findById(id)
			.orElseThrow(() -> new LectureNotFoundException("해당 강의가 없습니다. id=" + id));
		return new LectureResponseDto(lecture, lecture.getThumbnail());
	}

	@Transactional(readOnly = true)
	public List<LectureResponseDto> findAllByCustomQuery(int offset) {
		List<LectureResponseDto> result = new ArrayList<>();
		for (Lecture lecture : lectureRepository.findAllByCustomQuery(offset)) {
			ImageFile imageFile = imageRepository.findById(lecture.getThumbnail().getId())
				.orElseThrow(ImageNotFoundException::new);
			result.add(new LectureResponseDto(lecture, imageFile));
		}
		return result;
	}

	@Transactional(readOnly = true)
	public ImageFile getImage(Long id) {
		return imageRepository.findById(id).orElseThrow(ImageNotFoundException::new);
	}
}
