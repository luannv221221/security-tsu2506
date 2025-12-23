package com.ra.service.impl;

import com.ra.exception.HttpConflict;
import com.ra.model.dto.DataResponse;
import com.ra.model.dto.user.LoginRequestDTO;
import com.ra.model.dto.user.RegisterRequestDTO;
import com.ra.model.dto.user.UserResponseDTO;
import com.ra.model.entity.Role;
import com.ra.model.entity.User;
import com.ra.repository.RoleRepository;
import com.ra.repository.UserRepository;
import com.ra.security.UserPrinciple;
import com.ra.security.jwt.JwtProvider;
import com.ra.service.AuthService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.HashSet;
import java.util.Set;


@Service
public class AuthServiceImpl implements AuthService {
    @Autowired
    private AuthenticationProvider authenticationProvider;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtProvider jwtProvider;
    @Override
    public UserResponseDTO login(LoginRequestDTO loginRequestDTO) {
        Authentication authentication;
        authentication = authenticationProvider.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequestDTO.getUsername(), loginRequestDTO.getPassword())
        );
        UserPrinciple userPrinciple = (UserPrinciple) authentication.getPrincipal();
        return UserResponseDTO.builder()
                .username(userPrinciple.getUsername())
                .fullName(userPrinciple.getUser().getFullName())
                .typeToken("Bearer")
                .accessToken(jwtProvider.generateToken(userPrinciple))
                .roles(userPrinciple.getUser().getRoles())
                .build();
    }

    @Override
    public DataResponse register(RegisterRequestDTO registerRequestDTO) {
        // kiem tra xem username co chua
        if(userRepository.existsUserByUsername(registerRequestDTO.getUsername())){
            throw new HttpConflict("Username already exists");
        }
        // gan quyen mac dinh cho tai khoan dang ky la USER
        Set<Role> roles = new HashSet<>();
        Role role = roleRepository.findRoleByName("USER");
        roles.add(role);
        // convert DTO->ENTITY
        User user = User.builder()
                .fullName(registerRequestDTO.getFullName())
                .username(registerRequestDTO.getUsername())
                .password(new BCryptPasswordEncoder().encode(registerRequestDTO.getPassword()))
                .status(true)
                .roles(roles)
                .build();
        userRepository.save(user);
        return new DataResponse(201,"Register successfully");
    }
}
