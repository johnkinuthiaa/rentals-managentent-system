package com.slippery.rentalmanagementsystem.service.impl;

import com.slippery.rentalmanagementsystem.dto.UserDto;
import com.slippery.rentalmanagementsystem.exceptions.UserNotFound;
import com.slippery.rentalmanagementsystem.mail.LoginEmail;
import com.slippery.rentalmanagementsystem.mail.Register;
import com.slippery.rentalmanagementsystem.model.User;
import com.slippery.rentalmanagementsystem.repository.UserRepository;
import com.slippery.rentalmanagementsystem.service.UserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final JwtService jwtService;
    private final LoginEmail loginEmail;
    private final Register registrationEmail;
    private final UserRepository repository;
    private final AuthenticationManager authenticationManager;
    private PasswordEncoder passwordEncoder =new BCryptPasswordEncoder(12);

    public UserServiceImpl(JwtService jwtService, LoginEmail loginEmail, Register registrationEmail, UserRepository repository, AuthenticationManager authenticationManager) {
        this.jwtService = jwtService;
        this.loginEmail = loginEmail;
        this.registrationEmail = registrationEmail;
        this.repository = repository;
        this.authenticationManager = authenticationManager;
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
            registrationEmail.SendRegistrationEmail(user.getEmail(), user.getUsername(), user.getEmail(), LocalDateTime.now());
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
    public UserDto login(User user) throws UserNotFound, IOException {
        UserDto response =new UserDto();
        Optional<User> existingUser = Optional.ofNullable(repository.findUserByUsername(user.getUsername()));
        if(existingUser.isEmpty()){
            throw new UserNotFound("User not found");
        }
        try {
            Authentication authentication =authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    user.getUsername(),
                    user.getPassword()
            ));
            if(authentication.isAuthenticated()){
                var token =jwtService.generateJwtToken(user.getUsername());
                loginEmail.sendEmail(user.getEmail(), user.getUsername());
                existingUser.get().setIsActive(true);
                repository.save(existingUser.get());
                response.setJwtToken(token);
                response.setMessage("User "+user.getUsername()+" logged in successfully");
                response.setStatusCode(200);
            }else{
                response.setErrorMessage("User not authenticated successfully! please check your details and try again");
                response.setStatusCode(500);
            }

        } catch (IOException e) {
            throw new IOException(e);
        }
        return response;
    }

    @Override
    public UserDto logOut(Long userId) throws UserNotFound{
        UserDto response =new UserDto();
        Optional<User> existingUser =repository.findById(userId);
        if(existingUser.isEmpty()){
            throw new UserNotFound("user not found");
        }
        existingUser.get().setIsActive(false);
        repository.save(existingUser.get());
        response.setMessage("User logged out successfully");
        response.setStatusCode(200);
        return response;
    }
}
