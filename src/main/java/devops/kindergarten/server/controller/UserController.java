package devops.kindergarten.server.controller;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import devops.kindergarten.server.domain.User;
import devops.kindergarten.server.dto.InstanceIPDto;
import devops.kindergarten.server.dto.progress.ProgressRequestDto;
import devops.kindergarten.server.dto.progress.ProgressResponseDto;
import devops.kindergarten.server.service.ProgressService;
import devops.kindergarten.server.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Api(tags = {"회원가입 관련 컨트롤러"})
@RestController
@RequiredArgsConstructor
public class UserController {
	private final UserService userService;
	private final ProgressService progressService;

	@ApiOperation(value = "중복된 유저 아이디 검사기능", notes = "회원가입 할 떄, 중복된 아이디가 있는지 확인한다.")
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

	@DeleteMapping("/api/user/{id}")
	public ResponseEntity<Long> delete(@PathVariable Long id) {
		userService.delete(id);
		return ResponseEntity.ok(id);
	}

	@PutMapping("/api/user/{id}/ip")
	public ResponseEntity<String> setUserInstanceIp(@PathVariable Long id, @RequestBody InstanceIPDto ipDto) {
		return ResponseEntity.ok(userService.setInstanceIp(id, ipDto));
	}

	@PostMapping("/api/user/{id}/lecture")
	public ResponseEntity<ProgressResponseDto> setLectureProgress(@PathVariable Long id,
		@RequestBody ProgressRequestDto requestDto) {
		return ResponseEntity.ok(progressService.updateLectureProgress(id, requestDto));
	}

	@GetMapping("/api/user/{id}/lecture")
	public ResponseEntity<Map<Long, ProgressResponseDto>> getUserLectureProgresses(@PathVariable Long id) {
		return ResponseEntity.ok(progressService.getLectureProgresses(id));
	}
}
