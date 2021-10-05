package devops.kindergarten.server.service;

import devops.kindergarten.server.domain.Comment;
import devops.kindergarten.server.domain.Lecture;
import devops.kindergarten.server.domain.User;
import devops.kindergarten.server.dto.comment.*;
import devops.kindergarten.server.exception.custom.CommentNotFoundException;
import devops.kindergarten.server.repository.CommentRepository;
import devops.kindergarten.server.repository.LectureRepository;
import devops.kindergarten.server.repository.UserRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService {
	private final CommentRepository commentRepository;
	private final LectureRepository lectureRepository;
	private final UserRepository userRepository;

	@Transactional
	public Long save(CommentSaveRequestDto requestDto, Long lectureId) {
		User user = userRepository.findById(requestDto.getUserId()).orElseThrow();
		Lecture lecture = lectureRepository.findById(lectureId).orElseThrow();
		Comment comment;
		if (requestDto.getParentId() == null) {
			comment = Comment.createComment(user, lecture, null, requestDto.getContent(), user.getUsername());
		} else {
			Comment parentComment = commentRepository.findById(requestDto.getParentId()).orElseThrow();
			comment = Comment.createComment(user, lecture, parentComment, requestDto.getContent(), user.getUsername());
		}
		return commentRepository.save(comment).getId();
	}

	@Transactional
	public Long update(Long id, CommentUpdateRequestDto requestDto) {
		Comment comment = commentRepository.findById(id)
			.orElseThrow(() -> new CommentNotFoundException("해당 댓글 없습니다. id=" + id));
		comment.update(requestDto.getContent());
		return id;
	}

	@Transactional(readOnly = true)
	public List<CommentResponseDto> findAllByPageId(Long pageId) {
		List<CommentResponseDto> result = new ArrayList<>();
		List<Comment> commentList = commentRepository.findAllByLectureId(pageId);
		for (Comment comment : commentList) {
			if (comment.getParent() == null) {
				result.add(new CommentResponseDto(comment));
			}
		}
		for (CommentResponseDto commentResponseDto : result) {
			for (Comment comment : commentList) {
				if (comment.getParent() != null) {
					if (commentResponseDto.getCommentId().equals(comment.getParent().getId())) {
						commentResponseDto.getRecommentList().add(new CommentResponseDto(comment));
					}
				}
			}
		}
		return result;
	}

	@Transactional
	public void delete(Long id) {
		Comment comment = commentRepository.findById(id)
			.orElseThrow(() -> new CommentNotFoundException("해당 덧글이 존재하지 않습니다."));
		commentRepository.delete(comment);
	}

	@Transactional(readOnly = true)
	public List<CommentResponseDto> findAllByUsername(String username) {
		return commentRepository.findAllByUsername(username).stream()
			.map(CommentResponseDto::new).collect(Collectors.toList());
	}

}
