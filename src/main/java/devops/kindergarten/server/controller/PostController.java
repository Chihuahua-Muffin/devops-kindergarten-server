package devops.kindergarten.server.controller;

import devops.kindergarten.server.dto.post.PostListResponseDto;
import devops.kindergarten.server.dto.post.PostResponseDto;
import devops.kindergarten.server.dto.post.PostSaveRequestDto;
import devops.kindergarten.server.dto.post.PostUpdateRequestDto;
import devops.kindergarten.server.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @PostMapping("/api/post")
    public Long save(@RequestBody PostSaveRequestDto requestDto){
        return postService.save(requestDto);
    }

    @PutMapping("/api/post/{id}")
    public Long update(@PathVariable Long id, @RequestBody PostUpdateRequestDto requestDto){
        return postService.update(id,requestDto);
    }

    @GetMapping("/api/post/{id}")
    public PostResponseDto findById(@PathVariable Long id){
        return postService.findById(id);
    }

    @DeleteMapping("/api/post/{id}")
    public Long delete(@PathVariable Long id){
        postService.delete(id);
        return id;
    }

    @GetMapping("/api/post")
    public List<PostListResponseDto> findAllByCategoryAndLimitAndOffset(@RequestParam String category, @RequestParam int offset){
        return postService.findAllByCategoryAndLimitAndOffset(category,offset);
    }

    @GetMapping("/api/posts")
    public List<PostListResponseDto> findAllByLimitAndOffset(@RequestParam int offset){
        return postService.findAllByLimitAndOffset(offset);
    }
}

