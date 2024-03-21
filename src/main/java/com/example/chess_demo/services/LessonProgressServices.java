package com.example.chess_demo.services;

import com.example.chess_demo.entities.Lesson;
import com.example.chess_demo.entities.LessonProgress;
import com.example.chess_demo.entities.User;
import com.example.chess_demo.repos.LessonProgressRepository;
import com.example.chess_demo.requests.LessonProgressCreateRequest;
import com.example.chess_demo.requests.LessonProgressUpdateRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LessonProgressServices {
    private LessonProgressRepository lessonProgressRepository;

    private UserServices userService;

    private LessonServices lessonService;

    public LessonProgressServices(LessonProgressRepository lessonProgressRepository, UserServices userService, LessonServices lessonService) {
        this.lessonProgressRepository = lessonProgressRepository;
        this.userService = userService;
        this.lessonService = lessonService;
    }

    public List<LessonProgress> getAllProgress() {
        return lessonProgressRepository.findAll();
    }

    public List<LessonProgress> getUserProgress(Long userId) {
        return lessonProgressRepository.findByUser_UserId(userId);
    }

    public List<LessonProgress> getLessonProgress(Long lessonId) {
        return lessonProgressRepository.findByLesson_LessonId(lessonId);
    }
    public LessonProgress createLessonProgress(LessonProgressCreateRequest newLessonProgressCreateRequest) {
        User user = userService.getOneUser(newLessonProgressCreateRequest.getUserId());
        Lesson lesson = lessonService.getOneLessonById(newLessonProgressCreateRequest.getLessonId());
        if(user == null || lesson == null)
            return null;
        LessonProgress toCreate = new LessonProgress();
        toCreate.setProgressInLesson(newLessonProgressCreateRequest.getProgressInLesson());
        toCreate.setId(newLessonProgressCreateRequest.getId());
        toCreate.setUser(user);
        toCreate.setLesson(lesson);
        return lessonProgressRepository.save(toCreate);

    }
    public LessonProgress updateProgress(Long progressId, LessonProgressUpdateRequest newLessonProgressUpdateRequest) {
        Optional<LessonProgress> progress = lessonProgressRepository.findById(progressId);
        if(progress.isPresent())
        {
            LessonProgress toUpdateProgress = progress.get();
            toUpdateProgress.setProgressInLesson(newLessonProgressUpdateRequest.getProgressInLesson());
            lessonProgressRepository.save(toUpdateProgress);
            return toUpdateProgress;
        }
        return null;

    }

    public void deleteLessonProgress(Long progressId) {
        lessonProgressRepository.deleteById(progressId);
    }

    public LessonProgress getUserLessonProgress(Long userId, Long lessonId) {
        return lessonProgressRepository.findByUser_UserIdAndLesson_LessonId(userId,lessonId);
    }


}
