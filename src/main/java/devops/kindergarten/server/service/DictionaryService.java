package devops.kindergarten.server.service;

import devops.kindergarten.server.domain.Dictionary;
import devops.kindergarten.server.domain.Post;
import devops.kindergarten.server.dto.dictionary.DictionaryRequestDto;
import devops.kindergarten.server.dto.dictionary.DictionaryResponseDto;
import devops.kindergarten.server.dto.post.PostSaveRequestDto;
import devops.kindergarten.server.exception.custom.DictionaryNotFoundException;
import devops.kindergarten.server.repository.DictionaryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DictionaryService {
    private final DictionaryRepository dictionaryRepository;
    @Transactional
    public Long save(DictionaryRequestDto requestDto){
        Dictionary dictionary = requestDto.toEntity();
        return dictionaryRepository.save(dictionary).getId();
    }
    @Transactional
    public Long update(Long id,DictionaryRequestDto requestDto){
        Dictionary dictionary = dictionaryRepository.findById(id)
                .orElseThrow(()-> new DictionaryNotFoundException("해당 사전글이 없습니다. id="+id));
        dictionary.update(requestDto.getWordEnglish(), requestDto.getWordKorean(), requestDto.getDescription(), requestDto.getTagList());

        return id;
    }
    @Transactional(readOnly = true)
    public DictionaryResponseDto findById(Long id){
        Dictionary dictionary = dictionaryRepository.findById(id)
                .orElseThrow(()-> new DictionaryNotFoundException("해당 사전글이 없습니다. id="+id));
        return new DictionaryResponseDto(dictionary);
    }
    @Transactional(readOnly = true)
    public List<DictionaryResponseDto> findAllByCustomQuery(int offset){
        return dictionaryRepository.findAllByCustomQuery(offset)
                .stream().map(DictionaryResponseDto::new).collect(Collectors.toList());
    }
}
