package com.umaxcode.spring_boot_essentials_with_crud.model.entity;

import lombok.*;

import java.util.HashMap;
import java.util.Map;


/**
 * Represents a user with basic details such as username, password, and ID.
 * Provides getter and setter methods for each field.
 */

public class User {

    private String username;
    private String password;
    private String id;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    /**
     * Returns a string representation of the User.
     * The string contains the username, password, and ID fields.
     *
     * @return a string representation of the user
     */
    @Override
    public String toString() {
        Map<String, String> data = new HashMap<>();
        data.put("username", username);
        data.put("password", password);
        data.put("id", id);

        return data.toString();
    }
}
