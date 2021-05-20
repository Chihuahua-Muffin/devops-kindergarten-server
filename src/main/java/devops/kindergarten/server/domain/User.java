package devops.kindergarten.server.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name="user")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class User {
    // User 인덱스
    @JsonIgnore
    @Id
    @Column(name="user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 사용자의 이름
    @Column(name="username")
    private String username;

    // 사용자의 id
    @JsonIgnore
    @Column(name="uid",unique = true)
    private String uid;

    // 사용자의 email
    @JsonIgnore
    @Column(name = "email")
    private String email;

    // 사용자의 비밀번호
    @JsonIgnore
    @Column(name="password")
    private String password;

    // 학습자? 교육자? 관리자?
    @JsonIgnore
    @Enumerated(EnumType.STRING)
    private Status status;

    @JsonIgnore
    @Column(name = "activated")
    private boolean activated;

    @ManyToMany
    @JoinTable(
            name="user_authority",
            joinColumns = {@JoinColumn(name="user_id",referencedColumnName = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "authority_name",referencedColumnName = "authority_name")}
    )
    private Set<Authority> authorities;

    // 본인이 작성한 post와 comment의 인덱스를 저장하는 리스트
    @JsonIgnore
    @ElementCollection
    private List<Long> commentList = new ArrayList<>();

    @JsonIgnore
    @ElementCollection
    private List<Long> postList = new ArrayList<>();

    // 본인이 좋아요를 누를 post와 comment의 인덱스를 저장하는 리스트
    @JsonIgnore
    @ElementCollection
    private List<Long> postLikeIdList = new ArrayList<>();
    @JsonIgnore
    @ElementCollection
    private List<Long> commentLikeIdList = new ArrayList<>();


    public User(String subject, Collection<? extends GrantedAuthority> authorities) {
        this.username = subject;
        this.authorities = new HashSet<>((Collection<? extends Authority>) authorities);
    }
}
