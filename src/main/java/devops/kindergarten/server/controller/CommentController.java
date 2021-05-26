package devops.kindergarten.server.controller;

import devops.kindergarten.server.dto.comment.CommentResponseDto;
import devops.kindergarten.server.dto.comment.CommentSaveRequestDto;
import devops.kindergarten.server.dto.comment.CommentUpdateRequestDto;
import devops.kindergarten.server.dto.comment.RecommentSaveRequestDto;
import devops.kindergarten.server.dto.post.PostListResponseDto;
import devops.kindergarten.server.dto.post.PostResponseDto;
import devops.kindergarten.server.dto.post.PostSaveRequestDto;
import devops.kindergarten.server.dto.post.PostUpdateRequestDto;
import devops.kindergarten.server.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/api/comment")
    public Long save(@RequestBody CommentSaveRequestDto requestDto){
        return commentService.save(requestDto);
    }

    @PostMapping("/api/recomment")
    public Long save(@RequestBody RecommentSaveRequestDto requestDto){
        return commentService.save(requestDto);
    }

    @PutMapping("/api/comment/{id}")
    public Long update(@PathVariable Long id, @RequestBody CommentUpdateRequestDto requestDto){
        return commentService.update(id,requestDto);
    }

    @DeleteMapping("/api/comment/{id}")
    public Long delete(@PathVariable Long id){
        commentService.delete(id);
        return id;
    }

    @GetMapping("/api/comments")
    public List<CommentResponseDto> findAllByPostId(@RequestParam Long postId){
        return commentService.findAllByPostId(postId);
    }

    @GetMapping("/api/comments/{userId}")
    public List<CommentResponseDto> findAllByUserId(@PathVariable Long userId){
        return commentService.findAllByUserId(userId);
    }
}
