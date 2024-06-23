package com.example.chess_demo.repos;

import com.example.chess_demo.entities.UserLesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserLessonRepository extends JpaRepository<UserLesson, Long> {
    List<UserLesson> findByUser_UserId(Long userId);
    List<UserLesson> findByLesson_LessonId(Long lessonId);
    Optional<UserLesson> findByUser_UserIdAndLesson_LessonId(Long userId, Long lessonId);
    void deleteByUser_UserIdAndLesson_LessonId(Long userId, Long lessonId);

    int countByUser_UserIdAndIsCompletedTrue(Long userId);

}
