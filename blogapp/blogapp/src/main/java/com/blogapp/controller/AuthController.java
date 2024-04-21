package com.blogapp.controller;

import com.blogapp.entity.User;
import com.blogapp.payload.LoginDto;
import com.blogapp.payload.Signup;
import com.blogapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {  //this controller class is created for signup and sign in feature implementation
    @Autowired
    private UserRepository userRepository;


    @PostMapping
    public ResponseEntity<String> createUser(@RequestBody Signup signUp){ //for sign up
        if (userRepository.existsByEmail(signUp.getEmail())){
            return new ResponseEntity<>("Email Id is already registered!!", HttpStatus.BAD_REQUEST);
        }
        if (userRepository.existsByUsername(signUp.getUsername())){
            return new ResponseEntity<>("User already exists!!",HttpStatus.BAD_REQUEST);
        }
        User user=new User();
        user.setName(signUp.getName());
        user.setUsername(signUp.getUsername());
        user.setEmail(signUp.getEmail());
        user.setPassword(BCrypt.hashpw(signUp.getPassword(), BCrypt.gensalt(10))); //store the password in the database in encrypted detail
        user.setUserRole(signUp.getUserRole());
        userRepository.save(user); //save the user in the database
        return new ResponseEntity<>("User Registered",HttpStatus.CREATED);
    }

    @PostMapping("/sign-in")
    public ResponseEntity<String> signIn(@RequestBody LoginDto loginDto){ //this method is used to check the username and password with the actual username and password which is present in the database

        //1. Supply loginDto.getUsername() - username to loadByUser method in CustomUserDetail class
        //2. it will compare -
        // Expected credentials - loginDto.getUsername(),loginDto.getPassword()
        //With the actual credentials given by loadByUser method

//       ; UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken=new UsernamePasswordAuthenticationToken(loginDto.getUsername(),loginDto.getPassword());  // 'UsernamePasswordAuthenticationToken'--> this class is part of Spring Security and represent an authenticating token containing username and password
//        Authentication authenticate = authenticationManager.authenticate(usernamePasswordAuthenticationToken); //this line authenticate the user by passing the 'usernamePasswordAuthenticationToken' to the authenticate() method of AuthenticationManager. If authentication successful then it will return an object of Authentication containing user's authenticating details
//        SecurityContextHolder.getContext().setAuthentication(authenticate);  //create the session variable after successfully login
            return new ResponseEntity<>("Sign in successful",HttpStatus.OK);
    }
}
