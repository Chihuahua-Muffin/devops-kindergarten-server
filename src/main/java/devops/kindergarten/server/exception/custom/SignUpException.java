package devops.kindergarten.server.exception.custom;

public class SignUpException extends RuntimeException{
    public SignUpException() {
        super();
    }

    public SignUpException(String message) {
        super(message);
    }
}
