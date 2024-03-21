package com.example.chess_demo.services;

import com.example.chess_demo.entities.Lesson;
import com.example.chess_demo.entities.User;
import com.example.chess_demo.repos.ProgressRepository;
import com.example.chess_demo.requests.ProgressCreateRequest;
import com.example.chess_demo.requests.ProgressUpdateRequest;
import org.springframework.stereotype.Service;
import com.example.chess_demo.entities.Progress;

import java.util.List;
import java.util.Optional;

@Service
public class ProgressServices {
    private ProgressRepository progressRepository;

    private UserServices userService;

    private LessonServices lessonService;

    public ProgressServices(ProgressRepository progressRepository, UserServices userService) {
        this.progressRepository = progressRepository;
        this.userService =userService;
    }


    public List<Progress> gelAllProgresses() {
        return progressRepository.findAll();
    }


    public Progress createOneProgress(ProgressCreateRequest newProgressRequest) {
        Progress toSave = new Progress();
        toSave.setProgressId(newProgressRequest.getId());
        toSave.setProgressLevel(newProgressRequest.getProgressLevel());
        toSave.setCompletionDate(newProgressRequest.getCompletion_date());
        return progressRepository.save(toSave);
    }
    public Progress getOneProgressById(Long progressId) {
        return progressRepository.findById(progressId).orElse(null);
    }
    public Progress updateOneProgressById(Long progressId, ProgressUpdateRequest updateProgress) {
        Optional<Progress> progress = progressRepository.findById(progressId);
        if(progress.isPresent())
        {
            Progress toUpdate = progress.get();
            toUpdate.setProgressLevel(updateProgress.getProgressLevel());
            toUpdate.setCompletionDate(updateProgress.getCompletionDate());
            progressRepository.save(toUpdate);
            return toUpdate;
        }
        return null;
    }

    public void deleteOneLessonById(Long progressId) {
        progressRepository.deleteById(progressId);
    }
}
