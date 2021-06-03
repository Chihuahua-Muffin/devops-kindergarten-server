package devops.kindergarten.server.controller;

import devops.kindergarten.server.dto.dictionary.DictionaryRequestDto;
import devops.kindergarten.server.dto.dictionary.DictionaryResponseDto;
import devops.kindergarten.server.dto.post.PostResponseDto;
import devops.kindergarten.server.exception.custom.DictionaryNotFoundException;
import devops.kindergarten.server.service.DictionaryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = {"사전관련 기능 컨트롤러"})
@RestController
@RequiredArgsConstructor
public class DictionaryController {
    private final DictionaryService dictionaryService;

    @ApiOperation(value = "사전 등록",notes="사전을 등록하는데 사용된다.")
    @PostMapping("/api/dictionary")
    public Long save(@RequestBody DictionaryRequestDto requestDto){
        return dictionaryService.save(requestDto);
    }


    @ApiOperation(value = "사전 수정",notes="사전을 수정하는데 사용된다.")
    @PutMapping("/api/dictionary/{id}")
    public Long update(@PathVariable Long id, @RequestBody DictionaryRequestDto requestDto){
        return dictionaryService.update(id,requestDto);
    }

    @ApiOperation(value = "사전 불러오기",notes="선택된 사전(ID)를 불러온다.")
    @GetMapping("/api/dictionary/{id}")
    public DictionaryResponseDto findById(@PathVariable Long id){
        return dictionaryService.findById(id);
    }

    @ApiOperation(value = "사전 목록 불러오기",notes="사전의 목록을 불러오는데 사용된다.")
    @GetMapping("/api/dictionaries")
    public List<DictionaryResponseDto> findAllDesc(@RequestParam int offset){
        return dictionaryService.findAllByCustomQuery(offset);
    }

    @ApiOperation(value = "사전 검색기능",notes="주어진 키워드를 포함하고 있는 사전의 목록을 반환해준다.")
    @GetMapping("api/dictionaries/search")
    public List<DictionaryResponseDto> search(@RequestParam(value = "keyword") String keyword) {
        if(keyword.equals("")) throw new DictionaryNotFoundException("keyword 값이 공백입니다.");
        return dictionaryService.searchByValue(keyword);
    }
}
