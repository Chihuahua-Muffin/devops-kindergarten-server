package devops.kindergarten.server.domain;

import lombok.*;

import org.apache.tomcat.jni.Local;

import javax.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "comment")
@NoArgsConstructor
@Getter
@Setter
public class Comment {
	@Id
	@Column(name = "comment_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "content", nullable = false, columnDefinition = "TEXT")
	private String content;

	@Column(name = "like_count")
	private int likeCount;

	private String username;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User writer;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "post_id")
	private Post post;

	private LocalDateTime createdDate;
	private LocalDateTime updatedDate;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "parent_id")
	private Comment parent;

	@OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Comment> children = new ArrayList<>();

	private void setWriter(User writer) {
		this.writer = writer;
		writer.getCommentList().add(this);
	}

	private void setPost(Post post) {
		this.post = post;
		post.getCommentList().add(this);
	}

	private void setParent(Comment parent) {
		this.parent = parent;
		parent.getChildren().add(this);
	}

	public static Comment createComment(User writer, Post post, String content, String username) {
		Comment comment = new Comment();
		LocalDateTime now = LocalDateTime.now();

		comment.setWriter(writer);
		comment.setPost(post);
		comment.setContent(content);
		comment.setUsername(username);
		comment.setLikeCount(0);
		comment.setCreatedDate(now);
		comment.setUpdatedDate(now);

		return comment;
	}

	public static Comment createChildComment(User writer, Post post, Comment parent, String content, String username) {
		Comment comment = new Comment();
		LocalDateTime now = LocalDateTime.now();

		comment.setWriter(writer);
		comment.setPost(post);
		comment.setContent(content);
		comment.setUsername(username);
		comment.setLikeCount(0);
		comment.setCreatedDate(now);
		comment.setUpdatedDate(now);
		comment.setParent(parent);

		return comment;
	}

	public void update(String content) {
		this.content = content;
		this.updatedDate = LocalDateTime.now();
	}
}
