package com.blogapp.config;

import com.blogapp.security.CustomUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity  //enable web security to the application
@Deprecated 
public class SecurityConfig extends WebSecurityConfigurerAdapter { //'WebSecurityConfigurerAdapter' --> this class gives methods like 'configure' which is used to implement authentication and authorization in the application

    @Autowired
    private CustomUserDetailService customUserDetailService;

    @Bean
    public PasswordEncoder getPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }  //create the bean for PasswordEncoder

    @Bean
    public AuthenticationManager getAuthManager() throws Exception {
        return super.authenticationManagerBean();
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {  //this method will help to authenticate and authorize the user, this method is an overridden method which is originated from WebSecurityConfigurerAdapter class
        http.csrf().disable(). //disabling the CSRF(Cross-Security Request Forgery) protection, in this case it's a stateless authentication mechanism(using API) and our client is also not a web browser(PostMan)
                authorizeRequests().anyRequest().permitAll(); //Define the rule for URL pattern
//                antMatchers(HttpMethod.POST,"/api/auth/**").permitAll(). // Define the URL pattern type (POST) which can be accessed without authentication
//                anyRequest().authenticated() // Specify that if the client is try to access any other request(GET, PUT, DELETE) then it need to be authenticated
//                .and().httpBasic(); //this line provides a HTTP Basic security to the application if the client try to access a secured resource without authentication server will send a Status code(401) with Unauthorized message
    }

    @Override //it defines types of authentication
    public void configure(AuthenticationManagerBuilder auth) throws Exception { //by the help of this method the fetched user details by 'CustomUserDetailService' are given to spring memory
        auth.userDetailsService(customUserDetailService).passwordEncoder(getPasswordEncoder()); //decode the password

    }

}
