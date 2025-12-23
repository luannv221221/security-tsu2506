package com.ra.model.dto;

import lombok.*;
import org.springframework.http.HttpStatus;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ResponseWrapper <T>{
    private String message;
    private int code;
    private T dataResponse;
}
