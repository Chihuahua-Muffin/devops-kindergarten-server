package devops.kindergarten.server.service;

import devops.kindergarten.server.domain.Lecture;
import devops.kindergarten.server.dto.lecture.LectureRequestDto;
import devops.kindergarten.server.dto.lecture.LectureResponseDto;
import devops.kindergarten.server.exception.custom.LectureNotFoundException;
import devops.kindergarten.server.repository.LectureRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LectureService {
    private final LectureRepository lectureRepository;

    @Transactional
    public Long save(LectureRequestDto requestDto) {
        Lecture lecture = requestDto.toEntity();
        return lectureRepository.save(lecture).getId();
    }

    @Transactional
    public Long update(Long id, LectureRequestDto requestDto) {
        Lecture lecture = lectureRepository.findById(id)
                .orElseThrow(() -> new LectureNotFoundException("해당 강의가 없습니다. id=" + id));
        lecture.update(requestDto.getTitle(), requestDto.getThumbnail(), requestDto.getDescription(), requestDto.getTagList());

        return id;
    }

    @Transactional(readOnly = true)
    public LectureResponseDto findById(Long id) {
        Lecture lecture = lectureRepository.findById(id)
                .orElseThrow(() -> new LectureNotFoundException("해당 강의가 없습니다. id=" + id));
        return new LectureResponseDto(lecture);
    }

    @Transactional(readOnly = true)
    public List<LectureResponseDto> findAllByCustomQuery(int offset) {
        return lectureRepository.findAllByCustomQuery(offset)
                .stream().map(LectureResponseDto::new).collect(Collectors.toList());
    }
}
