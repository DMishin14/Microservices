package ru.itmentor.spring.boot_security.demo.configs;

// Импорт необходимых классов из Spring Security и Spring Framework
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

// Маркер аннотации, указывающий, что данный класс является компонентом Spring контекста
@Component
public class SuccessUserHandler implements AuthenticationSuccessHandler {

    // Переопределенный метод, вызываемый после успешной аутентификации пользователя
    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException {
        // Получение набора ролей пользователя из объекта Authentication
        Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());

        // Проверка роли пользователя и перенаправление на соответствующую страницу
        if (roles.contains("ROLE_ADMIN")) {
            // Если пользователь имеет роль ADMIN, перенаправляем его на страницу администратора
            httpServletResponse.sendRedirect("/admin");
        } else if (roles.contains("ROLE_USER")) {
            // Если пользователь имеет роль USER, перенаправляем его на страницу пользователя
            httpServletResponse.sendRedirect("/user");
        } else {
            // Для других ролей или если роли не определены, перенаправляем на главную страницу
            httpServletResponse.sendRedirect("/");
        }
    }
}
