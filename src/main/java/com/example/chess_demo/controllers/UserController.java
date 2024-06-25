package com.example.chess_demo.controllers;

import com.example.chess_demo.dto.PasswordChangeDto;
import com.example.chess_demo.dto.UserUpdateDto;
import com.example.chess_demo.entities.User;
import com.example.chess_demo.services.UserServices;
import com.example.chess_demo.util.FileUploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserServices userServices;

    @GetMapping
    public List<User> getAllUsers() {
        return userServices.getAllUsers();
    }

    @PostMapping
    public User createUser(@RequestBody User newUser) throws Exception {
        return userServices.saveOneUser(newUser);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<User> getOneUser(@PathVariable Long userId) {
        Optional<User> userOptional = userServices.getOneUser(userId);
        if (userOptional.isPresent()) {
            return ResponseEntity.ok(userOptional.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PutMapping("/{userId}/role")
    public ResponseEntity<User> updateUserRole(@PathVariable Long userId, @RequestBody User updatedUser) {
        User updatedUserRole = userServices.updateUserRole(userId, updatedUser.getRole());
        if (updatedUserRole != null) {
            return ResponseEntity.ok(updatedUserRole);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody UserUpdateDto userUpdateDto) {
        try {
            User updatedUser = userServices.updateUser(id, userUpdateDto);
            return ResponseEntity.ok(updatedUser);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\": \"" + e.getMessage() + "\"}");
        }
    }

    @PutMapping("/{id}/change-password")
    public ResponseEntity<?> changePassword(@PathVariable Long id, @RequestBody PasswordChangeDto passwordChangeDto) {
        try {
            User updatedUser = userServices.changePassword(id, passwordChangeDto);
            return ResponseEntity.ok(updatedUser);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\": \"" + e.getMessage() + "\"}");
        }
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteOneUser(@PathVariable Long userId) {
        userServices.deleteById(userId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<User> getOneUserByEmail(@PathVariable String email) {
        User user = userServices.getOneUserByEmail(email);
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PostMapping("/upload-avatar")
    public ResponseEntity<String> uploadAvatar(@RequestParam("avatar") MultipartFile file, @RequestParam("userId") Long userId) {
        try {
            String filename = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            Path filepath = Paths.get("user-avatars", filename);
            Files.copy(file.getInputStream(), filepath, StandardCopyOption.REPLACE_EXISTING);
            userServices.updateUserAvatar(userId, filename);
            return ResponseEntity.ok("Avatar uploaded successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error uploading avatar");
        }
    }


    @PostMapping("/add-badge")
    public ResponseEntity<String> addBadge(@RequestParam("badge") String badge, @RequestParam("userId") Long userId) {
        try {
            User user = userServices.getOneUser(userId).orElseThrow(() -> new UsernameNotFoundException("User not found"));
            user.getBadges().add(badge);
            userServices.saveOneUser(user);
            return ResponseEntity.ok("Badge added successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Could not add badge");
        }
    }




}
