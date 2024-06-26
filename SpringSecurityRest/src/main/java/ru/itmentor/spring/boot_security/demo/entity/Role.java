package ru.itmentor.spring.boot_security.demo.entity;

// Импорт необходимых классов для работы с JPA и Spring Data
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

// Импорт классов для работы с базой данных
import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

// Аннотация, указывающая, что данный класс является сущностью JPA
@Entity
// Аннотация Lombok, автоматически генерирующая геттеры, сеттеры, toString, equals и hashCode
@Data
// Аннотация, указывающая, что данный класс должен быть сопоставлен с таблицей в базе данных под названием "roles"
@Table(name = "roles")
public class Role implements GrantedAuthority {
    // Идентификатор роли, сгенерированный автоматически
    @Id
    // Спецификация колонки в таблице базы данных
    @Column(name = "role_id")
    // Стратегия генерации идентификатора
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    // Название роли
    @Column
    private String name;
    // Многомножественное отношение между ролями и пользователями
    @ManyToMany(mappedBy = "roles")
    // Аннотация, указывающая, что поле должно игнорироваться при сериализации
    @Transient
    private Set<User> userSet = new HashSet<>();

    // Конструктор без параметров
    public Role() {

    }

    // Конструктор с одним параметром - идентификатором
    public Role(Long id) {
        this.id = id;
    }

    // Конструктор с двумя параметрами - идентификатором и названием
    public Role(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    // Реализация метода getAuthority из интерфейса GrantedAuthority
    @Override
    public String getAuthority() {
        return getName();
    }
}
