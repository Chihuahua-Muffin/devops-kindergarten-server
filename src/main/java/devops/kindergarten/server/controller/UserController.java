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

	@ApiOperation(value = "해당 유저의 인스턴스 IP를 변경 관련", notes = "인스턴스를 생성하는 Script 에서 실행할 API 이다.")
	@PutMapping("/api/user/{id}/ip")
	public ResponseEntity<String> setUserInstanceIp(@PathVariable Long id, @RequestBody InstanceIPDto ipDto) {
		return ResponseEntity.ok(userService.setInstanceIp(id, ipDto));
	}

	@ApiOperation(value = "해당 유저의 인스턴스 IP를 읽는 기능", notes = "실습 페이지에서 해당 유저의 인스턴스의 IP를 불러올 때 사용한다.")
	@GetMapping("/api/user/{id}/ip")
	public ResponseEntity<String> getUserInstanceIp(@PathVariable Long id) {
		return ResponseEntity.ok(userService.getInstanceIp(id));
	}

	@ApiOperation(value = "해당 유저의 실습 진행도 수정 관련", notes = "실습 진행하면서 유저의 진행도를 업데이트할 때 사용")
	@PutMapping("/api/user/{id}/lecture")
	public ResponseEntity<ProgressResponseDto> setLectureProgress(@PathVariable Long id,
		@RequestBody ProgressRequestDto requestDto) {
		return ResponseEntity.ok(progressService.updateLectureProgress(id, requestDto));
	}

	@ApiOperation(value = "해당 유저의 전체 실습 진행도 관련", notes = "실습 페이지에서 사용하면서 유저의 진행도를 파악하기 위함이다.")
	@GetMapping("/api/user/{id}/lecture")
	public ResponseEntity<Map<Long, ProgressResponseDto>> getUserLectureProgresses(@PathVariable Long id) {
		return ResponseEntity.ok(progressService.getLectureProgresses(id));
	}
}
