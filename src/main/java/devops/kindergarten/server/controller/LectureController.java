package devops.kindergarten.server.controller;

import devops.kindergarten.server.domain.ImageFile;
import devops.kindergarten.server.dto.lecture.LectureResponseDto;
import devops.kindergarten.server.service.LectureService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


@Api(tags = {"강의관련 기능"})
@RestController
@RequiredArgsConstructor
public class LectureController {
    private final LectureService lectureService;

    @ApiOperation(value = "강의 기능",notes="강의를 등록되는데 사용된다.")
    @PostMapping(value = "/api/lecture", consumes = {
            MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public Long save(@RequestPart(value = "request") String request,
                     @RequestPart(value = "image") MultipartFile image) throws IOException {
        return lectureService.save(image, request);
    }


    @ApiOperation(value = "강의 수정 기능",notes="강의를 수정하는데 사용된다.")
    @PutMapping(value = "/api/lecture/{id}", consumes = {
            MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public Long update(@PathVariable Long id,
                       @RequestPart(value = "request") String request,
                       @RequestPart(value = "image") MultipartFile image) throws IOException {
        return lectureService.update(id, image, request);
    }

    @PostMapping("/api/upload-image")
    public Long saveImage(@RequestPart(value = "files") MultipartFile image) throws IOException {
        return lectureService.saveImage(image);
    }

    @GetMapping("/api/image/{id}")
    public ResponseEntity findByImageId(@PathVariable Long id)  {
        ImageFile file = lectureService.getImage(id);
        return ResponseEntity.ok()
                .body(file);
    }

    @ApiOperation(value = "해당 강의 불러오기",notes="선택한 강의를 불러온다.")
    @GetMapping("/api/lecture/{id}")
    public LectureResponseDto findById(@PathVariable Long id) {
        return lectureService.findById(id);
    }

    @ApiOperation(value = "강의 목록 불러오기",notes="강의 목록을 불러온다.")
    @GetMapping("/api/lectures")
    public List<LectureResponseDto> findAllDesc(@RequestParam int offset) {
        return lectureService.findAllByCustomQuery(offset);
    }
}
