package io.github.woominwang.messageboard.controller;

import io.github.woominwang.messageboard.dto.CommentDto;
import io.github.woominwang.messageboard.service.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts/{postId}/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<CommentDto.Response> createComment(
            @PathVariable Long postId,
            @Valid @RequestBody CommentDto.Request requestDto
    ) {
        CommentDto.Response response = commentService.createComment(postId, requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // 특정 게시글의 모든 댓글 조회
    @GetMapping
    public ResponseEntity<List<CommentDto.Response>> getCommentsByPostId(
            @PathVariable Long postId
    ) {
        return ResponseEntity.ok(commentService.getCommentsByPostId(postId));
    }

    // 특정 댓글 단건 조회
    @GetMapping("/{commentId}")
    public ResponseEntity<CommentDto.Response> getComment(
            @PathVariable Long postId,
            @PathVariable Long commentId
    ) {
        return ResponseEntity.ok(commentService.getCommentById(postId, commentId));
    }

    @PatchMapping("/{commentId}")
    public ResponseEntity<CommentDto.Response> updateComment(
            @PathVariable Long postId,
            @PathVariable Long commentId,
            @Valid @RequestBody CommentDto.Request requestDto
    ) {
        return ResponseEntity.ok(commentService.updateComment(postId, commentId, requestDto));
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(
            @PathVariable Long postId,
            @PathVariable Long commentId
    ) {
        commentService.deleteComment(postId, commentId);
        return ResponseEntity.noContent().build();
    }
}
