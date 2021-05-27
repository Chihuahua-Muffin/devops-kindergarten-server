package devops.kindergarten.server.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import devops.kindergarten.server.domain.Authority;
import devops.kindergarten.server.domain.User;
import devops.kindergarten.server.dto.LoginDto;
import devops.kindergarten.server.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class LoginControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void setup(){
        mvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
    }

    @Test
    public void 로그인_테스트() throws Exception{
        //given
        List<User> users = userRepository.findAll();
        String url = "http://localhost:" + port + "/api/login";
        User user = users.get(0);
        LoginDto requestDto = new LoginDto(user.getUsername(),"admin");

        //when
        ResultActions resultActions = mvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(new ObjectMapper().writeValueAsString(requestDto)));
        //then
        resultActions.andExpect(status().isOk());

    }
    @Test
    public void 로그인_실패_테스트() throws Exception{
        //given
        List<User> users = userRepository.findAll();
        String url = "http://localhost:" + port + "/api/login";
        User user = users.get(0);
        LoginDto requestDto = new LoginDto(user.getUsername(),"hello");

        //when
        ResultActions resultActions = mvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(new ObjectMapper().writeValueAsString(requestDto)));

        //then
        resultActions.andExpect(status().is4xxClientError());

    }
}