package devops.kindergarten.server.exception.custom;

public class ImageNotFoundException extends RuntimeException {
	public ImageNotFoundException() {
	}

	public ImageNotFoundException(String message) {
		super(message);
	}
}
