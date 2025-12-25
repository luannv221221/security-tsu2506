package com.ra.service;

import com.ra.model.dto.blog.BlogRequestDTO;
import com.ra.model.dto.blog.BlogResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BlogService {
    Page<BlogResponseDTO> findAll(Pageable pageable);
    BlogResponseDTO save(BlogRequestDTO blogDTO);
    Page<BlogResponseDTO> searchBlogByTitleOrContent( String keyword,Pageable pageable);

}
