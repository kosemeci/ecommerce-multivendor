package com.kosemeci.ecommerce.utils;

import java.util.Random;

public class OtpUtil {
    
    public static String generateOtp(){
        int otpLength=6;
        Random randomNumber = new Random();
        StringBuilder otp = new StringBuilder(otpLength);

        for (int i = 0; i < otpLength; i++) {
            otp.append(randomNumber.nextInt(10));
        }

        return otp.toString();
    }
}
