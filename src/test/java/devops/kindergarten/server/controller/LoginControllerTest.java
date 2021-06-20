package devops.kindergarten.server.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import devops.kindergarten.server.ControllerTestSupport;
import devops.kindergarten.server.domain.Authority;
import devops.kindergarten.server.domain.Status;
import devops.kindergarten.server.domain.User;
import devops.kindergarten.server.dto.LoginDto;
import devops.kindergarten.server.dto.TokenDto;
import devops.kindergarten.server.exception.custom.UserNotFoundException;
import devops.kindergarten.server.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.WebApplicationContext;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


class LoginControllerTest extends ControllerTestSupport {

    @Test
    public void 로그인_테스트() throws Exception{
        //given
        signup(username,name,password,email,status);

        //when
        ResponseEntity<TokenDto> result = login(username, password);

        //then
        Assertions.assertEquals(result.getStatusCode(), HttpStatus.OK);
    }
    @Test
    public void 로그인_실패_테스트1() throws Exception{
        //given
        signup(username,name,password,email,status);

        //when

        //then
        Assertions.assertThrows(RuntimeException.class,
                () -> login("InvalidUsername", password));
    }

    @Test
    public void 로그인_실패_테스트2() throws Exception{
        //given
        signup(username,name,password,email,status);

        //when

        //then
        Assertions.assertThrows(RuntimeException.class,
                () -> login(username, "InvalidPassword"));
    }

}