package devops.kindergarten.server.dto.dictionary;

import devops.kindergarten.server.domain.Dictionary;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class DictionaryRequestDto {
	private String wordEnglish;
	private String wordKorean;
	private String description;
	private List<String> tagList;

	@Builder
	public DictionaryRequestDto(String wordEnglish, String wordKorean, String description, List<String> tagList) {
		this.wordEnglish = wordEnglish;
		this.wordKorean = wordKorean;
		this.description = description;
		this.tagList = tagList;
	}

	public Dictionary toEntity() {
		return Dictionary.builder()
			.wordEnglish(wordEnglish)
			.wordKorean(wordKorean)
			.description(description)
			.tagList(tagList)
			.build();
	}
}
