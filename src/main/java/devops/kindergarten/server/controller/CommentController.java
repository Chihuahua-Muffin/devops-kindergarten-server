package devops.kindergarten.server.controller;

import devops.kindergarten.server.domain.Category;
import devops.kindergarten.server.dto.comment.*;
import devops.kindergarten.server.service.CommentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.Collections;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Api(tags = {"댓글관련 기능 컨트롤러"})
@RestController
@RequiredArgsConstructor
public class CommentController {
	private final CommentService commentService;

	@ApiOperation(value = "댓글 등록", notes = "댓글을 등록하는데 사용된다.")
	@PostMapping("/api/comment")
	public Long save(@RequestBody CommentSaveRequestDto requestDto) {
		Long lectureId = requestDto.getPageId();
		if (lectureId == null) {
			lectureId = Category.categoryToPageId(requestDto.getPageName()).orElseThrow();
		}
		return commentService.save(requestDto, lectureId);
	}

	@ApiOperation(value = "댓글 수정", notes = "댓글을 수정하는데 사용된다.")
	@PutMapping("/api/comment/{id}")
	public Long update(@PathVariable Long id, @RequestBody CommentUpdateRequestDto requestDto) {
		return commentService.update(id, requestDto);
	}

	@ApiOperation(value = "댓글 삭제", notes = "댓글을 삭제하는데 사용된다.")
	@DeleteMapping("/api/comment/{id}")
	public Long delete(@PathVariable Long id) {
		commentService.delete(id);
		return id;
	}

	@ApiOperation(value = "댓글 목록 불러오기", notes = "해당 PostId의 post에 작성된 댓글들을 불러온다.")
	@GetMapping("/api/comments")
	public List<CommentResponseDto> findAllByPageId(@RequestParam(value = "id") Optional<Long> pageId,
		@RequestParam(value = "name") Optional<String> pageName) {
		if (pageId.isEmpty() && pageName.isPresent()) {
			return commentService.findAllByPageId(Category.categoryToPageId(pageName.get()).orElseThrow());
		} else if (pageId.isPresent() && pageName.isEmpty()) {
			return commentService.findAllByPageId(pageId.get());
		} else {
			return Collections.emptyList();
		}
	}

	@ApiOperation(value = "유저가 작성한 댓글 목록 불러오기", notes = "유저가 작성한 댓글 목록 불러온다.")
	@GetMapping("/api/comments/{username}")
	public List<CommentResponseDto> findAllByUsername(@PathVariable String username) {
		return commentService.findAllByUsername(username);
	}
}
