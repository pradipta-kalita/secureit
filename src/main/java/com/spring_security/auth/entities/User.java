package com.spring_security.auth.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer UUID;

    @NotBlank(message = "User name cannot be blank.")
    private String name;

    @NotBlank(message = "Username cannot be blank")
    @Column(unique = true)
    private String username;

    @Email(message = "Not a valid email address")
    @NotBlank(message = "Email is required")
    @Column(unique = true)
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 8,message = "Password should be at least 8 characters long.")
    private String password;

    // One-to-One mapping
    @OneToOne(mappedBy = "user")
    private RefreshToken refreshToken;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    private boolean isEnabled = true;

    private boolean isAccountNonExpired = true;

    private boolean isAccountNonLocked = true;

    private boolean isCredentialsNonExpired = true;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getUsername(){
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
//        return this.isAccountNonExpired;
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
//        return this.isAccountNonLocked;
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
//        return this.isCredentialsNonExpired;
        return true;
    }

    @Override
    public boolean isEnabled() {
//        return this.isEnabled;
        return true;
    }


}
