package ru.platform.learning.compilerservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.platform.learning.compilerservice.entity.Role;


public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findRoleByRole(String name);


}
