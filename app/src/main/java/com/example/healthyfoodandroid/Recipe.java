package com.example.healthyfoodandroid;

public class Recipe {
    private String title;
    private String description;
    private int imageResourceId;

    public Recipe(String title, String description, int imageResourceId) {
        this.title = title;
        this.description = description;
        this.imageResourceId = imageResourceId;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getImageResourceId() {
        return imageResourceId;
    }
}
