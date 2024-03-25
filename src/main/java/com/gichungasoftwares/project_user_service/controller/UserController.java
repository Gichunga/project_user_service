package com.gichungasoftwares.project_user_service.controller;

import com.gichungasoftwares.project_user_service.model.User;
import com.gichungasoftwares.project_user_service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    public User findUserByJwt(@RequestHeader("Authorization") String jwt){
        return null;
    }
}
