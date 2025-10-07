package io.github.woominwang.messageboard.dto;

import io.github.woominwang.messageboard.domain.Post;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

public class PostDto {

    @Getter
    @Setter
    public static class Request {
        @NotBlank(message = "제목은 필수 입력 항목입니다.")
        @Size(max = 100, message = "제목은 100자를 초과할 수 없습니다.")
        private String title;

        @NotBlank(message = "내용은 필수 입력 항목입니다.")
        private String content;

        @NotBlank
        private String author;
    }

    public record Response(Long id, String title, String content, String author) {
        // Post Entity를 인자로 받는 편의 생성자
        public Response(Post post) {
            this(post.getId(), post.getTitle(), post.getContent(), post.getAuthor());
        }
    }
}
