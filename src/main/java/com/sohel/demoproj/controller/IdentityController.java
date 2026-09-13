package com.sohel.demoproj.controller;

import com.sohel.demoproj.dto.AuthRequest;
import com.sohel.demoproj.dto.AuthResponse;
import com.sohel.demoproj.security.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IdentityController {

    private static final Logger log = LoggerFactory.getLogger(IdentityController.class);
    private final JwtUtil jwtUtil;

    public IdentityController(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/identity")
    public ResponseEntity<?> authenticate(@RequestBody AuthRequest authRequest) {
        log.info("Received authentication request for username: '{}'", authRequest != null ? authRequest.getUsername() : "null");
        if (authRequest != null && "sohel".equals(authRequest.getUsername()) && "sohel123".equals(authRequest.getPassword())) {
            String token = jwtUtil.generateToken(authRequest.getUsername());
            log.info("Authentication successful for username: '{}'. Issued JWT token.", authRequest.getUsername());
            return ResponseEntity.ok(new AuthResponse(token));
        }

        log.warn("Authentication failed for username: '{}'. Invalid credentials.", authRequest != null ? authRequest.getUsername() : "null");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
    }
}

