package devops.kindergarten.server.controller;

import devops.kindergarten.server.ControllerTestSupport;
import devops.kindergarten.server.domain.Status;
import devops.kindergarten.server.domain.User;
import devops.kindergarten.server.exception.custom.UserNotFoundException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;


import java.util.Collections;

class UserControllerTest extends ControllerTestSupport {

    @Test
    public void 회원가입_테스트() throws Exception{
        //given

        //when
        ResponseEntity<User> result = signup(username,name,password,email,status);
        Long id = result.getBody().getId();

        //then
        User user = userRepository.findById(id).orElseThrow(UserNotFoundException::new);

        Assertions.assertThat(user.getName()).isEqualTo(name);
        Assertions.assertThat(user.getUsername()).isEqualTo(username);
        Assertions.assertThat(user.getEmail()).isEqualTo(email);
        Assertions.assertThat(passwordEncoder.matches(password,user.getPassword())).isEqualTo(true);
    }

    @Test
    public void 유저정보호출_테스트() throws Exception{
        //given
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:" + port + "/api/user/"+username;

        signup(username,name,password,email,status);
        String token = login(username,password).getBody().getToken();

        //when
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization","Bearer "+token);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        ResponseEntity<User> result = restTemplate.exchange(url, HttpMethod.GET,  new HttpEntity<>("parameters", headers), User.class);
        Long id = result.getBody().getId();

        //then
        User user = userRepository.findById(id).orElseThrow(UserNotFoundException::new);

        Assertions.assertThat(user.getName()).isEqualTo(name);
        Assertions.assertThat(user.getUsername()).isEqualTo(username);
        Assertions.assertThat(user.getEmail()).isEqualTo(email);
        Assertions.assertThat(passwordEncoder.matches(password,user.getPassword())).isEqualTo(true);
    }
}