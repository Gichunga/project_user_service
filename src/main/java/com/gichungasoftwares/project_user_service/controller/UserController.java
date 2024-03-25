package com.gichungasoftwares.project_user_service.controller;

import com.gichungasoftwares.project_user_service.model.User;
import com.gichungasoftwares.project_user_service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/profile")
    public ResponseEntity<User> findUserByJwt(@RequestHeader("Authorization") String jwt) throws Exception {
        return new ResponseEntity<>(userService.findUserByJwt(jwt), HttpStatus.OK);
    }
}
