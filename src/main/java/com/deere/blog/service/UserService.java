package com.deere.blog.service;

import com.deere.blog.payload.UserDto;
import com.deere.blog.payload.UserDtoRes;

import java.util.List;

public interface UserService {
    UserDtoRes registerNewUser(UserDto userDto);
    UserDtoRes createUser(UserDto user);
    UserDtoRes updateUser(UserDto user,Integer userId);
    UserDtoRes getUserById(Integer userId) throws Exception;
    List<UserDtoRes> getAllUsers();
    void deleteUser(Integer userId);
}
