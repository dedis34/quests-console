package com.example.repository.impl;

import com.example.model.QuestStatus;
import com.example.repository.AssignmentRepository;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class AssignmentRepositoryImpl implements AssignmentRepository {
    private final Map<String, QuestStatus> assignments = new HashMap<>();

    @Override
    public void save(String userId, QuestStatus status) {
        assignments.put(userId, status);
    }

    @Override
    public Optional<QuestStatus> findByUserId(String userId) {
        return Optional.ofNullable(assignments.get(userId));
    }

    @Override
    public void deleteByUserId(String userId) {
        assignments.remove(userId);
    }
}
