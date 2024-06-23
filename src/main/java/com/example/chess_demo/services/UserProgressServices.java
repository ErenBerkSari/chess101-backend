package com.example.chess_demo.services;

import com.example.chess_demo.entities.User;
import com.example.chess_demo.entities.UserProgress;
import com.example.chess_demo.repos.LessonRepository;
import com.example.chess_demo.repos.UserProgressRepository;
import com.example.chess_demo.repos.UserLessonRepository;
import com.example.chess_demo.requests.UserProgressCreateRequest;
import com.example.chess_demo.requests.UserProgressUpdateRequest;
import com.example.chess_demo.responses.UserProgressResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserProgressServices {

    @Autowired
    private UserProgressRepository userProgressRepository;

    @Autowired
    private UserLessonRepository userLessonRepository;

    @Autowired
    private UserServices userService;

    @Autowired
    private LessonRepository lessonRepository;

    public List<UserProgressResponse> getAllUserProgress() {
        List<UserProgress> list;
        list = userProgressRepository.findAll();
        return list.stream().map(UserProgressResponse::new).collect(Collectors.toList());
    }

   /* public List<UserProgressResponse> getUserProgress(Long userId) {
        List<UserProgress> list;
        list = userProgressRepository.findByUser_UserId(userId).stream().collect(Collectors.toList());
        return list.stream().map(UserProgressResponse::new).collect(Collectors.toList());
    }*/

    public int getCompletedLessonsCount(Long userId) {
        return userLessonRepository.countByUser_UserIdAndIsCompletedTrue(userId);
    }

    public int getTotalLessonsCount() {
        return (int) lessonRepository.count();
    }

    public UserProgress createUserProgress(UserProgressCreateRequest newUserProgressCreateRequest) {
        Optional<User> userOptional = userService.getOneUser(newUserProgressCreateRequest.getUserId());
        if (!userOptional.isPresent()) {
            return null;
        }

        User user = userOptional.get();

        UserProgress toCreate = new UserProgress();
        toCreate.setProgressInUser(newUserProgressCreateRequest.getProgressInUser());
        toCreate.setId(newUserProgressCreateRequest.getId());
        toCreate.setUser(user);

        return userProgressRepository.save(toCreate);
    }

    public UserProgress updateUserProgress(Long userId) {
        Optional<UserProgress> userProgressOpt = userProgressRepository.findByUser_UserId(userId);
        if (userProgressOpt.isPresent()) {
            UserProgress userProgress = userProgressOpt.get();
            int completedLessons = userLessonRepository.countByUser_UserIdAndIsCompletedTrue(userId);
            double progress = ((double) completedLessons / (double) lessonRepository.count()) * 100;
            userProgress.setProgressInUser(progress);
            return userProgressRepository.save(userProgress);
        }
        return null;
    }


    public void deleteUserProgress(Long progressId) {
        userProgressRepository.deleteById(progressId);
    }
}
