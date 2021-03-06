package com.schlader.james.jwtauthexample.services;

import com.schlader.james.jwtauthexample.models.dao.RoleDAO;
import com.schlader.james.jwtauthexample.models.dao.UserDAO;
import com.schlader.james.jwtauthexample.models.dto.AuthenticationRequest;
import com.schlader.james.jwtauthexample.models.dto.AuthenticationResponse;
import com.schlader.james.jwtauthexample.models.dto.MyUserDTO;
import com.schlader.james.jwtauthexample.models.dto.UserDTO;
import com.schlader.james.jwtauthexample.models.entity.MyUser;
import com.schlader.james.jwtauthexample.models.entity.MyUserPrincipal;
import com.schlader.james.jwtauthexample.models.entity.Role;
import com.schlader.james.jwtauthexample.utils.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@Slf4j
public class UserService {
    private UserDAO userDAO;
    private RoleDAO roleDAO;
    private JwtUtil jwtUtil;
    private AuthenticationManager authenticationManager;
    private MyUserDetailsService userDetailsService;

    public UserService(UserDAO userDAO, RoleDAO roleDAO, JwtUtil jwtUtil, AuthenticationManager authenticationManager, MyUserDetailsService userDetailsService) {
        this.userDAO = userDAO;
        this.roleDAO = roleDAO;
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
    }

    public MyUser showUserDetails(String auth) {
        String username = jwtUtil.extractUsername(auth.substring(7));
        return userDAO.findByUsername(username);
    }

    public MyUser addUser(UserDTO userDTO){
        String salt = BCrypt.gensalt();
        MyUser user = new MyUser(userDTO.getUsername(), BCrypt.hashpw(userDTO.getPassword(), salt));
        Set<Role> roles = new HashSet<>();
        roles.add(roleDAO.findByName("ROLE_User"));
        user.setRoles(roles);
        return userDAO.saveAndFlush(user);
    }

    public ResponseEntity<?> createAuthenticationToken(AuthenticationRequest request)  {
        try{
            log.info("incoming request from " + request.getUsername());
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(),
                    request.getPassword()));
        } catch (BadCredentialsException e){
            throw new UsernameNotFoundException("Incorrect username or password");
        }
        final MyUserPrincipal userDetails = userDetailsService.loadUserByUsername(request.getUsername());
        String jwt = jwtUtil.generateToken(new MyUserDTO(userDetails));
        return ResponseEntity.ok(new AuthenticationResponse(jwt));
    }
}
