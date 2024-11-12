package com.hzipyb.userservice.domain.user.controller;

import com.hzipyb.userservice.domain.user.dto.AuthRequestDTO;
import com.hzipyb.userservice.domain.user.dto.TokenRequestDTO;
import com.hzipyb.userservice.domain.user.entity.User;
import com.hzipyb.userservice.domain.user.service.JWTService;
import com.hzipyb.userservice.domain.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/user/auth")
public class AuthController {
    private final UserService userService;
    private final JWTService jwtService;

    @PostMapping("/token")
    public ResponseEntity<Map<String, String>> generateToken(
            HttpServletRequest request,
            @RequestBody AuthRequestDTO authRequestDTO)
    {
        User existingUser = userService.getUserByEmail(authRequestDTO.getEmail()).orElseThrow();
        String token = jwtService.generateToken(existingUser, authRequestDTO.getPassword());
        String ipAddress = request.getRemoteAddr();

        userService.logUserLogin(existingUser, ipAddress);

        return ResponseEntity.ok(Collections.singletonMap("token", token));
    }

    @PostMapping("/verify-token")
    public ResponseEntity<Map<String, Boolean>> verifyToken(
            @RequestBody TokenRequestDTO tokenRequestDTO
            )
    {
        boolean isValid = jwtService.validateToken(tokenRequestDTO.getToken());

        return ResponseEntity.ok(Collections.singletonMap("isValid", isValid));
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<Map<String, String>> refreshToken(
            @RequestBody TokenRequestDTO tokenRequestDTO
    )
    {
        String newToken = jwtService.refreshToken(tokenRequestDTO.getToken());

        return ResponseEntity.ok(Collections.singletonMap("token", newToken));
    }
}
