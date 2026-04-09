package com.apiece.springboot_twitter.post;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "posts")
@Entity
@Builder
public class Post {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        private String content;

        private LocalDateTime createdAt;

        private LocalDateTime updatedAt;

        private int commentCount = 0;

        public void updateContent(String content) {
                this.content = content;
        }

        public void increaseCommentCount() {
                this.commentCount++;
        }

        public void decreaseCommentCount() {
                this.commentCount--;
        }
}