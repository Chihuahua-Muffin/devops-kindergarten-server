package devops.kindergarten.server.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
@Setter
public class ErrorInfo {
	private String msg;

	private String result;

	private int httpStatus;

	private String httpMethod;

	private LocalDateTime timestamp;

	@JsonProperty("uri")
	private String uriRequested;

	public ErrorInfo(Exception exception, String httpMethod, String uriRequested) {
		this.msg = exception.getMessage();
		this.result = exception.toString();
		this.httpStatus = HttpStatus.BAD_REQUEST.value();
		this.httpMethod = httpMethod;
		this.uriRequested = uriRequested;
		this.timestamp = LocalDateTime.now();
	}
}