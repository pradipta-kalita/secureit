package com.spring_security.auth.entities;


import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer tokenId;

    @Column(nullable = false,length = 500)
    private String refreshToken;

    @Column(nullable = false)
    private Instant expirationTime;

    @OneToOne
    private User user;
}
