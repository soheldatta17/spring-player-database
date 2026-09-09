package com.sohel.demoproj.controller;

import com.sohel.demoproj.dto.AuthRequest;
import com.sohel.demoproj.dto.AuthResponse;
import com.sohel.demoproj.security.JwtUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IdentityController {

    private final JwtUtil jwtUtil;

    public IdentityController(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/identity")
    public ResponseEntity<?> authenticate(@RequestBody AuthRequest authRequest) {
        if ("sohel".equals(authRequest.getUsername()) && "sohel123".equals(authRequest.getPassword())) {
            String token = jwtUtil.generateToken(authRequest.getUsername());
            return ResponseEntity.ok(new AuthResponse(token));
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
    }
}
