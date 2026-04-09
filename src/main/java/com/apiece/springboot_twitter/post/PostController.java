package com.apiece.springboot_twitter.post;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class PostController {
    private final PostRepository postRepository;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/api/posts")
    public Post createPost(@RequestBody Post post) {
        Post newPost = Post.builder()
                .content(post.getContent())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();


        postRepository.save(newPost);

        return newPost;
    }

    @GetMapping("/api/posts")
    public List<Post> findAll() {
        return postRepository.findAll();
    }

    @GetMapping("/api/posts/{id}")
    public Post getPost(@PathVariable long id) {
        return postRepository.findById(id)
                .orElseThrow();
    }

    @PutMapping("/api/posts/{id}")
    public Post updatePost(@PathVariable long id, @RequestBody Post postRequest) {
        return postRepository.findById(id)
                .map(post -> {
                    post.updateContent(postRequest.getContent());
                    return postRepository.save(post);
                })
                .orElseThrow();
    }

    @DeleteMapping("/api/posts/{id}")
    public void deletePost(@PathVariable long id) {
        postRepository.deleteById(id);
    }

    @GetMapping("/api/posts/search")
    public List<Post> searchPosts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size
    ) {
        return postRepository.findAllPaged(page, size);
    }

}
