package com.ra.controller;

import com.ra.model.dto.ResponseWrapper;
import com.ra.model.dto.blog.BlogRequestDTO;
import com.ra.model.dto.blog.BlogResponseDTO;
import com.ra.service.BlogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.util.List;

@RestController
@RequestMapping("/api/v1/blogs")
public class BlogController {
    @Autowired
    private BlogService blogService;
    @PreAuthorize("hasAnyAuthority('ADMIN','SALE','BLOGGER')")
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
    @Operation(summary = "Danh sacsh bai viet"
            ,description = "Chi co quyen gi day moi xem duowc danh sach",
            responses = {
                @ApiResponse(
                        responseCode = "200",
                        description = "Lay ve danh sach",
                        content = @Content(schema = @Schema(implementation = ResponseWrapper.class))
                ),
                @ApiResponse(
                            responseCode = "301",
                            description = "Mo ta"
                    )
            }
    )
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
    @GetMapping("/search")
    public ResponseEntity<?> searchBlogByTitleOrContent(
            @RequestParam(name = "keyword",required = true) String keyword,
            @RequestParam(name = "page",defaultValue = "0") int page,
            @RequestParam(name = "size",defaultValue = "10") int size,
            @RequestParam(name = "sortBy",defaultValue = "id") String sortBy,
            @RequestParam(name = "orderBy",defaultValue = "asc") String orderBy){
        Sort sort = orderBy.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<BlogResponseDTO> blogResponseDTO = blogService.searchBlogByTitleOrContent(keyword,pageable);
        return ResponseEntity.status(HttpStatus.OK).body(ResponseWrapper.
                builder()
                .code(HttpStatus.OK.value())
                .message("Get blog successfully!")
                .dataResponse(blogResponseDTO)
                .build());
    }
    @PreAuthorize("hasAuthority('BLOGGER')")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(
            summary = "The moi bai viet",
            description = "Mo ta"
    )
    public ResponseEntity<?> addBlog(@ModelAttribute BlogRequestDTO blogRequestDTO){
        BlogResponseDTO blogResponseDTO = blogService.save(blogRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                ResponseWrapper.
                        builder()
                        .code(HttpStatus.OK.value())
                        .message("add blog successfully!")
                        .dataResponse(blogResponseDTO)
                        .build()
        );
    }

}
