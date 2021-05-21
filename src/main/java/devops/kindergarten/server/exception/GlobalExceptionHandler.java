package devops.kindergarten.server.exception;

import devops.kindergarten.server.dto.ErrorInfo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private ResponseEntity<ErrorInfo> error(final Exception exception, final HttpStatus httpStatus,
                                            HttpServletRequest request) {
        return new ResponseEntity<>(new ErrorInfo(exception, request.getRequestURI()), httpStatus);
    }

    public ResponseEntity<ErrorInfo> LoginHandler(HttpServletRequest request, final RuntimeException e){
        return error(e,HttpStatus.BAD_REQUEST,request);
    }
}
