package com.schlader.james.jwtauthexample.config;

import com.schlader.james.jwtauthexample.models.entity.MyUser;
import com.schlader.james.jwtauthexample.models.entity.Privilege;
import com.schlader.james.jwtauthexample.models.entity.Role;
import com.schlader.james.jwtauthexample.models.dao.PrivilegeDAO;
import com.schlader.james.jwtauthexample.models.dao.RoleDAO;
import com.schlader.james.jwtauthexample.models.dao.UserDAO;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class InitialDataLoader implements ApplicationListener<ContextRefreshedEvent> {

    private boolean alreadySetup = false;

    private UserDAO userDAO;
    private RoleDAO roleDAO;
    private PrivilegeDAO privilegeDAO;
    private PasswordEncoder passwordEncoder;

    public InitialDataLoader(UserDAO userDAO,
                             RoleDAO roleDAO,
                             PrivilegeDAO privilegeDAO,
                             PasswordEncoder passwordEncoder
    ) {
        this.userDAO = userDAO;
        this.roleDAO = roleDAO;
        this.privilegeDAO = privilegeDAO;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {

        if (alreadySetup)
            return;

        Privilege readPrivilege
                = createPrivilegeIfNotFound("READ_PRIVILEGE");
        Privilege writePrivilege
                = createPrivilegeIfNotFound("WRITE_PRIVILEGE");

        createRoleIfNotFound("ROLE_ADMIN", new HashSet<>(Arrays.asList(readPrivilege, writePrivilege)));
        createRoleIfNotFound("ROLE_User", new HashSet<>(Arrays.asList(writePrivilege)));

        MyUser user = userDAO.findByUsername("Test");
        if (user == null) {
            Role adminRole = roleDAO.findByName("ROLE_ADMIN");
            MyUser newUser = new MyUser(null, "Test", passwordEncoder.encode("test"), true, true, true, true,
                    new HashSet<>(Collections.singletonList(adminRole)));

            userDAO.save(newUser);
        }

        MyUser user2 = userDAO.findByUsername("User");
        if (user2 == null) {
            Role userRole = roleDAO.findByName("ROLE_User");
            MyUser newUser = new MyUser(null, "User", passwordEncoder.encode("user"), true, true, true, true,
                    new HashSet<>(Collections.singletonList(userRole)));
            userDAO.save(newUser);
        }

        alreadySetup = true;
    }

    @Transactional
    public Privilege createPrivilegeIfNotFound(String name) {
        GrantedAuthority test = new SimpleGrantedAuthority(name);
        Privilege privilege = privilegeDAO.findByNameEquals(test);
        if (privilege == null) {
            privilege = new Privilege(name);
            privilegeDAO.save(privilege);
        }
        return privilege;
    }

    @Transactional
    public Role createRoleIfNotFound(
            String name, Set<Privilege> privileges) {

        Role role = roleDAO.findByName(name);
        if (role == null) {
            role = new Role(name);
            role.setPrivileges(privileges);
            roleDAO.save(role);
        }
        return role;
    }

}
