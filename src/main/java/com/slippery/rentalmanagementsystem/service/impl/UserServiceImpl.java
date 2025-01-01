package com.slippery.rentalmanagementsystem.service.impl;

import com.slippery.rentalmanagementsystem.UserDto;
import com.slippery.rentalmanagementsystem.mail.LoginEmail;
import com.slippery.rentalmanagementsystem.model.User;
import com.slippery.rentalmanagementsystem.repository.UserRepository;
import com.slippery.rentalmanagementsystem.service.UserService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;

@Service
public class UserServiceImpl implements UserService {
    private final LoginEmail loginEmail;
    private final UserRepository repository;
    private PasswordEncoder passwordEncoder =new BCryptPasswordEncoder(12);

    public UserServiceImpl(LoginEmail loginEmail, UserRepository repository) {
        this.loginEmail = loginEmail;
        this.repository = repository;
    }

    @Override
    public UserDto register(User user) throws IOException {
        UserDto response =new UserDto();
        User existingUser =repository.findUserByUsername(user.getUsername());
        User existingEmail =repository.findUserByEmail(user.getEmail());
        if(existingUser !=null){
            response.setErrorMessage("User with username "+existingUser.getUsername() +"already exists");
            response.setStatusCode(500);
            return response;
        }
        if(existingEmail !=null){
            response.setErrorMessage("User with username "+existingEmail.getEmail() +" already exists");
            response.setStatusCode(500);
            return response;
        }

        user.setCreatedOn(LocalDateTime.now());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        try{
            loginEmail.sendEmail(user.getEmail(), user.getUsername());
            repository.save(user);
            response.setMessage("user "+user.getUsername()+" saved successfully");
            response.setStatusCode(200);
            response.setUser(user);
        } catch (Exception e) {
            throw new IOException(e);
        }
        return response;
    }

    @Override
    public UserDto login(User user) {
        return null;
    }
}
