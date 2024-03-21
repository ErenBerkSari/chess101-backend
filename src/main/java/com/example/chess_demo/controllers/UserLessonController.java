package com.example.chess_demo.controllers;

import com.example.chess_demo.entities.User;
import com.example.chess_demo.entities.UserLesson;
import com.example.chess_demo.requests.UserLessonCreateRequest;
import com.example.chess_demo.services.UserLessonServices;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/userLesson")
public class UserLessonController {
    private UserLessonServices userLessonService;

    public UserLessonController(UserLessonServices userLessonService) {
        this.userLessonService = userLessonService;
    }

    @GetMapping
    public List<UserLesson> getAllUserLesson(){
        return userLessonService.getAllUserLesson();
    }

    @GetMapping("/user/{userId}")
    public List<UserLesson> getUserLessonByUserId(@PathVariable Long userId)
    {
        return userLessonService.findByUserId(userId);
    }

    @GetMapping("/lesson/{lessonId}")
    public List<UserLesson> getUserLessonByLessonId(@PathVariable Long lessonId)
    {
        return userLessonService.findByLessonId(lessonId);
    }

    @PostMapping("/enroll")
    public UserLesson enrollUserToLesson(@RequestBody UserLessonCreateRequest newUserLessonCreateRequest)
    {
        return userLessonService.enrollUserToLesson(newUserLessonCreateRequest);
    }

    @DeleteMapping("/delete/{userId}/{lessonId}")
    public void deleteUserLesson(@PathVariable Long userId, @PathVariable Long lessonId)
    {
        userLessonService.deleteUserLesson(userId,lessonId);
    }
    //@PutMapping("/") //Kullanıcının belirli bir ders kaydını güncellemek için, daha sonra bak şu anlık gereksiz gibi

}
