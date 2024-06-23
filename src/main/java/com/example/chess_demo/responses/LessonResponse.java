package com.example.chess_demo.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LessonResponse {
    private Long lessonId;
    private String lessonName;
    private String lessonDesc;
    private String lessonLevel;
    private String lessonImageUrl;
    private String content;
}
