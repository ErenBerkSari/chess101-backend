package com.example.chess_demo.services;

import com.example.chess_demo.entities.Progress;
import com.example.chess_demo.entities.User;
import com.example.chess_demo.entities.UserProgress;
import com.example.chess_demo.repos.UserProgressRepository;
import com.example.chess_demo.requests.UserProgressCreateRequest;
import com.example.chess_demo.requests.UserProgressUpdateRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserProgressServices {
    private UserProgressRepository userProgressRepository;

    private UserServices userService;


    public UserProgressServices(UserProgressRepository userProgressRepository, UserServices userService) {
        this.userProgressRepository = userProgressRepository;
        this.userService = userService;
    }

    public List<UserProgress> getAllUserProgress() {
        return userProgressRepository.findAll();
    }

    public List<UserProgress> getUserProgress(Long userId) {
        return userProgressRepository.findByUser_UserId(userId);
    }


    public UserProgress createUserProgress(UserProgressCreateRequest newUserProgressCreateRequest) {
        User user = userService.getOneUser(newUserProgressCreateRequest.getUserId());
        if(user == null)
            return null;
        UserProgress toSave = new UserProgress();
        toSave.setProgressInUser(newUserProgressCreateRequest.getProgressInUser());
        toSave.setId(newUserProgressCreateRequest.getId());
        toSave.setUser(user);
        return userProgressRepository.save(toSave);

    }

    //Aklıma takıldı, burada progress nesnesi mi yoksa UserProgress nesnesi mi dönecek, daha sonra kontrol et
    public UserProgress updateUserProgress(Long progressId, UserProgressUpdateRequest newUserProgressUpdateRequest) {
        Optional<UserProgress> progress = userProgressRepository.findById(progressId);
        if(progress.isPresent())
        {
            UserProgress toUpdateProgress = progress.get();
            toUpdateProgress.setProgressInUser(newUserProgressUpdateRequest.getProgressInUser());
            userProgressRepository.save(toUpdateProgress);
            return toUpdateProgress;
        }
        return null;
    }

    public void deleteUserProgress(Long progressId) {
        userProgressRepository.deleteById(progressId);
    }
}
