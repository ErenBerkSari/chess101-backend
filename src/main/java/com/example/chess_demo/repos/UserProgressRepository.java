package com.example.chess_demo.repos;

import com.example.chess_demo.entities.UserProgress;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserProgressRepository extends JpaRepository<UserProgress,Long> {

    List<UserProgress> findByUser_UserId(Long userId);
}
