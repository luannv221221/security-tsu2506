package com.ra.service;

import com.ra.model.dto.LoginRequestDTO;
import com.ra.model.dto.UserResponseDTO;

public interface AuthService {
    UserResponseDTO login(LoginRequestDTO loginRequestDTO);
}
