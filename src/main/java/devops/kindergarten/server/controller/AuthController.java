package devops.kindergarten.server.controller;

import devops.kindergarten.server.domain.RefreshToken;
import devops.kindergarten.server.domain.User;
import devops.kindergarten.server.dto.*;
import devops.kindergarten.server.exception.custom.LoginException;
import devops.kindergarten.server.jwt.TokenProvider;
import devops.kindergarten.server.jwt.UserDetailsImpl;
import devops.kindergarten.server.service.RefreshTokenService;
import devops.kindergarten.server.service.UserService;
import devops.kindergarten.server.util.ShUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Api(tags = {"로그인 관련 컨트롤러"})
@RestController
@RequiredArgsConstructor
public class AuthController {
	private final TokenProvider tokenProvider;
	private final AuthenticationManagerBuilder authenticationManagerBuilder;
	private final UserService userService;
	private final RefreshTokenService refreshTokenService;
	private final ShUtil shUtil;

	@ApiOperation(value = "로그인 기능", notes = "로그인하는데 사용된다.")
	@PostMapping("/api/login")
	public ResponseEntity<TokenDto> authorize(@Valid @RequestBody LoginDto loginDto) {
		if (!userService.validatePassword(loginDto.getUsername(), loginDto.getPassword())) {
			throw new LoginException("해당 아이디와 패스워드 정보가 다릅니다.");
		}
		if (refreshTokenService.hasAnyRefreshTokenByUsername(loginDto.getUsername())) {
			refreshTokenService.deleteByUsername(loginDto.getUsername());
			throw new LoginException("다시 로그인을 시도해주세요.");
		}
		UsernamePasswordAuthenticationToken authenticationToken =
			new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword());
		Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

		SecurityContextHolder.getContext().setAuthentication(authentication);
		UserDetailsImpl userDetails = (UserDetailsImpl)authentication.getPrincipal();

		String accessToken = tokenProvider.createTokenFromPrincipal(userDetails);
		RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDetails.getUsername());

		TokenDto tokenDto = new TokenDto(accessToken, refreshToken.getToken(), userDetails.getUserId(),
			userDetails.getUsername(),
			userDetails.getAuthorities(),
			tokenProvider.getExp(accessToken),
			refreshToken.getExpiryDate().getEpochSecond());
		return ResponseEntity.ok(tokenDto);
	}

	@ApiOperation(value = "회원가입 기능", notes = "회원가입하는데 사용된다.")
	@PostMapping("/api/signup")
	public ResponseEntity<UserDto> signup(@Valid @RequestBody SignupDto signupDto) {
		User user = userService.signup(signupDto);
		shUtil.executeCommand(user.getId());
		return ResponseEntity.ok(new UserDto(user));
	}

	@PostMapping("/api/refresh")
	public ResponseEntity<TokenDto> refresh(@Valid @RequestBody TokenRefreshRequest request) {
		String token = request.getRefreshToken();
		User user = refreshTokenService.verifyExpirationAndGetUser(token);
		UserDetailsImpl userDetails = UserDetailsImpl.build(user);
		String accessToken = tokenProvider.createTokenFromPrincipal(userDetails);
		TokenDto tokenDto = new TokenDto(accessToken, token, userDetails.getUserId(),
			userDetails.getUsername(),
			userDetails.getAuthorities(),
			tokenProvider.getExp(accessToken),
			refreshTokenService.getRefreshTokenExp(token));
		return ResponseEntity.ok(tokenDto);
	}

	@PostMapping("/api/logout")
	public ResponseEntity<String> logoutUser(@Valid @RequestBody LogoutRequest logoutRequest) {
		refreshTokenService.deleteByUsername(logoutRequest.getUsername());
		return ResponseEntity.ok("Logout");
	}
}