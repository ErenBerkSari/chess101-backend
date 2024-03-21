package com.example.chess_demo.repos;

import com.example.chess_demo.entities.Progress;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProgressRepository extends JpaRepository<Progress,Long> {

}
