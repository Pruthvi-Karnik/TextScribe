package com.TextScribe.demo.dto;

import java.util.List;

public record CleanRequest(
        String url,
        List<String> removeSelectors,
        String outputFormat
) {}