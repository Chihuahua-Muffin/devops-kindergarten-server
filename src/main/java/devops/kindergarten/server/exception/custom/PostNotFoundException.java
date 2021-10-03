package devops.kindergarten.server.exception.custom;

public class PostNotFoundException extends RuntimeException {
	public PostNotFoundException(String msg) {
		super(msg);
	}

	public PostNotFoundException() {
		super();
	}
}
