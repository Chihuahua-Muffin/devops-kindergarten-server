package devops.kindergarten.server.controller;

import io.swagger.annotations.Api;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
	@GetMapping("/api/test")
	public ResponseEntity<String> test() {
		return ResponseEntity.ok("test");
	}

	@GetMapping("/api/test/admin")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<String> accessAuthorizeToAdmin() {
		return ResponseEntity.ok("AdminTest");
	}

	@GetMapping("/api/test/student")
	@PreAuthorize("hasAnyRole('STUDENT')")
	public ResponseEntity<String> accessAuthorizeToStudent() {
		return ResponseEntity.ok("STUDENT");
	}

	@GetMapping("/api/test/educator")
	@PreAuthorize("hasAnyRole('EDUCATOR')")
	public ResponseEntity<String> accessAuthorizeToEducator() {
		return ResponseEntity.ok("EDUCATOR");
	}
}
