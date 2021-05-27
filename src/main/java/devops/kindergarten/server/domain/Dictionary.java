package devops.kindergarten.server.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@Table(name="dictionary")
public class Dictionary {
    @Id
    @Column(name = "dictionary_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String wordEnglish;

    @Column(nullable = false)
    private String wordKorean;

    @Column(nullable = false)
    private String description;

    @ElementCollection
    private List<String> tagList = new ArrayList<>();

    @Builder
    public Dictionary(String wordEnglish,String wordKorean, String description,List<String> tagList){
        this.wordEnglish = wordEnglish;
        this.wordKorean = wordKorean;
        this.description = description;
        this.tagList = tagList;
    }
    public void update(String wordEnglish,String wordKorean, String description,List<String> tagList){
        this.wordEnglish = wordEnglish;
        this.wordKorean = wordKorean;
        this.description = description;
        this.tagList = tagList;
    }
}
