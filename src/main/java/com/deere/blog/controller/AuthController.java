package com.deere.blog.controller;

import com.deere.blog.exception.ApiException;
import com.deere.blog.payload.JwtAuthRequest;
import com.deere.blog.payload.JwtAuthResponse;
import com.deere.blog.payload.UserDto;
import com.deere.blog.payload.UserDtoRes;
import com.deere.blog.security.JwtTokenHelper;
import com.deere.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth/")
public class AuthController {


    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenHelper jwtTokenHelper;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private UserService userService;


    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponse> createToken(@RequestBody JwtAuthRequest request) throws Exception {
        doAuthenticate(request.getUserName(), request.getPassword());
        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUserName());
        String token = jwtTokenHelper.generateToken(userDetails);
        JwtAuthResponse response = JwtAuthResponse.builder().token(token).userName(userDetails.getUsername()).build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    private void doAuthenticate(String userName, String password) throws Exception {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userName,password);
        try {
            authenticationManager.authenticate(authenticationToken);
        } catch (BadCredentialsException e) {
            throw new ApiException("Invalid Username or Password !!");
        }
    }

    @RequestMapping(value = "/registerUser",method = RequestMethod.POST)
    public ResponseEntity<UserDtoRes> registerUser(@RequestBody UserDto userDto) throws Exception {
        return new ResponseEntity<>(userService.registerNewUser(userDto), HttpStatus.CREATED );
    }
}
