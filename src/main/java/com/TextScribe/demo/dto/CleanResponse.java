package com.TextScribe.demo.dto;

public record CleanResponse(
        String title,
        String content,
        String sourceUrl,
        int readingTimeMinutes
) {}