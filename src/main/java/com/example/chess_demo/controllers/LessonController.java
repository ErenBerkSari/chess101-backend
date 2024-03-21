package com.example.chess_demo.controllers;

import com.example.chess_demo.entities.Lesson;
import com.example.chess_demo.requests.LessonCreateRequest;
import com.example.chess_demo.requests.LessonUpdateRequest;
import com.example.chess_demo.services.LessonServices;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/lessons")
public class LessonController {
    private LessonServices lessonService;

    public LessonController(LessonServices lessonService) {
        this.lessonService = lessonService;
    }

    @GetMapping
    public List<Lesson> getAllLessons()
    {
        return lessonService.getAllLessons();
    }
/*
    @GetMapping("/{userId}")
    public List<Lesson> getAllLessonByUserId(@PathVariable Long userId)
    {
        return lessonService.getAllLessonByUserId(userId);
    }
*/
    @PostMapping
    public Lesson createOneLesson(@RequestBody LessonCreateRequest newLessonRequest)
    {
        return lessonService.createOneLesson(newLessonRequest);
    }

    @GetMapping("/{lessonId}")
    public Lesson getOneLesson(@PathVariable Long lessonId)
    {
        return lessonService.getOneLessonById(lessonId);
    }

    @PutMapping("/{lessonId}")
    public Lesson updateOneLesson(@PathVariable Long lessonId, @RequestBody LessonUpdateRequest updateLesson)
    {
        return lessonService.updateOneLessonById(lessonId, updateLesson);
    }

    @DeleteMapping("/{lessonId}")
    public void deleteOneLesson(@PathVariable Long lessonId)
    {
        lessonService.deleteOneLessonById(lessonId);
    }


}
