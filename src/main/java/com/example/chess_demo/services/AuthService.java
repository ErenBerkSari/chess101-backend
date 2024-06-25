package com.example.chess_demo.services;

import com.example.chess_demo.dto.ReqRes;
import com.example.chess_demo.dto.UserUpdateDto;
import com.example.chess_demo.entities.User;
import com.example.chess_demo.entities.UserProgress;
import com.example.chess_demo.repos.UserProgressRepository;
import com.example.chess_demo.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import com.example.chess_demo.dto.ReqRes;
import com.example.chess_demo.entities.User;
import com.example.chess_demo.entities.UserProgress;
import com.example.chess_demo.repos.UserProgressRepository;
import com.example.chess_demo.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import com.example.chess_demo.dto.ReqRes;
import com.example.chess_demo.dto.UserUpdateDto;
import com.example.chess_demo.entities.User;
import com.example.chess_demo.entities.UserProgress;
import com.example.chess_demo.repos.UserProgressRepository;
import com.example.chess_demo.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserRepository ourUserRepo;

    @Autowired
    private UserProgressRepository userProgressRepository;

    @Autowired
    private JWTUtils jwtUtils;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

//    public ReqRes signUp(ReqRes registrationRequest) {
//        ReqRes resp = new ReqRes();
//        try {
//            if (ourUserRepo.findByEmail(registrationRequest.getUsername()).isPresent()) {
//                resp.setStatusCode(400);
//                resp.setMessage("Email already in use");
//                return resp;
//            }
//
//            User ourUsers = new User();
//            ourUsers.setRealUsername(registrationRequest.getUsername());
//            ourUsers.setEmail(registrationRequest.getEmail());
//            ourUsers.setPassword(passwordEncoder.encode(registrationRequest.getPassword()));
//            ourUsers.setRole("USER");
//            User ourUserResult = ourUserRepo.save(ourUsers);
//            System.out.println("User registered: " + ourUserResult);
//
//            UserProgress userProgress = new UserProgress();
//            userProgress.setUser(ourUserResult);
//            userProgress.setProgressInUser(0);
//            userProgressRepository.save(userProgress);
//
//            resp.setOurUsers(ourUserResult);
//            resp.setMessage("User Saved Successfully");
//            resp.setStatusCode(200);
//            String jwt = jwtUtils.generateToken(ourUserResult);
//            resp.setToken(jwt);
//            resp.setUserId(ourUserResult.getUserId());
//            resp.setRole(ourUserResult.getRole());
//            resp.setUsername(ourUserResult.getRealUsername());
//
//        } catch (Exception e) {
//            resp.setStatusCode(500);
//            resp.setError(e.getMessage());
//        }
//        return resp;
//    }

    public ReqRes signUp(ReqRes registrationRequest) {
        ReqRes resp = new ReqRes();
        try {
            if (ourUserRepo.findByEmail(registrationRequest.getEmail()).isPresent()) {
                resp.setStatusCode(400);
                resp.setMessage("Email already in use");
                return resp;
            }
            User ourUsers = new User();
            ourUsers.setUsername(registrationRequest.getUsername());
            ourUsers.setEmail(registrationRequest.getEmail());
            ourUsers.setPassword(passwordEncoder.encode(registrationRequest.getPassword())); // Şifre kodlama
            ourUsers.setRole("USER");
            User ourUserResult = ourUserRepo.save(ourUsers);
            System.out.println("User registered: " + ourUserResult);
            if (ourUserResult != null && ourUserResult.getUserId() > 0) {
                resp.setOurUsers(ourUserResult);
                resp.setMessage("User Saved Successfully");
                resp.setStatusCode(200);
                String jwt = jwtUtils.generateToken(ourUserResult);
                resp.setToken(jwt);
                resp.setUserId(ourUserResult.getUserId());
                resp.setRole(ourUserResult.getRole()); // Rolü ekle
            }
        } catch (Exception e) {
            resp.setStatusCode(500);
            resp.setError(e.getMessage());
        }
        return resp;
    }


    public ReqRes signIn(ReqRes signinRequest) {
        ReqRes response = new ReqRes();
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(signinRequest.getEmail(), signinRequest.getPassword())
            );

            User user = ourUserRepo.findByEmail(signinRequest.getEmail())
                    .orElseThrow(() -> new Exception("User not found with email: " + signinRequest.getEmail()));

            String jwt = jwtUtils.generateToken(user);
            response.setStatusCode(200);
            response.setToken(jwt);
            response.setUserId(user.getUserId());
            response.setMessage("Successfully Signed In");
            response.setRole(user.getRole());
            response.setUsername(user.getRealUsername());
        } catch (AuthenticationException e) {
            response.setStatusCode(401);
            response.setError("Invalid email or password");
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setError(e.getMessage());
        }
        return response;
    }

    public ReqRes refreshToken(ReqRes refreshTokenRequest){
        ReqRes response = new ReqRes();
        String email = jwtUtils.extractUsername(refreshTokenRequest.getToken());
        User user = ourUserRepo.findByEmail(email).orElseThrow();
        if (jwtUtils.isTokenValid(refreshTokenRequest.getToken(), user)) {
            String jwt = jwtUtils.generateToken(user);
            response.setStatusCode(200);
            response.setToken(jwt);
            response.setRefreshToken(refreshTokenRequest.getToken());
            response.setExpirationTime("24Hr");
            response.setMessage("Successfully Refreshed Token");
        } else {
            response.setStatusCode(500);
            response.setError("Invalid refresh token");
        }
        return response;
    }

    public ReqRes updateUser(Long userId, UserUpdateDto userUpdateDto) {
        ReqRes resp = new ReqRes();
        try {
            User user = ourUserRepo.findById(userId)
                    .orElseThrow(() -> new Exception("User not found with id: " + userId));
            if (userUpdateDto.getUsername() != null && !userUpdateDto.getUsername().isEmpty()) {
                user.setRealUsername(userUpdateDto.getUsername());
            }
            if (userUpdateDto.getPassword() != null && !userUpdateDto.getPassword().isEmpty()) {
                user.setPassword(passwordEncoder.encode(userUpdateDto.getPassword()));
            }
            User updatedUser = ourUserRepo.save(user);
            resp.setStatusCode(200);
            resp.setMessage("User updated successfully");
            resp.setOurUsers(updatedUser);
        } catch (Exception e) {
            resp.setStatusCode(500);
            resp.setError(e.getMessage());
        }
        return resp;
    }
}

