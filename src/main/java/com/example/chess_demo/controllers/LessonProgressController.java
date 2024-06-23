package com.example.chess_demo.controllers;

import com.example.chess_demo.entities.Lesson;
import com.example.chess_demo.entities.LessonProgress;
import com.example.chess_demo.requests.LessonProgressCreateRequest;
import com.example.chess_demo.requests.LessonProgressUpdateRequest;
import com.example.chess_demo.responses.LessonProgressResponse;
import com.example.chess_demo.services.LessonProgressServices;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/lessonProgress")
public class LessonProgressController {
    private LessonProgressServices lessonProgressService;

    public LessonProgressController(LessonProgressServices lessonProgressService) {
        this.lessonProgressService = lessonProgressService;
    }

    // Tüm ilerlemeleri getirme
    @GetMapping
    public List<LessonProgressResponse> getAllProgress()
    {
        return lessonProgressService.getAllProgress();
    }

    // Belirli bir kullanıcının ilerlemelerini getirme
    @GetMapping("/user/{userId}")
    public List<LessonProgressResponse> getUserProgress(@PathVariable Long userId)
    {
        return lessonProgressService.getUserProgress(userId);
    }

    // Belirli bir dersin ilerlemelerini getirme
    @GetMapping("/lesson/{lessonId}")
    public List<LessonProgressResponse> getLessonProgress(@PathVariable Long lessonId)
    {
        return lessonProgressService.getLessonProgress(lessonId);
    }

    // Yeni ilerleme oluşturma gerek var mı bilmiyorum çünkü progressController da bir metot var,evet gerek var
    @PostMapping
    public LessonProgress createLessonProgress(@RequestBody LessonProgressCreateRequest newLessonProgressCreateRequest)
    {
        return lessonProgressService.createLessonProgress(newLessonProgressCreateRequest);
    }

    // İlerleme güncelleme
    @PutMapping("/{progressId}")
    public LessonProgress updateProgress(@PathVariable Long progressId, @RequestBody LessonProgressUpdateRequest newLessonProgressUpdateRequest)
    {
        return lessonProgressService.updateProgress(progressId, newLessonProgressUpdateRequest);
    }

    // İlerleme silme
    @DeleteMapping("{progressId}")
    public void deleteLessonProgress(Long progressId)
    {
        lessonProgressService.deleteLessonProgress(progressId);
    }

    // Belirli bir kullanıcının belirli bir dersinin ilerlemesini getirme
    @GetMapping("/user/{userId}/lesson/{lessonId}")
    public List<LessonProgressResponse> getUserLessonProgress(@PathVariable Long userId, @PathVariable Long lessonId)
    {
        return lessonProgressService.getUserLessonProgress(userId,lessonId);

    }
}
