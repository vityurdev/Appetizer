package com.example.vitalyyurenya.appetizer.api.responses;

public class AuthResponse {
    private String auth;
    private String token;

    public AuthResponse(String auth, String token) {
        this.auth = auth;
        this.token = token;
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
}
