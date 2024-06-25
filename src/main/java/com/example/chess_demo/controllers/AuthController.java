package com.example.chess_demo.controllers;

import com.example.chess_demo.dto.ReqRes;
import com.example.chess_demo.dto.UserUpdateDto;
import com.example.chess_demo.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<ReqRes> signUp(@RequestBody ReqRes signUpRequest) {
        return ResponseEntity.ok(authService.signUp(signUpRequest));
    }

    @PostMapping("/signin")
    public ResponseEntity<ReqRes> signIn(@RequestBody ReqRes signInRequest) {
        ReqRes response = authService.signIn(signInRequest);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @PostMapping("/refresh")
    public ResponseEntity<ReqRes> refreshToken(@RequestBody ReqRes refreshTokenRequest) {
        return ResponseEntity.ok(authService.refreshToken(refreshTokenRequest));
    }

    @PutMapping("/update/{userId}")
    public ResponseEntity<ReqRes> updateUser(@PathVariable Long userId, @RequestBody UserUpdateDto userUpdateDto) {
        return ResponseEntity.ok(authService.updateUser(userId, userUpdateDto));
    }
}
