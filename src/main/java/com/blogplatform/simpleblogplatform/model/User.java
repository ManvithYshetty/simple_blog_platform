package com.blogplatform.simpleblogplatform.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
// We explicitly name the table 'users' because 'user' is often a reserved keyword in SQL databases.
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    // --- NEW FIELD ---
    // This field will store the user's role, for example, "USER" or "ADMIN".
    // It's a simple yet effective way to implement role-based access control for our application.
    @Column(nullable = false)
    private String role;

    // --- RELATIONSHIPS ---
    // A single user can author many posts.
    @OneToMany(mappedBy = "user")
    private List<Post> posts;

    // A single user can write many comments.
    @OneToMany(mappedBy = "user")
    private List<Comment> comments;
    
    // --- GETTERS AND SETTERS ---
    // Standard getter and setter methods for all fields.

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // --- NEW GETTERS AND SETTERS FOR THE 'role' FIELD ---
    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
    
    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
}