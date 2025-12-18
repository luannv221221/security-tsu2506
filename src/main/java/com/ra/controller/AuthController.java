package com.ra.controller;

import com.ra.model.dto.LoginRequestDTO;
import com.ra.model.dto.UserResponseDTO;
import com.ra.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    @Autowired
    private AuthService authService;
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO loginRequestDTO) {
        UserResponseDTO  user = authService.login(loginRequestDTO);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
}
