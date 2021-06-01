package devops.kindergarten.server.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@NoArgsConstructor
@Table(name="lecture")
public class Lecture {
    @Id
    @Column(name = "lecture_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "image_id")
    private ImageFile thumbnail;

    @Column(nullable = false)
    private String description;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> tagList = new ArrayList<>();

    @Builder
    public Lecture(String title, ImageFile thumbnail, String description, List<String> tagList) {
        this.title = title;
        this.thumbnail = thumbnail;
        this.description = description;
        this.tagList = tagList;
    }

    public void update(String title, ImageFile thumbnail, String description, List<String> tagList) {
        this.title = title;
        this.thumbnail = thumbnail;
        this.description = description;
        this.tagList = tagList;
    }
}
