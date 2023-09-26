package com.kharido.controller;

import com.kharido.dto.UserDto;
import com.kharido.entity.User;
import com.kharido.mapper.UserMapper;
import com.kharido.request.AuthRequest;
import com.kharido.response.AuthResponse;
import com.kharido.service.JwtService;
import com.kharido.service.UserInfoService;
import jdk.jshell.spi.ExecutionControl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    UserMapper userMapper;

    @Autowired
    UserInfoService userInfoService;

    @Autowired
    JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> createUserHandler(@RequestBody UserDto userDto) throws UsernameNotFoundException {
        User user = userMapper.userDtoToUser(userDto);
        userInfoService.addUser(user);
        String token = jwtService.generateToken(user.getEmail());
        AuthResponse authResponse = new AuthResponse(token, "User Added Successfully");
        return new ResponseEntity<AuthResponse>(authResponse, HttpStatus.CREATED);
    }

    @PostMapping("/signin")
    public ResponseEntity<AuthResponse> loginUserHandler(@RequestBody AuthRequest authRequest) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword()));
        if (authentication.isAuthenticated()) {
            String token = jwtService.generateToken(authRequest.getEmail());
            AuthResponse authResponse = new AuthResponse(token, "Successfully Signin");
            return new ResponseEntity<AuthResponse>(authResponse, HttpStatus.OK);
        } else {
            throw new UsernameNotFoundException("invalid user request !");
        }
    }
}
