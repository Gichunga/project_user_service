package com.gichungasoftwares.project_user_service.service;

import com.gichungasoftwares.project_user_service.model.User;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    User createUser(User user) throws Exception;

    String deleteUser(Long id) throws Exception;

    List<User> allUsers();

    User updateUser(User user, Long id) throws Exception;

    User findUserById(Long id) throws Exception;

    User findUserByJwt(String jwt) throws Exception;
}
