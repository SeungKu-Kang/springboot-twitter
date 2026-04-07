package com.apiece.springboot_twitter.comment;

import com.apiece.springboot_twitter.post.Post;
import com.apiece.springboot_twitter.post.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class CommentService {
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    public CommentResponse createComment(Long postId, CommentRequest request) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다"));

        Comment comment = Comment.builder()
                .content(request.content())
                .post(post)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        Comment newComment = commentRepository.save(comment);

        post.increaseCommentCount();
        postRepository.save(post);

        return CommentResponse.from(newComment);
    }

    public List<CommentResponse> getComments(Long postId) {
        return commentRepository.findByPostIdOrderByIdDesc(postId);
    }

    public CommentResponse updateComment(Long postId, Long commentId, CommentRequest request) {
        Comment comment = commentRepository.findByIdAndPostId(commentId, postId)
                .orElseThrow(() -> new IllegalArgumentException("댓글을 찾을 수 없습니다"));
        comment.updateComment(request.content());

        Comment updatedComment = commentRepository.save(comment);

        // 2. 컨트롤러가 기다리는 예쁜 택배 상자(CommentResponse)로 포장해서 return 합니다!
        return CommentResponse.from(updatedComment);
    }

    public void deleteComment(Long postId, Long commentId) {

        Comment comment = commentRepository.findByIdAndPostId(commentId, postId)
                .orElseThrow(() -> new IllegalArgumentException("댓글을 찾을 수 없습니다"));

        Post post = comment.getPost();
        post.decreaseCommentCount();
        postRepository.save(post);
        commentRepository.delete(comment);
    }

    public List<CommentResponse> getAllComments() {
        return commentRepository.findAll()
                .stream()
                .map(comment -> {
                    Post post = comment.getPost();
                    log.info("댓글 ID: {} 게시글 ID : {}, 게시글 내용 : {}, comment.getId(), post.getId(), post.getContent()");
                    return CommentResponse.from(comment);
                })
                .toList();

    }
}
