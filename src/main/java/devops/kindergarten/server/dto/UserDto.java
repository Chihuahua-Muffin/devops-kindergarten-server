package devops.kindergarten.server.dto;

import devops.kindergarten.server.domain.User;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class UserDto {
	private Long userId;
	private String username;
	private String name;
	private String email;

	public UserDto(User user) {
		this.userId = user.getId();
		this.username = user.getUsername();
		this.name = user.getName();
		this.email = user.getEmail();
	}
}
