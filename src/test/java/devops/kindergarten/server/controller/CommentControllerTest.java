package devops.kindergarten.server.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import devops.kindergarten.server.ControllerTestSupport;
import devops.kindergarten.server.domain.Comment;
import devops.kindergarten.server.domain.Status;
import devops.kindergarten.server.domain.User;
import devops.kindergarten.server.dto.UserDto;
import devops.kindergarten.server.dto.comment.*;
import devops.kindergarten.server.dto.post.PostSaveRequestDto;
import devops.kindergarten.server.exception.custom.CommentNotFoundException;
import devops.kindergarten.server.exception.custom.UserNotFoundException;
import devops.kindergarten.server.repository.CommentRepository;
import devops.kindergarten.server.repository.PostRepository;
import devops.kindergarten.server.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class CommentControllerTest extends ControllerTestSupport {
    @BeforeEach
    private void setup() throws Exception {
        signup(username, name, password, email, status);
    }
    @Test
    public void 댓글_추가기능_테스트() throws Exception{
        //given
        User user = userRepository.findByUsername(username).orElseThrow(UserNotFoundException::new);
        Long postId = createPost(title, content, category, user).getBody();

        //when
        createComment(content,username,postId);

        //then
        List<Comment> commentList = commentRepository.findAll();
        Comment comment = commentList.get(commentList.size()-1);

        assertEquals(comment.getContent(),content);
        assertEquals(comment.getUsername(),username);
        assertEquals(comment.getPost().getId(),postId);
        assertEquals(comment.getLikeCount(),0);
    }
    @Test
    public void 대댓글_추가기능_테스트() throws Exception{
        //given
        User user = userRepository.findByUsername(username).orElseThrow(UserNotFoundException::new);
        Long postId = createPost(title, content, category, user).getBody();

        Long parentId = createComment(content,username,postId).getBody();

        //when
        createRecomment("대댓글",username,postId,parentId);

        //then
        List<Comment> commentList = commentRepository.findAll();
        Comment recomment = commentList.get(commentList.size()-1);

        assertEquals(recomment.getContent(),"대댓글");
        assertEquals(recomment.getUsername(),username);
        assertEquals(recomment.getPost().getId(),postId);
        assertEquals(recomment.getParent().getId(),parentId);
        assertEquals(recomment.getLikeCount(),0);
    }

    @Test
    public void 댓글_수정기능_테스트() throws Exception{
        //given
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:" + port + "/api/comment";
        User user = userRepository.findByUsername(username).orElseThrow(UserNotFoundException::new);
        Long postId = createPost(title, content, category, user).getBody();
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

        User user = userRepository.findByUsername(username).orElseThrow(UserNotFoundException::new);
        Long postId = createPost(title, content, category, user).getBody();
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
    public void 댓글_좋아요기능_테스트() throws Exception{
        //given
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:" + port + "/api";
        User user = userRepository.findByUsername(username).orElseThrow(UserNotFoundException::new);
        Long postId = createPost(title, content, category, user).getBody();

        Long parentId1 = createComment("댓글 1",username,postId).getBody();
        Long parentId2 = createComment("댓글 2",username,postId).getBody();
        Long parentId3 = createComment("댓글 3",username,postId).getBody();

        Long recommentId1 = createRecomment("대댓글 1 - 4",username,postId,parentId1).getBody();
        Long recommentId2 = createRecomment("대댓글 1 - 5",username,postId,parentId1).getBody();
        Long recommentId3 = createRecomment("대댓글 2 - 6",username,postId,parentId2).getBody();
        Long recommentId4 = createRecomment("대댓글 3 - 7",username,postId,parentId3).getBody();
        Long recommentId5 = createRecomment("대댓글 3 - 8",username,postId,parentId3).getBody();
        Long recommentId6 = createRecomment("대댓글 3 - 9",username,postId,parentId3).getBody();

        URI uri = new URI(url+"/comments?postId="+postId+"&username="+username);
        URI likeUri = new URI(url+"/comment/like");

        CommentLikeRequestDto requestDto1 = new CommentLikeRequestDto(parentId1,username);
        CommentLikeRequestDto requestDto2 = new CommentLikeRequestDto(parentId3,username);
        CommentLikeRequestDto requestDto3 = new CommentLikeRequestDto(recommentId1,username);
        CommentLikeRequestDto requestDto4 = new CommentLikeRequestDto(recommentId3,username);
        CommentLikeRequestDto requestDto5 = new CommentLikeRequestDto(recommentId5,username);
        //when
        restTemplate.postForEntity(likeUri,requestDto1, CommentLikeResponseDto.class);
        restTemplate.postForEntity(likeUri,requestDto2, CommentLikeResponseDto.class);
        restTemplate.postForEntity(likeUri,requestDto3, CommentLikeResponseDto.class);
        restTemplate.postForEntity(likeUri,requestDto4, CommentLikeResponseDto.class);
        restTemplate.postForEntity(likeUri,requestDto5, CommentLikeResponseDto.class);

        ResponseEntity<List> result = restTemplate.getForEntity(uri,List.class);
        //then
        System.out.println(result.getBody());
    }
    @Test
    public void 댓글_목록기능_테스트() throws Exception{
        //given
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:" + port + "/api/comments";
        User user = userRepository.findByUsername(username).orElseThrow(UserNotFoundException::new);
        Long postId = createPost(title, content, category, user).getBody();

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
        User user = userRepository.findByUsername(username).orElseThrow(UserNotFoundException::new);
        Long postId = createPost(title, content, category, user).getBody();

        for (int i = 0; i < 4; i++) {
            createComment("content"+i,username,postId);
        }
        URI uri = new URI(url+"/"+username);
        //when
        ResponseEntity<List> result = restTemplate.getForEntity(uri,List.class);

        //then
        for (int i = 0; i < result.getBody().size(); i++) {
            System.out.println(result.getBody().get(i));
        }
    }
}