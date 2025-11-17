package com.blogplatform.simpleblogplatform.config;

import com.blogplatform.simpleblogplatform.model.Post;
import com.blogplatform.simpleblogplatform.model.User;
import com.blogplatform.simpleblogplatform.repository.PostRepository;
import com.blogplatform.simpleblogplatform.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(UserRepository userRepository,PostRepository postRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.postRepository = postRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {

        // CREATE ADMIN USER IF NOT EXISTS
        User adminUser = userRepository.findByUsername("admin")
                .orElseGet(() -> {
                    User newAdmin = new User();
                    newAdmin.setUsername("admin");
                    newAdmin.setPassword(passwordEncoder.encode("password"));
                    newAdmin.setRole("ADMIN");
                    return userRepository.save(newAdmin);
                });

        // CREATE SAMPLE POSTS ONLY IF POSTS TABLE IS EMPTY
        if (postRepository.count() == 0) {

            Post p1 = new Post();
            p1.setTitle("Welcome to the Simple Blog");
            p1.setContent("<p>This is the <strong>first post</strong> on the blog!</p>");
            p1.setCreatedAt(LocalDateTime.now());
            p1.setUser(adminUser);

            Post p2 = new Post();
            p2.setTitle("Spring Boot + Thymeleaf is Awesome");
            p2.setContent("<p>You're now learning how to build a full blog platform!</p>");
            p2.setCreatedAt(LocalDateTime.now());
            p2.setUser(adminUser);

            postRepository.save(p1);
            postRepository.save(p2);

            System.out.println("Sample posts inserted!");
        }
    }
}
