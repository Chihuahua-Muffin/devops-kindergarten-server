package devops.kindergarten.server.controller;

import devops.kindergarten.server.domain.RefreshToken;
import devops.kindergarten.server.domain.User;
import devops.kindergarten.server.dto.*;
import devops.kindergarten.server.exception.custom.LoginException;
import devops.kindergarten.server.exception.custom.TokenRefreshException;
import devops.kindergarten.server.jwt.JwtFilter;
import devops.kindergarten.server.jwt.TokenProvider;
import devops.kindergarten.server.jwt.UserDetailsImpl;
import devops.kindergarten.server.service.RefreshTokenService;
import devops.kindergarten.server.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.validation.Valid;

@Api(tags = {"로그인 관련 컨트롤러"})
@RestController
@RequiredArgsConstructor
public class AuthController {
    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final UserService userService;
    private final RefreshTokenService refreshTokenService;

    @ApiOperation(value = "로그인 기능",notes="로그인하는데 사용된다.")
    @PostMapping("/api/login")
    public ResponseEntity<TokenDto> authorize(@Valid @RequestBody LoginDto loginDto) {
        if(!userService.validatePassword(loginDto.getUsername(), loginDto.getPassword())){
            throw new LoginException("해당 아이디와 패스워드 정보가 다릅니다.");
        }

//        UsernamePasswordAuthenticationToken authenticationToken =
//                new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword());

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        String jwt = tokenProvider.createToken(authentication);

        RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDetails.getUsername());

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);
        httpHeaders.add("Set-cookie",refreshToken.getToken());

        return new ResponseEntity<>(new TokenDto(jwt),httpHeaders,HttpStatus.OK);
    }

    @ApiOperation(value = "회원가입 기능",notes="회원가입하는데 사용된다.")
    @PostMapping("/api/signup")
    public ResponseEntity<User> signup(@Valid @RequestBody UserDto userDto) {
        return ResponseEntity.ok(userService.signup(userDto));
    }
    @PostMapping("/api/refresh")
    public ResponseEntity<?> refresh(@Valid @RequestBody TokenRefreshRequest request) {
        String requestRefreshToken = request.getRefreshToken();

        return refreshTokenService.findByToken(requestRefreshToken)
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUser)
                .map(user -> {
                    String jwt = tokenProvider.createTokenFromUsername(user.getUsername());
                    HttpHeaders httpHeaders = new HttpHeaders();
                    httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);
                    httpHeaders.add("Set-cookie",requestRefreshToken);

                    return new ResponseEntity<>(new TokenDto(jwt), httpHeaders, HttpStatus.OK);
                })
                .orElseThrow(() -> new TokenRefreshException(requestRefreshToken,
                        "RefreshToken이 존재하지 않습니다."));
    }

    @PostMapping("/api/logout")
    public ResponseEntity<String> logoutUser(@Valid @RequestBody LogoutRequest logoutRequest) {
        refreshTokenService.deleteByUsername(logoutRequest.getUsername());
        return ResponseEntity.ok("Logout");
    }
}