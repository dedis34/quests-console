package com.example.repository;

import com.example.model.QuestStatus;
import java.util.Optional;

public interface AssignmentRepository {
    void save(String userId, QuestStatus status);
    Optional<QuestStatus> findByUserId(String userId);
    void deleteByUserId(String userId);
}
