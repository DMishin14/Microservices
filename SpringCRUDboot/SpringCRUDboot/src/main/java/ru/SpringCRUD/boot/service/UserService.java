package ru.SpringCRUD.boot.service;


import ru.SpringCRUD.boot.model.User;

import java.util.List;

public interface UserService {
    List<User> getAllUsers();

    void saveUser(User user);

    void deleteUser(Long id);

    User getUserById(Long id);
//TODO
//    void updateUser(Long id, User updatedUser);
}