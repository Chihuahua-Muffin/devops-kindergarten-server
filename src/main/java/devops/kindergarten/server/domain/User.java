package devops.kindergarten.server.domain;

import lombok.*;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

import java.util.*;

@Entity
@Table(name = "user", uniqueConstraints = {
	@UniqueConstraint(columnNames = {"username","email"})
})
@NoArgsConstructor
@Getter
public class User {
	@Id
	@Column(name = "user_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "username", unique = true)
	private String username;

	@Column(name = "name")
	private String name;

	@Column(name = "email")
	private String email;

	@Column(name = "password")
	private String password;

	@Column(name = "instance_ip")
	private String instanceIP;

	@OneToOne(mappedBy = "user")
	private RefreshToken refreshToken;

	@OneToMany(mappedBy = "user")
	List<Progress> lectureProgress = new ArrayList<>();

	@ManyToMany
	@JoinTable(
		name = "user_authority",
		joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "user_id")},
		inverseJoinColumns = {@JoinColumn(name = "authority_name", referencedColumnName = "authority_name")}
	)
	private Set<Authority> authorities;

	@OneToMany(mappedBy = "writer", cascade = CascadeType.ALL)
	private List<Comment> commentList = new ArrayList<>();

	@OneToMany(mappedBy = "author", cascade = CascadeType.ALL)
	private List<Post> postList = new ArrayList<>();

	public void setInstanceIP(String instanceIP){
		this.instanceIP = instanceIP;
	}
	public User(String subject, Collection<? extends GrantedAuthority> authorities) {
		this.username = subject;
		this.authorities = new HashSet<>((Collection<? extends Authority>)authorities);
	}

	@Builder
	public User(String username, String name, String email, String password, Set<Authority> authorities) {
		this.username = username;
		this.name = name;
		this.email = email;
		this.password = password;
		this.authorities = authorities;
	}
}