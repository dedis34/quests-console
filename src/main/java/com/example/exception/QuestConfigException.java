package com.example.exception;

public class QuestConfigException extends RuntimeException {
    public QuestConfigException(String message) {
        super("[CONFIG ERROR] " + message);
    }
}
