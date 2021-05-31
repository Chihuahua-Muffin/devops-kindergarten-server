package devops.kindergarten.server.service;

import devops.kindergarten.server.domain.Post;
import devops.kindergarten.server.domain.User;
import devops.kindergarten.server.dto.post.PostListResponseDto;
import devops.kindergarten.server.dto.post.PostResponseDto;
import devops.kindergarten.server.dto.post.PostSaveRequestDto;
import devops.kindergarten.server.dto.post.PostUpdateRequestDto;
import devops.kindergarten.server.exception.custom.PostNotFoundException;
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
        return new PostResponseDto(entity);
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

    @Transactional(readOnly = true)
    public List<PostListResponseDto> searchByTitleOrContent(String keyword) {
        return postRepository.searchByTitleOrContent(keyword).stream()
                .map(PostListResponseDto::new)
                .collect(Collectors.toList());
    }
}
