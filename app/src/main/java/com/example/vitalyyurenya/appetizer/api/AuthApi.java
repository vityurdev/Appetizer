package com.example.vitalyyurenya.appetizer.api;

import com.example.vitalyyurenya.appetizer.models.User;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;

public interface AuthApi {
    String BASE_URL = "http://192.168.56.1:8081";
    // String BASE_URL = "http://localhost:8081";

    @POST("api/auth/register")
    Call<AuthResponse> register(@Body User user);

    @POST("api/auth/login")
    Call<AuthResponse> login(@Body User user);
}
