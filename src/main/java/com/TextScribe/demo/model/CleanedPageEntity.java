package com.TextScribe.demo.model;

import jakarta.persistence.*;

@Entity
@Table(name = "cleaned_pages")
public class CleanedPageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    private String sourceUrl;

    private int readingTimeMinutes;

    public CleanedPageEntity() {}

    public CleanedPageEntity(String title, String content, String sourceUrl, int readingTimeMinutes) {
        this.title = title;
        this.content = content;
        this.sourceUrl = sourceUrl;
        this.readingTimeMinutes = readingTimeMinutes;
    }

    public Long getId() { return id; }
    public String getTitle() { return title; }
    public String getContent() { return content; }
    public String getSourceUrl() { return sourceUrl; }
    public int getReadingTimeMinutes() { return readingTimeMinutes; }
}