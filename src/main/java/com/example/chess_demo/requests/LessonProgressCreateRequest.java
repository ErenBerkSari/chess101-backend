package com.example.chess_demo.requests;

import lombok.Data;

@Data
public class LessonProgressCreateRequest {
    Long id;
    Long userId;
    Long lessonId;
    int progressInLesson;
}
