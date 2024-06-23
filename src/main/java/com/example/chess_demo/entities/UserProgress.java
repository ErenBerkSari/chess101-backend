package com.example.chess_demo.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name="user_progress")
@Data
public class UserProgress {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_progress_id")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="user_id", nullable=false)
    private User user;

    @Column(name = "progress_in_user")
    private double progressInUser; // Changed to double
}
