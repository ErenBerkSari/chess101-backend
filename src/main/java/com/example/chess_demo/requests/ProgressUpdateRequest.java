package com.example.chess_demo.requests;

import lombok.Data;

@Data
public class ProgressUpdateRequest {
    int progressLevel;
    String completionDate;
}
