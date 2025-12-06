package com.Kabeer.Uber.controller;

import jakarta.validation.Valid;
import com.Kabeer.Uber.dto.JwtResponse;
import com.Kabeer.Uber.dto.LoginRequest;
import com.Kabeer.Uber.dto.RegisterRequest;
import com.Kabeer.Uber.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) { this.authService = authService; }

    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody RegisterRequest request) {
        System.out.println("➡️ REGISTER ENDPOINT HIT");
        authService.register(request);
        return ResponseEntity.ok("User registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@Valid @RequestBody LoginRequest request) {
        System.out.println("🔥 LOGIN ENDPOINT HIT");    // ADD THIS
        String token = authService.login(request);
        return ResponseEntity.ok(new JwtResponse(token));
    }

}

