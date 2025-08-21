package com.example.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.Map;

@Data
@AllArgsConstructor
public class QuestDefinition {
    private String id;
    private QuestType type;
    private String description;
    private Map<String, Object> params;

    public enum QuestType {
        COLLECT_ITEMS,
        KILL_MOBS
    }
}