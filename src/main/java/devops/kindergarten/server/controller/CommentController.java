package devops.kindergarten.server.controller;

import devops.kindergarten.server.dto.comment.*;
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

    @PostMapping("/api/comment/like")
    public CommentLikeResponseDto userLikeComment(@RequestBody CommentLikeRequestDto requestDto){
        return commentService.userLikeComment(requestDto);
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
    public List<CommentResponseDto> findAllByPostId(
            @RequestParam Long postId,
            @RequestParam(required = false) String username){
        if(username==null){
            return commentService.findAllByPostId(postId);
        }else{
            return commentService.findAllByPostIdAndUsername(postId,username);
        }
    }
//    @GetMapping("/api/comments/{userId}")
//    public List<CommentResponseDto> findAllByUserId(@PathVariable Long userId){
//        return commentService.findAllByUserId(userId);
//    }
    @GetMapping("/api/comments/{username}")
    public List<CommentResponseDto> findAllByUsername(@PathVariable String username){
        return commentService.findAllByUsername(username);
    }
}
