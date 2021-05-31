package devops.kindergarten.server.controller;

import devops.kindergarten.server.domain.Comment;
import devops.kindergarten.server.domain.Status;
import devops.kindergarten.server.domain.User;
import devops.kindergarten.server.dto.UserDto;
import devops.kindergarten.server.dto.comment.CommentSaveRequestDto;
import devops.kindergarten.server.dto.comment.CommentUpdateRequestDto;
import devops.kindergarten.server.dto.comment.RecommentSaveRequestDto;
import devops.kindergarten.server.dto.post.PostSaveRequestDto;
import devops.kindergarten.server.exception.custom.CommentNotFoundException;
import devops.kindergarten.server.repository.CommentRepository;
import devops.kindergarten.server.repository.PostRepository;
import devops.kindergarten.server.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.StringTokenizer;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CommentControllerTest {

    @LocalServerPort
    private int port;
    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    public HashMap<String,String> createUserAndPost() throws Exception {
        userRepository.deleteAll();
        postRepository.deleteAll();
        commentRepository.deleteAll();
        HashMap<String,String> result = new HashMap<>();

        RestTemplate restTemplate = new RestTemplate();
        String username = "ProtoSeo";
        String name = "seoseunghun";
        String password = "password";
        String email = "hello@naver.com";
        Status status = Status.STUDENT;

        String signupUrl = "http://localhost:" + port + "/api/signup";
        UserDto userDto = new UserDto(username,name,email,password,status.toString());
        restTemplate.postForEntity(new URI(signupUrl),userDto, User.class);

        String title = "title";
        String content = "content";
        String category = "develop";

        String postUrl = "http://localhost:" + port + "/api/post";
        PostSaveRequestDto requestDto = new PostSaveRequestDto(title,content, username, category);

        Long postId = restTemplate.postForEntity(new URI(postUrl),requestDto,Long.class).getBody();

        result.put("username",username);
        result.put("postId",postId.toString());
        return result;
    }

    public ResponseEntity<Long> createComment(String content, String username, Long postId) throws Exception{
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:" + port + "/api/comment";
        URI uri = new URI(url);
        CommentSaveRequestDto requestDto = new CommentSaveRequestDto(content,username,postId);
        return restTemplate.postForEntity(uri,requestDto,Long.class);
    }

    public ResponseEntity<Long> createRecomment(String content, String username, Long postId,Long parentId) throws Exception{
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:" + port + "/api/recomment";
        URI uri = new URI(url);
        RecommentSaveRequestDto requestDto = new RecommentSaveRequestDto(content,username,postId,parentId);
        return restTemplate.postForEntity(uri,requestDto,Long.class);
    }

    @Test
    public void 댓글_추가기능_테스트() throws Exception{
        //given
        HashMap<String,String> memory = createUserAndPost();
        String content = "댓글을 달았어요,,";
        String username = memory.get("username");
        Long postId = Long.parseLong(memory.get("postId"));

        //when
        createComment(content,username,postId);

        //then
        List<Comment> commentList = commentRepository.findAll();
        Comment comment = commentList.get(commentList.size()-1);

        assertEquals(comment.getContent(),content);
        assertEquals(comment.getUsername(),username);
        assertEquals(comment.getPost().getId(),postId);
        assertEquals(comment.getLike(),0);
    }
    @Test
    public void 대댓글_추가기능_테스트() throws Exception{
        //given
        HashMap<String,String> memory = createUserAndPost();
        String content = "대댓글을 달았어요,,";
        String username = memory.get("username");
        Long postId = Long.parseLong(memory.get("postId"));
        Long parentId = createComment(content,username,postId).getBody();

        //when
        createRecomment(content,username,postId,parentId);

        //then
        List<Comment> commentList = commentRepository.findAll();
        Comment recomment = commentList.get(commentList.size()-1);

        assertEquals(recomment.getContent(),content);
        assertEquals(recomment.getUsername(),username);
        assertEquals(recomment.getPost().getId(),postId);
        assertEquals(recomment.getParent().getId(),parentId);
        assertEquals(recomment.getLike(),0);
    }

    @Test
    public void 댓글_수정기능_테스트() throws Exception{
        //given
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:" + port + "/api/comment";
        HashMap<String,String> memory = createUserAndPost();
        String username = memory.get("username");
        Long postId = Long.parseLong(memory.get("postId"));
        Long commentId = createComment("댓글을 달았어요,,",username,postId).getBody();
        URI uri = new URI(url +"/"+commentId);

        String updateContent = "update Content";
        CommentUpdateRequestDto requestDto = new CommentUpdateRequestDto(updateContent);
        //when
        restTemplate.put(uri,requestDto);

        //then
        Comment comment  = commentRepository.findById(commentId).orElseThrow(CommentNotFoundException::new);
        assertEquals(comment.getContent(),updateContent);
        assertEquals(comment.getPost().getId(),postId);
        assertEquals(comment.getUsername(),username);

    }
    @Test
    public void  대댓글_수정기능_테스트() throws Exception{
        //given
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:" + port + "/api/comment";
        HashMap<String,String> memory = createUserAndPost();

        String username = memory.get("username");

        Long postId = Long.parseLong(memory.get("postId"));
        Long parentId = createComment("댓글을 달았어요,,",username,postId).getBody();
        Long recommentId = createRecomment("대댓글을 달았어요",username,postId,parentId).getBody();

        URI uri = new URI(url+"/"+recommentId);
        String updateContent = "update Content";
        CommentUpdateRequestDto requestDto = new CommentUpdateRequestDto(updateContent);

        //when
        restTemplate.put(uri,requestDto);

        //then
        Comment recomment  = commentRepository.findById(recommentId).orElseThrow(CommentNotFoundException::new);
        assertEquals(recomment.getContent(),updateContent);
        assertEquals(recomment.getPost().getId(),postId);
        assertEquals(recomment.getUsername(),username);
        assertEquals(recomment.getParent().getId(),parentId);
    }
    @Test
    public void 댓글_목록기능_테스트() throws Exception{
        //given
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:" + port + "/api/comments";
        HashMap<String,String> memory = createUserAndPost();

        String username = memory.get("username");
        Long postId = Long.parseLong(memory.get("postId"));
        Long parentId1 = createComment("11111",username,postId).getBody();
        Long parentId2 = createComment("22222",username,postId).getBody();
        Long parentId3 = createComment("33333",username,postId).getBody();

        createRecomment("대댓글 1 - 4",username,postId,parentId1);
        createRecomment("대댓글 1 - 5",username,postId,parentId1);
        createRecomment("대댓글 2 - 6",username,postId,parentId2);
        createRecomment("대댓글 3 - 7",username,postId,parentId3);
        createRecomment("대댓글 3 - 8",username,postId,parentId3);
        createRecomment("대댓글 3 - 9",username,postId,parentId3);

        URI uri = new URI(url+"?postId="+postId);
        //when
        ResponseEntity<List> result = restTemplate.getForEntity(uri,List.class);
        //then
        assertTrue(result.getBody().get(0).toString().contains("대댓글 1 - 4"));
        assertTrue(result.getBody().get(0).toString().contains("대댓글 1 - 5"));
        assertTrue(result.getBody().get(1).toString().contains("대댓글 2 - 6"));
        assertTrue(result.getBody().get(2).toString().contains("대댓글 3 - 7"));
        assertTrue(result.getBody().get(2).toString().contains("대댓글 3 - 8"));
        assertTrue(result.getBody().get(2).toString().contains("대댓글 3 - 9"));
    }

    @Test
    public void 댓글_유저목록기능_테스트() throws Exception{
        //given
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:" + port + "/api/comments";
        HashMap<String,String> memory = createUserAndPost();

        String username = memory.get("username");
        Long postId = Long.parseLong(memory.get("postId"));
        for (int i = 0; i < 4; i++) {
            createComment("content"+i,username,postId);
        }
        URI uri = new URI(url+"/"+username);
        //when
        ResponseEntity<List> result = restTemplate.getForEntity(uri,List.class);

        //then
        for (int i = 0; i < 4; i++) {
            System.out.println(result.getBody().get(i));
            assertTrue(result.getBody().get(i).toString().contains("content"+i));
        }
    }
}