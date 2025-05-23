package com.kosemeci.ecommerce.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.kosemeci.ecommerce.entity.User;
import com.kosemeci.ecommerce.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/user/profile")
    public ResponseEntity<User> createUserHandle(@RequestHeader("Authorization") String token) throws Exception {

        User user = userService.findUserByJwt(token);
        
        return ResponseEntity.ok(user);
    }

    
}
