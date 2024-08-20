package com.sr.social.blog.service;

import com.sr.social.blog.entity.User;
import com.sr.social.blog.exception.UserNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class UserDaoService {
    private static List<User> userList;
    static {
        userList = new ArrayList<>();
        userList.add(new User(1, "Sanjay", LocalDate.of(2000, 12, 02)));
        userList.add(new User(2, "Ram", LocalDate.of(2000, 11, 22)));
        userList.add(new User(3, "Tom", LocalDate.of(2000, 6, 1)));
        userList.add(new User(4, "Drake", LocalDate.of(2000, 12, 2)));
        userList.add(new User(5, "Eliza", LocalDate.of(2000, 2, 3)));
        userList.add(new User(6, "Gamora", LocalDate.of(2000, 12, 30)));
        userList.add(new User(7, "Chris", LocalDate.of(2000, 9, 11)));
    }
    public List<User> findAll() {
        return userList;
    }

    public User findById(Integer id) {
        return userList.stream().filter(record-> Objects.equals(id, record.getId())).findAny().orElse(null);
    }

    public int generateId() {
        return userList.size()+1;
    }

    public int createUser(User user) {
        user.setId(generateId());
        userList.add(user);
        return user.getId();
    }

    public void deleteById(int id) {
        User user = findById(id);
        if(user == null) {
            throw new UserNotFoundException(String.format("Id %s not found", id));
        }
        userList.removeIf(record-> Objects.equals(id, record.getId()));
    }
}
