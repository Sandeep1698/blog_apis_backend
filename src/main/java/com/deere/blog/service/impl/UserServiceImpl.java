package com.deere.blog.service.impl;

import com.deere.blog.config.BlogLiterals;
import com.deere.blog.entity.User;
import com.deere.blog.exception.ResourceNotFoundException;
import com.deere.blog.payload.UserDto;
import com.deere.blog.payload.UserDtoRes;
import com.deere.blog.repository.RoleRepository;
import com.deere.blog.repository.UserRepository;
import com.deere.blog.service.UserService;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired(required=true)
    private RoleRepository roleRepo;

    @Override
    public UserDtoRes registerNewUser(UserDto dto) {
        User user = modelMapper.map(dto,User.class);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.getRoles().add(roleRepo.findById(BlogLiterals.NORMAL_USER).get());
        return modelMapper.map(repository.save(user),UserDtoRes.class);
    }

    @Override
    @Transactional
    public UserDtoRes createUser(UserDto user) {
        return modelMapper.map(repository.save(dtoToEntity(user)), UserDtoRes.class);
    }
    private User dtoToEntity(UserDto dto){
        return this.modelMapper.map(dto,User.class);
    }

    private UserDtoRes entityToDto(User user){
        return  this.modelMapper.map(user,UserDtoRes.class);
    }

    @Override
    public UserDtoRes updateUser(UserDto dto, Integer userId) throws ResourceNotFoundException {
        User user = this.repository.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User","id",userId));
        user.setName(dto.getName());
        user.setPassword(dto.getPassword());
        user.setEmail(dto.getEmail());
        user.setAbout(dto.getAbout());
        return modelMapper.map(repository.save(user), UserDtoRes.class);
    }

    @Override
    public UserDtoRes getUserById(Integer userId) throws Exception{
        return modelMapper.map(repository.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User","id",userId)), UserDtoRes.class);
    }

    @Override
    public List<UserDtoRes> getAllUsers() {
        return repository.findAll().stream().map(this::entityToDto).collect(Collectors.toList());
    }

    @Override
    public void deleteUser(Integer userId) {
        User user = this.repository.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User","id",userId));
        repository.delete(user);
    }
}
