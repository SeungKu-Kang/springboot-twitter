package com.apiece.springboot_twitter.comment;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment,Long> {

    List<CommentResponse> findByPostIdOrderByIdDesc(Long postId);

    Optional<Comment> findByIdAndPostId(Long commentId, Long postId);
}
