package com.deere.blog.controller;

import com.deere.blog.payload.ApiResponse;
import com.deere.blog.payload.UserDto;
import com.deere.blog.payload.UserDtoRes;
import com.deere.blog.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/deere/users")
public class UserController {

    @Autowired
    private UserService service;

    @RequestMapping(value = "/create",method = RequestMethod.POST)
    public ResponseEntity<UserDtoRes> createNewUser(@Valid @RequestBody UserDto dto){
        return new ResponseEntity<>(service.createUser(dto), HttpStatus.CREATED);
    }

    @RequestMapping(value = "/update/{userId}",method = RequestMethod.PUT)
    public ResponseEntity<UserDtoRes> updateUser(@Valid @RequestBody UserDto dto,@PathVariable("userId") Integer userId){
        return new ResponseEntity<>(service.updateUser(dto,userId), HttpStatus.OK);
    }

    @RequestMapping(value = "/getUsers",method = RequestMethod.GET)
    public ResponseEntity<List<UserDtoRes>> getAllUsers(){
        return new ResponseEntity<>(service.getAllUsers(), HttpStatus.OK);
    }

    @RequestMapping(value = "/getByUserId",method = RequestMethod.GET)
    public ResponseEntity<UserDtoRes> getByUserId(@RequestParam("userId") Integer id) throws Exception {
        return new ResponseEntity<>(service.getUserById(id), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "/delete",method = RequestMethod.DELETE)
    public ResponseEntity<ApiResponse> deleteUser(@RequestParam("userId") Integer id){
        service.deleteUser(id);
        return new ResponseEntity<>(new ApiResponse("Deletion Successful !!",true,"200"), HttpStatus.OK);
    }
}
