package devops.kindergarten.server.service;

import devops.kindergarten.server.domain.Post;
import devops.kindergarten.server.dto.post.PostListResponseDto;
import devops.kindergarten.server.dto.post.PostResponseDto;
import devops.kindergarten.server.dto.post.PostSaveRequestDto;
import devops.kindergarten.server.dto.post.PostUpdateRequestDto;
import devops.kindergarten.server.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    @Transactional
    public Long save(PostSaveRequestDto requestDto){
        Post post = requestDto.toEntity();
        return postRepository.save(post).getId();
    }

    @Transactional
    public Long update(Long id, PostUpdateRequestDto requestDto){
        Post post = postRepository.findById(id)
                .orElseThrow(()-> new IllegalArgumentException("해당 게시글이 없습니다. id="+id));
        post.update(requestDto.getTitle(), requestDto.getContent(),requestDto.getCategory());

        return id;
    }

    public PostResponseDto findById(Long id){
        Post entity = postRepository.findById(id)
                .orElseThrow(()->new IllegalArgumentException("해당 게시글이 없습니다. id="+id));
        return new PostResponseDto(entity);
    }

    @Transactional(readOnly = true)
    public List<PostListResponseDto> findAllDesc(){
        return postRepository.findAllDesc().stream()
                .map(PostListResponseDto::new)
                .collect(Collectors.toList());
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
                .orElseThrow(()->new IllegalArgumentException("해당 게시글이 없습니다. id="+id));
        postRepository.delete(post);
    }

}
