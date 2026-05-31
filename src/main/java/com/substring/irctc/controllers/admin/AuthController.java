package com.substring.irctc.controllers.admin;

import com.substring.irctc.Repository.UserRepo;
import com.substring.irctc.config.Security.JwtHelper;
import com.substring.irctc.dto.ErrorResponse;
import com.substring.irctc.dto.JwtResponse;
import com.substring.irctc.dto.LoginRequest;
import com.substring.irctc.dto.UserDto;
import com.substring.irctc.entity.User;
import com.substring.irctc.services.UserService;
import io.jsonwebtoken.JwtHandler;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
 private AuthenticationManager authenticationManager;
 private UserDetailsService  userDetailsService;
 private JwtHelper jwtHelper;
 private UserService userService;
 private UserRepo  userRepo;
 private ModelMapper modelMapper;

    public AuthController(AuthenticationManager authenticationManager, UserDetailsService userDetailsService, JwtHelper jwtHelper, UserService userService, UserRepo userRepo, ModelMapper modelMapper) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtHelper = jwtHelper;
        this.userService = userService;
        this.userRepo = userRepo;
        this.modelMapper = modelMapper;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
     // token generate code
        try {
            UsernamePasswordAuthenticationToken authentication  = new UsernamePasswordAuthenticationToken(loginRequest.username(), loginRequest.password());
            this.authenticationManager.authenticate(authentication);
            // generate token
            UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.username());
            String token = this.jwtHelper.generateAccessToken(userDetails);
            String refreshToken = this.jwtHelper.generateRefreshToken(userDetails);
            User user = userRepo.findByEmail(loginRequest.username()).get();
            JwtResponse jwtResponse = new JwtResponse(
                    token,
                    refreshToken,
                    modelMapper.map(user, UserDto.class)
            );
             return  new ResponseEntity<>(jwtResponse, HttpStatus.OK);

        } catch(BadCredentialsException e) {
            System.out.println("Bad credentials, Authentication Failed");
            ErrorResponse errorResponse = new ErrorResponse("The username and password you entered is incorrect", "404", false );
            e.printStackTrace();
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }

    }
    @PostMapping("/register")
     public ResponseEntity<?> registerUser(@RequestBody UserDto userDto) {
        UserDto dto = userService.registerUser(userDto);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
     }

     @PostMapping("/refresh-token")
     public ResponseEntity<?> refreshToken(@RequestBody String refreshToken) {
        if(refreshToken == null) {
            return new ResponseEntity<>(new ErrorResponse("Refresh token is empty", "400", false), HttpStatus.BAD_REQUEST);
        }

        if(!jwtHelper.isRefreshToken(refreshToken)) {
            return new ResponseEntity<>(new ErrorResponse("Refresh token is not valid", "400", false), HttpStatus.BAD_REQUEST);
        }


       String username =  jwtHelper.getUsernameFromToken(refreshToken);
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        if(jwtHelper.isTokenValid(refreshToken,userDetails)) {
           String accessToken = jwtHelper.generateAccessToken(userDetails);
           String newRefreshToken = jwtHelper.generateRefreshToken(userDetails);
           UserDto userDto = modelMapper.map(userRepo.findByEmail(username).orElse(null), UserDto.class);
           return  new ResponseEntity<>(new JwtResponse(accessToken, newRefreshToken, userDto), HttpStatus.OK);

        } else {
            return new ResponseEntity<>(new ErrorResponse("Refresh token is not valid", "400", false), HttpStatus.BAD_REQUEST);

        }

     }
}
