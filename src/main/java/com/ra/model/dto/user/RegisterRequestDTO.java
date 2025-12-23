package com.ra.model.dto.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class RegisterRequestDTO {
    @NotBlank(message = "username not null")
    private String username;
    @NotBlank(message = "fullName not null")
    private String fullName;
    @NotBlank(message = "Password not null")
    @Size(min = 6,message = "password not invalid (min 6 character)")
    private String password;
}
