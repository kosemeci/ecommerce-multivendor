package com.kosemeci.ecommerce.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kosemeci.ecommerce.config.JwtProvider;
import com.kosemeci.ecommerce.domain.AccountStatus;
import com.kosemeci.ecommerce.entity.Seller;
import com.kosemeci.ecommerce.entity.VerificationCode;
import com.kosemeci.ecommerce.repository.VerificationCodeRepository;
import com.kosemeci.ecommerce.request.LoginRequest;
import com.kosemeci.ecommerce.response.AuthResponse;
import com.kosemeci.ecommerce.service.AuthService;
import com.kosemeci.ecommerce.service.EmailService;
import com.kosemeci.ecommerce.service.SellerService;
import com.kosemeci.ecommerce.utils.OtpUtil;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;



@RestController
@RequiredArgsConstructor
@RequestMapping("/seller")
public class SellerController {

    private SellerService sellerService;
    private VerificationCodeRepository verificationCodeRepository;
    private AuthService authService;
    private EmailService emailService;
    private JwtProvider jwtProvider;
    
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        
        String email = request.getEmail();
        // String otp = request.getOtp();

        request.setEmail("seller_"+email);
        AuthResponse response = authService.login(request);

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/verify/{otp}")
    public ResponseEntity<Seller> verifyEmail(@PathVariable String otp) throws Exception{

        VerificationCode verificationCode = verificationCodeRepository.findByOtp(otp);
        if(verificationCode==null){
            throw new Exception("wrong otp in verify seller email");
        }

        Seller seller = sellerService.verifyEmail(verificationCode.getEmail(), otp);

        // return new ResponseEntity<>(seller,HttpStatus.OK);
        return ResponseEntity.ok(seller);
    }

    @PostMapping("/signup")
    public ResponseEntity<Seller> createSeller (@RequestBody Seller seller) throws MessagingException {
        Seller savedSeller = sellerService.createSeller(seller);

        String otp = OtpUtil.generateOtp();

        VerificationCode verificationCode = new VerificationCode();
        verificationCode.setEmail(seller.getEmail());
        verificationCode.setOtp(otp);
        verificationCodeRepository.save(verificationCode);


        String subject = "Ecommerce Email Verfication Code";
        String text = "Welcome to ecommerce multivendor, verify your account this link ";
        String frontend_url = "http://localhost:5173/verify-seller";
        emailService.sendVerificationOtpEmail(seller.getEmail(),verificationCode.getOtp(),subject,text + frontend_url);

        
        return ResponseEntity.ok(savedSeller);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Seller> getSellerById(@PathVariable Long id) throws Exception {
        
        Seller seller = sellerService.getSellerById(id);
        return ResponseEntity.ok(seller);
    }
    
    @GetMapping("/profile")
    public ResponseEntity<Seller> getSellerByJwt(@RequestHeader String jwt) {

        String email = jwtProvider.getEmailFromJwtToken(jwt);
        Seller seller = sellerService.getSellerByEmail(email);
        return ResponseEntity.ok(seller);
    }

    @GetMapping
    public ResponseEntity<List<Seller>> getAllSellers(
            @RequestParam(required=false) AccountStatus accountStatus) {
            List<Seller> sellerList = sellerService.getAllSellers(accountStatus);
        return ResponseEntity.ok(sellerList);
    }
    
    
}
