package devops.kindergarten.server.controller;

import devops.kindergarten.server.dto.post.*;
import devops.kindergarten.server.exception.custom.PostNotFoundException;
import devops.kindergarten.server.service.PostService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = {"글 관련 컨트롤러"})
@RestController
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @ApiOperation(value = "글 작성 기능",notes="글을 작성하는데 사용된다.")
    @PostMapping("/api/post")
    public Long save(@RequestBody PostSaveRequestDto requestDto){
        return postService.save(requestDto);
    }

    @ApiOperation(value = "작성한 글 수정",notes="작성한 글을 수정하는데 사용된다.")
    @PutMapping("/api/post/{id}")
    public Long update(@PathVariable Long id, @RequestBody PostUpdateRequestDto requestDto){
        return postService.update(id,requestDto);
    }

    @ApiOperation(value = "선택한 글을 불러오는 기능",notes="선택한 글을 불러온다.")
    @GetMapping("/api/post/{id}")
    public PostResponseDto findById(@PathVariable Long id){
        return postService.findById(id);
    }

    @ApiOperation(value = "선택한 글을 불러오는 기능",notes="글을 선택한 유저의 정보를 포함해서 글을 불러온다.")
    @GetMapping("/api/post/{id}/{username}")
    public PostResponseDto findByIdWithViewer(@PathVariable Long id,@PathVariable String username){
        return postService.findByIdWithViewer(id,username);
    }

    @ApiOperation(value = "글 삭제 기능",notes="해당 글을 삭제하는데 사용된다.")
    @DeleteMapping("/api/post/{id}")
    public Long delete(@PathVariable Long id){
        postService.delete(id);
        return id;
    }

    // category 해당하는 글을 offset(글 페이지)에 맞춰서 10개씩 반환해준다.
    @ApiOperation(value = "글 목록 불러오기 기능 1",notes="해당 카테고리에 맞게 글 목록을 불러오는 기능이다.")
    @GetMapping("/api/posts/{category}")
    public List<PostListResponseDto> findAllByCategoryAndLimitAndOffset(@PathVariable String category, @RequestParam int offset){
        return postService.findAllByCategoryAndLimitAndOffset(category,offset);
    }

    // category 에 상관 없이 글을 offset(글 페이지)에 맞춰서 10개씩 반환해준다.
    @ApiOperation(value = "글 목록 불러오기 기능 2",notes="카테고리에 상관 없이 글 목록을 불러오는 기능이다.")
    @GetMapping("/api/posts")
    public List<PostListResponseDto> findAllByLimitAndOffset(@RequestParam int offset){
        return postService.findAllByLimitAndOffset(offset);
    }

    @ApiOperation(value = "글 검색 기능",notes="글을 검색하는데 사용된다.")
    @GetMapping("/api/posts/search")
    public List<PostListResponseDto> searchByTitleOrContent(@RequestParam(value = "tc") String tc) {
        if(tc.equals("")) throw new PostNotFoundException("검색 값이 공백입니다.");
        return postService.searchByTitleOrContent(tc);
    }
}

