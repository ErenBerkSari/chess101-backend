package com.example.chess_demo.repos;

import com.example.chess_demo.entities.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LessonRepository extends JpaRepository<Lesson,Long> {

}
