package com.ra.controller;

import com.ra.model.dto.ResponseWrapper;
import com.ra.model.dto.blog.BlogResponseDTO;
import com.ra.service.BlogService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import java.util.List;

@RestController
@RequestMapping("/api/v1/blogs")
public class BlogController {
    @Autowired
    private BlogService blogService;
    @GetMapping
//    public ResponseEntity<?> findAll(@PageableDefault(page = 0, size = 10,sort = "id",direction = Sort.Direction.DESC) Pageable pageable) {
//        Page<BlogResponseDTO> blogResponseDTO = blogService.findAll(pageable);
//
//        return ResponseEntity.status(HttpStatus.OK).body(ResponseWrapper.
//                builder()
//                .code(HttpStatus.OK.value())
//                .message("Get blog successfully!")
//                .dataResponse(blogResponseDTO)
//                .build());
//    }
    public ResponseEntity<?> findAll(
            @RequestParam(name = "page",defaultValue = "0") int page,
            @RequestParam(name = "size",defaultValue = "10") int size,
            @RequestParam(name = "sortBy",defaultValue = "id") String sortBy,
            @RequestParam(name = "orderBy",defaultValue = "asc") String orderBy

    ) {
        Sort sort = orderBy.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<BlogResponseDTO> blogResponseDTO = blogService.findAll(pageable);

        return ResponseEntity.status(HttpStatus.OK).body(ResponseWrapper.
                builder()
                .code(HttpStatus.OK.value())
                .message("Get blog successfully!")
                .dataResponse(blogResponseDTO)
                .build());
    }

}
