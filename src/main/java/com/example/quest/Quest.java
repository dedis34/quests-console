package com.example.quest;

import com.example.model.ProgressUpdate;
import com.example.model.QuestStatus;

public interface Quest {
    String getId();
    String getDescription();
    int getTarget();
    void updateProgress(QuestStatus status, ProgressUpdate update);
    boolean isCompleted(QuestStatus status);
    void complete(QuestStatus status);
}
