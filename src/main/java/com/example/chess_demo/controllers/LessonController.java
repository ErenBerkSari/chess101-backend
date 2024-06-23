package com.example.chess_demo.controllers;

import com.example.chess_demo.entities.Lesson;
import com.example.chess_demo.requests.LessonUpdateRequest;
import com.example.chess_demo.services.LessonServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/lessons")
public class LessonController {

    @Autowired
    private LessonServices lessonService;

    @GetMapping
    public List<Lesson> getAllLessons() {
        return lessonService.getAllLessons();
    }

    @PostMapping
    public Lesson createOneLesson(@RequestBody Lesson newLesson) {
        return lessonService.createOneLesson(newLesson);
    }

    @GetMapping("/{lessonId}")
    public ResponseEntity<Lesson> getOneLesson(@PathVariable Long lessonId) {
        Optional<Lesson> lesson = lessonService.getOneLessonById(lessonId);
        if (lesson.isPresent()) {
            return ResponseEntity.ok(lesson.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping("/{lessonId}/content")
    public ResponseEntity<String> getLessonContent(@PathVariable Long lessonId) {
        Optional<Lesson> lesson = lessonService.getOneLessonById(lessonId);
        if (lesson.isPresent()) {
            return ResponseEntity.ok(lesson.get().getContent());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping("/{lessonId}/test")
    public ResponseEntity<String> getTestQuestions(@PathVariable Long lessonId) {
        Optional<Lesson> lesson = lessonService.getOneLessonById(lessonId);
        if (lesson.isPresent()) {
            return ResponseEntity.ok(lesson.get().getTestQuestions());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PutMapping("/{lessonId}")
    public Lesson updateOneLesson(@PathVariable Long lessonId, @RequestBody LessonUpdateRequest updateLesson) {
        return lessonService.updateOneLessonById(lessonId, updateLesson);
    }

    @DeleteMapping("/{lessonId}")
    public void deleteOneLesson(@PathVariable Long lessonId) {
        lessonService.deleteOneLessonById(lessonId);
    }



}
