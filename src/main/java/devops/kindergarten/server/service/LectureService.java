package devops.kindergarten.server.service;

import com.fasterxml.jackson.databind.ObjectMapper;
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
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LectureService {
    private final LectureRepository lectureRepository;
    private final ImageRepository imageRepository;

    @Transactional
    public Long save(MultipartFile image, String request) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        LectureRequestDto requestDto = objectMapper.readValue(request,LectureRequestDto.class);
        Lecture lecture = requestDto.toLectureEntity();
        ImageFile imageFile = ImageFile.createImageFile(image.getOriginalFilename(),image.getContentType(),image.getBytes());
        lecture.setThumbnail(imageFile);
        imageRepository.save(imageFile);
        return lectureRepository.save(lecture).getId();
    }

    @Transactional
    public Long update(Long id,MultipartFile image, String request) throws IOException {
        Lecture lecture = lectureRepository.findById(id)
                .orElseThrow(() -> new LectureNotFoundException("해당 강의가 없습니다. id=" + id));
        ImageFile imageFile = imageRepository.findById(lecture.getThumbnail().getId())
                .orElseThrow(()->new ImageNotFoundException("해당 사진이 존재하지 않습니다."));

        ObjectMapper objectMapper = new ObjectMapper();
        LectureRequestDto requestDto = objectMapper.readValue(request,LectureRequestDto.class);
        imageFile.update(image.getOriginalFilename(),image.getContentType(),image.getBytes());
        lecture.update(requestDto.getTitle(), imageFile, requestDto.getDescription(), requestDto.getTagList());

        return id;
    }

    @Transactional(readOnly = true)
    public LectureResponseDto findById(Long id) {
        Lecture lecture = lectureRepository.findById(id)
                .orElseThrow(() -> new LectureNotFoundException("해당 강의가 없습니다. id=" + id));
        ImageFile imageFile = imageRepository.findById(lecture.getThumbnail().getId())
                .orElseThrow(()->new ImageNotFoundException("해당 사진이 존재하지 않습니다."));

        return new LectureResponseDto(lecture,imageFile);
    }

    @Transactional(readOnly = true)
    public List<LectureResponseDto> findAllByCustomQuery(int offset) {
        List<LectureResponseDto> result = new ArrayList<>();
        for (Lecture lecture:lectureRepository.findAllByCustomQuery(offset)) {
            ImageFile imageFile = imageRepository.findById(lecture.getThumbnail().getId())
                    .orElseThrow(ImageNotFoundException::new);
            result.add(new LectureResponseDto(lecture,imageFile));
        }
        return result;
    }

    @Transactional
    public Long saveImage(MultipartFile image)throws IOException{
        return imageRepository.save(ImageFile.createImageFile(
                image.getName(),
                image.getContentType(),
                image.getBytes())).getId();
    }
    @Transactional(readOnly = true)
    public ImageFile getImage(Long id) {
        return imageRepository.findById(id).orElseThrow(ImageNotFoundException::new);
    }
}
