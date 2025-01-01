package com.slippery.rentalmanagementsystem.controller;

import com.slippery.rentalmanagementsystem.UserNotFound;
import com.slippery.rentalmanagementsystem.dto.UserDto;
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
    @PostMapping("/login")
    public ResponseEntity<UserDto> login(@RequestBody User user) throws UserNotFound, IOException {
        return ResponseEntity.ok(service.login(user));
    }
    @PostMapping("/logout")
    public ResponseEntity<UserDto> logOut(@RequestBody Long userId) throws UserNotFound{
        return ResponseEntity.ok(service.logOut(userId));
    }
}
