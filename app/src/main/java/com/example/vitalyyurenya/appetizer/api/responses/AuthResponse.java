package com.example.vitalyyurenya.appetizer.api.responses;

public class AuthResponse {
    private String auth;
    private String token;
    private String userId;

    public AuthResponse(String auth, String token, String userId) {
        this.auth = auth;
        this.token = token;
        this.userId = userId;
    }

    public String getAuth() {
        return auth;
    }

    public void setAuth(String auth) {
        this.auth = auth;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
