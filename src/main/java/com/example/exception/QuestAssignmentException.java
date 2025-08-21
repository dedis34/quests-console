package com.example.exception;

public class QuestAssignmentException extends RuntimeException {
    public QuestAssignmentException(String message) {
        super("[ASSIGN ERROR] " + message);
    }
}
