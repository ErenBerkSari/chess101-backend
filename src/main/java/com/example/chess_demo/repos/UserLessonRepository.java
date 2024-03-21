package com.example.chess_demo.repos;

import com.example.chess_demo.entities.UserLesson;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserLessonRepository extends JpaRepository<UserLesson,Long> {

    List<UserLesson> findByUser_UserId(Long userId);

    List<UserLesson> findByLesson_LessonId(Long lessonId);

    void deleteByUser_UserIdAndLesson_LessonId(Long userId, Long lessonId);
}
