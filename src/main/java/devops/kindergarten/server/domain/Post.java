package devops.kindergarten.server.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@NoArgsConstructor
@Table(name="post")
public class Post {
    @Id
    @Column(name="post_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="title",nullable = false)
    private String title;

    @Column(name = "content",nullable = false,columnDefinition = "TEXT")
    private String content;

    @Column(name = "username",nullable = false)
    private String username;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User author;

    @Column(nullable = false)
    private String category;

    @Column(name="like_count")
    private int likeCount;

    private int hit;

    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;

    @OneToMany(mappedBy = "post",cascade = CascadeType.ALL)
    private List<Comment> commentList = new ArrayList<>();

    private void setAuthor(User author){
        this.author = author;
        author.getPostList().add(this);
    }

    public static Post createPost(User author,String title,String content,String username,String category){
        Post post = new Post();
        LocalDateTime now = LocalDateTime.now();

        post.setAuthor(author);
        post.setTitle(title);
        post.setContent(content);
        post.setCategory(category);
        post.setUsername(username);
        post.setCreatedDate(now);
        post.setUpdatedDate(now);

        return post;
    }
    public void update(String title,String content,String category){
        this.title = title;
        this.content = content;
        this.category = category;
        this.updatedDate = LocalDateTime.now();
    }
}
