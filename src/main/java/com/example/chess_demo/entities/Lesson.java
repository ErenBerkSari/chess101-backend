package com.example.chess_demo.entities;

import jakarta.persistence.*;
import lombok.Data;


@Entity
@Table(name="lesson")
@Data
public class Lesson {
    @Id
    Long lessonId;


    String lessonName;
    String lessonDesc;
}
