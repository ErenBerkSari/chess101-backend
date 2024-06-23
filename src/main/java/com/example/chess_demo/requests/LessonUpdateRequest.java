package com.example.chess_demo.requests;

import lombok.Data;

@Data
public class LessonUpdateRequest {
    String lessonName;
    String lessonDesc;
    String lessonLevel;
    String lessonImageUrl;
    String content; // JSON string
    String testQuestions; // JSON string
}
