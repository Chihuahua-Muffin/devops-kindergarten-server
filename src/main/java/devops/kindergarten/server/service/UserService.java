package devops.kindergarten.server.service;

import devops.kindergarten.server.domain.Authority;
import devops.kindergarten.server.domain.User;
import devops.kindergarten.server.dto.InstanceIPDto;
import devops.kindergarten.server.dto.SignupDto;
import devops.kindergarten.server.dto.UserDto;
import devops.kindergarten.server.exception.custom.SignUpException;
import devops.kindergarten.server.exception.custom.UserNotFoundException;
import devops.kindergarten.server.repository.UserRepository;
import devops.kindergarten.server.util.SecurityUtil;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	@Transactional(readOnly = true)
	public String checkUsername(String username) {
		if (userRepository.findOneWithAuthoritiesByUsername(username).orElse(null) != null) {
			throw new SignUpException("이미 가입한 회원이 있습니다.");
		}
		return "사용 가능한 아이디 입니다.";
	}

	@Transactional(readOnly = true)
	public boolean validatePassword(String username, String password) {
		User user = userRepository.findByUsername(username)
			.orElseThrow(() -> new UserNotFoundException("해당 유저를 찾을 수 없습니다."));
		return passwordEncoder.matches(password, user.getPassword());
	}

	@Transactional
	public User signup(SignupDto signupDto) {
		if (userRepository.findOneWithAuthoritiesByUsername(signupDto.getUsername()).orElse(null) != null) {
			throw new SignUpException("이미 가입한 회원이 있습니다.");
		}

		Authority authority = Authority.builder()
			.authorityName(signupDto.getStatus())
			.build();

		User user = User.builder()
			.username(signupDto.getUsername())
			.password(passwordEncoder.encode(signupDto.getPassword()))
			.email(signupDto.getEmail())
			.name(signupDto.getName())
			.authorities(Collections.singleton(authority))
			.build();

		return userRepository.save(user);
	}

	@Transactional(readOnly = true)
	public Optional<User> getUserWithAuthorities(String username) {
		return userRepository.findOneWithAuthoritiesByUsername(username);
	}

	@Transactional(readOnly = true)
	public Optional<User> getMyUserWithAuthorities() {
		return SecurityUtil.getCurrentUsername().flatMap(userRepository::findOneWithAuthoritiesByUsername);
	}

	@Transactional
	public Long delete(Long id) {
		User user = userRepository.findById(id).orElseThrow(UserNotFoundException::new);
		userRepository.delete(user);
		return id;
	}

	@Transactional
	public String setInstanceIp(Long userId, InstanceIPDto ipDto) {
		User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
		user.setInstanceIP("http://" +ipDto.getPublicIp()+":3000");
		return ipDto.getPublicIp();
	}

	@Transactional(readOnly = true)
	public String getInstanceIp(Long userId) {
		User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
		return user.getInstanceIP();
	}
}