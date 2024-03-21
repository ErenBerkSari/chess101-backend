package com.example.chess_demo.requests;

import lombok.Data;

@Data
public class LessonCreateRequest {
    Long id;
    String lessonName;
    String lessonDesc;
}
