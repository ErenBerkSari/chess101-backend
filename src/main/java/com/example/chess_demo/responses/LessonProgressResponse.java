package com.example.chess_demo.responses;

import com.example.chess_demo.entities.LessonProgress;
import lombok.Data;

@Data
public class LessonProgressResponse {
    Long id;

    Long userId;

    String username;

    String lessonName;

    String lessonDesc;

    Long lessonId;

    Long progressId;

    int progressInLesson;

    public LessonProgressResponse(LessonProgress entity)
    {
        this.id = entity.getId();
        this.userId = entity.getUser().getUserId();
        this.username = entity.getUser().getUsername();
        this.lessonId = entity.getLesson().getLessonId();
        this.lessonName = entity.getLesson().getLessonName();
        this.lessonDesc = entity.getLesson().getLessonDesc();
        this.progressId = entity.getProgress().getProgressId();
        this.progressInLesson = entity.getProgressInLesson();

    }

}
