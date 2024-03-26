package com.gichungasoftwares.project_user_service.service;

import com.gichungasoftwares.project_user_service.config.JwtProvider;
import com.gichungasoftwares.project_user_service.model.User;
import com.gichungasoftwares.project_user_service.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserRepository userRepository;

    @Override
    public List<User> allUsers() {
        return userRepository.findAll();
    }

    @Override
    public User findUserByJwt(String jwt) throws Exception {
        // get email from jwt
        String email = JwtProvider.getEmailFromJwtToken(jwt);
        // check if email exists in the database
        User user = userRepository.findByEmail(email);
        if(user == null){
            System.out.println("user with provided email does not exist - email from jwt " + user);
            throw new Exception("User with provided email does not exist or invalid token");
        }
        return user;
    }
}
