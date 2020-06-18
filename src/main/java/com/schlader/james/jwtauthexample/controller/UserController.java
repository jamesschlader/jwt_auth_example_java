package com.schlader.james.jwtauthexample.controller;

import com.schlader.james.jwtauthexample.models.dto.AuthenticationRequest;
import com.schlader.james.jwtauthexample.models.dto.UserDTO;
import com.schlader.james.jwtauthexample.models.entity.MyUser;
import com.schlader.james.jwtauthexample.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/user")
public class UserController {

    private UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @GetMapping("/details")
    @PreAuthorize("hasAuthority('READ_PRIVILEGE')")
    public MyUser showUserDetails(@RequestHeader("Authorization") String auth){
        return userService.showUserDetails(auth);
    }

    @PostMapping("/adduser")
    @PreAuthorize("hasAuthority('WRITE_PRIVILEGE')")
    public MyUser addUser(@RequestBody UserDTO userDTO){
       return userService.addUser(userDTO);
    }

    @PostMapping("/login")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest request) throws Exception {
       return userService.createAuthenticationToken(request);
    }
}
