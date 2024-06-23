package com.example.chess_demo.controllers;

import com.example.chess_demo.entities.Progress;
import com.example.chess_demo.requests.ProgressCreateRequest;
import com.example.chess_demo.requests.ProgressUpdateRequest;
import com.example.chess_demo.services.ProgressServices;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/progresses")
public class ProgressController {
    private ProgressServices progressService;

    public ProgressController(ProgressServices progressService) {
        this.progressService = progressService;
    }

    @GetMapping
    public List<Progress> getAllProgresses()
    {
        return progressService.gelAllProgresses();
    }

    @PostMapping
    public Progress createOneProgress(@RequestBody ProgressCreateRequest newProgressRequest)
    {
        return progressService.createOneProgress(newProgressRequest);
    }

    @GetMapping("/{progressId}")
    public ResponseEntity<Progress> getOneProgress(@PathVariable Long progressId) {
        Optional<Progress> progressOptional = progressService.getOneProgressById(progressId);
        if (progressOptional.isPresent()) {
            return ResponseEntity.ok(progressOptional.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
    @PutMapping("/{progressId}")
    public Progress updateOneProgress(@PathVariable Long progressId, @RequestBody ProgressUpdateRequest updateProgress)
    {
        return progressService.updateOneProgressById(progressId, updateProgress);
    }

    @DeleteMapping("/{progressId}")
    public void deleteOneLesson(@PathVariable Long progressId)
    {
        progressService.deleteOneLessonById(progressId);
    }
}
