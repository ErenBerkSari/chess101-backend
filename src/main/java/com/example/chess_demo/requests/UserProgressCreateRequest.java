package com.example.chess_demo.requests;

import lombok.Data;

@Data
public class UserProgressCreateRequest {
    Long id;
    Long userId;
    int progressInUser;
}
