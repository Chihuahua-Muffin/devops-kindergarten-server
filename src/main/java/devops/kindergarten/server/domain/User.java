package devops.kindergarten.server.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class User {
    @Id
    @GeneratedValue
    private Long id;

    // 사용자의 이름
    private String name;
    // 사용자의 id
    private String uid;
    // 사용자의 email
    private String email;

    // 학습자? 교육자? 관리자?
    @Enumerated(EnumType.STRING)
    private Status status;

    @OneToMany
    private List<Comment> commentList = new ArrayList<>();

    @OneToMany
    private List<Post> postList = new ArrayList<>();

    // List? Set?
    @ElementCollection
    private List<Integer> postLikeIdList = new ArrayList<>();

    @ElementCollection
    private List<Integer> commentLikeIdList = new ArrayList<>();

    @OneToMany
    private List<Lecture> lectureList = new ArrayList<>();

}
