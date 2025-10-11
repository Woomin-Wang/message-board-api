package io.github.woominwang.messageboard.dto;

import io.github.woominwang.messageboard.domain.Comment;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

public class CommentDto {

    @Getter
    @Setter
    public static class Request {

        @NotBlank(message = "내용은 필수 입력 항목입니다.")
        @Size(max = 1000, message = "내용은 1000자를 초과할 수 없습니다.")
        private String content;

        @NotBlank
        private String author;
    }

    public record Response(Long id, String content, String author) {
        public Response(Comment comment) {
            this(comment.getId(), comment.getContent(), comment.getAuthor());
        }
    }
}
