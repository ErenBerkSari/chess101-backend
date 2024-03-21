package com.example.chess_demo.controllers;

import com.example.chess_demo.entities.UserProgress;
import com.example.chess_demo.requests.ProgressCreateRequest;
import com.example.chess_demo.requests.UserProgressCreateRequest;
import com.example.chess_demo.requests.UserProgressUpdateRequest;
import com.example.chess_demo.services.UserProgressServices;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/userProgress")
public class UserProgressController {
    private UserProgressServices userProgressService;

    public UserProgressController(UserProgressServices userProgressService) {
        this.userProgressService = userProgressService;
    }

    // Tüm kullanıcı ilerlemelerini getirme
    @GetMapping
    public List<UserProgress> getAllUserProgress() {
        List<UserProgress> userProgressList = userProgressService.getAllUserProgress();
        return userProgressList;
    }

    // Belirli bir kullanıcının ilerlemelerini getirme
    @GetMapping("/user/{userId}")
    public List<UserProgress> getUserProgress(@PathVariable Long userId) {
        List<UserProgress> userProgressList = userProgressService.getUserProgress(userId);
        return userProgressList;
    }

    //Yeni bir kullanıcı ilerlemesi oluşturma
    @PostMapping
    public UserProgress createUserProgress(@RequestBody UserProgressCreateRequest newUserProgressCreateRequest)
    {
        return userProgressService.createUserProgress(newUserProgressCreateRequest);
    }

    //Kullanıcı ilerlemesini güncelleme, daha sonra kontrol et
    @PutMapping("/{progressId}")
    public UserProgress updateUserProgress(@PathVariable Long progressId, @RequestBody UserProgressUpdateRequest newUserProgressUpdateRequest)
    {
        return userProgressService.updateUserProgress(progressId, newUserProgressUpdateRequest);
    }

    //Kullanıcı ilerlemesini silme
    @DeleteMapping("/{progressId}")
    public void deleteUserProgress(@PathVariable Long progressId)
    {
        userProgressService.deleteUserProgress(progressId);
    }
}
