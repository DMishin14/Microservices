package ru.itmentor.spring.boot_security.demo.controller;

// Импорт необходимых классов для работы с веб-контроллерами и моделями в Spring MVC
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.itmentor.spring.boot_security.demo.entity.User;
import ru.itmentor.spring.boot_security.demo.service.RoleServiceImpl;
import ru.itmentor.spring.boot_security.demo.service.UserServiceImpl;

import java.util.List;

// Аннотация, помечающая класс как контроллер Spring MVC
@Controller
// Аннотация Lombok, автоматически генерирующая конструктор с требуемыми зависимостями
@RequiredArgsConstructor
public class AdminController {

    // Зависимости от сервисов для работы с пользователями и ролями
    private final UserServiceImpl userService;
    private final RoleServiceImpl roleService;

    // Мappings для обработки GET-запросов на страницу администратора
    @GetMapping("/admin")
    public String showAllUsers(Model model) {
        // Получение списка всех пользователей через сервис
        List<User> users = userService.findAllUsers();
        // Добавление списка пользователей в модель для передачи в представление
        model.addAttribute("allUser", users);
        // Возвращение имени представления для отображения
        return "admin";
    }

    // Mapping для обработки GET-запросов на страницу сохранения нового пользователя
    @GetMapping("/admin/user-save")
    public String saveUserForm(Model model) {
        // Добавление списка ролей и пустого объекта User в модель для создания формы
        model.addAttribute("roles", roleService.getAllRoles());
        model.addAttribute("user", new User());
        // Возвращение имени представления для отображения формы
        return "admin-save";
    }

    // Mapping для обработки POST-запросов на сохранение нового пользователя
    @PostMapping("/admin/user-save")
    public String saveUser(User user) {
        // Сохранение пользователя с привязкой роли
        userService.getNotNullRole(user);
        userService.saveUser(user);
        // Перенаправление обратно на страницу администратора после сохранения
        return "redirect:/admin";
    }

    // Mapping для обработки DELETE-запросов на удаление пользователя по ID
    @DeleteMapping("/admin/user-delete/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        // Удаление пользователя по ID
        userService.deleteUserById(id);
        // Перенаправление обратно на страницу администратора после удаления
        return "redirect:/admin";
    }

    // Mapping для обработки GET-запросов на страницу обновления пользователя по ID
    @GetMapping("/admin/user-update/{id}")
    public String updateUserForm(@PathVariable("id") Long id, Model model) {
        // Добавление списка ролей и найденного пользователя в модель для создания формы обновления
        model.addAttribute("roles", roleService.getAllRoles());
        model.addAttribute("user", userService.findUserById(id));
        // Возвращение имени представления для отображения формы обновления
        return "admin-update";
    }

    // Mapping для обработки POST-запросов на обновление пользователя
    @PostMapping("/admin/user-update")
    public String updateUsers(@ModelAttribute("user") User user, @RequestParam(value = "nameRoles", required = false) String[] roles) {
        // Обновление пользователя с новыми ролями
        userService.getUserAndRoles(user, roles);
        userService.updateUser(user);
        // Перенаправление обратно на страницу администратора после обновления
        return "redirect:/admin";
    }
}
