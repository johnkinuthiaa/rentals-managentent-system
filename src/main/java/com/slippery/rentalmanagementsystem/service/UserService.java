package com.slippery.rentalmanagementsystem.service;

import com.slippery.rentalmanagementsystem.UserDto;
import com.slippery.rentalmanagementsystem.model.User;

import java.io.IOException;

public interface UserService {
    UserDto register(User user) throws IOException;
    UserDto login(User user);
}
