package com.ra.model.dto.user;

import com.ra.model.entity.Role;
import lombok.*;

import java.util.Set;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UserResponseDTO {
    private String username;
    private String fullName;
    private String typeToken;
    private String accessToken;
    private Set<Role> roles;
}
