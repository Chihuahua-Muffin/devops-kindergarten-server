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
@Table(name="lecture")
public class Lecture {
    @Id
    @Column(name = "lecture_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column()
    private String thumbnail;

    @Column(nullable = false)
    private String description;

    //오류나면 바꾸기
    @ElementCollection(fetch = FetchType.LAZY)
    private List<String> tagList = new ArrayList<>();

    @Builder
    public Lecture(String title, String thumbnail, String description, List<String> tagList) {
        this.title = title;
        this.thumbnail = thumbnail;
        this.description = description;
        this.tagList = tagList;
    }

    public void update(String title, String thumbnail, String description, List<String> tagList) {
        this.title = title;
        this.thumbnail = thumbnail;
        this.description = description;
        this.tagList = tagList;
    }
}
