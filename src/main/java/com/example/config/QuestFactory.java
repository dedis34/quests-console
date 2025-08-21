package com.example.config;

import com.example.exception.QuestAssignmentException;
import com.example.exception.QuestConfigException;
import com.example.quest.Quest;
import com.example.quest.impl.CollectItemsQuest;
import com.example.quest.impl.KillMobsQuest;
import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QuestFactory {

    private final Map<String, QuestCreator> creators = new HashMap<>();

    public QuestFactory() {
        register("collectItems", (id, description, params) ->
                new CollectItemsQuest(
                        id,
                        description,
                        (String) params.get("item"),
                        toInteger(params.get("target"), id)
                )
        );

        register("killMobs", (id, description, params) ->
                new KillMobsQuest(
                        id,
                        description,
                        (String) params.get("mob"),
                        toInteger(params.get("target"), id)
                )
        );
    }

    public void register(String type, QuestCreator creator) {
        creators.put(type, creator);
    }

    public List<Quest> loadQuests(String yamlFile) {
        Yaml yaml = new Yaml();
        InputStream input = getClass().getClassLoader().getResourceAsStream(yamlFile);

        if (input == null) {
            throw new QuestConfigException("Could not find the YAML file: " + yamlFile);
        }

        Map<String, Object> rawData = yaml.load(input);
        if (rawData == null || !rawData.containsKey("quests")) {
            throw new QuestConfigException("Missing 'quests' section in YAML file: " + yamlFile);
        }

        Object questsObj = rawData.get("quests");
        if (!(questsObj instanceof List<?>)) {
            throw new QuestConfigException("'quests' section must be a list in YAML file: "
                    + yamlFile);
        }

        List<?> questDataListRaw = (List<?>) questsObj;
        List<Quest> quests = new ArrayList<>();

        for (Object questDataObj : questDataListRaw) {
            if (!(questDataObj instanceof Map<?, ?>)) {
                throw new QuestConfigException("Each quest entry must be a map in YAML file: "
                        + yamlFile);
            }

            Map<?, ?> questDataRaw = (Map<?, ?>) questDataObj;
            String id = (String) questDataRaw.get("id");
            String type = (String) questDataRaw.get("type");
            String description = (String) questDataRaw.get("description");

            Object paramsObj = questDataRaw.get("params");
            if (!(paramsObj instanceof Map<?, ?>)) {
                throw new QuestAssignmentException("Invalid 'params' for quest '"
                        + id + "': expected a map.");
            }

            Map<String, Object> params = new HashMap<>();
            for (Map.Entry<?, ?> entry : ((Map<?, ?>) paramsObj).entrySet()) {
                if (!(entry.getKey() instanceof String)) {
                    throw new QuestAssignmentException("Invalid key type in 'params' map for quest '"
                            + id + "'");
                }
                params.put((String) entry.getKey(), entry.getValue());
            }

            if (id == null || type == null || description == null || params.isEmpty()) {
                throw new QuestAssignmentException("Missing required fields for quest '"
                        + id + "'");
            }

            QuestCreator creator = creators.get(type);
            if (creator == null) {
                throw new QuestAssignmentException("Unknown quest type: '" + type
                        + "' for quest '" + id + "'");
            }

            try {
                quests.add(creator.create(id, description, params));
            } catch (ClassCastException e) {
                throw new QuestAssignmentException("Invalid parameter types for quest '"
                        + id + "': " + e.getMessage());
            }
        }

        return quests;
    }

    private static Integer toInteger(Object value, String questId) {
        if (value instanceof Integer) {
            return (Integer) value;
        } else if (value instanceof Number) {
            return ((Number) value).intValue();
        } else {
            throw new QuestAssignmentException("Invalid numeric value for quest '" + questId + "'");
        }
    }

    @FunctionalInterface
    public interface QuestCreator {
        Quest create(String id, String description, Map<String, Object> params);
    }
}
