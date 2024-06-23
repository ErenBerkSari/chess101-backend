package com.example.chess_demo.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import java.util.Map;
import java.util.List;

@Entity
@Table(name = "lesson")
@Data
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Lesson {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lesson_id")
    private Long lessonId;

    @Column(name = "lesson_name")
    private String lessonName;

    @Column(name = "lesson_desc")
    private String lessonDesc;

    @Column(name = "lesson_level")
    private String lessonLevel;

    @Column(name = "lesson_image_url")
    private String lessonImageUrl;

    @Column(name = "content", columnDefinition = "TEXT")
    private String content; // Update type if needed to handle JSON properly

    @Column(name = "test_questions", columnDefinition = "TEXT")
    private String testQuestions; // Update type if needed to handle JSON properly
}
