package com.blogapp.security;

import com.blogapp.entity.User;
import com.blogapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;


//this class is used to fetch the actual username and password from the database  --> spring security recommended that in this way should be authentication done
@Component
public class CustomUserDetailService implements UserDetailsService { // 'UserDetailsService' --> this interface provides the functionality required to load the user details from the database

    @Autowired
    private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException { //loadByUsername--> this method is an overridden method which is extends from the UserDetailsService interface
        User user = userRepository.findByUsername(username); //return the object of entity class 'User'
        if (user==null){
            throw new UsernameNotFoundException("User not found");
        }

        return new org.springframework.security.core.userdetails.User(user.getUsername(),user.getPassword(),null);  //returns a UserDetails (User(Spring security) object is upcasted) with authenticate user details

    }
}
