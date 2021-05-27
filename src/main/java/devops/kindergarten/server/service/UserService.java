package devops.kindergarten.server.service;

import devops.kindergarten.server.domain.Authority;
import devops.kindergarten.server.domain.User;
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
    public String checkUsername(String username){
        if (userRepository.findOneWithAuthoritiesByUsername(username).orElse(null) != null) {
            throw new SignUpException("이미 가입한 회원이 있습니다.");
        }
        return "사용 가능한 아이디 입니다.";
    }

    @Transactional(readOnly = true)
    public boolean validatePassword(String username,String password){
        User user = userRepository.findByUsername(username)
                .orElseThrow(()->new UserNotFoundException("해당 유저를 찾을 수 없습니다."));
        return passwordEncoder.matches(password,user.getPassword());
    }
    @Transactional
    public User signup(UserDto userDto) {
        if (userRepository.findOneWithAuthoritiesByUsername(userDto.getUsername()).orElse(null) != null) {
            throw new SignUpException("이미 가입한 회원이 있습니다.");
        }

        Authority authority = Authority.builder()
                .authorityName(userDto.getStatus().toString())
                .build();

        User user = User.builder()
                .username(userDto.getUsername())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .email(userDto.getEmail())
                .name(userDto.getName())
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
}