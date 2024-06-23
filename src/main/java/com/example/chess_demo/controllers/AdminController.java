package com.example.chess_demo.controllers;

import com.example.chess_demo.entities.Lesson;
import com.example.chess_demo.requests.LessonCreateRequest;
import com.example.chess_demo.requests.LessonUpdateRequest;
import com.example.chess_demo.services.LessonServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private LessonServices lessonServices;

    @PostMapping("/addLesson")
    public ResponseEntity<Lesson> addLesson(@RequestBody LessonCreateRequest newLessonRequest) {
        Lesson newLesson = new Lesson();
        newLesson.setLessonName(newLessonRequest.getLessonName());
        newLesson.setLessonDesc(newLessonRequest.getLessonDesc());
        newLesson.setLessonLevel(newLessonRequest.getLessonLevel());
        newLesson.setLessonImageUrl(newLessonRequest.getLessonImageUrl());
        newLesson.setContent(newLessonRequest.getContent());
        newLesson.setTestQuestions(newLessonRequest.getTestQuestions());
        Lesson lesson = lessonServices.createOneLesson(newLesson);
        return ResponseEntity.ok(lesson);
    }

    @PutMapping("/updateLesson/{lessonId}")
    public ResponseEntity<Lesson> updateLesson(@PathVariable Long lessonId, @RequestBody LessonUpdateRequest updateLessonRequest) {
        Lesson updatedLesson = lessonServices.updateOneLessonById(lessonId, updateLessonRequest);
        return ResponseEntity.ok(updatedLesson);
    }


    @DeleteMapping("/deleteLesson/{lessonId}")
    public ResponseEntity<Void> deleteLesson(@PathVariable Long lessonId) {
        lessonServices.deleteOneLessonById(lessonId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/lessons")
    public ResponseEntity<List<Lesson>> getAllLessons() {
        List<Lesson> lessons = lessonServices.getAllLessons();
        return ResponseEntity.ok(lessons);
    }
}
