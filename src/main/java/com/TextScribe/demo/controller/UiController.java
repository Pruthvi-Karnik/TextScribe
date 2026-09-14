package com.TextScribe.demo.controller;

import com.TextScribe.demo.dto.CleanRequest;
import com.TextScribe.demo.dto.CleanResponse;
import com.TextScribe.demo.repository.CleanedPageRepository;
import com.TextScribe.demo.service.CleaningService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UiController {

    private final CleaningService cleaningService;
    private final CleanedPageRepository cleanedPageRepository;

    public UiController(CleaningService cleaningService, CleanedPageRepository cleanedPageRepository) {
        this.cleaningService = cleaningService;
        this.cleanedPageRepository = cleanedPageRepository;
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("history", cleanedPageRepository.findAll());
        return "index";
    }

    @PostMapping("/clean-ui")
    public String cleanPageUi(@RequestParam String url, Model model) {
        try {
            CleanRequest request = new CleanRequest(url, null, "markdown");
            CleanResponse response = cleaningService.cleanPage(request);
            model.addAttribute("response", response);
        } catch (Exception e) {
            model.addAttribute("error", "Failed to fetch or parse URL: " + e.getMessage());
        }
        model.addAttribute("history", cleanedPageRepository.findAll());
        return "index";
    }
}