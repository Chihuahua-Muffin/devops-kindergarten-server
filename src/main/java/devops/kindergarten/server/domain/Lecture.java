package devops.kindergarten.server.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter @Setter
public class Lecture {

    @Id
    @GeneratedValue
    private Long id;

    private String title;

    private int checkpoint;
}
