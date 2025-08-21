# Quests Console Application (Java 17)

This is a console-based application called **Quests**, implemented in Java 17 using a clean architecture with models, services, and in-memory repositories.

## Overview

The application simulates a simple quest system where users can have **one active quest at a time**.  
There are two types of quests:

- **Collect Items** (e.g., collect 5 diamonds)
- **Kill Mobs** (e.g., kill 3 zombies)

Quests are loaded from a YAML configuration file (`quests.yaml`), and each quest has its parameters defined.

## Architecture

The project is structured as follows:

- `com.example.model` – Data classes (`User`, `QuestStatus`, `ProgressUpdate`, `QuestDefinition`)
- `com.example.quest` – Quest interface and implementations (`CollectItemsQuest`, `KillMobsQuest`)
- `com.example.repository` – Interfaces for repositories (`UserRepository`, `AssignmentRepository`, `QuestCatalogRepository`)
- `com.example.repository.impl` – In-memory implementations of repositories
- `com.example.service` – Service interface (`QuestService`)
- `com.example.service.impl` – Service implementation (`QuestServiceImpl`)
- `com.example.config` – Configuration, including `QuestFactory` for loading quests from YAML
- `com.example.Main` – Console entry point

## YAML Configuration Example

```yaml
quests:
  - id: "q1"
    type: "collectItems"
    description: "Collect 5 diamonds"
    params:
      item: "diamond"
      target: 5
  - id: "q2"
    type: "killMobs"
    description: "Kill 3 zombies"
    params:
      mob: "zombie"
      target: 3
```

## Features

- **Assign a quest to a user** – Users can only have one active quest at a time.
- **Update progress** – Increment quest progress based on actions (`collect` or `kill`).
- **Show status** – Display the current quest, progress, and completion status.
- **Complete a quest** – Mark a quest as completed when progress reaches the target.

## How to Run

1. Make sure you have **Java 17** installed.
2. Clone the repository and open it in IntelliJ IDEA.
3. Build the project using Maven.
4. Place `quests.yaml` in the `resources` folder.
5. Run `com.example.Main`.

## Example Output

**Before completing quests:**

```text
Alice quest: q1
Progress: 5/5
Completed: true
Bob quest: q2
Progress: 3/3
Completed: true
```

**After completing quests:**

```text
Alice quest: q1
Progress: 5/5
Completed: true
Bob quest: q2
Progress: 3/3
Completed: true
```

## Notes

- The application uses **in-memory repositories**, so data is not persisted between runs.
- Quest logic is **extensible**: you can add new quest types without changing the existing code.
- **Lombok** is used for generating getters, setters, and constructors to reduce boilerplate.

## Dependencies

- [SnakeYAML](https://bitbucket.org/asomov/snakeyaml) – for YAML parsing
- [Lombok](https://projectlombok.org/) – for automatic getter/setter/constructor generation