package devops.kindergarten.server.controller;

import devops.kindergarten.server.domain.Dictionary;
import devops.kindergarten.server.dto.dictionary.DictionaryRequestDto;
import devops.kindergarten.server.dto.dictionary.DictionaryResponseDto;
import devops.kindergarten.server.dto.post.PostResponseDto;
import devops.kindergarten.server.repository.DictionaryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class DictionaryControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private DictionaryRepository dictionaryRepository;

    public ResponseEntity<Long> createDictionary(String wordEnglish, String wordKorean, String description, List<String> tagList) throws Exception{
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:" + port + "/api/dictionary";
        URI uri = new URI(url);
        DictionaryRequestDto requestDto = new DictionaryRequestDto(wordEnglish,wordKorean,description,tagList);
        return restTemplate.postForEntity(uri,requestDto,Long.class);
    }
    @Test
    public void 사전_추가기능_테스트() throws Exception{
        //given
        String wordEnglish = "docker";
        String wordKorean = "도커";
        String description = "도커는 어떻게 사용할까요??";
        List<String> tagList = new ArrayList<>();
        tagList.add("Devops");
        tagList.add("Docker");
        tagList.add("Cloud");

        //when
        createDictionary(wordEnglish,wordKorean,description,tagList);

        //then
        List<Dictionary> dictionaries = dictionaryRepository.findAll();
        Dictionary dictionary = dictionaries.get(dictionaries.size()-1);
        assertEquals(dictionary.getWordEnglish(),wordEnglish,"사전 영어 명칭 확인");
        assertEquals(dictionary.getWordKorean(),wordKorean,"사전 한국어 명칭 확인");
        assertEquals(dictionary.getDescription(),description,"사전 내용 확인");
        assertEquals(dictionary.getTagList().toString(),tagList.toString(),"해당 사전의 태그들 확인");
    }
    @Test
    public void 사전_확인기능_테스트() throws Exception{
        //given
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:" + port + "/api/dictionary";
        String wordEnglish = "docker";
        String wordKorean = "도커";
        String description = "도커는 어떻게 사용할까요??";
        List<String> tagList = new ArrayList<>();
        tagList.add("Devops");
        tagList.add("Docker");
        tagList.add("Cloud");
        ResponseEntity<Long> post = createDictionary(wordEnglish,wordKorean,description,tagList);
        Long postId = post.getBody();
        URI uri = new URI(url+"/"+postId);

        //when
        ResponseEntity<DictionaryResponseDto> result = restTemplate.getForEntity(uri,DictionaryResponseDto.class);

        //then

        assertEquals(result.getBody().getWordEnglish(),wordEnglish,"사전 영어 명칭 확인");
        assertEquals(result.getBody().getWordKorean(),wordKorean,"사전 한국어 명칭 확인");
        assertEquals(result.getBody().getDescription(),description,"사전 내용 확인");
        assertEquals(result.getBody().getTagList().toString(),tagList.toString(),"해당 사전의 태그들 확인");
    }

    @Test
    public void 사전_수정기능_테스트() throws Exception{
        //given
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:" + port + "/api/dictionary";
        List<String> tagList = new ArrayList<>();
        tagList.add("Devops");
        tagList.add("Docker");
        tagList.add("Cloud");
        ResponseEntity<Long> post = createDictionary("docker","도커","도커는 어떻게 사용할까요??", tagList);
        Long postId  = post.getBody();
        URI uri = new URI(url+"/"+postId);

        String updateWordEnglish = "DOCKER";
        String updateWordKorean = "도 커";
        String updateDescription = "도커는 잘 사용합니다.";
        tagList.clear();
        tagList.add("도커");
        tagList.add("클라우드");
        tagList.add("데브옵스");

        DictionaryRequestDto requestDto = new DictionaryRequestDto(updateWordEnglish,updateWordKorean,updateDescription,tagList);
        //when
        restTemplate.put(uri,requestDto);
        ResponseEntity<DictionaryResponseDto> result = restTemplate.getForEntity(uri,DictionaryResponseDto.class);
        //then
        assertEquals(result.getBody().getWordEnglish(),updateWordEnglish,"사전 영어 명칭 확인");
        assertEquals(result.getBody().getWordKorean(),updateWordKorean,"사전 한국어 명칭 확인");
        assertEquals(result.getBody().getDescription(),updateDescription,"사전 내용 확인");
        assertEquals(result.getBody().getTagList().toString(),tagList.toString(),"해당 사전의 태그들 확인");
    }

    @Test
    public void 사전_목록기능_테스트() throws Exception{
        //given
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:" + port + "/api/dictionaries?offset=0";
        String wordEnglish = "docker";
        String wordKorean = "도커";
        String description = "도커는 어떻게 사용할까요??";
        for (int i = 0; i < 10; i++) {
            List<String> tagList = new ArrayList<>();
            tagList.add("Devops"+i);
            tagList.add("Docker"+i);
            tagList.add("Cloud"+i);
            createDictionary(wordEnglish+i,wordKorean+i,description+i,tagList);
        }
        URI uri = new URI(url);
        //when
        ResponseEntity<List> result = restTemplate.getForEntity(uri,List.class);

        //then
        for (int i = 0; i < 10; i++) {
            System.out.println(result.getBody().get(9-i));
            assertTrue(result.getBody().get(9 - i).toString().contains(wordEnglish + i));
            assertTrue(result.getBody().get(9 - i).toString().contains(wordKorean + i));
            assertTrue(result.getBody().get(9 - i).toString().contains(description + i));
        }
    }

    @Test
    public void 사전_검색_테스트() throws Exception{
        //given
        String keyword = "도커";
        String wordEnglish = "docker";
        String wordKorean = "도커";
        String description = "도커는 어떻게 사용할까요??";
        List<String> tagList = new ArrayList<>();
        tagList.add("Devops");
        tagList.add("Docker");
        tagList.add("Cloud");

        createDictionary(wordEnglish,wordKorean,description,tagList);

        //when
        List<Dictionary> result_key = dictionaryRepository.searchByValue(keyword);
        List<Dictionary> result_eng = dictionaryRepository.searchByValue(wordEnglish);
        List<Dictionary> result_kor = dictionaryRepository.searchByValue(wordKorean);

        // then
        assertTrue(result_key.get(0).getDescription().contains(keyword));
        assertTrue(result_eng.get(0).getWordEnglish().contains(wordEnglish));
        assertTrue(result_kor.get(0).getWordKorean().contains(wordKorean));
    }
}