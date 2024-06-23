package com.example.chess_demo.requests;

import lombok.Data;

@Data
    public class ProgressCreateRequest {
    Long id;
    int progressLevel;
    String completionDate;

}
