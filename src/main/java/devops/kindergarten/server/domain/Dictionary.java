package devops.kindergarten.server.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@Getter @Setter
public class Dictionary {
    @Id
    @GeneratedValue
    private Long id;

    @Column(name="title")
    private String title;

    private String content;

    private LocalDateTime createAt;
}
