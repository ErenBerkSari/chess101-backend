package com.example.chess_demo.controllers;

import com.example.chess_demo.entities.Lesson;
import com.example.chess_demo.entities.UserLesson;
import com.example.chess_demo.repos.LessonRepository;
import com.example.chess_demo.requests.UserLessonCreateRequest;
import com.example.chess_demo.responses.UserLessonResponse;
import com.example.chess_demo.responses.LessonResponse;
import com.example.chess_demo.services.UserLessonServices;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/userLesson")
public class UserLessonController {
    private final UserLessonServices userLessonService;
    private final LessonRepository lessonRepository;

    public UserLessonController(UserLessonServices userLessonService, LessonRepository lessonRepository) {
        this.userLessonService = userLessonService;
        this.lessonRepository = lessonRepository;
    }

    @GetMapping("/user/{userId}/lessons")
    @PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
    public ResponseEntity<List<UserLessonResponse>> getUserLessons(@PathVariable Long userId) {
        List<UserLesson> userLessons = userLessonService.findByUserId(userId);
        List<UserLessonResponse> userLessonResponses = userLessons.stream()
                .map(this::convertToUserLessonResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(userLessonResponses);
    }

    private UserLessonResponse convertToUserLessonResponse(UserLesson userLesson) {
        UserLessonResponse response = new UserLessonResponse();
        response.setId(userLesson.getId());
        response.setCompleted(userLesson.getIsCompleted());

        LessonResponse lessonResponse = new LessonResponse();
        Lesson lesson = userLesson.getLesson();
        lessonResponse.setLessonId(lesson.getLessonId());
        lessonResponse.setLessonName(lesson.getLessonName());
        lessonResponse.setLessonDesc(lesson.getLessonDesc());
        lessonResponse.setLessonLevel(lesson.getLessonLevel());
        lessonResponse.setLessonImageUrl(lesson.getLessonImageUrl());
        lessonResponse.setContent(lesson.getContent());

        response.setLesson(lessonResponse);
        return response;
    }

    @PostMapping("/register")
    public ResponseEntity<?> enrollUserToLesson(@RequestBody UserLessonCreateRequest request) {
        Lesson lesson = lessonRepository.findById(request.getLessonId()).orElse(null);
        if (lesson == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Collections.singletonMap("message", "Lesson not found."));
        }

        if (userLessonService.hasIncompleteLessons(request.getUserId())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(Collections.singletonMap("message", "You must complete your current lesson before enrolling in a new one."));
        }

        String lessonLevel = lesson.getLessonLevel();
        if (lessonLevel.equals("Intermediate") && !userLessonService.hasCompletedAllLessonsAtLevel(request.getUserId(), "Beginner")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(Collections.singletonMap("message", "You must complete all Beginner lessons before enrolling in Intermediate lessons."));
        }

        if (lessonLevel.equals("Advanced") && !userLessonService.hasCompletedAllLessonsAtLevel(request.getUserId(), "Intermediate")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(Collections.singletonMap("message", "You must complete all Intermediate lessons before enrolling in Advanced lessons."));
        }

        UserLesson userLesson = userLessonService.enrollToLesson(request);
        if (userLesson != null) {
            return ResponseEntity.ok(userLesson);
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Collections.singletonMap("message", "User is already enrolled in this lesson."));
        }
    }

    @DeleteMapping("/delete/{userId}/{lessonId}")
    public void deleteUserLesson(@PathVariable Long userId, @PathVariable Long lessonId) {
        userLessonService.deleteUserLesson(userId, lessonId);
    }

    @GetMapping("/isEnrolled")
    public ResponseEntity<Boolean> isUserEnrolled(@RequestParam Long userId, @RequestParam Long lessonId) {
        boolean isEnrolled = userLessonService.isUserEnrolled(userId, lessonId);
        return ResponseEntity.ok(isEnrolled);
    }

    @PostMapping("/complete")
    public ResponseEntity<UserLesson> completeLesson(@RequestBody UserLessonCreateRequest request) {
        UserLesson userLesson = userLessonService.markLessonAsCompleted(request.getUserId(), request.getLessonId());
        if (userLesson != null) {
            return ResponseEntity.ok(userLesson);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @PostMapping("/complete-test")
    @PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
    public ResponseEntity<UserLesson> completeTest(@RequestBody UserLessonCreateRequest request, @RequestParam int score) {
        if (score >= 70) {
            UserLesson userLesson = userLessonService.markLessonAsCompleted(request.getUserId(), request.getLessonId());
            if (userLesson != null) {
                return ResponseEntity.ok(userLesson);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            }
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }
    }
}
