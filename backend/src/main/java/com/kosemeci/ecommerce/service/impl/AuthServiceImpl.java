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
import com.kosemeci.ecommerce.entity.VerificationCode;
import com.kosemeci.ecommerce.repository.CartRepository;
import com.kosemeci.ecommerce.repository.UserRepository;
import com.kosemeci.ecommerce.repository.VerificationCodeRepository;
import com.kosemeci.ecommerce.response.SignupRequest;
import com.kosemeci.ecommerce.service.AuthService;
import com.kosemeci.ecommerce.service.EmailService;
import com.kosemeci.ecommerce.utils.OtpUtil;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService{
    
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final CartRepository cartRepository;
    private final JwtProvider jwtProvider;
    private final VerificationCodeRepository verificationCodeRepository;
    private final EmailService emailService; 

    @Override
    public String createUser(SignupRequest request) throws Exception {

        VerificationCode verificationCode = verificationCodeRepository.findByEmail(request.getEmail());

        if(verificationCode==null || !verificationCode.getOtp().equals(request.getOtp())){
            throw new Exception("wrong otp");
        }
        
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

    @Override
    public void sendLoginOtp(String email) {
        
        String SIGNING_PREFIX = "signin_";
        if(email.startsWith(SIGNING_PREFIX)){
            email = email.substring(SIGNING_PREFIX.length());
            User user = userRepository.findByEmail(email);
            if(user==null){
                try {
                    throw new Exception("User not exist with provided email");
                } catch (Exception ex) {
                    System.out.println("boku yedik 2");

                }
            }
        }

        VerificationCode isExist = verificationCodeRepository.findByEmail(email);
        if(isExist!=null){
            verificationCodeRepository.delete(isExist);
        }

        String otp = OtpUtil.generateOtp();
        VerificationCode verificationCode = new VerificationCode();
        verificationCode.setEmail(email);
        verificationCode.setOtp(otp);
        verificationCodeRepository.save(verificationCode);

        String subject = "Signup/Login OTP";
        String text = "Aramıza katılmak için otp kodu ile doğrulamanı gerçekleştir";
        try {
            emailService.sendVerificationOtpEmail(email, otp, subject, text);
        } catch (MessagingException e) {
            System.out.println("boku yedik 1");
        }
    }

}