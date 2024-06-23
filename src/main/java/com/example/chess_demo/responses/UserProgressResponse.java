package com.example.chess_demo.responses;

import com.example.chess_demo.entities.UserProgress;
import lombok.Data;


@Data
public class UserProgressResponse {
    Long id;
    Long userId;
    String username;
    double progressInUser;

    public UserProgressResponse(UserProgress entity){
        this.id = entity.getId();
        this.userId = entity.getUser().getUserId();
        this.username = entity.getUser().getUsername();
        this.progressInUser = entity.getProgressInUser();
    }
}
