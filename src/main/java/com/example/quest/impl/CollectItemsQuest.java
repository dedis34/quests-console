package com.example.quest.impl;

import com.example.model.ProgressUpdate;
import com.example.model.QuestStatus;
import com.example.quest.Quest;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CollectItemsQuest implements Quest {

    private final String id;
    private final String description;
    private final String item;
    private final int target;

    @Override
    public void updateProgress(QuestStatus status, ProgressUpdate update) {
        if ("collect".equalsIgnoreCase(update.getKind()) && item.equals(update.getTargetName())) {
            int newProgress = Math.min(status.getProgress() + update.getAmount(), target);
            status.setProgress(newProgress);
            if (newProgress >= target) {
                status.setCompleted(true);
            }
        }
    }

    @Override
    public boolean isCompleted(QuestStatus status) {
        return status.isCompleted();
    }

    @Override
    public void complete(QuestStatus status) {
        status.setProgress(target);
        status.setCompleted(true);
    }
}
