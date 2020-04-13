package com.schlader.james.jwtauthexample.models.dao;

import com.schlader.james.jwtauthexample.models.entity.Privilege;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Repository;

@Repository
public interface PrivilegeDAO extends JpaRepository<Privilege, Long> {
    Privilege findByNameEquals(GrantedAuthority name);
}
