package com.kharido.service;

import com.kharido.entity.User;

public interface UserService {

    public User addUser(User user);

    public boolean checkExistsByEmail(String email);

    public User findUserById(Long userId);

    public User findUserByEmail(String email);

    public User findUserByToken(String token);
}
