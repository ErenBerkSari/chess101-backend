package com.example.chess_demo.services;

import com.example.chess_demo.entities.Lesson;
import com.example.chess_demo.entities.User;
import com.example.chess_demo.entities.UserLesson;
import com.example.chess_demo.repos.LessonRepository;
import com.example.chess_demo.repos.UserLessonRepository;
import com.example.chess_demo.requests.UserLessonCreateRequest;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserLessonServices {
    @Autowired
    private UserLessonRepository userLessonRepository;

    @Autowired
    private LessonRepository lessonRepository;

    @Autowired
    private UserServices userService;

    @Autowired
    private LessonServices lessonService;

    public List<UserLesson> getAllUserLesson() {
        return userLessonRepository.findAll();
    }

    public List<UserLesson> findByUserId(Long userId) {
        List<UserLesson> userLessons = userLessonRepository.findByUser_UserId(userId);
        userLessons.forEach(userLesson -> {
            Hibernate.initialize(userLesson.getUser());
            Hibernate.initialize(userLesson.getLesson());
        });
        return userLessons;
    }

    public List<UserLesson> findByLessonId(Long lessonId) {
        List<UserLesson> userLessons = userLessonRepository.findByLesson_LessonId(lessonId);
        userLessons.forEach(userLesson -> {
            Hibernate.initialize(userLesson.getUser());
            Hibernate.initialize(userLesson.getLesson());
        });
        return userLessons;
    }

    public void deleteUserLesson(Long userId, Long lessonId) {
        userLessonRepository.deleteByUser_UserIdAndLesson_LessonId(userId, lessonId);
    }

    public UserLesson enrollToLesson(UserLessonCreateRequest request) {
        Optional<User> userOptional = userService.getOneUser(request.getUserId());
        Optional<Lesson> lessonOptional = lessonService.getOneLessonById(request.getLessonId());

        if (userOptional.isPresent() && lessonOptional.isPresent()) {
            Optional<UserLesson> existingUserLesson = userLessonRepository.findByUser_UserIdAndLesson_LessonId(
                    request.getUserId(), request.getLessonId());
            if (existingUserLesson.isPresent()) {
                return null;
            }

            UserLesson userLesson = new UserLesson();
            userLesson.setUser(userOptional.get());
            userLesson.setLesson(lessonOptional.get());
            userLesson.setIsCompleted(false);
            return userLessonRepository.save(userLesson);
        }
        return null;
    }

    public boolean isUserEnrolled(Long userId, Long lessonId) {
        Optional<UserLesson> existingUserLesson = userLessonRepository.findByUser_UserIdAndLesson_LessonId(userId, lessonId);
        return existingUserLesson.isPresent();
    }

    public UserLesson markLessonAsCompleted(Long userId, Long lessonId) {
        Optional<UserLesson> optionalUserLesson = userLessonRepository.findByUser_UserIdAndLesson_LessonId(userId, lessonId);
        if (optionalUserLesson.isPresent()) {
            UserLesson userLesson = optionalUserLesson.get();
            userLesson.setIsCompleted(true);
            return userLessonRepository.save(userLesson);
        }
        return null;
    }

    public boolean hasIncompleteLessons(Long userId) {
        List<UserLesson> userLessons = userLessonRepository.findByUser_UserId(userId);
        for (UserLesson userLesson : userLessons) {
            if (!userLesson.getIsCompleted()) {
                return true;
            }
        }
        return false;
    }

    public boolean hasCompletedAllLessonsAtLevel(Long userId, String level) {
        List<UserLesson> userLessons = userLessonRepository.findByUser_UserId(userId);
        List<Lesson> lessonsAtLevel = lessonRepository.findByLessonLevel(level);

        for (Lesson lesson : lessonsAtLevel) {
            boolean found = false;
            for (UserLesson userLesson : userLessons) {
                if (userLesson.getLesson().getLessonId().equals(lesson.getLessonId()) && userLesson.getIsCompleted()) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                return false;
            }
        }
        return true;
    }
}
