package com.example.vitalyyurenya.appetizer.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Recipe {
    @SerializedName("id")
    private String id;

    @SerializedName("title")
    private String title;

    @SerializedName("authorUsername")
    private String authorUsername;

    @SerializedName("ingredients")
    private List<String> ingredients;

    @SerializedName("likes")
    private List<String> likes;

    @SerializedName("comments")
    private List<String> comments;

    @SerializedName("recipePhotoUrl")
    private String recipePhotoUrl;

    @SerializedName("youtubeLink")
    private String youtubeLink;

    @SerializedName("createdAt")
    private String createdAt;

    @SerializedName("lastEditedAt")
    private String lastEditedAt;

    @SerializedName("directions")
    private String directions;


    public Recipe(String id, String title, String authorUsername, List<String> ingredients, List<String> likes, List<String> comments, String recipePhotoUrl, String youtubeLink, String createdAt, String lastEditedAt, String directions) {
        this.id = id;
        this.title = title;
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

    public String getAuthorUsername() {
        return authorUsername;
    }

    public void setAuthorUsername(String authorUsername) {
        this.authorUsername = authorUsername;
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }

    public List<String> getLikes() {
        return likes;
    }

    public void setLikes(List<String> likes) {
        this.likes = likes;
    }

    public List<String> getComments() {
        return comments;
    }

    public void setComments(List<String> comments) {
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

    public String getDirections() {
        return directions;
    }

    public void setDirections(String directions) {
        this.directions = directions;
    }
}
