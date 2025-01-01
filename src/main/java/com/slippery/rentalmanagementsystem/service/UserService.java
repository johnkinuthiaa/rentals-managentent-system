package com.slippery.rentalmanagementsystem.service;

import com.slippery.rentalmanagementsystem.dto.UserDto;
import com.slippery.rentalmanagementsystem.model.User;

import java.io.IOException;

public interface UserService {
    UserDto register(User user) throws IOException;
    UserDto login(User user) throws IOException;
    UserDto logOut(Long userId);
}
