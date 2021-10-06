package devops.kindergarten.server.domain;

import lombok.*;

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

	private String username;

	@Column(name = "theory_page")
	@Enumerated(EnumType.STRING)
	private TheoryPageName theoryPage;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User writer;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "lecture_id")
	private Lecture lecture;

	private LocalDateTime createdDate;
	private LocalDateTime updatedDate;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "parent_id")
	private Comment parent;

	@OneToMany(mappedBy = "parent", orphanRemoval = true)
	private List<Comment> children = new ArrayList<>();

	private void setWriter(User writer) {
		this.writer = writer;
		writer.getCommentList().add(this);
	}

	private void setParent(Comment parent) {
		if (parent != null) {
			this.parent = parent;
			parent.getChildren().add(this);
		}
	}

	public static Comment createComment(User writer, Lecture lecture, Comment parent, String content, String username, TheoryPageName pageName) {
		Comment comment = new Comment();
		LocalDateTime now = LocalDateTime.now();

		comment.setWriter(writer);
		comment.setLecture(lecture);
		comment.setParent(parent);
		comment.setContent(content);
		comment.setUsername(username);
		comment.setCreatedDate(now);
		comment.setUpdatedDate(now);
		comment.setTheoryPage(pageName);
		return comment;
	}

	public void update(String content) {
		this.content = content;
		this.updatedDate = LocalDateTime.now();
	}
}
