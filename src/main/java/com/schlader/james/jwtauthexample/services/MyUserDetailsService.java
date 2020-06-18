package com.schlader.james.jwtauthexample.services;

import com.schlader.james.jwtauthexample.models.entity.MyUser;
import com.schlader.james.jwtauthexample.models.entity.MyUserPrincipal;
import com.schlader.james.jwtauthexample.models.dao.UserDAO;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class MyUserDetailsService implements UserDetailsService {

    private UserDAO userDAO;

    public MyUserDetailsService(UserDAO userDAO){
        this.userDAO = userDAO;
    }

    @Override
    public MyUserPrincipal loadUserByUsername(String username)  {
        MyUser user = userDAO.findByUsername(username);
        if (user == null){
            throw new UsernameNotFoundException(username);
        }
        return new MyUserPrincipal(user);
    }
}
