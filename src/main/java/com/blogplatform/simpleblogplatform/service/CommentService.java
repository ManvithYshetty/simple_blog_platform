package com.blogplatform.simpleblogplatform.service;

import com.blogplatform.simpleblogplatform.model.Comment; // NEW: Import the Comment model
import com.blogplatform.simpleblogplatform.repository.CommentRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime; // NEW: Import LocalDateTime for timestamping

/**
 * The CommentService class encapsulates the business logic for all operations
 * related to comments. It uses the CommentRepository to interact with the database.
 */
@Service
public class CommentService {

    private final CommentRepository commentRepository;

    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    // --- NEW: Implement the method to save a comment ---
    /**
     * Saves a Comment entity to the database.
     * This method handles the creation of new comments.
     *
     * @param comment The Comment object to be saved. It is expected that the 'post' and 'user'
     *                properties are already set on this object before it's passed to the service.
     * @return The saved Comment entity, which will now include its auto-generated ID and createdAt timestamp.
     */
    public Comment saveComment(Comment comment) {
        // Enforce the business rule: set the creation timestamp for all new comments.
        // We assume any comment passed to this service is a new one.
        comment.setCreatedAt(LocalDateTime.now());

        // Delegate the persistence operation to the repository. The save() method
        // will perform a SQL INSERT and return the managed entity with the populated ID.
        return commentRepository.save(comment);
    }
}