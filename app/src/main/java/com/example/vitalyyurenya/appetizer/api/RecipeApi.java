package com.example.vitalyyurenya.appetizer.api;

import com.example.vitalyyurenya.appetizer.models.Recipe;

import java.io.File;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.HeaderMap;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface RecipeApi {
    String BASE_URL = "https://09a624d0.ngrok.io"; // ngrok (provide your own link)

    /*
    @Multipart
    @POST("api/recipes")
    Call<Recipe> uploadRecipe(
            @Part MultipartBody.Part image,
            @Part("info")Recipe recipe,
            @HeaderMap Map<String, String> headers
    );*/

    /*
    @POST("api/recipes")
    Call<Recipe> uploadRecipe(@Body Recipe recipe, @HeaderMap Map<String, String> headers);
    */

    @POST("api/recipes")
    @Multipart
    Call<ResponseBody> uploadRecipe(/*@Part("title") RequestBody title,*/
                                    @Part MultipartBody.Part imageFile,
                                    @Part("recipe") Recipe recipe,
                                    @HeaderMap Map<String, String> headers);
}
