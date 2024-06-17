package ru.SpringCRUD.boot.dao;


import org.springframework.data.jpa.repository.JpaRepository;
import ru.SpringCRUD.boot.model.User;

public interface UserRepository extends JpaRepository<User,Long> {
}