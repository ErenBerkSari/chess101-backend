package com.example.chess_demo.services;

import com.example.chess_demo.entities.Lesson;
import com.example.chess_demo.entities.User;
import com.example.chess_demo.entities.UserLesson;
import com.example.chess_demo.repos.UserLessonRepository;
import com.example.chess_demo.requests.UserLessonCreateRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserLessonServices {
    UserLessonRepository userLessonRepository;

    UserServices userService;

    LessonServices lessonService;

    public UserLessonServices(UserLessonRepository userLessonRepository, UserServices userService, LessonServices lessonService) {
        this.userLessonRepository = userLessonRepository;
        this.userService = userService;
        this.lessonService = lessonService;
    }

    public List<UserLesson> getAllUserLesson() {
        return userLessonRepository.findAll();
    }

    public List<UserLesson> findByUserId(Long userId) {
        return userLessonRepository.findByUser_UserId(userId);
    }

    public List<UserLesson> findByLessonId(Long lessonId) {
        return userLessonRepository.findByLesson_LessonId(lessonId);
    }

    public void deleteUserLesson(Long userId, Long lessonId) {
        userLessonRepository.deleteByUser_UserIdAndLesson_LessonId(userId,lessonId);
    }

    public UserLesson enrollUserToLesson(UserLessonCreateRequest newUserLessonCreateRequest) {
        User user = userService.getOneUser(newUserLessonCreateRequest.getUserId());
        Lesson lesson = lessonService.getOneLessonById(newUserLessonCreateRequest.getLessonId());
        if(user == null || lesson == null)
            return null;
        UserLesson toSave = new UserLesson();
        toSave.setId(newUserLessonCreateRequest.getId());
        toSave.setLesson(lesson);
        toSave.setUser(user);
        return userLessonRepository.save(toSave);
    }


}
