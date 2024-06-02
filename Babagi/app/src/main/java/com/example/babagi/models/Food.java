package com.example.babagi.models;

import java.util.List;

public class Food {
    private int id;
    private String title;
    private String description;
    private List<String> ingredients;
    private String image;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Food(int id, String title, String description, List<String> ingredients, String image){
        this.id = id;
        this.title = title;
        this.description = description;
        this.ingredients = ingredients;
        this.image = image;
    }
}
