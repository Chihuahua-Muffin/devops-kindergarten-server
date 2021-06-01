package devops.kindergarten.server.service;

import devops.kindergarten.server.domain.Comment;
import devops.kindergarten.server.domain.Post;
import devops.kindergarten.server.domain.User;
import devops.kindergarten.server.dto.comment.*;
import devops.kindergarten.server.dto.post.PostListResponseDto;
import devops.kindergarten.server.dto.post.PostResponseDto;
import devops.kindergarten.server.exception.custom.CommentNotFoundException;
import devops.kindergarten.server.exception.custom.PostNotFoundException;
import devops.kindergarten.server.exception.custom.UserNotFoundException;
import devops.kindergarten.server.repository.CommentRepository;
import devops.kindergarten.server.repository.PostRepository;
import devops.kindergarten.server.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Transactional
    public Long save(CommentSaveRequestDto requestDto){
        User user = userRepository.findOneWithAuthoritiesByUsername(requestDto.getUsername())
                .orElseThrow(()->new UsernameNotFoundException("해당 유저가 존재하지 않습니다."));
        Post post = postRepository.findById(requestDto.getPostId())
                .orElseThrow(()->new PostNotFoundException("해당 게시글을 찾을 수 없습니다."));

        Comment comment = requestDto.toEntity(user,post);
        return commentRepository.save(comment).getId();
    }
    @Transactional
    public Long save(RecommentSaveRequestDto requestDto){
        User user = userRepository.findOneWithAuthoritiesByUsername(requestDto.getUsername())
                .orElseThrow(()->new UsernameNotFoundException("해당 유저가 존재하지 않습니다."));
        Post post = postRepository.findById(requestDto.getPostId())
                .orElseThrow(()->new PostNotFoundException("해당 게시글을 찾을 수 없습니다."));
        Comment parent = commentRepository.findById(requestDto.getParentId())
                .orElseThrow(()->new CommentNotFoundException("해당 덧글을 찾을 수 없습니다."));
        Comment comment = requestDto.toEntity(user,post,parent);
        return commentRepository.save(comment).getId();
    }
    @Transactional
    public Long update(Long id, CommentUpdateRequestDto requestDto){
        Comment comment = commentRepository.findById(id)
                .orElseThrow(()-> new CommentNotFoundException("해당 댓글 없습니다. id="+id));
        comment.update(requestDto.getContent());
        return id;
    }

    @Transactional(readOnly = true)
    public List<CommentResponseDto> findAllByPostId(Long postId){
        List<CommentResponseDto> result = new ArrayList<>();
        List<Comment> commentList = commentRepository.findAllByPostId(postId);
        for (Comment comment : commentList) {
            if (comment.getParent() == null) {
                result.add(new CommentResponseDto(comment,false));
            }
        }
        for (CommentResponseDto commentResponseDto : result) {
            for (Comment comment : commentList) {
                if(comment.getParent()!=null){
                    if (commentResponseDto.getId().equals(comment.getParent().getId())) {
                        commentResponseDto.getRecommentList().add(new CommentResponseDto(comment,false));
                    }
                }
            }
        }
        return result;
    }


    @Transactional(readOnly = true)
    public List<CommentResponseDto> findAllByPostIdAndUsername(Long postId, String username) {
        List<CommentResponseDto> result = new ArrayList<>();
        List<Comment> commentList = commentRepository.findAllByPostId(postId);
        User user = userRepository.findByUsername(username)
                .orElseThrow(UserNotFoundException::new);
        Set<Long> userCommentLikeIdList = user.getCommentLikeIdList();
        for (Comment comment : commentList) {
            if (comment.getParent() == null) {
                if(userCommentLikeIdList.contains(comment.getId())){
                    result.add(new CommentResponseDto(comment,true));
                }else{
                    result.add(new CommentResponseDto(comment,false));
                }
            }
        }
        for (CommentResponseDto commentResponseDto : result) {
            for (Comment comment : commentList) {
                if(comment.getParent()!=null){
                    if (commentResponseDto.getId().equals(comment.getParent().getId())) {
                        if(userCommentLikeIdList.contains(comment.getId())){
                            commentResponseDto.getRecommentList().add(new CommentResponseDto(comment,true));
                        }else{
                            commentResponseDto.getRecommentList().add(new CommentResponseDto(comment,false));
                        }
                    }
                }
            }
        }
        return result;
    }

    /*@Transactional(readOnly = true)
    public List<CommentResponseDto> findAllByUserId(Long userId){
        return commentRepository.findAllByWriterId(userId).stream()
                .map(CommentResponseDto::new).collect(Collectors.toList());
    }*/
    @Transactional
    public void delete(Long id){
        Comment comment = commentRepository.findById(id)
                .orElseThrow(()->new CommentNotFoundException("해당 덧글이 존재하지 않습니다."));
        commentRepository.delete(comment);
    }
    @Transactional(readOnly = true)
    public List<CommentResponseDto> findAllByUsername(String username){
        User user = userRepository.findByUsername(username)
                .orElseThrow(UserNotFoundException::new);
        Set<Long> userCommentLikeIdList = user.getCommentLikeIdList();
        return commentRepository.findAllByUsername(username).stream()
                .map((entity)->new CommentResponseDto(entity,userCommentLikeIdList.contains(entity.getId()))).collect(Collectors.toList());
    }

    @Transactional
    public CommentLikeResponseDto userLikeComment(CommentLikeRequestDto requestDto) {
        Long commentId = requestDto.getCommentId();
        String username = requestDto.getUsername();

        User user = userRepository.findByUsername(username)
                .orElseThrow(()->new UserNotFoundException("해당 유저가 존재하지 않습니다."));
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(()->new CommentNotFoundException("해당 댓글이 존재하지 않습니다."));
        int likeCount = comment.getLikeCount();
        boolean viewerHasLike = user.getCommentLikeIdList().contains(commentId);

        if(viewerHasLike){
            user.getCommentLikeIdList().remove(commentId);
            comment.setLikeCount(--likeCount);
        }else {
            user.getCommentLikeIdList().add(commentId);
            comment.setLikeCount(++likeCount);
        }
        commentRepository.save(comment);
        userRepository.save(user);
        return new CommentLikeResponseDto(!viewerHasLike,likeCount);
    }

}
