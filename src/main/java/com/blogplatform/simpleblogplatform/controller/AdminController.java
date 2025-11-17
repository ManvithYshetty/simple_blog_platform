package com.blogplatform.simpleblogplatform.controller;

import com.blogplatform.simpleblogplatform.model.Post;
import com.blogplatform.simpleblogplatform.service.PostService;

import java.security.Principal;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")   // Base URL for all admin functions
public class AdminController {

    private final PostService postService;

    public AdminController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/posts")
    public String showPostListDashboard(Model model) {
        List<Post> allPosts = postService.findAllPosts();
        model.addAttribute("posts", allPosts);
        return "admin/list-posts";
    }

    @GetMapping("/posts/new")
    public String showNewPostForm(Model model) {
        Post post = new Post();          // Empty form-backing object
        model.addAttribute("post", post); 
        return "admin/post-form";        // Template to create/edit posts
    }
    @PostMapping("/posts")
    public String savePost(@ModelAttribute("post") Post post,Principal principal) {

        String username = principal.getName();   // logged-in admin name

        postService.savePost(post, username);    // delegate business logic

        return "redirect:/admin/posts";          // PRG pattern
    }
    @GetMapping("/posts/edit/{id}")
    public String showEditPostForm(@PathVariable Long id, Model model) {

        Post post = postService.findPostById(id);

        model.addAttribute("post", post);

        return "admin/post-form";
    }
    // --- DELETE POST ---
    @GetMapping("/posts/delete/{id}")
    public String deletePost(@PathVariable Long id) {

        // Let the service delete the post
        postService.deletePostById(id);

        // Redirect back to the admin dashboard
        return "redirect:/admin/posts";
    }

}

