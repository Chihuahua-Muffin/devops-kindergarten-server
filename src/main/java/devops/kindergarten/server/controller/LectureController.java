package devops.kindergarten.server.controller;

import devops.kindergarten.server.dto.lecture.LectureRequestDto;
import devops.kindergarten.server.dto.lecture.LectureResponseDto;
import devops.kindergarten.server.service.LectureService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class LectureController {
    private final LectureService lectureService;

    @GetMapping("/api/lecture")
    public Long save(@RequestBody LectureRequestDto requestDto) { return lectureService.save(requestDto);}

    @PutMapping("/api/lecture/{id}")
    public Long update(@PathVariable Long id, @RequestBody LectureRequestDto requestDto) {
        return lectureService.update(id, requestDto);
    }

    @GetMapping("/api/lecture/{id}")
    public LectureResponseDto findById(@PathVariable Long id) { return  lectureService.findById(id); }

    @GetMapping("/api/lectures")
    public List<LectureResponseDto> findAllDesc(@RequestParam int offset) {
        return lectureService.findAllByCustomQuery(offset);
    }
}
