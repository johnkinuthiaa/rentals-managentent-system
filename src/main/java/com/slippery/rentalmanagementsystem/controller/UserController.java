package com.slippery.rentalmanagementsystem.controller;

import com.slippery.rentalmanagementsystem.UserDto;
import com.slippery.rentalmanagementsystem.model.User;
import com.slippery.rentalmanagementsystem.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/auth")
public class UserController {
    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }
    @PostMapping("/register")
    public ResponseEntity<UserDto> register(@RequestBody User user) throws IOException {
        return ResponseEntity.ok(service.register(user));
    }
}
