package devops.kindergarten.server.service;

import devops.kindergarten.server.jwt.TokenProvider;
import devops.kindergarten.server.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class SecurityService {
    private final TokenProvider tokenProvider;
    private final UserRepository userRepository;

}
