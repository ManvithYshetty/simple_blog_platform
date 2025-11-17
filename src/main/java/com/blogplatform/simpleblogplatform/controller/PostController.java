package com.blogplatform.simpleblogplatform.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.blogplatform.simpleblogplatform.dto.CommentDto;
import com.blogplatform.simpleblogplatform.model.Post;
import com.blogplatform.simpleblogplatform.service.CommentService;
import com.blogplatform.simpleblogplatform.service.PostService;



@Controller
public class PostController {
    private final PostService postService;
    private final CommentService commentService;

    public PostController(PostService postService,CommentService commentService) {
        this.postService = postService;
        this.commentService = commentService;
    }
     // Home Page Handler
    @GetMapping("/")
    public String showHomePage(Model model) {

        // Fetch all posts from service
        List<Post> allPosts = postService.findAllPosts();

        // Add them to the model for Thymeleaf
        model.addAttribute("posts", allPosts);

        // Return the name of the template to load (home.html)
        return "home";
    }
    @GetMapping("/posts/{id}")
    public String showPostDetail(@PathVariable Long id, Model model) {
        // Fetch the post by ID
        Post post = postService.findPostById(id);

        // Add the post to the model so Thymeleaf can access it
        model.addAttribute("post", post);
            
        // NEW: Empty form object for comment submission
        model.addAttribute("newComment", new CommentDto());

            // Return the template name (post-detail.html)
        return "post-detail";
    }
    @PostMapping("/posts/{postId}/comments")
    public String submitComment(@PathVariable Long postId,@ModelAttribute("newComment") CommentDto commentDto,Principal principal) {

        String username = principal.getName();
        commentService.saveComment(postId, username, commentDto);

    return "redirect:/posts/" + postId;
    }

}
