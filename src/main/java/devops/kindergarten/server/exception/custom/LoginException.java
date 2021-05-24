package devops.kindergarten.server.exception.custom;

public class LoginException extends RuntimeException{
    public LoginException(String message) {
        super(message);
    }

    public LoginException() {
        super();
    }
}
