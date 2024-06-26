package ru.itmentor.spring.boot_security.demo.controller;

// Импорт необходимых классов для работы с аутентификацией и выходом из системы в Spring Security
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

// Импорт необходимых классов для работы с HTTP-запросами и ответами
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class LogoutController {
    // Mapping для обработки POST-запросов на выход из системы
    @PostMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        // Получение текущего контекста безопасности, содержащего информацию о текущем пользователе
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // Проверка, есть ли у пользователя активная аутентификация
        if (authentication!= null) {
            // Создание экземпляра обработчика выхода из системы
            new SecurityContextLogoutHandler().logout(request, response, authentication);
        }
        // После успешного выхода из системы перенаправление на страницу входа
        return "redirect:/login";
    }
}
