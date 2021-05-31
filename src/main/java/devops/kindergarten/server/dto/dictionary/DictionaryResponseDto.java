package devops.kindergarten.server.dto.dictionary;

import devops.kindergarten.server.domain.Dictionary;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class DictionaryResponseDto {
    private Long id;
    private String wordEnglish;
    private String wordKorean;
    private String description;
    private List<String> tagList;

    public DictionaryResponseDto(Dictionary entity) {
        this.id = entity.getId();
        this.wordEnglish = entity.getWordEnglish();
        this.wordKorean = entity.getWordKorean();
        this.description = entity.getDescription();
        this.tagList = entity.getTagList();
    }
}
