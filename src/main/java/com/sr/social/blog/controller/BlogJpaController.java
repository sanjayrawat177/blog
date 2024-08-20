package com.sr.social.blog.controller;

import com.sr.social.blog.entity.Post;
import com.sr.social.blog.entity.User;
import com.sr.social.blog.exception.UserNotFoundException;
import com.sr.social.blog.repository.PostRepository;
import com.sr.social.blog.repository.UserRepository;
import com.sr.social.blog.service.UserDaoService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@RestController
@RequestMapping("/api/v2")
public class BlogJpaController {
    Logger _log = LoggerFactory.getLogger(BlogJpaController.class);
    @Autowired
    UserRepository userRepository;
    @Autowired
    PostRepository postRepository;

    @Autowired
    private MessageSource messageSource;

    @GetMapping("/message")
    public String getMessage() {
        Locale locale = LocaleContextHolder.getLocale();
        return messageSource.getMessage("good.morning,message", null, "Default Message", locale);
    }

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/users/{id}")
    public User getUser(@PathVariable Integer id) {
        Optional<User> user = userRepository.findById(id);
        if(user.isEmpty()) {
            throw new UserNotFoundException(String.format("Id %s not found", id));
        }

        return user.get();
    }

    @PostMapping("/users")
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
        User savedUser = userRepository.save(user);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").build(savedUser.getId());
        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<User> deleteUser(@PathVariable Integer id) {
        userRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }


    /// Post Entity

    @GetMapping("/users/{id}/posts")
    public List<Post> getUserForUser(@PathVariable Integer id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if(optionalUser.isEmpty()) {
            throw new UserNotFoundException(String.format("Id %s not found", id));
        }
        User user = optionalUser.get();
        return user.getPosts();
    }

    @PostMapping("/users/{id}/posts")
    public ResponseEntity<User> createPost(@PathVariable Integer id, @Valid @RequestBody Post post) {
        Optional<User> optionalUser = userRepository.findById(id);
        if(optionalUser.isEmpty()) {
            throw new UserNotFoundException(String.format("Id %s not found", id));
        }
        User user = optionalUser.get();
        post.setUser(user);
        Post savedPost = postRepository.save(post);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").build(savedPost.getId());
        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/posts/{id}")
    public ResponseEntity<User> deletePost(@PathVariable Integer id) {
        userRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
