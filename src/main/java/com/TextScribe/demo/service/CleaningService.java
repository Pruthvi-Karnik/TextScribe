package com.TextScribe.demo.service;

import com.TextScribe.demo.dto.CleanRequest;
import com.TextScribe.demo.dto.CleanResponse;
import com.TextScribe.demo.model.CleanedPageEntity;
import com.TextScribe.demo.repository.CleanedPageRepository;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class CleaningService {

    private final CleanedPageRepository cleanedPageRepository;

    public CleaningService(CleanedPageRepository cleanedPageRepository) {
        this.cleanedPageRepository = cleanedPageRepository;
    }

    public CleanResponse cleanPage(CleanRequest request) {

        try {
            Optional<CleanedPageEntity> existing = cleanedPageRepository.findBySourceUrl(request.url());
            if (existing.isPresent()) {
                CleanedPageEntity page = existing.get();
                return new CleanResponse(page.getTitle(), page.getContent(), page.getSourceUrl(), page.getReadingTimeMinutes());
            }

            Document doc = Jsoup.connect(request.url())
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36")
                    .referrer("https://www.google.com")
                    .timeout(12000)
                    .get();

            String title = doc.title();

            String contentSelector = "article, main, [role='main'], .cha-content, .post-content, .article-body, .mw-parser-output";
            Element targetElement = doc.selectFirst(contentSelector);

            if (targetElement == null) {
                targetElement = doc.body();
            }

            List<String> selectors = request.removeSelectors();
            if (selectors != null && !selectors.isEmpty()) {
                for (String selector : selectors) {
                    targetElement.select(selector).remove();
                }
            } else {
                targetElement.select("script, style, iframe, nav, header, footer, .ads, .breadcrumb, .breadcrumbs, .chapter-nav, .novel-header, .top-bar, .bottom-bar").remove();
            }

            String content;
            if ("markdown".equalsIgnoreCase(request.outputFormat())) {
                content = extractCleanText(targetElement);
                content = content.replaceAll("\n{3,}", "\n\n").trim();
            } else {
                content = targetElement.html();
            }

            int wordCount = content.split("\\s+").length;
            int readingTime = Math.max(1, wordCount / 200);

            CleanedPageEntity entity = new CleanedPageEntity(title, content, request.url(), readingTime);
            cleanedPageRepository.save(entity);

            return new CleanResponse(title, content, request.url(), readingTime);

        } catch (IOException e) {
            throw new RuntimeException("Failed to fetch or parse the URL: " + e.getMessage());
        }
    }

    private String extractCleanText(org.jsoup.nodes.Element element) {
        StringBuilder sb = new StringBuilder();
        for (org.jsoup.nodes.Node node : element.childNodes()) {
            if (node instanceof org.jsoup.nodes.TextNode) {
                sb.append(((org.jsoup.nodes.TextNode) node).text());
            } else if (node instanceof org.jsoup.nodes.Element) {
                org.jsoup.nodes.Element el = (org.jsoup.nodes.Element) node;
                String tagName = el.tagName().toLowerCase();
                if (tagName.equals("br")) {
                    sb.append("\n");
                } else if (tagName.matches("p|div|h[1-6]|li|tr|section")) {
                    sb.append("\n\n");
                    sb.append(extractCleanText(el));
                    sb.append("\n\n");
                } else {
                    sb.append(extractCleanText(el));
                }
            }
        }
        return sb.toString();
    }
}