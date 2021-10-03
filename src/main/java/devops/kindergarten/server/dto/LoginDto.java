package devops.kindergarten.server.dto;

import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
public class LoginDto {
	@NotNull
	private String username;

	@NotNull
	private String password;

	public LoginDto(String username, String password) {
		this.username = username;
		this.password = password;
	}
}
