package com.spring_security.auth.service;

import com.spring_security.auth.entities.RefreshToken;
import com.spring_security.auth.entities.User;
import com.spring_security.auth.repositories.RefreshTokenRepository;
import com.spring_security.auth.repositories.UserRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
public class RefreshTokenService {
    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;

    public RefreshTokenService(UserRepository userRepository,
                               RefreshTokenRepository refreshTokenRepository) {
        this.userRepository = userRepository;
        this.refreshTokenRepository = refreshTokenRepository;
    }

    public RefreshToken createRefreshToken(String email){
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if(optionalUser.isPresent()){
            User user = optionalUser.get();
            RefreshToken refreshToken = user.getRefreshToken();
            if(refreshToken == null){
                long refreshTokenValidityTime = 30*1000;
                refreshToken =  RefreshToken.builder()
                        .refreshToken(UUID.randomUUID().toString())
                        .expirationTime(Instant.now().plusMillis(refreshTokenValidityTime))
                        .user(user)
                        .build();

                refreshTokenRepository.save(refreshToken);
            }
            return refreshToken;
        }else{
            throw new UsernameNotFoundException("User not found with email : "+ email);
        }
    }

    public RefreshToken verifyRefreshToken(String refreshToken){
        Optional<RefreshToken> optionalRefreshToken = refreshTokenRepository
                .findByRefreshToken(refreshToken);
        if(optionalRefreshToken.isPresent()){
            RefreshToken refToken =  optionalRefreshToken.get();
            if(refToken.getExpirationTime().compareTo(Instant.now())<0){
                refreshTokenRepository.delete(refToken);
                throw new RuntimeException("Refresh Token expired");
            }
            return refToken;
        }else{
            throw new RuntimeException("Refresh Token not found!");
        }
    }
}
