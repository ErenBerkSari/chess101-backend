package com.example.chess_demo.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name="lesson_progress")
@Data
public class LessonProgress {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lesson_progress_id")
    Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="user_id",nullable=false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="lesson_id",nullable=false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    Lesson lesson;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="progress_id",nullable=false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    Progress progress;

    @Column(name = "progress_in_lesson")
    int progressInLesson;
}
