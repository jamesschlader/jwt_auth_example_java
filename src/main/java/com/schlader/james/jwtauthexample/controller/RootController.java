package com.schlader.james.jwtauthexample.controller;

import com.schlader.james.jwtauthexample.models.dto.AuthenticationResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class RootController {

    @GetMapping("/hello")
    public AuthenticationResponse hello(){
        return new AuthenticationResponse("Hello World!");
    }
}
