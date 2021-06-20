package devops.kindergarten.server.controller;

import devops.kindergarten.server.domain.User;
import devops.kindergarten.server.dto.UserDto;
import devops.kindergarten.server.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api(tags = {"회원가입 관련 컨트롤러"})
@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @ApiOperation(value = "회원가입 기능",notes="회원가입하는데 사용된다.")
    @PostMapping("/api/signup")
    public ResponseEntity<User> signup(@Valid @RequestBody UserDto userDto) {
        return ResponseEntity.ok(userService.signup(userDto));
    }

    @ApiOperation(value = "중복된 유저 아이디 검사기능",notes="회원가입 할 떄, 중복된 아이디가 있는지 확인한다.")
    @GetMapping("/api/validate/{username}")
    public ResponseEntity<String> check(@PathVariable String username) {
        return ResponseEntity.ok(userService.checkUsername(username));
    }

    @GetMapping("/api/user")
    @PreAuthorize("hasAnyRole('STUDENT','EDUCATOR','ADMIN')")
    public ResponseEntity<User> getMyUserInfo() {
        return ResponseEntity.ok(userService.getMyUserWithAuthorities().get());
    }

    @GetMapping("/api/user/{username}")
    @PreAuthorize("hasAnyRole('STUDENT','EDUCATOR','ADMIN')")
    public ResponseEntity<User> getUserInfo(@PathVariable String username) {
        return ResponseEntity.ok(userService.getUserWithAuthorities(username).get());
    }
}
