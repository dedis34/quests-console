package com.example.service.impl;

import com.example.exception.QuestAssignmentException;
import com.example.model.ProgressUpdate;
import com.example.model.QuestStatus;
import com.example.quest.Quest;
import com.example.repository.AssignmentRepository;
import com.example.repository.QuestCatalogRepository;
import com.example.repository.UserRepository;
import com.example.service.QuestService;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class QuestServiceImpl implements QuestService {

    private final UserRepository userRepo;
    private final QuestCatalogRepository catalogRepo;
    private final AssignmentRepository assignmentRepo;

    @Override
    public boolean assignQuestToUser(String userId, Quest quest) {
        if (userRepo.findById(userId).isEmpty()) {
            throw new QuestAssignmentException("User with id '" + userId + "' does not exist");
        }

        if (catalogRepo.findById(quest.getId()).isEmpty()) {
            throw new QuestAssignmentException("Quest '" + quest.getId()
                    + "' does not exist in catalog");
        }

        Optional<QuestStatus> existing = assignmentRepo.findByUserId(userId);
        if (existing.isPresent() && !existing.get().isCompleted()) {
            return false;
        }

        QuestStatus status = new QuestStatus();
        status.setQuestId(quest.getId());
        status.setProgress(0);
        status.setCompleted(false);

        status.setTarget(quest.getTarget());

        assignmentRepo.save(userId, status);
        return true;
    }

    @Override
    public void updateUserProgress(String userId, ProgressUpdate update, Quest quest) {
        assignmentRepo.findByUserId(userId).ifPresent(status -> {
            quest.updateProgress(status, update);
            assignmentRepo.save(userId, status);
        });
    }

    @Override
    public Optional<QuestStatus> getUserQuestStatus(String userId) {
        return assignmentRepo.findByUserId(userId);
    }

    @Override
    public void completeUserQuest(String userId, Quest quest) {
        assignmentRepo.findByUserId(userId).ifPresent(status -> {
            if (quest.getId().equals(status.getQuestId())) {
                quest.complete(status);
                assignmentRepo.save(userId, status);
            }
        });
    }

}
