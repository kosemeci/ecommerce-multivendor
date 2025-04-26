package com.kosemeci.ecommerce.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.kosemeci.ecommerce.config.JwtProvider;
import com.kosemeci.ecommerce.domain.USER_ROLE;
import com.kosemeci.ecommerce.entity.Cart;
import com.kosemeci.ecommerce.entity.User;
import com.kosemeci.ecommerce.repository.CartRepository;
import com.kosemeci.ecommerce.repository.UserRepository;
import com.kosemeci.ecommerce.response.SignupRequest;
import com.kosemeci.ecommerce.service.AuthService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService{
    
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final CartRepository cartRepository;
    private final JwtProvider jwtProvider;

    @Override
    public String createUser(SignupRequest request) {
        
        User user = userRepository.findByEmail(request.getEmail());
        if(user==null){
            User createdUser = new User();
            createdUser.setEmail(request.getEmail());
            createdUser.setFullName(request.getFullName());
            createdUser.setRole(USER_ROLE.ROLE_CUSTOMER);
            createdUser.setMobile("87888956");
            createdUser.setPassword(passwordEncoder.encode(request.getOtp()));
            user = userRepository.save(createdUser);

            Cart cart = new Cart();
            cart.setUser(user);
            cartRepository.save(cart);
        }
        List <GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(USER_ROLE.ROLE_CUSTOMER.toString()));

        Authentication authentication =  new UsernamePasswordAuthenticationToken(request.getEmail(),null, authorities);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return jwtProvider.generateToken(authentication);
    }

}