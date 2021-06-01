package devops.kindergarten.server.service;

import devops.kindergarten.server.domain.Post;
import devops.kindergarten.server.domain.User;
import devops.kindergarten.server.dto.post.*;
import devops.kindergarten.server.exception.custom.PostNotFoundException;
import devops.kindergarten.server.exception.custom.UserNotFoundException;
import devops.kindergarten.server.repository.PostRepository;
import devops.kindergarten.server.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Transactional
    public Long save(PostSaveRequestDto requestDto){
        User user = userRepository.findOneWithAuthoritiesByUsername(requestDto.getUsername())
                .orElseThrow(()->new UsernameNotFoundException("해당 유저가 존재하지 않습니다."));
        Post post = requestDto.toEntity(user);
        return postRepository.save(post).getId();
    }

    @Transactional
    public Long update(Long id, PostUpdateRequestDto requestDto){
        Post post = postRepository.findById(id)
                .orElseThrow(()-> new PostNotFoundException("해당 게시글이 없습니다. id="+id));
        post.update(requestDto.getTitle(), requestDto.getContent(),requestDto.getCategory());

        return id;
    }
    @Transactional
    public PostResponseDto findById(Long id){
        Post entity = postRepository.findById(id)
                .orElseThrow(()->new PostNotFoundException("해당 게시글이 없습니다. id="+id));
        entity.setHit(entity.getHit()+1);
        postRepository.save(entity);
        return new PostResponseDto(entity,false);
    }
    @Transactional
    public PostResponseDto findByIdWithViewer(Long id,String username){
        Post entity = postRepository.findById(id)
                .orElseThrow(()->new PostNotFoundException("해당 게시글이 없습니다. id="+id));
        User user = userRepository.findByUsername(username)
                .orElseThrow(()->new UserNotFoundException("해당 유저가 존재하지 않습니다."));
        entity.setHit(entity.getHit()+1);
        postRepository.save(entity);
        boolean viewerHasLiked = user.getPostLikeIdList().contains(id);
        return new PostResponseDto(entity,viewerHasLiked);
    }
    @Transactional(readOnly = true)
    public List<PostListResponseDto> findAllByCategoryAndLimitAndOffset(String category, int offset){
        return postRepository.findAllByCategoryCustomQuery(category,offset).stream()
                .map(PostListResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<PostListResponseDto> findAllByLimitAndOffset(int offset) {
        return postRepository.findAllByCustomQuery(offset).stream()
                .map(PostListResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public void delete(Long id){
        Post post = postRepository.findById(id)
                .orElseThrow(()->new PostNotFoundException("해당 id의 post 가 존재하지 않습니다."));
        postRepository.delete(post);
    }
    @Transactional
    public PostLikeResponseDto userLikePost(PostLikeRequestDto requestDto) {
        String username = requestDto.getUsername();
        Long postId = requestDto.getPostId();

        User user = userRepository.findOneWithAuthoritiesByUsername(username)
                .orElseThrow(()->new UsernameNotFoundException("해당 유저가 존재하지 않습니다."));
        Post post = postRepository.findById(postId)
                .orElseThrow(()->new PostNotFoundException("해당 게시글이 존재하지 않습니다."));
        int likeCount = post.getLikeCount();
        boolean viewerHasLike = user.getPostLikeIdList().contains(postId);
        if(viewerHasLike){
            user.getPostLikeIdList().remove(postId);
            post.setLikeCount(--likeCount);
        }else{
            user.getPostLikeIdList().add(postId);
            post.setLikeCount(++likeCount);
        }
        userRepository.save(user);
        postRepository.save(post);
        return new PostLikeResponseDto(!viewerHasLike,likeCount);
    }

    @Transactional(readOnly = true)
    public List<PostListResponseDto> searchByTitleOrContent(String keyword) {
        return postRepository.searchByTitleOrContent(keyword).stream()
                .map(PostListResponseDto::new)
                .collect(Collectors.toList());
    }
}
