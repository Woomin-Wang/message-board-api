package io.github.woominwang.messageboard.service;

import io.github.woominwang.messageboard.domain.Comment;
import io.github.woominwang.messageboard.domain.Post;
import io.github.woominwang.messageboard.dto.CommentDto;
import io.github.woominwang.messageboard.exception.CommentNotFoundException;
import io.github.woominwang.messageboard.exception.PostNotFoundException;
import io.github.woominwang.messageboard.repository.CommentRepository;
import io.github.woominwang.messageboard.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    @Transactional
    public CommentDto.Response createComment(Long postId, CommentDto.Request request) {

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException("ID " + postId + "에 해당하는 게시글을 찾을 수 없습니다."));

        Comment comment = Comment.builder()
                .content(request.getContent())
                .author(request.getAuthor())
                .build();

        post.addComment(comment);
        commentRepository.save(comment);

        return new CommentDto.Response(comment);
    }

    public List<CommentDto.Response> getCommentsByPostId(Long postId) {
        if (commentRepository.existsById(postId) == false) {
            throw new PostNotFoundException("ID " + postId + "에 해당하는 게시글을 찾을 수 없습니다.");
        }
        return commentRepository.findByPostId(postId).stream()
                .map(CommentDto.Response::new)
                .collect(Collectors.toList());
    }

    public CommentDto.Response getCommentById(Long postId, Long commentId) {
        Comment comment = findVerifyingComment(postId, commentId);
        return new CommentDto.Response(comment);
    }

    @Transactional
    public CommentDto.Response updateComment(Long postId, Long commentId, CommentDto.Request requestDto) {
        Comment comment = findVerifyingComment(postId, commentId);
        comment.update(requestDto.getContent());
        return new CommentDto.Response(comment);
    }

    @Transactional
    public void deleteComment(Long postId, Long commentId) {
        Comment comment = findVerifyingComment(postId, commentId);
        commentRepository.delete(comment);
    }

    private Comment findVerifyingComment(Long postId, Long commentId) {
        return commentRepository.findByIdAndPostId(commentId, postId)
                .orElseThrow(() -> new CommentNotFoundException("해당 게시글에 존재하지 않는 댓글입니다."));
    }
}
