package com.oracledev.taskmanager;

import com.oracledev.taskmanager.io.LoadFile;
import com.oracledev.taskmanager.io.SaveFile;
import com.oracledev.taskmanager.task.Task;
import com.oracledev.taskmanager.user.User;

import java.util.List;
import java.util.Scanner;

public class TaskManager {

    static Scanner scanner = new Scanner(System.in);
    private static final String homePath = System.getProperty("user.home");
    private static final String filePath = "/.taskmanager/people-database.json";
    static final String fullPath = homePath + filePath;

    public static void main(String[] args) {
        TaskManager taskManager = new TaskManager();
        SaveFile saveFile = new SaveFile(fullPath);
        LoadFile loadedFile;
        User user;

        try {
            loadedFile = new LoadFile(fullPath);
        } catch (NullPointerException e) {
            System.out.println("Creating the first user!");
            loadedFile = new LoadFile();
            loadedFile.putUser(taskManager.createUser());
        }

        user = taskManager.loginUser(loadedFile);
        System.out.println("Logged in successfully!");

        try {
            taskManager.menuHandler(user);
        } catch (InterruptedException e) {
            System.out.println("Interrupted while on menu... Closing");
            System.exit(1);
        }

        saveFile.save(loadedFile.getUserList());
        System.out.println("Closing program. Good bye!");
        scanner.close();
    }

    public User createUser() {
        String name;

        System.out.println("\nWelcome new user!");
        while (true) {
            System.out.println("Enter a name:");
            System.out.print("> ");
            name = scanner.nextLine();
            try {
                System.out.println("Confirm name (Y/n): " + name);
                System.out.print("> ");
                String choice = scanner.nextLine();
                if (choice.equalsIgnoreCase("y")) {
                    break;
                } else {
                    throw new Exception();
                }
            } catch (Exception e) {
                System.out.println("Please try again.\n\n");
            }
        }
        System.out.println("User created!");
        return new User(name);
    }

    public User createUser(LoadFile loadFile) {
        String name;

        System.out.println("\nWelcome new user!");
        while (true) {
            System.out.println("Enter a name:");
            System.out.print("> ");
            name = scanner.nextLine();
            try {
                System.out.println("Confirm name (Y/n): " + name);
                System.out.print("> ");
                String choice = scanner.nextLine();
                if (loadFile.checkTakenUsername(name)) {
                    System.out.println("This username is taken, choose again!");
                } else if (choice.equalsIgnoreCase("y")) {
                    break;
                } else {
                    throw new Exception();
                }
            } catch (Exception e) {
                System.out.println("Please try again.\n\n");
            }
        }
        System.out.println("User created!");
        return new User(name);
    }

    public User loginUser(LoadFile loadFile) {
        while (true) {
            System.out.println("\nEnter a username to login:");
            System.out.print("> ");
            String username = scanner.nextLine();
            try {
                User user = loadFile.getUser(username);
                if (user != null) {
                    return user;
                } else { throw new Exception(); }
            } catch (Exception e) {
                try {
                    System.out.println("\nWould you like to create a new user (Y/n)?");
                    System.out.print("> ");
                    String input = scanner.nextLine().toLowerCase();
                    if (input.equals("y")) {
                        loadFile.putUser(createUser(loadFile));
                        System.out.println("Users loaded: " + loadFile.getUserList().keySet());
                    } else if (input.equals("n")) {
                        System.out.println("Goodbye!");
                        System.exit(0);
                    } else {
                        throw new Exception();
                    }
                } catch (Exception ignored) {}
            }
        }
    }

    public void menuHandler(User user) throws InterruptedException {
        List<String> validChoices = List.of(
                "1", "show", "show tasks",
                "2", "create", "create task",
                "3", "modify", "modify task",
                "4", "complete", "complete task",
                "5", "delete", "delete task",
                "6", "add", "add note",
                "7", "exit"
        );
        boolean isRunning = true;

        System.out.print("Loading");
        for (int i = 0; i < 3; i++) {
            System.out.print(".");
            Thread.sleep(500);
            if (i == 2) {
                System.out.println();
            }
        }
        showMainMenu(user);

        // User makes choice on what to do.
        while (isRunning) {
            try {
                System.out.print("> ");
                String userInput = scanner.nextLine().toLowerCase();
                if (validChoices.contains(userInput)) {
                    switch (userInput) {
                        case "1", "show", "show tasks" -> showTasks(user);
                        case "2", "create", "create task" -> {
                            createTask(user);
                            showMainMenu(user);
                        }
                        case "3", "modify", "modify task" -> modifyTask(user);
                        case "4", "complete", "complete task" -> completeTask(user);
                        case "5", "delete", "delete task" -> deleteTask(user);
                        case "6", "add", "add note" -> addNoteToTask(user);
                        case "7", "exit" -> isRunning = false;
                    }
                } else { throw new Exception(); }
            } catch (Exception e) {
                System.out.println("Invalid input");
                Thread.sleep(2000);
                showMainMenu(user);
            }
        }
    }

