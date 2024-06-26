package ru.itmentor.spring.boot_security.demo.configs;

// Импорт необходимых классов из Spring Security и Spring Framework
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import ru.itmentor.spring.boot_security.demo.service.UserServiceImpl;

// Аннотация, указывающая, что данный класс является конфигурационным классом Spring
@Configuration
// Включение безопасности веб-приложения
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    // Зависимость от SuccessUserHandler для перенаправления после успешной аутентификации
    private final SuccessUserHandler successUserHandler;

    // Зависимость от UserServiceImpl для работы с пользовательскими данными
    private final UserServiceImpl userService;

    // Конструктор класса, инициализирующий зависимости
    public WebSecurityConfig(SuccessUserHandler successUserHandler, UserServiceImpl userService) {
        this.successUserHandler = successUserHandler;
        this.userService = userService;
    }

    // Переопределенный метод для настройки источника аутентификации
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // Настройка источника аутентификации с использованием UserServiceImpl и BCrypt для хэширования паролей
        auth
                .userDetailsService(userService)
                .passwordEncoder(bCryptPasswordEncoder());
    }

    // Переопределенный метод для настройки безопасности веб-приложения
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // Настройка правил доступа к URL
        http
                .authorizeRequests()
                .antMatchers("/login").permitAll() // Разрешить доступ ко всем URL, начинающимся с /login
                .antMatchers("/admin/**").hasAnyRole("ADMIN") // Требовать наличие роли ADMIN для доступа к URL, начинающимся с /admin
                .antMatchers("/user/**").hasAnyRole("USER", "ADMIN") // Требовать наличие роли USER или ADMIN для доступа к URL, начинающимся с /user
                .antMatchers("/news").permitAll() // Разрешить доступ ко всем URL, начинающимся с /news
                .anyRequest().authenticated()
                .and()
                .formLogin().successHandler(successUserHandler)
                .permitAll()
                .and()
                .logout()
                .permitAll()
                .and()
                .csrf().disable();
    }

    // Создание бина для BCryptPasswordEncoder
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}


