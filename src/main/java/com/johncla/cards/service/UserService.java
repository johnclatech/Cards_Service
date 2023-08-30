package com.johncla.cards.service;

import com.johncla.cards.dto.UserDto;
import com.johncla.cards.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    Optional<User> getUserByEmail(String email);
    List<User> getUsersByRole(String role);
    User authenticateUser(String username, String password);

    User createUser(UserDto userDto);

    User updateUser(String email, UserDto userDto);

    void deleteUser(String email);
}
