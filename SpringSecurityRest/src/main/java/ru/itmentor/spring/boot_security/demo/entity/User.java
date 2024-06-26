package ru.itmentor.spring.boot_security.demo.entity;

// Импорт необходимых классов для работы с JPA и Spring Data
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

// Импорт классов для работы с базой данных
import javax.persistence.*;
import java.util.Collection;
import java.util.Set;

// Аннотация Lombok, автоматически генерирующая геттеры, сеттеры, toString, equals и hashCode
@Data
// Аннотация JPA, указывающая, что данный класс является сущностью
@Entity
// Аннотация JPA, указывающая, что данный класс должен быть сопоставлен с таблицей в базе данных под названием "users"
@Table(name = "users")
public class User implements UserDetails {
    // Идентификатор пользователя, сгенерированный автоматически
    @Id
    // Спецификация колонки в таблице базы данных
    @Column(name = "user_id")
    // Стратегия генерации идентификатора
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    // Имя пользователя
    @Column
    private String username;
    // Пароль пользователя
    @Column
    private String password;
    // Возраст пользователя
    @Column
    private int age;
    // Фамилия пользователя
    @Column
    private String surname;

    // Многомножественное отношение между пользователями и ролями
    @ManyToMany(fetch = FetchType.LAZY)
    // Определение таблицы связи и колонок для связи
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"), // Колонка в таблице связи, ссылка на пользователя
            inverseJoinColumns = @JoinColumn(name = "role_id") // Колонка в таблице связи, ссылка на роль
    )
    private Set<Role> roles;

    // Конструктор без параметров
    public User() {
    }

    // Конструктор с параметрами
    public User(String username, String password, Set<Role> roles, int age, String surname) {
        this.username = username;
        this.password = password;
        this.roles = roles;
        this.age = age;
        this.surname = surname;
    }

    // Реализация методов интерфейса UserDetails
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles(); // Возвращает коллекцию ролей пользователя
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // Предполагается, что аккаунт не истек
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // Предполагается, что аккаунт заблокирован не был
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Предполагается, что учетные данные не истекли
    }

    @Override
    public boolean isEnabled() {
        return true; // Предполагается, что аккаунт активен
    }
}
