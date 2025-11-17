package com.blogplatform.simpleblogplatform.service;

import com.blogplatform.simpleblogplatform.dto.CommentDto;
import com.blogplatform.simpleblogplatform.model.Comment;
import com.blogplatform.simpleblogplatform.model.Post;
import com.blogplatform.simpleblogplatform.model.User;
import com.blogplatform.simpleblogplatform.repository.CommentRepository;
import com.blogplatform.simpleblogplatform.repository.PostRepository;
import com.blogplatform.simpleblogplatform.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public CommentService(CommentRepository commentRepository,PostRepository postRepository,UserRepository userRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public void saveComment(Long postId, String username, CommentDto commentDto) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("Post not found: " + postId));

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + username));

        Comment comment = new Comment();
        comment.setContent(commentDto.getContent());
        comment.setCreatedAt(LocalDateTime.now());
        comment.setPost(post);
        comment.setUser(user);

        commentRepository.save(comment);
    }
}
