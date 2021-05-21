package devops.kindergarten.server.domain;

import javax.persistence.*;

@Entity
@Table(name="post")
public class Post{
    @Id
    @Column(name="post_id")
    private Long id;

}
