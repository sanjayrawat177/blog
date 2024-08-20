package com.sr.social.blog.repository;

import com.sr.social.blog.entity.Post;
import com.sr.social.blog.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Integer> {
}
