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
@Getter
public class User {
    // User 인덱스
    @Id
    @Column(name="user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 사용자의 username(user의 회원가입 할 때의 ID)
    @Column(name="username",unique = true)
    private String username;

    // 사용자의 이름
    @Column(name="name")
    private String name;

    // 사용자의 email
    @Column(name = "email")
    private String email;

    // 사용자의 비밀번호
    @Column(name="password")
    private String password;

    @ManyToMany
    @JoinTable(
            name="user_authority",
            joinColumns = {@JoinColumn(name="user_id",referencedColumnName = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "authority_name",referencedColumnName = "authority_name")}
    )
    private Set<Authority> authorities;

    // 본인이 작성한 post와 comment의 인덱스를 저장하는 리스트
    @JsonIgnore
    @Builder.Default
    @OneToMany(cascade = CascadeType.ALL)
    private List<Comment> commentList = new ArrayList<>();

    @JsonIgnore
    @Builder.Default
    @OneToMany(cascade = CascadeType.ALL)
    private List<Post> postList = new ArrayList<>();

    // 본인이 좋아요를 누를 post와 comment의 인덱스를 저장하는 리스트
    @JsonIgnore
    @Builder.Default
    @ElementCollection
    private Set<Long> postLikeIdList = new HashSet<>();

    @JsonIgnore
    @Builder.Default
    @ElementCollection
    private Set<Long> commentLikeIdList = new HashSet<>();

    public User(String subject, Collection<? extends GrantedAuthority> authorities) {
        this.username = subject;
        this.authorities = new HashSet<>((Collection<? extends Authority>) authorities);
    }
}