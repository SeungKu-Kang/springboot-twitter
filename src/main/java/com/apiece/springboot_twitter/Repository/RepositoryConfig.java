package com.apiece.springboot_twitter.Repository;

import com.apiece.springboot_twitter.post.PostRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RepositoryConfig {

    @Bean
    public PostRepository postRepository(JpaPostRepository jpaPostRepository) {
        return jpaPostRepository;
    }
}
