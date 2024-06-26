package ru.itmentor.spring.boot_security.demo.controller;

// Импорт необходимых классов для работы с веб-контроллерами и моделями в Spring MVC
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.itmentor.spring.boot_security.demo.entity.User;
import ru.itmentor.spring.boot_security.demo.service.UserServiceImpl;

// Импорт класса Principal для получения информации о текущем пользователе
import java.security.Principal;

@Controller
// Аннотация Lombok, автоматически генерирующая конструктор с требуемыми зависимостями
@RequiredArgsConstructor
public class UserController {
    // Зависимость от UserServiceImpl для работы с пользователями
    private final UserServiceImpl userService;

    // Mapping для обработки GET-запросов на отображение информации о текущем пользователе
    @GetMapping("/user")
    public String showUser(Principal principal, Model model) {
        // Загрузка информации о пользователе по его имени, полученному из контекста безопасности
        User user = (User) userService.loadUserByUsername(principal.getName());
        // Добавление информации о пользователе в модель для передачи в представление
        model.addAttribute("oneUser", user);
        // Возвращение имени представления для отображения информации о пользователе
        return "/user";
    }

    // Mapping для обработки GET-запросов на отображение формы обновления информации о пользователе по ID
    @GetMapping("/user/user-update/{id}")
    public String updateUserForm(@PathVariable("id") Long id, Model model) {
        // Поиск пользователя по ID и добавление его в модель для создания формы обновления
        model.addAttribute("user", userService.findUserById(id));
        // Возвращение имени представления для отображения формы обновления
        return "user-update";
    }

    // Mapping для обработки POST-запросов на обновление информации о пользователе
    @PostMapping("/user/user-update")
    public String updateUsers(@ModelAttribute("user") User user, Principal principal) {
        // Обновление информации о пользователе с учетом текущего пользователя, выполнившего запрос
        userService.getUserForUpdateUsers(user, principal.getName());
        userService.updateUser(user);
        // Перенаправление обратно на страницу пользователя после обновления
        return "redirect:/user";
    }

    // Mapping для обработки GET-запросов на отображение новостей
    @GetMapping("/news")
    public String showNews() {
        // Возвращение имени представления для отображения новостей
        return "/news";
    }
}
