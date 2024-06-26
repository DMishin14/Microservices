package ru.itmentor.spring.boot_security.demo.service;

// Импорт необходимых классов и аннотаций для работы со службой в Spring
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
// Импорт сущности Role и репозитория RoleRepository для работы с ролями
import ru.itmentor.spring.boot_security.demo.entity.Role;
import ru.itmentor.spring.boot_security.demo.dao.RoleRepository;

// Импорт классов для работы с множествами и массивами
import java.util.HashSet;
import java.util.Set;

// Аннотация Service, помечающая класс как Spring Bean для работы с бизнес-логикой
@Service
// Аннотация Lombok, автоматически генерирующая конструктор с требуемыми зависимостями
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    // Зависимость от RoleRepository для выполнения операций с ролями в базе данных
    private final RoleRepository roleRepository;

    // Реализация метода getAllRoles из интерфейса RoleService
    @Override
    public Set<Role> getAllRoles() {
        // Возвращение всех ролей из репозитория в виде неизменяемого множества
        return new HashSet<>(roleRepository.findAll());
    }

    // Реализация метода getRoleByName из интерфейса RoleService
    @Override
    public Set<Role> getRoleByName(String[] roleName) {
        // Создание множества ролей для возврата
        Set<Role> roleSet = new HashSet<>();
        // Цикл по всем именам ролей, полученным в качестве параметра
        for (String role : roleName) {
            // Добавление роли, найденной по имени, в множество
            roleSet.add(roleRepository.findRoleByName(role));
        }
        // Возвращение множества ролей
        return roleSet;
    }
}
