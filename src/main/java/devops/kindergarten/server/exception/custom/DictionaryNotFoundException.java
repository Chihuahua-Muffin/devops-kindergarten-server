package devops.kindergarten.server.exception.custom;

public class DictionaryNotFoundException extends RuntimeException{
    public DictionaryNotFoundException() {
    }

    public DictionaryNotFoundException(String message) {
        super(message);
    }
}
