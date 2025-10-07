package io.github.woominwang.messageboard.service;

import io.github.woominwang.messageboard.domain.Post;
import io.github.woominwang.messageboard.dto.PostDto;
import io.github.woominwang.messageboard.repository.PostRepository;
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
    public PostDto.Response createPost(PostDto.Request requestDto) {

        Post post = Post.builder()
                .title(requestDto.getTitle())
                .content(requestDto.getContent())
                .author(requestDto.getAuthor())
                .build();

        Post savedPost = postRepository.save(post);

        return new PostDto.Response(savedPost);
    }

    @Transactional(readOnly = true)
    public List<PostDto.Response> getAllPosts() {
        return postRepository
                .findAll()
                .stream()
                .map(PostDto.Response::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public PostDto.Response getPostById(Long id) {

        Post post = postRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 ID의 게시글이 없습니다."));

        return new PostDto.Response(post);
    }

    @Transactional
    public PostDto.Response updatePost(Long id, PostDto.Request requestDto) {

        Post post = postRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException(("해당 ID의 게시글이 없습니다.")));

        post.update(requestDto.getTitle(), requestDto.getContent());

        return new PostDto.Response(post);
    }

    @Transactional
    public void deletePost(Long id) {

        Post post = postRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException(("해당 ID의 게시글이 없습니다.")));

        postRepository.delete(post);
    }
}



