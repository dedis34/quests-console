package com.example;

import com.example.config.QuestFactory;
import com.example.model.ProgressUpdate;
import com.example.model.QuestDefinition;
import com.example.model.QuestStatus;
import com.example.model.User;
import com.example.quest.Quest;
import com.example.repository.impl.AssignmentRepositoryImpl;
import com.example.repository.impl.QuestCatalogRepositoryImpl;
import com.example.repository.impl.UserRepositoryImpl;
import com.example.service.impl.QuestServiceImpl;

import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

public class Main {
    public static void main(String[] args) {
        UserRepositoryImpl userRepo = new UserRepositoryImpl();
        QuestCatalogRepositoryImpl catalogRepo = new QuestCatalogRepositoryImpl();
        AssignmentRepositoryImpl assignmentRepo = new AssignmentRepositoryImpl();

        QuestServiceImpl questService = new QuestServiceImpl(userRepo, catalogRepo, assignmentRepo);

        QuestFactory factory = new QuestFactory();
        List<Quest> quests = factory.loadQuests("quests.yaml");

        for (Quest q : quests) {
            List<QuestDefinition> defs = new ArrayList<>();
            QuestDefinition def = new QuestDefinition(
                    q.getId(),
                    q instanceof com.example.quest.impl.CollectItemsQuest ?
                            QuestDefinition.QuestType.COLLECT_ITEMS :
                            QuestDefinition.QuestType.KILL_MOBS,
                    q.getDescription(),
                    null
            );
            defs.add(def);
            catalogRepo.saveAll(defs);
        }

        User alice = new User("u1", "Alice", null);
        User bob = new User("u2", "Bob", null);
        userRepo.save(alice);
        userRepo.save(bob);

        questService.assignQuestToUser(alice.getId(), quests.get(0));
        questService.assignQuestToUser(bob.getId(), quests.get(1));

        questService.updateUserProgress(alice.getId(), new ProgressUpdate("collect",
                "diamond", 3), quests.get(0));
        questService.updateUserProgress(alice.getId(), new ProgressUpdate("collect",
                "diamond", 2), quests.get(0));

        questService.updateUserProgress(bob.getId(), new ProgressUpdate("kill",
                "zombie", 1), quests.get(1));
        questService.updateUserProgress(bob.getId(), new ProgressUpdate("kill",
                "zombie", 2), quests.get(1));

        printStatus(questService.getUserQuestStatus(alice.getId()), "Alice");
        printStatus(questService.getUserQuestStatus(bob.getId()), "Bob");

        for (Quest q : quests) {
            questService.completeUserQuest(alice.getId(), q);
            questService.completeUserQuest(bob.getId(), q);
        }

        System.out.println("\nAfter completing quests:");
        printStatus(questService.getUserQuestStatus(alice.getId()), "Alice");
        printStatus(questService.getUserQuestStatus(bob.getId()), "Bob");
    }

    private static void printStatus(Optional<QuestStatus> statusOpt, String userName) {
        if (statusOpt.isPresent()) {
            QuestStatus status = statusOpt.get();
            System.out.println(userName + " quest: " + status.getQuestId());
            System.out.println("Progress: " + status.getProgress() + "/" + status.getTarget());
            System.out.println("Completed: " + status.isCompleted());
        } else {
            System.out.println(userName + " has no active quest.");
        }
    }
}
