package com.ra.model.dto.blog;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class BlogRequestDTO {
    private String title;
    private String content;
    private MultipartFile image;
}
