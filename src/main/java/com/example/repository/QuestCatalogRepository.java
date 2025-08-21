package com.example.repository;

import com.example.model.QuestDefinition;

import java.util.List;
import java.util.Optional;

public interface QuestCatalogRepository {
    void saveAll(List<QuestDefinition> quests);
    Optional<QuestDefinition> findById(String id);
    List<QuestDefinition> findAll();
}
