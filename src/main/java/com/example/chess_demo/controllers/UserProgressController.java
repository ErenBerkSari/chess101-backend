package com.example.chess_demo.controllers;

import com.example.chess_demo.entities.UserProgress;
import com.example.chess_demo.repos.UserProgressRepository;
import com.example.chess_demo.requests.UserProgressCreateRequest;
import com.example.chess_demo.requests.UserProgressUpdateRequest;
import com.example.chess_demo.responses.UserProgressResponse;
import com.example.chess_demo.services.UserProgressServices;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/userProgress")
public class UserProgressController {
    private UserProgressServices userProgressService;

    private UserProgressRepository userProgressRepository;

    public UserProgressController(UserProgressServices userProgressService) {
        this.userProgressService = userProgressService;
    }

    @GetMapping
    public List<UserProgressResponse> getAllUserProgress() {
        return userProgressService.getAllUserProgress();
    }



    @PostMapping
    public UserProgress createUserProgress(@RequestBody UserProgressCreateRequest newUserProgressCreateRequest) {
        return userProgressService.createUserProgress(newUserProgressCreateRequest);
    }

    @PutMapping("/update/{userId}")
    public ResponseEntity<UserProgress> updateUserProgress(@PathVariable Long userId) {
        UserProgress updatedProgress = userProgressService.updateUserProgress(userId);
        if (updatedProgress != null) {
            return ResponseEntity.ok(updatedProgress);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @DeleteMapping("/{progressId}")
    public void deleteUserProgress(@PathVariable Long progressId) {
        userProgressService.deleteUserProgress(progressId);
    }

    @PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
    @GetMapping("/progress/{userId}")
    public ResponseEntity<Map<String, Integer>> getUserProgressStats(@PathVariable Long userId) {
        int completedLessons = userProgressService.getCompletedLessonsCount(userId);
        int totalLessons = userProgressService.getTotalLessonsCount();
        Map<String, Integer> progress = new HashMap<>();
        progress.put("completed", completedLessons);
        progress.put("total", totalLessons);
        return ResponseEntity.ok(progress);
    }


    @GetMapping("/user/{userId}/progress")
    @PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
    public ResponseEntity<UserProgress> getUserProgress(@PathVariable Long userId) {
        Optional<UserProgress> userProgress = userProgressRepository.findByUser_UserId(userId);
        return userProgress.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }


}
