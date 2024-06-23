package com.example.chess_demo.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users") // Tablo adını değiştirdik
@Data
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id") // Sütun adını belirttik
    private Long userId;

    @Column(name = "username") // Sütun adını belirttik
    private String username;

    @Column(name = "password") // Sütun adını belirttik
    private String password;

    @Column(name = "email",unique = true) // Sütun adını belirttik
    private String email;

    @Column(name = "role") // Sütun adını belirttik
    private String role;

    @Column(name = "avatar_url")
    private String avatarUrl;

    @Column(name = "badges")
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<String> badges = new HashSet<>();



    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role));
    }


    @Override
    public String getUsername() {
        return email;
    }//değiştirdim

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
