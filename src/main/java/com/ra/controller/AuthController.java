package com.ra.controller;

import com.ra.model.dto.DataResponse;
import com.ra.model.dto.user.LoginRequestDTO;
import com.ra.model.dto.user.RegisterRequestDTO;
import com.ra.model.dto.user.UserResponseDTO;
import com.ra.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequestDTO registerRequestDTO) {
        DataResponse dataResponse =  authService.register(registerRequestDTO);
        return new ResponseEntity<>(dataResponse,HttpStatus.CREATED);
    }
}
