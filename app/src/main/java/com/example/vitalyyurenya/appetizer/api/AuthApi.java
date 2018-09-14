package com.example.vitalyyurenya.appetizer.api;

import com.example.vitalyyurenya.appetizer.api.responses.AuthResponse;
import com.example.vitalyyurenya.appetizer.models.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AuthApi {

    // String BASE_URL = "http://192.168.56.1:8081"; // for emulator
    // String BASE_URL = "http://localhost:8081"; // for real USB-connected device

    String BASE_URL = "https://73fad399.ngrok.io"; // ngrok (provide your own link)

    @POST("api/auth/register")
    Call<AuthResponse> register(@Body User user);

    @POST("api/auth/login")
    Call<AuthResponse> login(@Body User user);
}
