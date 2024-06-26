package ru.itmentor.spring.boot_security.demo.dao;

// Импорт необходимого интерфейса для работы с репозиторием в Spring Data JPA
import org.springframework.data.jpa.repository.JpaRepository;
// Импорт сущности Role, которую будем использовать в репозитории
import ru.itmentor.spring.boot_security.demo.entity.Role;

// Интерфейс RoleRepository расширяет JpaRepository, предоставляя базовые операции CRUD для сущности Role
public interface RoleRepository extends JpaRepository<Role, Long> {
    // Добавляем метод findRoleByName для поиска роли по её имени
    Role findRoleByName(String name);
}
