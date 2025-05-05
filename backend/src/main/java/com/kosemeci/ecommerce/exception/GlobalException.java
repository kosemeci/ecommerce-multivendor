package com.kosemeci.ecommerce.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalException {
    
    @ExceptionHandler(SellerException.class)
    public ResponseEntity<ErrorDetails> sellerExceptionHandler (
            SellerException sellerException, WebRequest request){
            
        ErrorDetails errorDetails = new ErrorDetails();
        errorDetails.setMessage(sellerException.getMessage());
        errorDetails.setLocalDateTime(LocalDateTime.now());
        errorDetails.setDetails(request.getDescription(false));
        
        return new ResponseEntity<>(errorDetails,HttpStatus.BAD_REQUEST); 
    }
}
