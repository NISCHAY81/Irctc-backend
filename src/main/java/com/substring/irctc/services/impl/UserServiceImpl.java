package com.substring.irctc.services.impl;

import com.substring.irctc.Repository.RoleRepo;
import com.substring.irctc.Repository.UserRepo;
import com.substring.irctc.dto.UserDto;
import com.substring.irctc.entity.Role;
import com.substring.irctc.entity.User;
import com.substring.irctc.exception.ResourceNotFoundException;
import com.substring.irctc.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Service
public class UserServiceImpl implements UserService {

    private UserRepo userRepo;
    private RoleRepo roleRepo;
    private ModelMapper modelMapper;
    private PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepo userRepo, RoleRepo roleRepo, ModelMapper modelMapper, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.roleRepo = roleRepo;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDto registerUser(UserDto userDto) {

        User user = modelMapper.map(userDto, User.class);

        if(user.getRoles() == null) {
            user.setRoles(new ArrayList<>());
        }

        Role role = roleRepo.findByName("ROLE_USER")
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Server is not configured properly, Please contact support."
                        ));

        user.getRoles().add(role);

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        user.setCreatedAt(LocalDateTime.now());

        User savedUser = userRepo.save(user);

        return modelMapper.map(savedUser, UserDto.class);
    }
}
