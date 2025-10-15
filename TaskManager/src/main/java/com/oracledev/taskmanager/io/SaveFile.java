package com.oracledev.taskmanager.io;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.oracledev.taskmanager.user.User;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

public record SaveFile(String filePath) {

    public void save(Map<String, User> userlist) {
        try (FileWriter fileWriter = new FileWriter(filePath)) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(userlist, fileWriter);
            System.out.println("File saved successfully to " + filePath);
        } catch (IOException e) {
            System.out.println("Unable to write to the file at " + filePath);
        }
    }

    public static void createFile(String filePath) {

        File file = new File(filePath);
        try {
            file.getParentFile().mkdirs();
            file.createNewFile();
            System.out.println("File successfully created!");
        } catch (IOException e) {
            System.out.println("Unable to create file at " + filePath);
        }
    }
}
