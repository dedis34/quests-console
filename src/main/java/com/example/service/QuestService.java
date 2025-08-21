package com.example.service;

import com.example.model.ProgressUpdate;
import com.example.model.QuestStatus;
import com.example.quest.Quest;
import java.util.Optional;

public interface QuestService {
    boolean assignQuestToUser(String userId, Quest quest);

    void updateUserProgress(String userId, ProgressUpdate update, Quest quest);

    Optional<QuestStatus> getUserQuestStatus(String userId);

    void completeUserQuest(String userId, Quest quest);
}
