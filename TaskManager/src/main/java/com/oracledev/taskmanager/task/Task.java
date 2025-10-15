package com.oracledev.taskmanager.task;

import java.util.ArrayList;
import java.util.List;

public class Task {

    private String taskName;
    private String taskDescription;
    private Difficulty difficulty;
    private boolean isComplete;
    private final List<String> notes;

    public Task (String name, String desc, String difficulty) {
        this.taskName = name;
        this.taskDescription = desc;
        this.difficulty = Difficulty.valueOf(difficulty.toUpperCase());
        this.isComplete = false;
        this.notes = new ArrayList<>();
    }

    public void printTask(boolean showNotes) {
        System.out.println("\nTask name: " + taskName);
        System.out.println("  Description: " + taskDescription);
        System.out.println("  Difficulty: " + difficulty);
        System.out.println("  Completed: " + isComplete);
        if (showNotes) {
            listNotes();
        }
    }



    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }

    private enum Difficulty {
        EASY,
        MEDIUM,
        HARD
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = Difficulty.valueOf(difficulty.toUpperCase());
    }

    public void setComplete(boolean complete) {
        isComplete = complete;
    }

    public void addNote(String note) {
        notes.add(note);
    }

    public void listNotes() {
        System.out.println("==== Notes ====");
        for (String note : notes) {
            System.out.println(" * " + note);
        }
    }
}
