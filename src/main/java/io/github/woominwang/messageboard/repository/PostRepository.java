package io.github.woominwang.messageboard.repository;

import io.github.woominwang.messageboard.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
