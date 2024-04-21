package com.blogapp.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.blogapp.entity.User;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JWTService {

    @Value("${jwt.algorithm.key}") //it will read the value from the properties file and initialize the variable
    private String algorithmKey; //for secret key
    @Value("${jwt.issuer}")
    private String issuer; //for creating the issuer details
    @Value("${jwt.expiry.duration}")
    private int expiryTime; //to set the expiry time
    private Algorithm algorithm; //to set the algorithm

    private final static String USER_NAME="username"; //to set the username, because of static it is initialised with dummy value

    @PostConstruct //this method will automatically execute after DI
    public void postContract(){
        algorithm=Algorithm.HMAC256(algorithmKey); //perform HMAC(Hash based Message Authentication Code) algorithm on secret key
    }

    public String generateToken(User user){ //this method is used to generate JWT token
        return JWT.create() //creating a JWT token
                .withClaim(USER_NAME,user.getUsername()) //putting the username in the payload
                .withExpiresAt(new Date(System.currentTimeMillis()+expiryTime)) //set the expiry time from the current time
                .withIssuer(issuer) //set the issuer detail
                .sign(algorithm); //return the token as String
    }

    public String getUserName(String token){ //this method is used to extract the username from the token during the subsequent request made by the client
        DecodedJWT decodedJWT=JWT.require(algorithm).withIssuer(issuer).build().verify(token); //decode the token
        return decodedJWT.getClaim(USER_NAME).asString(); //extract the username from the token as a string
    }
}
