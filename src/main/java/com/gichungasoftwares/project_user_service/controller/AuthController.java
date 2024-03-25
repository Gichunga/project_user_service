package com.gichungasoftwares.project_user_service.controller;

import com.gichungasoftwares.project_user_service.config.JwtProvider;
import com.gichungasoftwares.project_user_service.model.User;
import com.gichungasoftwares.project_user_service.repository.UserRepository;
import com.gichungasoftwares.project_user_service.request.LoginRequest;
import com.gichungasoftwares.project_user_service.response.AuthResponse;
import com.gichungasoftwares.project_user_service.service.CustomUserDetailsServiceImpl;
import jdk.jshell.spi.ExecutionControl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CustomUserDetailsServiceImpl customUserDetails;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> signupHandler(@RequestBody User user) throws Exception {
        String email = user.getEmail();
        String password = user.getPassword();
        String fullName = user.getFullName();
        String mobile = user.getMobile();
        String role = user.getRole();

        // check if user already exists
        User isEmailExisting = userRepository.findByEmail(email);
        if (isEmailExisting != null) {
            throw new Exception("A user already exists with the provided email address" + email);
        }

        // set user details
        User createdUser = new User();
        createdUser.setEmail(email);
        createdUser.setPassword(passwordEncoder.encode(password));
        createdUser.setFullName(fullName);
        createdUser.setRole(role);
        createdUser.setMobile(mobile);

        // save user to the database
        User savedUser = userRepository.save(createdUser);

        // create authentication
        Authentication authentication = new UsernamePasswordAuthenticationToken(email, password);

        // set authentication to context holder
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // generate token
        String token = JwtProvider.generateToken(authentication);

        AuthResponse authResponse = new AuthResponse();
        authResponse.setJwt(token);
        authResponse.setMessage("Signup was successful");
        authResponse.setStatus(true);

        return new ResponseEntity<>(authResponse, HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> signinHandler(@RequestBody LoginRequest loginRequest) {
        String username = loginRequest.getEmail();
        String password = loginRequest.getPassword();

        System.out.println(username + "-----------" + password);

        Authentication authentication = authenticate(username, password);
        // set authentication to context holder
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // generate jwt token
        String token = JwtProvider.generateToken(authentication);

        AuthResponse authResponse = new AuthResponse();
        authResponse.setJwt(token);
        authResponse.setMessage("Login was successful");
        authResponse.setStatus(true);
        return new ResponseEntity<>(authResponse, HttpStatus.OK);
    }

    private Authentication authenticate(String username, String password) {
        UserDetails userDetails = customUserDetails.loadUserByUsername(username);

        System.out.println("Sign in user details : " + userDetails);

        if (userDetails == null) {
            System.out.println("sign in user details - null " + userDetails);
            throw new BadCredentialsException("Invalid username or password");
        }

        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            System.out.println("sign in user details - password not match " + userDetails);
            throw new BadCredentialsException("Invalid password");
        }

        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }
}
