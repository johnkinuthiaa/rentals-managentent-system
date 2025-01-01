package com.slippery.rentalmanagementsystem;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.slippery.rentalmanagementsystem.model.User;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDto {
    private int statusCode;
    private String message;
    private String errorMessage;
    private User user;
    private List<User> userList;
}
