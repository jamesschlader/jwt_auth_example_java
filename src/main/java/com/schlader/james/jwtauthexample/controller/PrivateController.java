package com.schlader.james.jwtauthexample.controller;

import com.schlader.james.jwtauthexample.models.entity.Privilege;
import com.schlader.james.jwtauthexample.models.entity.Role;
import com.schlader.james.jwtauthexample.services.PrivateService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/private")
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class PrivateController {

    private PrivateService privateService;

    public PrivateController(PrivateService privateService){
        this.privateService = privateService;
    }

    @GetMapping
    public ResponseEntity<String> privateRoot(){
        String response = "You made it to the private root.";
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/guarded")
    @PreAuthorize("hasAuthority('READ_PRIVILEGE')")
    public ResponseEntity<String> guardedRoute(){
        String message = "Wow you're a user!";
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @GetMapping("/getallprivileges")
    @PreAuthorize("hasAuthority('WRITE_PRIVILEGE')")
    public ResponseEntity<List<Privilege>> getAllPrivileges(){
        return new ResponseEntity<>(privateService.getAllPrivileges(), HttpStatus.OK);
    }

    @GetMapping("/getallroles")
    @PreAuthorize("hasAuthority('WRITE_PRIVILEGE')")
    public ResponseEntity<List<Role>> getAllRoles(){
        return new ResponseEntity<>(privateService.getAllRoles(), HttpStatus.OK);
    }

}
