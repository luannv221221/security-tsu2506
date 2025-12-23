package com.ra.service;

import com.ra.model.dto.DataResponse;
import com.ra.model.dto.user.LoginRequestDTO;
import com.ra.model.dto.user.RegisterRequestDTO;
import com.ra.model.dto.user.UserResponseDTO;

public interface AuthService {
    UserResponseDTO login(LoginRequestDTO loginRequestDTO);
    DataResponse register(RegisterRequestDTO registerRequestDTO);
}
