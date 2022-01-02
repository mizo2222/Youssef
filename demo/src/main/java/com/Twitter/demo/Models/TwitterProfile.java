package com.Twitter.demo.Models;

public class TwitterProfile {
    private String id;
    private String description;
    private String createdAt;
    private String username;
    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "TwitterProfile{" +
                "id='" + id + '\'' +
              //", createdAt='" + createdAt + '\'' +
              //  ", username='" + username + '\'' +
               // ", name='" + name + '\'' +
                '}';
    }
}
