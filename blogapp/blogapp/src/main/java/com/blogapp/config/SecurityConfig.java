package com.blogapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity  //enable web security to the application
public class SecurityConfig {


    @Bean
    public SecurityFilterChain doFilter(HttpSecurity http) throws Exception {  //this method will help to authenticate and authorize the user
        http.csrf().disable().cors().disable(); //disabling the CSRF(Cross-Security Request Forgery) protection, in this case it's a stateless authentication mechanism(using API) and our client is also not a web browser(PostMan)
                http.authorizeRequests(). //Define the rule for URL pattern
                requestMatchers("/api/auth/show/{postId}").hasAnyRole("USER","ADMIN"). // Define the URL pattern type (POST) which can be accessed without authentication
                requestMatchers("/api/post/create/{postId}").hasRole("ADMIN").
                        requestMatchers("/api/comments/**").hasRole("USER").
                        requestMatchers("/api/auth/**").permitAll().
                anyRequest().authenticated();// Specify that if the client is try to access any other request(GET, PUT, DELETE) then it need to be authenticated
            return http.build();
    }


}
