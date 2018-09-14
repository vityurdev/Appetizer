package com.example.vitalyyurenya.appetizer.models;

public class Like {
    private String likerId;
    private String likedPostId;

    public Like(String likerId, String likedPostId) {
        this.likerId = likerId;
        this.likedPostId = likedPostId;
    }

    public String getLikerId() {
        return likerId;
    }

    public void setLikerId(String likerId) {
        this.likerId = likerId;
    }

    public String getLikedPostId() {
        return likedPostId;
    }

    public void setLikedPostId(String likedPostId) {
        this.likedPostId = likedPostId;
    }
}
