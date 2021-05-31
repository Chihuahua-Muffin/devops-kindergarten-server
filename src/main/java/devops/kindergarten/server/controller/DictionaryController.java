package devops.kindergarten.server.controller;

import devops.kindergarten.server.dto.dictionary.DictionaryRequestDto;
import devops.kindergarten.server.dto.dictionary.DictionaryResponseDto;
import devops.kindergarten.server.dto.post.PostResponseDto;
import devops.kindergarten.server.exception.custom.DictionaryNotFoundException;
import devops.kindergarten.server.service.DictionaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class DictionaryController {
    private final DictionaryService dictionaryService;

    @PostMapping("/api/dictionary")
    public Long save(@RequestBody DictionaryRequestDto requestDto){
        return dictionaryService.save(requestDto);
    }

    @PutMapping("/api/dictionary/{id}")
    public Long update(@PathVariable Long id, @RequestBody DictionaryRequestDto requestDto){
        return dictionaryService.update(id,requestDto);
    }
    @GetMapping("/api/dictionary/{id}")
    public DictionaryResponseDto findById(@PathVariable Long id){
        return dictionaryService.findById(id);
    }

    @GetMapping("/api/dictionaries")
    public List<DictionaryResponseDto> findAllDesc(@RequestParam int offset){
        return dictionaryService.findAllByCustomQuery(offset);
    }

    @GetMapping("api/dictionaries/search")
    public List<DictionaryResponseDto> search(@RequestParam(value = "keyword") String keyword) {
        if(keyword.equals("")) throw new DictionaryNotFoundException("keyword 값이 공백입니다.");
        return dictionaryService.searchByValue(keyword);
    }
}
