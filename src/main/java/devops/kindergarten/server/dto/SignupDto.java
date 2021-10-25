package devops.kindergarten.server.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SignupDto {
	@Size(min = 6)
	private String username;

	private String name;

	@NotBlank
	private String email;

	@NotBlank
	private String password;

	private String status;

	public SignupDto(String username, String name, String email, String password, String status) {
		this.username = username;
		this.name = name;
		this.email = email;
		this.password = password;
		this.status = status;
	}
}
