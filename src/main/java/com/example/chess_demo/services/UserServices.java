package com.example.chess_demo.services;

import com.example.chess_demo.entities.User;
import com.example.chess_demo.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServices implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public boolean emailExists(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        return user.isPresent();
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User saveOneUser(User user) throws Exception {
        if (userRepository.findByUsername(user.getUsername()) != null) {
            throw new Exception("Kullanıcı adı zaten mevcut");
        }

        if (user.getPassword().length() > 255) {
            throw new Exception("Şifre çok uzun");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public Optional<User> getOneUser(Long userId) {
        return userRepository.findById(userId);
    }

    public User updateOneUser(Long userId, User newUser) {
        return userRepository.findById(userId)
                .map(user -> {
                    user.setUsername(newUser.getUsername());
                    user.setPassword(passwordEncoder.encode(newUser.getPassword()));
                    return userRepository.save(user);
                }).orElse(null);
    }

    public User updateUserRole(Long userId, String newRole) {
        return userRepository.findById(userId)
                .map(user -> {
                    user.setRole(newRole);
                    return userRepository.save(user);
                }).orElse(null);
    }

    public void deleteById(Long userId) {
        userRepository.deleteById(userId);
    }

    public User getOneUserByUserName(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return user;
    }

    public User getOneUserByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }

    public void updateUserAvatar(Long userId, String filename) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setAvatarUrl("/user-avatars/" + filename);
        userRepository.save(user);
    }
}
