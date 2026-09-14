package com.TextScribe.demo.controller;

import com.TextScribe.demo.dto.CleanRequest;
import com.TextScribe.demo.dto.CleanResponse;
import com.TextScribe.demo.service.CleaningService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class CleanerController {

    private final CleaningService cleaningService;

    public CleanerController(CleaningService cleaningService) {
        this.cleaningService = cleaningService;
    }

    @PostMapping("/clean")
    public ResponseEntity<CleanResponse> cleanPage(@RequestBody CleanRequest request) {
        try {
            CleanResponse response = cleaningService.cleanPage(request);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                    new CleanResponse("Error", "Failed to fetch or parse URL: " + e.getMessage(), request.url(), 0)
            );
        }
    }
}