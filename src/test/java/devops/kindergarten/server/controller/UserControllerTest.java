package devops.kindergarten.server.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import devops.kindergarten.server.domain.Status;
import devops.kindergarten.server.domain.User;
import devops.kindergarten.server.dto.UserDto;
import devops.kindergarten.server.repository.UserRepository;
import devops.kindergarten.server.service.UserService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.persistence.EntityManager;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserControllerTest {
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
    @WithMockUser("ADMIN")
    public void 회원가입_테스트() throws Exception{
        //given
        String username = "helloTest1";
        String name = "seo";
        String password = "helloPassword";
        String email = "hello@naver.com";
        Status status = Status.STUDENT;

        UserDto userDto = new UserDto(username,name,email,password,status.toString());

        String url = "http://localhost:" + port + "/api/signup";

        //when
        ResultActions resultActions = mvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(new ObjectMapper().writeValueAsString(userDto)))
                .andExpect(status().isOk());

        //then
        Optional<User> opt = userRepository.findOneWithAuthoritiesByUsername(username);
        if(opt.isPresent()){
            User user = opt.get();
            System.out.println("UserControllerTest.회원가입_테스트"+user.getId());
            Assertions.assertThat(user.getEmail()).isEqualTo(email);
            Assertions.assertThat(user.getName()).isEqualTo(name);
            Assertions.assertThat(user.getUsername()).isEqualTo(username);
        }
    }
}