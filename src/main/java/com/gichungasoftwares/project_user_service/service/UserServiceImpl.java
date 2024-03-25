package com.gichungasoftwares.project_user_service.service;

import com.gichungasoftwares.project_user_service.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService{

    @Override
    public User createUser(User user) throws Exception {
        return null;
    }

    @Override
    public String deleteUser(Long id) throws Exception {
        return null;
    }

    @Override
    public List<User> allUsers() {
        return null;
    }

    @Override
    public User updateUser(User user, Long id) throws Exception {
        return null;
    }

    @Override
    public User findUserById(Long id) throws Exception {
        return null;
    }

    @Override
    public User findUserByJwt(String jwt) throws Exception {
        return null;
    }
}
