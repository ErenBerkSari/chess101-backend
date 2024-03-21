package com.example.chess_demo.services;

import com.example.chess_demo.entities.Lesson;
import com.example.chess_demo.entities.User;
import com.example.chess_demo.repos.LessonRepository;
import com.example.chess_demo.requests.LessonCreateRequest;
import com.example.chess_demo.requests.LessonUpdateRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LessonServices {
    private LessonRepository lessonRepository;

    private UserServices userService;

    public LessonServices(LessonRepository lessonRepository, UserServices userService) {
        this.lessonRepository = lessonRepository;
        this.userService = userService;
    }


    public List<Lesson> getAllLessons() {
        return lessonRepository.findAll();
    }

    public Lesson createOneLesson(LessonCreateRequest newLessonRequest) {
        Lesson toSave = new Lesson();
        toSave.setLessonId(newLessonRequest.getId());
        toSave.setLessonName(newLessonRequest.getLessonName());
        toSave.setLessonDesc(newLessonRequest.getLessonDesc());
        return lessonRepository.save(toSave);
    }

    public Lesson getOneLessonById(Long lessonId) {
        return lessonRepository.findById(lessonId).orElse(null);
    }


    public Lesson updateOneLessonById(Long lessonId, LessonUpdateRequest updateLesson) {
        Optional<Lesson> lesson = lessonRepository.findById(lessonId);
        if(lesson.isPresent())
        {
            Lesson toUpdate = lesson.get();
            toUpdate.setLessonName(updateLesson.getLessonName());
            toUpdate.setLessonDesc(toUpdate.getLessonDesc());
            lessonRepository.save(toUpdate);
            return toUpdate;
        }
        return null;
    }

    public void deleteOneLessonById(Long lessonId) {
        lessonRepository.deleteById(lessonId);
    }

/*
    public List<Lesson> getAllLessonByUserId(Long userId) {
        User user = userService.getOneUser(userId);
        if(user != null)
        {
            return lessonRepository.findByUserId(userId);
        }
        return null;
    }
 */
}
