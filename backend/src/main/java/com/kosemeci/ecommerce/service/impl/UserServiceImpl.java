package com.kosemeci.ecommerce.service.impl;

import org.springframework.stereotype.Service;

import com.kosemeci.ecommerce.config.JwtProvider;
import com.kosemeci.ecommerce.entity.User;
import com.kosemeci.ecommerce.repository.UserRepository;
import com.kosemeci.ecommerce.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;


    @Override
    public User findUserByJwt(String token) {
        
        String email = jwtProvider.getEmailFromJwtToken(token);

        return this.findUserByEmail(email);
    }

    @Override
    public User findUserByEmail(String email) {
        
        User user = userRepository.findByEmail(email);
        if(user==null){
            try {
                throw new Exception("user not found with email - "+ email);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        return user;
    }
    
}
