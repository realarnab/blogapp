package com.blogapp.controller;

import com.blogapp.entity.User;
import com.blogapp.payload.LoginDto;
import com.blogapp.payload.Signup;
import com.blogapp.repository.UserRepository;
import com.blogapp.service.JWTService;
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
    @Autowired
    private JWTService jwtService;


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
        User user = userRepository.findByUsername(loginDto.getUsername());
        boolean checkpw = BCrypt.checkpw(loginDto.getPassword(),user.getPassword());
        if (checkpw){
            String s = jwtService.generateToken(user);
            return new ResponseEntity<>("token :"+s,HttpStatus.OK);
        }
        return new ResponseEntity<>("invalid credentials",HttpStatus.OK);
    }
}
