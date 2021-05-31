package devops.kindergarten.server.controller;

import devops.kindergarten.server.dto.post.PostListResponseDto;
import devops.kindergarten.server.dto.post.PostResponseDto;
import devops.kindergarten.server.dto.post.PostSaveRequestDto;
import devops.kindergarten.server.dto.post.PostUpdateRequestDto;
import devops.kindergarten.server.exception.custom.DictionaryNotFoundException;
import devops.kindergarten.server.exception.custom.PostNotFoundException;
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

    // category 해당하는 글을 offset(글 페이지)에 맞춰서 10개씩 반환해준다.
    @GetMapping("/api/posts/{category}")
    public List<PostListResponseDto> findAllByCategoryAndLimitAndOffset(@PathVariable String category, @RequestParam int offset){
        return postService.findAllByCategoryAndLimitAndOffset(category,offset);
    }
    // category 에 상관 없이 글을 offset(글 페이지)에 맞춰서 10개씩 반환해준다.
    @GetMapping("/api/posts")
    public List<PostListResponseDto> findAllByLimitAndOffset(@RequestParam int offset){
        return postService.findAllByLimitAndOffset(offset);
    }

    @GetMapping("/api/posts/search")
    public List<PostListResponseDto> searchByTitleOrContent(@RequestParam(value = "tc") String tc) {
        if(tc.equals("")) throw new PostNotFoundException("검색 값이 공백입니다.");
        return postService.searchByTitleOrContent(tc);
    }
}

