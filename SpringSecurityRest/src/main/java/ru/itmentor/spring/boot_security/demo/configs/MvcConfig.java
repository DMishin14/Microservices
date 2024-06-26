package ru.itmentor.spring.boot_security.demo.configs;

// Импорт необходимых классов из Spring Framework
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// Анонимация конфигурационного класса, который будет использоваться для настройки компонентов приложения
@Configuration
public class MvcConfig implements WebMvcConfigurer {

    // Метод, реализующий интерфейс WebMvcConfigurer, позволяющий добавить контроллеры представлений
    public void addViewControllers(ViewControllerRegistry registry) {
        // Добавление контроллера представления, который будет перенаправлять запросы к "/user" на страницу "user"
        registry.addViewController("/user").setViewName("user");
    }
}
