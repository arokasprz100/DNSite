package com.dnsite.security.user.service;

import com.dnsite.security.user.model.User;

import java.util.List;

public interface UserService {

    void save(User user);

    User findByUsername(String username);

    List<User> findAll();
}