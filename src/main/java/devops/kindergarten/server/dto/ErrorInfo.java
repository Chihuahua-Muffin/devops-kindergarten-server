package devops.kindergarten.server.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ErrorInfo {
    @JsonProperty
    private String message;

    @JsonProperty("timestamp")
    private LocalDateTime timestamp ;

    @JsonProperty("uri")
    private String uriRequested;

    public ErrorInfo(Exception exception, String uriRequested) {
        this.message = exception.toString();
        this.uriRequested = uriRequested;
        this.timestamp = LocalDateTime.now();
    }
}