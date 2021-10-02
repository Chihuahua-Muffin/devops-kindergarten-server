package devops.kindergarten.server.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class TokenDto {
	private String accessToken;
	private String refreshToken;

	public TokenDto(String accessToken, String refreshToken) {
		this.accessToken = accessToken;
		this.refreshToken = refreshToken;
	}
}
