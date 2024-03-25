package com.gichungasoftwares.project_user_service.service;

import com.gichungasoftwares.project_user_service.model.User;
import com.gichungasoftwares.project_user_service.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomUserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    /*
    * load user details (email, password and authorities based on the provided username)*/
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username);
        if(user == null) {
            throw new UsernameNotFoundException("User not found " + username);
        }
        // if the user is found, create a list of GrantedAuthority object (which represents the authorities/roles assigned to the user)
        List<GrantedAuthority> grantedAuthorityList = new ArrayList<>();
        // construct and return an instance of security.core.userDetails.User class which implements the UserDetails interface
        // this class represents the authenticated user with details such as username, password, and authorities.
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), grantedAuthorityList);
    }
}
