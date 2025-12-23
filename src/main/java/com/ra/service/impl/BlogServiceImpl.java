package com.ra.service.impl;

import com.ra.model.dto.blog.BlogRequestDTO;
import com.ra.model.dto.blog.BlogResponseDTO;
import com.ra.model.entity.Blog;
import com.ra.repository.BlogRepository;
import com.ra.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class BlogServiceImpl implements BlogService {
    @Autowired
    private BlogRepository blogRepository;
    @Override
    public Page<BlogResponseDTO> findAll(Pageable pageable) {
        Page<Blog> blogs = blogRepository.findAll(pageable);
        return blogs.map(blog ->
                BlogResponseDTO.builder()
                        .id(blog.getId())
                        .title(blog.getTitle())
                        .content(blog.getContent())
                        .image(blog.getImage())
                        .build());
    }

    @Override
    public BlogResponseDTO save(BlogRequestDTO blogDTO) {
        return null;
    }
}
