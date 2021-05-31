package devops.kindergarten.server.exception;

import devops.kindergarten.server.dto.ErrorInfo;
import devops.kindergarten.server.exception.custom.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private ResponseEntity<ErrorInfo> error(final Exception exception, final HttpStatus httpStatus,
                                            HttpServletRequest request) {
        return new ResponseEntity<>(new ErrorInfo(exception, request.getMethod() ,request.getRequestURI()), httpStatus);
    }

    @ExceptionHandler(PostNotFoundException.class)
    public ResponseEntity<ErrorInfo> postNotFoundHandler(HttpServletRequest request, final RuntimeException e) {
        return error(e, HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(SignUpException.class)
    public ResponseEntity<ErrorInfo> signUpHandler(HttpServletRequest request, final RuntimeException e) {
        return error(e, HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(DictionaryNotFoundException.class)
    public ResponseEntity<ErrorInfo> dictionaryNotFoundHandler(HttpServletRequest request, final RuntimeException e) {
        return error(e, HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(CommentNotFoundException.class)
    public ResponseEntity<ErrorInfo> commentNotFoundHandler(HttpServletRequest request, final RuntimeException e) {
        return error(e, HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(LoginException.class)
    public ResponseEntity<ErrorInfo> loginHandler(HttpServletRequest request, final RuntimeException e) {
        return error(e, HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorInfo> userNotFoundHandler(HttpServletRequest request, final RuntimeException e) {
        return error(e, HttpStatus.BAD_REQUEST, request);
    }
}