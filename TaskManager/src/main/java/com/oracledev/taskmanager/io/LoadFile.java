package com.oracledev.taskmanager.io;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.oracledev.taskmanager.user.User;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class LoadFile {

    private final Map<String, User> userList;

    public LoadFile() {
        this.userList = new HashMap<>();
    }

    public LoadFile(String filePath) {
        Type personMap = new TypeToken<Map<String, User>>(){}.getType();
        Map<String, User> loaded = null;
        try (FileReader reader = new FileReader(filePath)) {
            loaded = new Gson().fromJson(reader, personMap);
        } catch (FileNotFoundException e) {
            System.out.println("Database not found at " + filePath + ", creating file...");
            SaveFile.createFile(filePath);
        } catch (Exception e) {
            System.out.println("Error pulling data at" + e.getMessage());
        }
        this.userList = (loaded != null) ? loaded : new HashMap<>();

    }

    public Map<String, User> getUserList() { return userList; }
    public void putUser(User person) {
        System.out.println(person.getUserName());
        userList.put(person.getUserName(), person);
    }
    public boolean checkTakenUsername(String username) {
        if(userList == null) {
            return false;
        } else {
            return userList.containsKey(username);
        }
    }
    public User getUser(String username) {
        User user;
        try {
            if (!checkTakenUsername(username)){
                throw new Exception();
            }
            user = userList.get(username);
            return user;
        } catch (Exception e) {
            System.out.println("This is an unknown username.");
            return null;
        }
    }
}
