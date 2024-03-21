package com.example.chess_demo.repos;

import com.example.chess_demo.entities.LessonProgress;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LessonProgressRepository extends JpaRepository<LessonProgress,Long> {

    

    LessonProgress findByUser_UserIdAndLesson_LessonId(Long userId, Long lessonId);

    List<LessonProgress> findByLesson_LessonId(Long lessonId);


    List<LessonProgress> findByUser_UserId(Long userId);
}
