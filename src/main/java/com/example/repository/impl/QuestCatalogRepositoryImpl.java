package com.example.repository.impl;

import com.example.model.QuestDefinition;
import com.example.repository.QuestCatalogRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class QuestCatalogRepositoryImpl implements QuestCatalogRepository {
    private final Map<String, QuestDefinition> quests = new HashMap<>();

    @Override
    public void saveAll(List<QuestDefinition> questList) {
        questList.forEach(q -> quests.put(q.getId(), q));
    }

    @Override
    public Optional<QuestDefinition> findById(String id) {
        return Optional.ofNullable(quests.get(id));
    }

    @Override
    public List<QuestDefinition> findAll() {
        return List.copyOf(quests.values());
    }
}
