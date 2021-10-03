package devops.kindergarten.server.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import devops.kindergarten.server.domain.Status;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
public class UserDto {
	@Size(min = 6)
	private String username;

	private String name;

	@NotBlank
	private String email;

	@NotBlank
	private String password;

	private String status;

	public UserDto(String username, String name, String email, String password, String status) {
		this.username = username;
		this.name = name;
		this.email = email;
		this.password = password;
		this.status = status;
	}
}
