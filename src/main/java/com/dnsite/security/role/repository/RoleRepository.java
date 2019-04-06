package com.dnsite.security.role.repository;

import com.dnsite.security.role.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {

}