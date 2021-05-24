package devops.kindergarten.server.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
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

    @Column(nullable = false)
    private String author;

    @Column(nullable = false)
    private String category;
    @Column(name="like_count")
    private int like;

    private int hit;

    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
    @Builder
    public Post(String title,String content,String author,String category){
        this.title = title;
        this.content = content;
        this.author = author;
        this.category = category;
        this.like = 0;
        this.hit = 0;
        this.createdDate = this.updatedDate = LocalDateTime.now();
    }

    public void update(String title,String content,String category){
        this.title = title;
        this.content = content;
        this.category = category;
        this.updatedDate = LocalDateTime.now();
    }
}
