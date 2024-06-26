package ru.itmentor.spring.boot_security.demo.service;

// Импорт необходимых классов и интерфейсов для работы со службой в Spring
import ru.itmentor.spring.boot_security.demo.entity.Role;

// Импорт классов для работы с множествами и массивами
import java.util.Set;

// Объявление интерфейса RoleService, который определяет методы для работы с ролями
public interface RoleService {
    // Метод для получения всех ролей из базы данных
    Set<Role> getAllRoles();

    // Метод для получения ролей по их названиям
    Set<Role> getRoleByName(String[] roleName);
}
