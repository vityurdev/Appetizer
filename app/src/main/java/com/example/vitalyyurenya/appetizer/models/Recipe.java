package com.example.vitalyyurenya.appetizer.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Recipe {
    @SerializedName("id")
    private String id;

    @SerializedName("title")
    private String title;

    @SerializedName("authorId")
    private String authorId;

    @SerializedName("authorUsername")
    private String authorUsername;

    @SerializedName("ingredients")
    private ArrayList<String> ingredients;

    @SerializedName("likes")
    private ArrayList<String> likes;

    @SerializedName("comments")
    private ArrayList<String> comments;

    @SerializedName("recipePhotoUrl")
    private String recipePhotoUrl;

    @SerializedName("youtubeLink")
    private String youtubeLink;

    @SerializedName("createdAt")
    private String createdAt;

    @SerializedName("lastEditedAt")
    private String lastEditedAt;

    @SerializedName("directions")
    private ArrayList<String> directions;


    public Recipe(String id, String title, String authorId, String authorUsername, ArrayList<String> ingredients, ArrayList<String> likes, ArrayList<String> comments, String recipePhotoUrl, String youtubeLink, String createdAt, String lastEditedAt, ArrayList<String> directions) {
        this.id = id;
        this.title = title;
        this.authorId = authorId;
        this.authorUsername = authorUsername;
        this.ingredients = ingredients;
        this.likes = likes;
        this.comments = comments;
        this.recipePhotoUrl = recipePhotoUrl;
        this.youtubeLink = youtubeLink;
        this.createdAt = createdAt;
        this.lastEditedAt = lastEditedAt;
        this.directions = directions;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthorId() {
        return authorId;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }

    public String getAuthorUsername() {
        return authorUsername;
    }

    public void setAuthorUsername(String authorUsername) {
        this.authorUsername = authorUsername;
    }

    public ArrayList<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(ArrayList<String> ingredients) {
        this.ingredients = ingredients;
    }

    public ArrayList<String> getLikes() {
        return likes;
    }

    public void setLikes(ArrayList<String> likes) {
        this.likes = likes;
    }

    public ArrayList<String> getComments() {
        return comments;
    }

    public void setComments(ArrayList<String> comments) {
        this.comments = comments;
    }

    public String getRecipePhotoUrl() {
        return recipePhotoUrl;
    }

    public void setRecipePhotoUrl(String recipePhotoUrl) {
        this.recipePhotoUrl = recipePhotoUrl;
    }

    public String getYoutubeLink() {
        return youtubeLink;
    }

    public void setYoutubeLink(String youtubeLink) {
        this.youtubeLink = youtubeLink;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getLastEditedAt() {
        return lastEditedAt;
    }

    public void setLastEditedAt(String lastEditedAt) {
        this.lastEditedAt = lastEditedAt;
    }

    public ArrayList<String> getDirections() {
        return directions;
    }

    public void setDirections(ArrayList<String> directions) {
        this.directions = directions;
    }
}
