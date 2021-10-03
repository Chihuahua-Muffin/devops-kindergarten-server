package devops.kindergarten.server.exception.custom;

public class CommentNotFoundException extends RuntimeException {
	public CommentNotFoundException() {
		super();
	}

	public CommentNotFoundException(String message) {
		super(message);
	}
}
