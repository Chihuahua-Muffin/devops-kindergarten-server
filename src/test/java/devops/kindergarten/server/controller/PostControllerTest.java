package devops.kindergarten.server.controller;

import devops.kindergarten.server.domain.Post;
import devops.kindergarten.server.domain.Status;
import devops.kindergarten.server.domain.User;
import devops.kindergarten.server.dto.UserDto;
import devops.kindergarten.server.dto.post.*;
import devops.kindergarten.server.exception.custom.PostNotFoundException;
import devops.kindergarten.server.exception.custom.UserNotFoundException;
import devops.kindergarten.server.repository.PostRepository;
import devops.kindergarten.server.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PostControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private PostRepository postRepository;
    @Autowired
    private UserRepository userRepository;

    public ResponseEntity<Long> createPost(String title, String content, String category, User user) throws Exception{
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:" + port + "/api/post";
        PostSaveRequestDto requestDto = new PostSaveRequestDto(title,content, user.getUsername(), category);
        URI uri = new URI(url);

        return restTemplate.postForEntity(uri,requestDto,Long.class);
    }

    @Test
    public void 글_작성기능_테스트() throws Exception{
        //given
        String url = "http://localhost:" + port + "/api/post";

        User user = userRepository.findById(1L).orElseThrow(UserNotFoundException::new);
        String title = "title";
        String content = "content";
        String username = user.getUsername();
        String category = "develop";

        //when
        ResponseEntity<Long> result = createPost(title,content,category,user);

        //then
        List<Post> posts = postRepository.findAll();
        Post post = posts.get(posts.size()-1);

        Assertions.assertThat(result.getBody()).isEqualTo(post.getId());
        Assertions.assertThat(post.getAuthor().getId()).isEqualTo(user.getId());
        Assertions.assertThat(post.getUsername()).isEqualTo(username);
        Assertions.assertThat(post.getTitle()).isEqualTo(title);
        Assertions.assertThat(post.getContent()).isEqualTo(content);
        Assertions.assertThat(post.getCategory()).isEqualTo(category);
    }

    @Test
    public void 글_확인기능_테스트1 () throws Exception{
        //given
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:" + port + "/api/post";

        User user = userRepository.findById(1L).orElseThrow(UserNotFoundException::new);
        String title = "title";
        String username = user.getUsername();
        String content = "content";
        String category = "develop";
        ResponseEntity<Long> post = createPost(title,content,category,user);
        Long postId = post.getBody();

        URI uri = new URI(url+"/"+postId);

        //when
        ResponseEntity<PostResponseDto> result = restTemplate.getForEntity(uri,PostResponseDto.class);

        //then
        Assertions.assertThat(title).isEqualTo(result.getBody().getTitle());
        Assertions.assertThat(content).isEqualTo(result.getBody().getContent());
        Assertions.assertThat(username).isEqualTo(result.getBody().getUsername());
        Assertions.assertThat(category).isEqualTo(result.getBody().getCategory());
        Assertions.assertThat(1).isEqualTo(result.getBody().getHit());
    }
    @Test
    public void 글_확인기능_테스트2 () throws Exception{
        //given
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:" + port + "/api/post";

        User user = userRepository.findById(1L).orElseThrow(UserNotFoundException::new);
        String title = "title";
        String username = user.getUsername();
        String content = "content";
        String category = "develop";
        ResponseEntity<Long> post = createPost(title,content,category,user);
        Long postId = post.getBody();

        URI uri = new URI(url+"/"+postId+"/"+username);

        //when
        ResponseEntity<PostResponseDto> result = restTemplate.getForEntity(uri,PostResponseDto.class);

        //then
        Assertions.assertThat(title).isEqualTo(result.getBody().getTitle());
        Assertions.assertThat(content).isEqualTo(result.getBody().getContent());
        Assertions.assertThat(username).isEqualTo(result.getBody().getUsername());
        Assertions.assertThat(category).isEqualTo(result.getBody().getCategory());
        Assertions.assertThat(1).isEqualTo(result.getBody().getHit());
        Assertions.assertThat(false).isEqualTo(result.getBody().isViewerHasLike());
    }
    @Test
    public void 글_확인기능_테스트3 () throws Exception{
        //given
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:" + port + "/api/post";

        User user = userRepository.findById(1L).orElseThrow(UserNotFoundException::new);
        String title = "title";
        String username = user.getUsername();
        String content = "content";
        String category = "develop";
        ResponseEntity<Long> post = createPost(title,content,category,user);
        Long postId = post.getBody();

        URI uri = new URI(url+"/"+postId+"/"+username);
        URI likeUri = new URI(url+"/like");
        PostLikeRequestDto requestDto = new PostLikeRequestDto(username,postId);
        //when
        restTemplate.postForEntity(likeUri,requestDto,PostLikeResponseDto.class);
        ResponseEntity<PostResponseDto> result = restTemplate.getForEntity(uri,PostResponseDto.class);

        //then
        Assertions.assertThat(title).isEqualTo(result.getBody().getTitle());
        Assertions.assertThat(content).isEqualTo(result.getBody().getContent());
        Assertions.assertThat(username).isEqualTo(result.getBody().getUsername());
        Assertions.assertThat(category).isEqualTo(result.getBody().getCategory());
        Assertions.assertThat(1).isEqualTo(result.getBody().getHit());
        Assertions.assertThat(true).isEqualTo(result.getBody().isViewerHasLike());
    }

    @Test
    public void 글_좋아요기능_테스트1() throws Exception{
        //given
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:" + port + "/api/post/like";
        User user = userRepository.findById(1L).orElseThrow(UserNotFoundException::new);
        ResponseEntity<Long> post = createPost("title","content","develop",user);
        Long postId = post.getBody();
        String username = user.getUsername();

        PostLikeRequestDto requestDto = new PostLikeRequestDto(username,postId);
        URI uri = new URI(url);
        //when
        ResponseEntity<PostLikeResponseDto> result = restTemplate.postForEntity(uri,requestDto,PostLikeResponseDto.class);
        //then
        org.junit.jupiter.api.Assertions.assertEquals(result.getBody().getLikeCount(),1);
        org.junit.jupiter.api.Assertions.assertTrue(result.getBody().isViewerHasLike());
    }
    @Test
    public void 글_좋아요기능_테스트2() throws Exception{
        //given
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:" + port + "/api/post/like";
        User user1 = userRepository.findById(1L).orElseThrow(UserNotFoundException::new);
        User user2 = userRepository.findById(2L).orElseThrow(UserNotFoundException::new);
        ResponseEntity<Long> post = createPost("title","content","develop",user1);

        Long postId = post.getBody();
        String username1 = user1.getUsername();
        String username2 = user2.getUsername();

        PostLikeRequestDto requestDto1 = new PostLikeRequestDto(username1,postId);
        PostLikeRequestDto requestDto2 = new PostLikeRequestDto(username2,postId);
        URI uri = new URI(url);
        //when
        restTemplate.postForEntity(uri,requestDto1,PostLikeResponseDto.class);
        restTemplate.postForEntity(uri,requestDto2,PostLikeResponseDto.class);

        //then
        Post result = postRepository.findById(postId).orElseThrow(PostNotFoundException::new);
        org.junit.jupiter.api.Assertions.assertEquals(result.getLikeCount(),2);
    }

    @Test
    public void 글_수정기능_테스트() throws Exception{
        //given
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:" + port + "/api/post";
        User user = userRepository.findById(1L).orElseThrow(UserNotFoundException::new);
        ResponseEntity<Long> post = createPost("title","content","develop",user);

        Long postId = post.getBody();
        URI uri = new URI(url+"/"+postId);
        String updateTitle = "Update Title";
        String updateContent = "Update Content";
        String updateCategory = "Talk";
        PostUpdateRequestDto requestDto = new PostUpdateRequestDto(updateTitle,updateContent,updateCategory);
        //when
        restTemplate.put(uri,requestDto);
        ResponseEntity<PostResponseDto> result = restTemplate.getForEntity(uri,PostResponseDto.class);
        //then
        Assertions.assertThat(user.getName()).isEqualTo(result.getBody().getUsername());
        Assertions.assertThat(updateTitle).isEqualTo(result.getBody().getTitle());
        Assertions.assertThat(updateContent).isEqualTo(result.getBody().getContent());
        Assertions.assertThat(updateCategory).isEqualTo(result.getBody().getCategory());
        Assertions.assertThat(result.getBody().getCreatedDate()).isNotEqualTo(result.getBody().getUpdatedDate());
    }
    @Test
    public void 글_삭제기능_테스트() throws Exception{
        //given
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:" + port + "/api/post/1";
        URI uri = new URI(url);
        //when
        restTemplate.delete(uri);
        //then
        try {
            restTemplate.getForEntity(uri,PostResponseDto.class);
        }catch (HttpClientErrorException e){
            Assertions.assertThat(e.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        }
    }
    @Test
    public void 글_카테고리_목록기능_테스트() throws Exception{
        //given
        RestTemplate restTemplate = new RestTemplate();
        User user = userRepository.findById(1L).orElseThrow(UserNotFoundException::new);
        String url = "http://localhost:" + port + "/api/posts/develop?offset=0";
        for (int i = 0; i < 10; i++) {
            createPost("title"+i,"content"+i,"develop",user);
        }
        URI uri = new URI(url);
        //when
        ResponseEntity<List> result = restTemplate.getForEntity(uri,List.class);

        //then
        for (int i = 9; i >=0 ; i--) {
            Assertions.assertThat(result.getBody().get(9-i).toString().contains("title"+i));
            Assertions.assertThat(result.getBody().get(9-i).toString().contains("develop"));
        }
    }

    @Test
    public void 글_목록기능_테스트() throws Exception{
        //given
        RestTemplate restTemplate = new RestTemplate();
        User user = userRepository.findById(1L).orElseThrow(UserNotFoundException::new);
        String url = "http://localhost:" + port + "/api/posts?offset=0";
        for (int i = 0; i < 10; i++) {
            createPost("title"+i,"content"+i,"develop"+i,user);
        }
        URI uri = new URI(url);
        //when
        ResponseEntity<List> result = restTemplate.getForEntity(uri,List.class);

        //then
        for (int i = 9; i >=0 ; i--) {
            Assertions.assertThat(result.getBody().get(9-i).toString().contains("title"+i));
            Assertions.assertThat(result.getBody().get(9-i).toString().contains("develop"+i));
        }
    }
}