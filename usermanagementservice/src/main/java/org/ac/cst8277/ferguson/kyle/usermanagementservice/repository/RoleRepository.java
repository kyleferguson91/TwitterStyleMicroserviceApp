package org.ac.cst8277.ferguson.kyle.usermanagementservice.repository;

import org.ac.cst8277.ferguson.kyle.usermanagementservice.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
