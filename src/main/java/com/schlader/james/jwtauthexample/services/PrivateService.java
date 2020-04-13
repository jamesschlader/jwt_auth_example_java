package com.schlader.james.jwtauthexample.services;

import com.schlader.james.jwtauthexample.models.dao.PrivilegeDAO;
import com.schlader.james.jwtauthexample.models.dao.RoleDAO;
import com.schlader.james.jwtauthexample.models.entity.Privilege;
import com.schlader.james.jwtauthexample.models.entity.Role;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PrivateService {

    private PrivilegeDAO privilegeDAO;
    private RoleDAO roleDAO;

    public PrivateService(PrivilegeDAO privilegeDAO, RoleDAO roleDAO){
        this.privilegeDAO = privilegeDAO;
        this.roleDAO = roleDAO;
    }

    public List<Privilege> getAllPrivileges(){
        return privilegeDAO.findAll();
    }

    public List<Role> getAllRoles() {
        return roleDAO.findAll();
    }
}
