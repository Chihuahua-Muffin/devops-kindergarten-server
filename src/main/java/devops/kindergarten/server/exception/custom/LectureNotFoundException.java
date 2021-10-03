package devops.kindergarten.server.exception.custom;

public class LectureNotFoundException extends RuntimeException {
	public LectureNotFoundException() {
		super();
	}

	public LectureNotFoundException(String message) {
		super(message);
	}
}
