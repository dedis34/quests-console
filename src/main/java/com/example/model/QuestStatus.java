package com.example.model;

import lombok.Data;

@Data
public class QuestStatus {
    private String questId;
    private int progress;
    private int target;
    private boolean completed;
}