    private void showMainMenu(User user) {
        user.updateNumTasks();
        System.out.println();
        System.out.flush();
        System.out.println("Welcome " + user.getUserName() + "!");
        System.out.println("Number of tasks loaded: " + user.getNumTasks());
        System.out.println("""
                \n1. Show tasks
                2. Create task
                3. Modify task
                4. Complete task
                5. Delete task
                6. Add note
                7. Exit
                """);
    }

    // Methods for menu items
    private void showTasks(User user) throws InterruptedException { user.listTasks(scanner); }
    private void createTask(User user) {
        String taskName = enterName();
        String taskDesc = enterDescription();
        String difficulty = enterDifficulty();
        user.addTask(taskName, taskDesc, difficulty);
    }
    private void modifyTask(User user) {
        List<String> valid = List.of("name", "1", "description", "desc", "2", "difficulty", "diff", "3", "exit", "4");
        while (true) {
            String input;
            Task task = getDesiredTask(user);
            if (task == null) {
                return;
            }

            // Modify the task
            System.out.println("""
                    What field would you like to modify?
                      Options:
                      1. Name
                      2. Description
                      3. Difficulty
                      4. Exit
                    """);
            System.out.print("> ");
            try {
                input = scanner.nextLine();
                if (valid.contains(input)) {
                    switch (input) {
                        case "name", "1" -> {
                            task.setTaskName(enterName());
                            System.out.println("Task name edited successfully!");
                        }
                        case "description", "desc", "2" -> {
                            task.setTaskDescription(enterDescription());
                            System.out.println("Task description edited successfully!");
                        }
                        case "difficulty", "diff", "3" -> {
                            task.setDifficulty(enterDifficulty());
                            System.out.println("Task difficulty edited successfully!");
                        }
                        case "exit", "4" -> { return; }
                    }
                } else { throw new Exception(); }
            } catch (Exception e) {
                System.out.println("Invalid input, please choose again.");
            }
        }

    }
    private void completeTask(User user) {
        Task task = getDesiredTask(user);
        if (task == null) {
            return;
        }
        task.setComplete(true);
    }
    private void deleteTask(User user) {
        Task task = getDesiredTask(user);
        if (task == null) {
            return;
        }
        user.deleteTask(scanner, task);
    }
    private void addNoteToTask(User user) {
        Task task = getDesiredTask(user);
        if (task == null) {
            return;
        }
        System.out.println("What would you like to note to the task?");
        System.out.print("> ");
        task.addNote(scanner.nextLine());
        System.out.println("Note added successfully!\n");
    }

    // User pulls desired task
    private Task getDesiredTask(User user) {
        String input;
        Task task;
        try {
            System.out.println("Input task name you would like to modify (or type 'exit' to exit):");
            System.out.print("> ");
            input = scanner.nextLine();
            if (input.equals("exit")) {
                return null;
            }
            task = user.getTask(input);
            if (task == null) {
                throw new Exception();
            }
        } catch (Exception e) {
            System.out.println("There is no task by that name.");
            return null;
        }
        return task;
    }

    // Methods for gathering field variables for task.
    private String enterName() {
        System.out.println("Enter task name:");
        System.out.print("> ");
        return scanner.nextLine();
    }
    private String enterDescription() {
        System.out.println("Enter task description:");
        System.out.print("> ");
        return scanner.nextLine();
    }
    private String enterDifficulty() {
        String difficulty;
        while (true) {
            try {
                List<String> valid = List.of("easy", "medium", "hard");
                System.out.println("Enter difficulty (easy, medium, hard):");
                System.out.print("> ");
                difficulty = scanner.nextLine();
                if (!valid.contains(difficulty)) {
                    throw new Exception();
                } else { break; }
            } catch (Exception e) {
                System.out.println("Please enter 'easy', 'medium' or 'hard'\n");
            }
        }
        return difficulty;
    }

}

