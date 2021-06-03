package devops.kindergarten.server.controller;

import io.swagger.annotations.Api;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(hidden = true)
@RestController
public class TestController {
    @GetMapping("/api/test")
    public ResponseEntity<String> test(){
        return ResponseEntity.ok("test");
    }
}
