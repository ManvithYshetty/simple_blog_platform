package com.blogplatform.simpleblogplatform.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * Represents a comment entity made by a user on a post.
 * This entity is fully relational, linked to both a Post and a User (the author).
 */
@Entity
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    private LocalDateTime createdAt;

    // --- Relationship to Post (owning side) ---
    @ManyToOne
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    // --- NEW: Relationship to User (author of the comment) ---
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Comment() {
    }

    // --- Getters and Setters ---
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", createdAt=" + createdAt +
                ", postId=" + (post != null ? post.getId() : "null") +
                ", userId=" + (user != null ? user.getId() : "null") +
                '}';
    }
}
