package com.example.chess_demo.responses;

import com.example.chess_demo.entities.UserLesson;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserLessonResponse {
    private Long id;
    private LessonResponse lesson;
    private boolean isCompleted;
}
