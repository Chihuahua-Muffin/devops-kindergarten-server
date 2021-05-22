package devops.kindergarten.server.domain;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "comment")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter @Setter
public class Comment {
    @Id
    @Column(name = "comment_id")
    private Long id;

}
