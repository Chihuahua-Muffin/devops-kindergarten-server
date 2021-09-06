package devops.kindergarten.server.service;

import devops.kindergarten.server.exception.custom.LoginException;
import devops.kindergarten.server.jwt.UserDetailsImpl;
import devops.kindergarten.server.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component("userDetailsService")
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(final String username) {
        // userRepository 에서 username 으로 검색해서 User 객체를 찾은 뒤, 해당 객체를 UserDetailsImpl 로 build 해서 반환한다.
        return userRepository.findOneWithAuthoritiesByUsername(username)
                .map(UserDetailsImpl::build)
                .orElseThrow(() -> new LoginException(username + " -> 데이터베이스에서 찾을 수 없습니다."));
    }
}