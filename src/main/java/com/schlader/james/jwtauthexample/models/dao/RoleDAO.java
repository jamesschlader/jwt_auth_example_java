package com.schlader.james.jwtauthexample.models.dao;

import com.schlader.james.jwtauthexample.models.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleDAO extends JpaRepository<Role, Long> {
    Role findByName(String role_admin);
}
