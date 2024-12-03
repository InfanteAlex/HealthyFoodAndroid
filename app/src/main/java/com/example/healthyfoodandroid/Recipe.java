package com.example.healthyfoodandroid;

public class Recipe {
    private String title;
    private String description;
    private int imageResourceId; // Drawable resource ID
    private String instructions;

    public Recipe(String title, String description, int imageResourceId, String instructions) {
        this.title = title;
        this.description = description;
        this.imageResourceId = imageResourceId;
        this.instructions = instructions;
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

    public String getInstructions() {
        return instructions;
    }

}
