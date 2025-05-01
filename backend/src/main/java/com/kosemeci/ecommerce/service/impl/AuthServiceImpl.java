package com.kosemeci.ecommerce.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.kosemeci.ecommerce.config.JwtProvider;
import com.kosemeci.ecommerce.domain.USER_ROLE;
import com.kosemeci.ecommerce.entity.Cart;
import com.kosemeci.ecommerce.entity.Seller;
import com.kosemeci.ecommerce.entity.User;
import com.kosemeci.ecommerce.entity.VerificationCode;
import com.kosemeci.ecommerce.repository.CartRepository;
import com.kosemeci.ecommerce.repository.SellerRepository;
import com.kosemeci.ecommerce.repository.UserRepository;
import com.kosemeci.ecommerce.repository.VerificationCodeRepository;
import com.kosemeci.ecommerce.request.LoginRequest;
import com.kosemeci.ecommerce.response.AuthResponse;
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
    private final CustomUserServiceImpl customUserService;
    private final SellerRepository sellerRepository;

    @Override
    public String createUser(SignupRequest request){

        VerificationCode verificationCode = verificationCodeRepository.findByEmail(request.getEmail());

        if(verificationCode==null || !verificationCode.getOtp().equals(request.getOtp())){
            try {
                throw new Exception("wrong otp");
            } catch (Exception ex) {
                System.out.println("boku yedik 3");
            }
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
    public void sendLoginOtp(String email,USER_ROLE role) throws Exception {
        
        String SIGNING_PREFIX = "signing_";
        if(email.startsWith(SIGNING_PREFIX)){
            email = email.substring(SIGNING_PREFIX.length());

            if(role.equals(USER_ROLE.ROLE_SELLER)){
                Seller seller = sellerRepository.findByEmail(email);
                if(seller==null){
                    throw new Exception("seller not found");
                }
 
            }
            else{
                User user = userRepository.findByEmail(email);
                if(user==null){
                    throw new Exception("user not found");
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
            System.out.println(e.getMessage());
        }
    }

    @Override
    public AuthResponse login(LoginRequest request) {
        
        String username= request.getEmail();
        String otp = request.getOtp();

        Authentication authentication = authenticate(username,otp);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtProvider.generateToken(authentication);
        AuthResponse response = new AuthResponse();
        response.setJwt(token);
        response.setMessage("Login Success.");

        Collection <? extends  GrantedAuthority> authorities = authentication.getAuthorities();
        String roleName = authorities.isEmpty() ? null : authorities.iterator().next().getAuthority();
        response.setRole(USER_ROLE.valueOf(roleName));
        return response;
    }

    private Authentication authenticate(String username, String otp) {
        UserDetails userDetails =  customUserService.loadUserByUsername(username);
        if(userDetails==null){
            throw new BadCredentialsException("invalid username or password");
        }

        VerificationCode verificationCode = verificationCodeRepository.findByEmail(username);
        if(verificationCode==null || !verificationCode.getOtp().equals(otp)){
            throw new BadCredentialsException("invalid code");
        }
        return new UsernamePasswordAuthenticationToken(
            userDetails,
            null,
            userDetails.getAuthorities()
        );
    }

}