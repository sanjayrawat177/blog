package com.sr.social.blog.controller;

import com.sr.social.blog.entity.User;
import com.sr.social.blog.exception.UserNotFoundException;
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

@RestController
@RequestMapping("/api/v1")
public class BlogController {
    Logger _log = LoggerFactory.getLogger(BlogController.class);
    @Autowired
    UserDaoService userDaoService;
    @Autowired
    private MessageSource messageSource;

    @GetMapping("/message")
    public String getMessage() {
        Locale locale = LocaleContextHolder.getLocale();
        return messageSource.getMessage("good.morning,message", null, "Default Message", locale);
    }

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userDaoService.findAll();
    }

    @GetMapping("/users/{id}")
    public User getUser(@PathVariable Integer id) {
        User user = userDaoService.findById(id);
        if(user == null) {
            throw new UserNotFoundException(String.format("Id %s not found", id));
        }
        return user;
    }

    @PostMapping("/users")
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
        int id = userDaoService.createUser(user);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").build(id);
        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<User> deleteUser(@PathVariable Integer id) {
        userDaoService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
