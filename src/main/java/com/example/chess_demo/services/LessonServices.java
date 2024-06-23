package com.example.chess_demo.services;

import com.example.chess_demo.entities.Lesson;
import com.example.chess_demo.repos.LessonRepository;
import com.example.chess_demo.requests.LessonUpdateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LessonServices {

    @Autowired
    private LessonRepository lessonRepository;

    public List<Lesson> getAllLessons() {
        return lessonRepository.findAll();
    }

    public Lesson createOneLesson(Lesson newLesson) {
        return lessonRepository.save(newLesson);
    }

    public Optional<Lesson> getOneLessonById(Long lessonId) {
        return lessonRepository.findById(lessonId);
    }

    public Lesson updateOneLessonById(Long lessonId, LessonUpdateRequest updateLessonRequest) {
        Optional<Lesson> lessonOptional = lessonRepository.findById(lessonId);
        if (lessonOptional.isPresent()) {
            Lesson existingLesson = lessonOptional.get();
            existingLesson.setLessonName(updateLessonRequest.getLessonName());
            existingLesson.setLessonDesc(updateLessonRequest.getLessonDesc());
            existingLesson.setLessonLevel(updateLessonRequest.getLessonLevel());
            existingLesson.setLessonImageUrl(updateLessonRequest.getLessonImageUrl());
            existingLesson.setContent(updateLessonRequest.getContent());
            existingLesson.setTestQuestions(updateLessonRequest.getTestQuestions());
            return lessonRepository.save(existingLesson);
        } else {
            return null;
        }
    }

    public void deleteOneLessonById(Long lessonId) {
        lessonRepository.deleteById(lessonId);
    }
}
