package com.example.chess_demo.dto;

import com.example.chess_demo.entities.Lesson;
import com.example.chess_demo.entities.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReqRes {

    private int statusCode;
    private String error;
    private String message;
    private String token;
    private String refreshToken;
    private String expirationTime;
    private String username;
    private Long userId;
    private String email;
    private String role;
    private String password;// BURADA TUTMA
    private List<Lesson> lessons;
    private User ourUsers;

}
