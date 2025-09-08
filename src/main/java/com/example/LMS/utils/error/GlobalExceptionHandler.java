package com.example.LMS.utils.error;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.LMS.domain.res.ApiResponse;

import jakarta.validation.ConstraintViolationException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = {
            AlreadyExistsException.class,

    })
    public ResponseEntity<ApiResponse<Object>> EmailAlreadyExistsException(Exception ex) {
        ApiResponse<Object> res = new ApiResponse<>();
        res.setStatusCode(HttpStatus.BAD_REQUEST.value());
        res.setError("Resource already exists");
        res.setMessage(ex.getMessage());
        return ResponseEntity.status(res.getStatusCode()).body(res);

    }

    @ExceptionHandler(value = {
            BadCredentialsException.class,
            UsernameNotFoundException.class
    })
    public ResponseEntity<ApiResponse<Object>> handleException(Exception ex) {
        ApiResponse<Object> res = new ApiResponse<>();
        res.setStatusCode(HttpStatus.BAD_REQUEST.value());
        res.setError("Đăng nhập không thành công");
        res.setMessage(ex.getMessage());
        return ResponseEntity.status(res.getStatusCode()).body(res);

    }

    @ExceptionHandler(value = {
            ConstraintViolationException.class
    })
    public ResponseEntity<ApiResponse<Object>> handleExceptionConstraint(ConstraintViolationException ex) {
        ApiResponse<Object> res = new ApiResponse<>();
        res.setStatusCode(HttpStatus.BAD_REQUEST.value());
        res.setMessage("Validation failed");

        // Map lỗi field
        Map<String, String> errors = new HashMap<>();
        ex.getConstraintViolations().forEach(cv -> {
            String field = cv.getPropertyPath().toString(); // tên field
            errors.put(field, cv.getMessage());

        });

        res.setError(errors);
        return ResponseEntity.status(res.getStatusCode()).body(res);

    }
}