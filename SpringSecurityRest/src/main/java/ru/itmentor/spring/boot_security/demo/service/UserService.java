package ru.itmentor.spring.boot_security.demo.service;

// Импорт необходимых классов и интерфейсов для работы со службой в Spring
import org.springframework.security.core.userdetails.UserDetails;
// Импорт сущности User для работы с пользователями
import ru.itmentor.spring.boot_security.demo.entity.User;

// Импорт классов для работы с списками
import java.util.List;

// Объявление интерфейса UserService, который определяет методы для работы с пользователями
public interface UserService {
    // Метод для поиска пользователя по его ID
    User findUserById(Long id);

    // Метод для получения списка всех пользователей
    List<User> findAllUsers();

    // Метод для удаления пользователя по его ID
    void deleteUserById(Long id);

    // Метод для загрузки информации о пользователе по его имени для аутентификации
    UserDetails loadUserByUsername(String username);

    // Метод для проверки возможности сохранения пользователя
    boolean saveUser(User user);

    // Метод для обновления информации о пользователе
    boolean updateUser(User user);

    // Метод для получения имени пользователя по его имени
    Long getUsernameByName(String name);

    // Метод для получения пользователя с привязанными ролями
    User getUserAndRoles(User user, String[] roles);

    // Метод для получения пользователя с непустым набором ролей
    User getNotNullRole(User user);

    // Метод для добавления тестовых пользователей
    void addTestUsers();
}
