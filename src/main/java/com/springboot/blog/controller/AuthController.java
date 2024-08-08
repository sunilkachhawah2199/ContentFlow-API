package com.springboot.blog.controller;


import com.springboot.blog.payload.LoginDto;
import com.springboot.blog.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    // auth interface instance
    private AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    // login method
    // when we want to add multiple endpoints then we use value attribute
    // now user can both endpoint
    @PostMapping(value = {"/login", "/signin"})
    public ResponseEntity<String> login(@RequestBody LoginDto loginDto){
        String res=authService.login(loginDto);
        return ResponseEntity.ok(res);
    }

}
