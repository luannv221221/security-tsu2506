package com.ra.model.dto.blog;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class BlogResponseDTO {
    private Long id;
    private String title;
    private String content;
    private String image;
}
