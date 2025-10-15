package com.oracledev.taskmanager.user;

import com.oracledev.taskmanager.task.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class User {

    private final String userName;
    private final List<Task> taskList;
    private int numTasks;

    public User(String name) {
        this.userName = name;
        taskList = new ArrayList<>();
        this.numTasks = 0;
    }

    public String getUserName() { return userName; }
    public void updateNumTasks() {
        numTasks = taskList.size();
    }
    public int getNumTasks() { return numTasks; }

    public void listTasks(Scanner scanner) throws InterruptedException {
        boolean showNotes = false;
        while (true) {
            try {
                System.out.print("Do you want to list notes as well (Y/n)? ");
                String choice = scanner.nextLine().toLowerCase();
                if (choice.equals("y")) {
                    showNotes = true;
                    break;
                } else if (choice.equals("n")) {
                    break;
                } else {
                    throw new Exception();
                }
            } catch (Exception e) {
                System.out.println("Please enter 'y' or 'n'.\n\n");
            }
        }
        if (taskList.isEmpty()) {
            System.out.println("You have no tasks!\n");
            return;
        }
        for (Task task : taskList) {
            task.printTask(showNotes);
            Thread.sleep(500);
        }
    }

    public void addTask(String name, String desc, String difficulty) {
        taskList.add(new Task(name, desc, difficulty));
        System.out.println("Task added successfully!");
    }

    public Task getTask (String name) {
        for (Task task : taskList) {
            if (task.getTaskName().equals(name)) {
                return task;
            }
        }
        System.out.println("There is no task by this name!");
        return null;
    }

    public void deleteTask(Scanner scanner, Task task) {
        boolean confirmDelete = false;

        // Verify if they want to delete first
        System.out.println("\nAre you sure you want to delete this task (Y/n)? (This is permanent!)");
        String input = scanner.nextLine().toLowerCase();
        if (input.equals("y")) {
            confirmDelete = true;
        } else {
            System.out.println("Cancelling task deletion...");
        }

        if (confirmDelete) {
            taskList.remove(task);
        }
    }

}
