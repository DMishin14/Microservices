package ru.itmentor.spring.boot_security.demo.service;

// Импорт необходимых классов и аннотаций для работы со службой в Spring
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
// Импорт сущностей Role и User для работы с пользователями и ролями
import ru.itmentor.spring.boot_security.demo.entity.Role;
import ru.itmentor.spring.boot_security.demo.entity.User;
// Импорт репозиториев для работы с пользователями и ролями
import ru.itmentor.spring.boot_security.demo.dao.RoleRepository;
import ru.itmentor.spring.boot_security.demo.dao.UserRepository;

// Импорт классов для работы с коллекциями и опциональными значениями
import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

// Аннотация Service, помечающая класс как Spring Bean для работы с бизнес-логикой
@Service
// Аннотация Lombok, автоматически генерирующая конструктор с требуемыми зависимостями
@RequiredArgsConstructor
public class UserServiceImpl implements UserDetailsService, UserService {

    // Зависимости от репозиториев для работы с пользователями и ролями
    private final UserRepository userRepository;
    private final RoleService roleService;
    private final RoleRepository roleRepository;

    // Метод для поиска пользователя по его ID
    public User findUsersById(Long id) {
        return userRepository.getOne(id);
    }

    // Метод для получения списка всех пользователей
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    // Метод для удаления пользователя по его ID
    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }

    // Реализация метода loadUserByUsername из интерфейса UserDetailsService
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetails userDetails = userRepository.findByUsername(username);
        if (userDetails == null) {
            throw new UsernameNotFoundException("User with this " + username + " User Name not found");
        }
        return userDetails;
    }

    // Метод для поиска пользователя по его ID
    public User findUserById(Long userId) {
        Optional<User> userFromDb = userRepository.findById(userId);
        return userFromDb.orElse(new User());
    }

    // Метод для сохранения пользователя
    public boolean saveUser(User user) {
        User userFromDB = userRepository.findByUsername(user.getUsername());
        if (userFromDB!= null) {
            return false;
        }
        if (user.getUsername().equals("") | user.getPassword().equals("")) {
            return false;
        }
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        userRepository.save(user);
        return true;
    }

    // Реализация метода updateUser из интерфейса UserService
    @Override
    public boolean updateUser(User user) {
        if (user.getUsername().equals("") | user.getPassword().equals("")) {
            return false;
        }
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        userRepository.save(user);
        return true;
    }

    // Метод для получения имени пользователя по его имени
    @Override
    public Long getUsernameByName(String name) {
        User user = userRepository.findByUsername(name);
        return user.getId();
    }

    // Метод для сохранения тестового пользователя
    public boolean saveUserTest(User user) {
        User userFromDB = userRepository.findByUsername(user.getUsername());
        if (userFromDB!= null) {
            return false;
        }
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        userRepository.save(user);
        return true;
    }

    // Метод для получения пользователя с привязанными ролями
    public User getUserForUpdateUsers(User user, String username) {
        User userDb = findUserById(getUsernameByName(username));
        Set<Role> roles = userDb.getRoles();
        user.setRoles(roleService.getRoleByName(roles.stream().map(role -> role.getName()).toArray(String[]::new)));
        return user;
    }

    // Метод для получения пользователя с привязанными ролями
    public User getUserAndRoles(User user, String[] roles) {
        if (roles == null) {
            user.setRoles(Collections.singleton(new Role(2L, "ROLE_USER")));
        } else {
            user.setRoles(roleService.getRoleByName(roles));
        }
        return user;
    }

    // Реализация метода getNotNullRole из интерфейса UserService
    @Override
    public User getNotNullRole(User user) {
        if (user.getRoles() == null) {
            user.setRoles(Collections.singleton(new Role(2L, "ROLE_USER")));
        }
        return user;
    }

    // Метод для добавления тестовых пользователей
    @PostConstruct
    public void addTestUsers() {
        roleRepository.save(new Role(1L, "ROLE_ADMIN"));
        roleRepository.save(new Role(2L, "ROLE_USER"));
        User newAdmin = new User("admin"
                , "admin"
                , roleService.getRoleByName(new String[]{"ROLE_ADMIN"})
                , 0
                , null);
        saveUserTest(newAdmin);
        User newUser = new User("user"
                , "user"
                , roleService.getRoleByName(new String[]{"ROLE_USER"})
                , 0,
                null);
        saveUserTest(newUser);
    }
}