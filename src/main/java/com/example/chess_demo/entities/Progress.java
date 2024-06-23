package com.example.chess_demo.entities;

import jakarta.persistence.*;
import lombok.Data;


@Entity
@Table(name="progress")
@Data
public class Progress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "progress_id")
    Long progressId;

    @Column(name = "progress_level")
    int progressLevel;

    @Column(name = "completion_date")
    String completionDate;
}
