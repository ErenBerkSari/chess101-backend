package com.example.chess_demo.entities;

import jakarta.persistence.*;
import lombok.Data;


@Entity
@Table(name="progress")
@Data
public class Progress {

    @Id
    Long progressId;

    int progressLevel;
    String completionDate;
}
