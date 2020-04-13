package com.schlader.james.jwtauthexample.models.dao;

import com.schlader.james.jwtauthexample.models.entity.MyUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDAO extends JpaRepository<MyUser, Long> {
    MyUser findByUsername(String username);
}
