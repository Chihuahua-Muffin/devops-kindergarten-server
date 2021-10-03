package devops.kindergarten.server.service;

import devops.kindergarten.server.domain.RefreshToken;
import devops.kindergarten.server.exception.custom.TokenRefreshException;
import devops.kindergarten.server.exception.custom.UserNotFoundException;
import devops.kindergarten.server.repository.RefreshTokenRepository;
import devops.kindergarten.server.repository.UserRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {
	@Value("${jwt.refresh-validity-in-seconds}")
	private Long refreshTokenDurationMs;

	private final RefreshTokenRepository refreshTokenRepository;

	private final UserRepository userRepository;

	@Transactional(readOnly = true)
	public Optional<RefreshToken> findByToken(String token) {
		return refreshTokenRepository.findByToken(token);
	}

	@Transactional
	public RefreshToken createRefreshToken(String username) {
		RefreshToken refreshToken = new RefreshToken();

		refreshToken.setUser(userRepository.findByUsername(username)
			.orElseThrow(UserNotFoundException::new));
		refreshToken.setExpiryDate(Instant.now().plusMillis(refreshTokenDurationMs * 1000));
		refreshToken.setToken(UUID.randomUUID().toString());

		refreshToken = refreshTokenRepository.save(refreshToken);
		return refreshToken;
	}

	@Transactional
	public RefreshToken verifyExpiration(RefreshToken token) {
		if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
			refreshTokenRepository.deleteById(token.getId());
			throw new TokenRefreshException(token.getToken(), "토큰이 만료되었습니다. 새롭게 로그인해 주세요.");
		}
		return token;
	}

	@Transactional
	public int deleteByUsername(String username) {
		return refreshTokenRepository.deleteByUser(userRepository.findByUsername(username).orElseThrow(
			UserNotFoundException::new
		));
	}
}
