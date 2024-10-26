package com.spring_security.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/demo")
public class MainController {

    @GetMapping
    public ResponseEntity<String> getGreetings(){
        return ResponseEntity.ok().body("Hello, user. This is get method");
    }

//    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/admin")
    public ResponseEntity<String> postGreetings(){
        return ResponseEntity.ok().body("Hello, admin.");
    }

//    @GetMapping
//    public ResponseEntity<String> greetings(){
//        return ResponseEntity.ok().body("Hello, user");
//    }
}
