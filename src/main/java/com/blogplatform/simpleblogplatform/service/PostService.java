package com.blogplatform.simpleblogplatform.service;

import com.blogplatform.simpleblogplatform.model.Post;
import com.blogplatform.simpleblogplatform.repository.PostRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PostService {

    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
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
     * Saves a Post entity. Handles both creation and updates.
     */
    public Post savePost(Post post) {
        if (post.getId() == null) {
            post.setCreatedAt(LocalDateTime.now());
        }
        return postRepository.save(post);
    }

    /**
     * Deletes a Post from the database by its ID.
     */
    public void deletePostById(Long id) {
        postRepository.deleteById(id);
    }
}
