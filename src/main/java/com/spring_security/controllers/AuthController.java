package com.spring_security.controllers;


import com.spring_security.auth.entities.RefreshToken;
import com.spring_security.auth.entities.User;
import com.spring_security.auth.service.AuthService;
import com.spring_security.auth.service.JwtService;
import com.spring_security.auth.service.RefreshTokenService;
import com.spring_security.auth.utils.AuthResponse;
import com.spring_security.auth.utils.LoginRequest;
import com.spring_security.auth.utils.RefreshTokenRequest;
import com.spring_security.auth.utils.RegisterRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;
    private final RefreshTokenService refreshTokenService;
    private final JwtService jwtService;

    public AuthController(AuthService authService, RefreshTokenService refreshTokenService, JwtService jwtService) {
        this.authService = authService;
        this.refreshTokenService = refreshTokenService;
        this.jwtService = jwtService;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest registerRequest){
        return ResponseEntity.ok(authService.register(registerRequest));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest loginRequest){
        return ResponseEntity.ok(authService.login(loginRequest));
    }

    // endpoint to authenticate user for login
//    @PostMapping("/authenticate")
//    public ResponseEntity<AuthResponse> authenticate(@Valid @RequestBody LoginRequest request) {
//        return ResponseEntity.ok(authService.authenticate(request));
//    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthResponse> refreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest){
        System.out.println(refreshTokenRequest != null?"something happened":" it expired");
        RefreshToken refreshToken =  refreshTokenService.verifyRefreshToken(refreshTokenRequest.getRefreshToken());
        User user = refreshToken.getUser();

        String accessToken = jwtService.generateToken(user);

        return  ResponseEntity.ok(AuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken.getRefreshToken())
                .build()
        );

    }
}
