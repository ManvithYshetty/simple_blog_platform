package com.blogplatform.simpleblogplatform.service;

import com.blogplatform.simpleblogplatform.model.Post;
import com.blogplatform.simpleblogplatform.model.User;
import com.blogplatform.simpleblogplatform.repository.PostRepository;
import com.blogplatform.simpleblogplatform.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public PostService(PostRepository postRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    /**
     * Retrieves all Post entities from the database.
     */
    public List<Post> findAllPosts() {
        return postRepository.findAll();
    }

    /**
     * Finds a single Post by its ID.
     */
    public Post findPostById(Long id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post not found with id: " + id));
    }

    /**
     * NEW: Save a new post or update an existing one.
     * This now links the post to the logged-in user + sets createdAt.
     */
     @Transactional
    public Post savePost(Post postFromForm, String username) {

    // ID = null OR ID = 0  → treat as NEW POST
    if (postFromForm.getId() == null || postFromForm.getId() == 0) {

        User author = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalStateException("User not found: " + username));

        postFromForm.setUser(author);
        postFromForm.setCreatedAt(LocalDateTime.now());

        return postRepository.save(postFromForm);
    }

    // --- UPDATE ---
    Post existingPost = postRepository.findById(postFromForm.getId())
            .orElseThrow(() -> new IllegalArgumentException("Invalid post Id: " + postFromForm.getId()));

    existingPost.setTitle(postFromForm.getTitle());
    existingPost.setContent(postFromForm.getContent());

    return postRepository.save(existingPost);
}

    /**
     * Deletes a Post from the database by its ID.
     */
    public void deletePostById(Long id) {
        postRepository.deleteById(id);
    }
}
