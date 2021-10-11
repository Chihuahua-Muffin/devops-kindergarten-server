package devops.kindergarten.server.dto;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;

import devops.kindergarten.server.domain.Authority;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class TokenDto {
	private String accessToken;
	private String refreshToken;
	private Long userId;
	private String username;
	private List<String> authority;
	private Integer exp;

	public TokenDto(String accessToken, String refreshToken, Long userId, String username,
		Collection<? extends GrantedAuthority> authority, Integer exp) {
		this.accessToken = accessToken;
		this.refreshToken = refreshToken;
		this.userId = userId;
		this.username = username;
		this.authority = authority.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
		this.exp = exp;
	}
}
