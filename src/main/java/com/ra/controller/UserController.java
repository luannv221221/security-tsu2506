package com.ra.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @GetMapping("/user")
    public ResponseEntity<?> index(){
        return ResponseEntity.ok().body("Xin chao API");
    }
    @GetMapping("/cart")
    public ResponseEntity<?> cart(){
        return ResponseEntity.ok().body("Gio hang");
    }
}
