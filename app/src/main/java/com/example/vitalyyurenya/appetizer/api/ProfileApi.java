package com.example.vitalyyurenya.appetizer.api;

import com.example.vitalyyurenya.appetizer.models.User;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;

public interface ProfileApi {

    String BASE_URL = "https://09a624d0.ngrok.io"; // ngrok (provide your own link)

    @GET("api/profile")
    Call<User> getOwnProfile(
            @HeaderMap Map<String, String> headers
    );
}
