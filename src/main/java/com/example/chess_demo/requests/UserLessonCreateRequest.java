package com.example.chess_demo.requests;

import lombok.Data;

@Data
public class UserLessonCreateRequest {
    Long id;
    Long userId;
    Long lessonId;
}
