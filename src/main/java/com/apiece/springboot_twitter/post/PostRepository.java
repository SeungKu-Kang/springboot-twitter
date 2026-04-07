package com.apiece.springboot_twitter.post;


import java.util.List;
import java.util.Optional;

public interface PostRepository {
    Post save(Post post);

    List<Post> findAll();

    Optional<Post> findById(long id);

    void deleteById(long id);

    List<Post> findAllPaged(int page, int size);

}
