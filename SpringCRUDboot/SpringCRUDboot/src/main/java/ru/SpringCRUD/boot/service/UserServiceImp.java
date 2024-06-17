package ru.SpringCRUD.boot.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.SpringCRUD.boot.dao.UserRepository;
import ru.SpringCRUD.boot.model.User;

import java.util.List;

@Service
public class UserServiceImp implements UserService{
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImp(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public void saveUser(User user) {
        userRepository.save(user);
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);

    }

    @Override
    public User getUserById(Long id) {
        return userRepository.getOne(id);
    }
//TODO
//    @Override
//    public void updateUser(Long id, User updatedUser) {
//        userRepository.save(updatedUser);

}