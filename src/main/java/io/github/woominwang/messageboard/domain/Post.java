package io.github.woominwang.messageboard.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access =  AccessLevel.PROTECTED)
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NonNull
    @Column(nullable = false, length = 100)
    private String title;

    @NonNull
    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @NonNull
    @Column(nullable = false, length = 20)
    private String author;

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
