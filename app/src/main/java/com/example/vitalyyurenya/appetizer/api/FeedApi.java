package com.example.vitalyyurenya.appetizer.api;

import com.example.vitalyyurenya.appetizer.models.Recipe;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;

public interface FeedApi {

    String BASE_URL = "https://09a624d0.ngrok.io"; // ngrok (provide your own link)

    @GET("api")
    Call<List<Recipe>> getFeed(
            @HeaderMap Map<String, String> headers
    );
}
