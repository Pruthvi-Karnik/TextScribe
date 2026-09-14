package com.TextScribe.demo.repository;

import com.TextScribe.demo.model.CleanedPageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface CleanedPageRepository extends JpaRepository<CleanedPageEntity, Long> {
    Optional<CleanedPageEntity> findBySourceUrl(String sourceUrl);
}