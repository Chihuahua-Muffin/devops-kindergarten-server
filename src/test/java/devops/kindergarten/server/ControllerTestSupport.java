package devops.kindergarten.server;

import devops.kindergarten.server.domain.Status;
import devops.kindergarten.server.domain.User;
import devops.kindergarten.server.dto.LoginDto;
import devops.kindergarten.server.dto.TokenDto;
import devops.kindergarten.server.dto.UserDto;
import devops.kindergarten.server.dto.comment.CommentSaveRequestDto;
import devops.kindergarten.server.dto.comment.RecommentSaveRequestDto;
import devops.kindergarten.server.dto.dictionary.DictionaryRequestDto;
import devops.kindergarten.server.dto.post.PostSaveRequestDto;
import devops.kindergarten.server.repository.CommentRepository;
import devops.kindergarten.server.repository.DictionaryRepository;
import devops.kindergarten.server.repository.PostRepository;
import devops.kindergarten.server.repository.UserRepository;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.List;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql("/truncate.sql")
public abstract class ControllerTestSupport {
    @LocalServerPort
    protected int port;

    @Autowired
    protected UserRepository userRepository;

    @Autowired
    protected PostRepository postRepository;

    @Autowired
    protected PasswordEncoder passwordEncoder;

    @Autowired
    protected CommentRepository commentRepository;

    @Autowired
    protected DictionaryRepository dictionaryRepository;

    protected final String username = "username";
    protected final String name = "name";
    protected final String password = "password";
    protected final String email = "signup@test.com";
    protected final Status status = Status.ROLE_STUDENT;
    protected final String title = "title";
    protected final String content = "content";
    protected final String category = "category";

    protected ResponseEntity<User> signup(String username, String name, String password, String email, Status status) throws Exception {
        RestTemplate restTemplate = new RestTemplate();

        UserDto requestDto = new UserDto(username,name,email,password,status.toString());

        String url = "http://localhost:" + port + "/api/signup";
        URI uri = new URI(url);

        return restTemplate.postForEntity(uri, requestDto, User.class);
    }

    protected ResponseEntity<TokenDto> login(String username, String password) throws Exception {
        RestTemplate restTemplate = new RestTemplate();

        LoginDto requestDto = new LoginDto(username,password);

        String url = "http://localhost:" + port + "/api/login";
        URI uri = new URI(url);

        return restTemplate.postForEntity(uri, requestDto, TokenDto.class);
    }

    protected ResponseEntity<Long> createPost(String title, String content, String category, User user) throws Exception{
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:" + port + "/api/post";
        PostSaveRequestDto requestDto = new PostSaveRequestDto(title,content, user.getUsername(), category);
        URI uri = new URI(url);

        return restTemplate.postForEntity(uri,requestDto,Long.class);
    }
    protected ResponseEntity<Long> createComment(String content, String username, Long postId) throws Exception{
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:" + port + "/api/comment";
        URI uri = new URI(url);
        CommentSaveRequestDto requestDto = new CommentSaveRequestDto(content,username,postId);
        return restTemplate.postForEntity(uri,requestDto,Long.class);
    }

    protected ResponseEntity<Long> createRecomment(String content, String username, Long postId,Long parentId) throws Exception{
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:" + port + "/api/recomment";
        URI uri = new URI(url);
        RecommentSaveRequestDto requestDto = new RecommentSaveRequestDto(content,username,postId,parentId);
        return restTemplate.postForEntity(uri,requestDto,Long.class);
    }

    protected ResponseEntity<Long> createDictionary(String wordEnglish, String wordKorean, String description, List<String> tagList) throws Exception{
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:" + port + "/api/dictionary";
        URI uri = new URI(url);
        DictionaryRequestDto requestDto = new DictionaryRequestDto(wordEnglish,wordKorean,description,tagList);
        return restTemplate.postForEntity(uri,requestDto,Long.class);
    }

}
