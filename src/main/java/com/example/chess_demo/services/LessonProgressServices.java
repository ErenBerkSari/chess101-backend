package com.example.chess_demo.services;

import com.example.chess_demo.entities.Lesson;
import com.example.chess_demo.entities.LessonProgress;
import com.example.chess_demo.entities.Progress;
import com.example.chess_demo.entities.User;
import com.example.chess_demo.repos.LessonProgressRepository;
import com.example.chess_demo.requests.LessonProgressCreateRequest;
import com.example.chess_demo.requests.LessonProgressUpdateRequest;
import com.example.chess_demo.responses.LessonProgressResponse;
import com.example.chess_demo.responses.UserLessonResponse;
import com.example.chess_demo.responses.UserProgressResponse;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LessonProgressServices {
    private LessonProgressRepository lessonProgressRepository;

    private UserServices userService;

    private LessonServices lessonService;

    private ProgressServices progressService;

    public LessonProgressServices(LessonProgressRepository lessonProgressRepository, UserServices userService, LessonServices lessonService, ProgressServices progressService) {
        this.lessonProgressRepository = lessonProgressRepository;
        this.userService = userService;
        this.lessonService = lessonService;
        this.progressService = progressService;
    }
    public List<LessonProgressResponse> getAllProgress() {
        List<LessonProgress> list;
        list = lessonProgressRepository.findAll();
        return list.stream().map(p -> new LessonProgressResponse(p)).collect(Collectors.toList());

    }
    public List<LessonProgressResponse> getUserProgress(Long userId) {
        List<LessonProgress> list;
        list = lessonProgressRepository.findByUser_UserId(userId);
        return list.stream().map(p -> new LessonProgressResponse(p)).collect(Collectors.toList());
    }
    public List<LessonProgressResponse> getLessonProgress(Long lessonId) {
        List<LessonProgress> list;
        list = lessonProgressRepository.findByLesson_LessonId(lessonId);
        return list.stream().map(p -> new LessonProgressResponse(p)).collect(Collectors.toList());

    }
    public List<LessonProgressResponse> getUserLessonProgress(Long userId, Long lessonId) {
        List<LessonProgress> list = lessonProgressRepository.findByUser_UserIdAndLesson_LessonId(userId, lessonId);

        // Null kontrolü ekleyelim
        if (list == null) {
            return new ArrayList<>();
        }

        return list.stream()
                .map(p -> new LessonProgressResponse(p))
                .collect(Collectors.toList());
    }

    public LessonProgress createLessonProgress(LessonProgressCreateRequest newLessonProgressCreateRequest) {
        Optional<User> userOptional = userService.getOneUser(newLessonProgressCreateRequest.getUserId());
        Optional<Lesson> lessonOptional = lessonService.getOneLessonById(newLessonProgressCreateRequest.getLessonId());
        Optional<Progress> progressOptional = progressService.getOneProgressById(newLessonProgressCreateRequest.getProgressId());

        if (!userOptional.isPresent() || !lessonOptional.isPresent() || !progressOptional.isPresent()) {
            return null;
        }

        User user = userOptional.get();
        Lesson lesson = lessonOptional.get();
        Progress progress = progressOptional.get();

        LessonProgress toCreate = new LessonProgress();
        toCreate.setProgressInLesson(newLessonProgressCreateRequest.getProgressInLesson());
        toCreate.setId(newLessonProgressCreateRequest.getId());
        toCreate.setUser(user);
        toCreate.setLesson(lesson);
        toCreate.setProgress(progress);

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



}
