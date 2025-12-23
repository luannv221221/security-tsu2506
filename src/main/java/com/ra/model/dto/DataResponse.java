package com.ra.model.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class DataResponse {
    private int httpStatusCode;
    private String message;

}
