package devops.kindergarten.server.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "progress")
@NoArgsConstructor
@Getter
public class Progress {
	@Id
	@Column(name = "progress_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;

	@ManyToOne
	@JoinColumn(name = "user_id")
	User user;

	@Column(name = "lecture_id")
	Long lectureId;

	@Column(name = "rate")
	int progressRate;

	public void setUser(User user) {
		user.getLectureProgress().add(this);
		this.user = user;
	}

	public void setProgressRate(int progressRate) {
		this.progressRate = progressRate;
	}

	public void setLectureId(Long lectureId) {
		this.lectureId = lectureId;
	}

	public static Progress createProgress(User user, Long lectureId, int progressRate) {
		Progress progress = new Progress();
		progress.setUser(user);
		progress.setLectureId(lectureId);
		progress.setProgressRate(progressRate);
		return progress;
	}
}
