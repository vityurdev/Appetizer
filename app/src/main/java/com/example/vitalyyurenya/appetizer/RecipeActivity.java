package com.example.vitalyyurenya.appetizer;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vitalyyurenya.appetizer.adapters.IngredientAdapter;
import com.example.vitalyyurenya.appetizer.api.ProfileApi;
import com.example.vitalyyurenya.appetizer.api.RecipeApi;
import com.example.vitalyyurenya.appetizer.models.Recipe;
import com.example.vitalyyurenya.appetizer.utils.UrlConverter;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.lang.reflect.GenericSignatureFormatError;
import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RecipeActivity extends AppCompatActivity {

    private ImageView recipePhotoImageView;
    private TextView recipeTitleTextView;
    private TextView recipeCreatedByTextView;
    private TextView recipeIngredientsTextView;
    private TextView recipeDirectionsTextView;

    private RecyclerView ingredientsRecyclerView;
    private RecyclerView directionsRecyclerView;

    private ImageView likeButtonImageView;
    private TextView likesCountTextView;

    ArrayList<String> ingredientsList;
    ArrayList<String> directionsList;

    Boolean isLikeClicked;
    Boolean isLikeStateChanged;

    String recipeId;
    String authToken;

    String currentUserId;
    String authorId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        AccountManager accountManager = AccountManager.get(getApplicationContext());
        Account[] account = accountManager.getAccountsByType("com.vityur.appetizer.account");
        authToken = accountManager.peekAuthToken(account[0], "com.vityur.appetizer.account.token");
        currentUserId = accountManager.getUserData(account[0], "USER_ID");

        recipePhotoImageView = findViewById(R.id.image_view_recipe_activity);
        recipeTitleTextView = findViewById(R.id.title_recipe_activity);
        recipeCreatedByTextView = findViewById(R.id.created_by_recipe_activity);
        // recipeIngredientsTextView = findViewById(R.id.ingredients_recipe_activity);
        // recipeDirectionsTextView = findViewById(R.id.directions_recipe_activity);

        ingredientsRecyclerView = findViewById(R.id.recipe_activity_ingredients_recycler_view);
        directionsRecyclerView = findViewById(R.id.recipe_activity_directions_recycler_view);
        likeButtonImageView = findViewById(R.id.recipe_activity_like_image_view_button);
        likesCountTextView = findViewById(R.id.recipe_activity_likes_amount);

        likeButtonImageView.setClickable(false);
        likesCountTextView.setClickable(false);
        recipeCreatedByTextView.setClickable(false);
        isLikeClicked = false;
        isLikeStateChanged = false;

        likeButtonImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isLikeStateChanged = !isLikeStateChanged;
                int likesAmount = Integer.parseInt(likesCountTextView.getText().toString());

                if (isLikeClicked) {
                    likesAmount--;
                    likeButtonImageView.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_action_like_border));
                    isLikeClicked = false;
                } else {
                    likesAmount++;
                    likeButtonImageView.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_action_like));
                    isLikeClicked = true;
                }

                String likesAmountString = Integer.toString(likesAmount);
                likesCountTextView.setText(likesAmountString);
            }
        });

        recipeCreatedByTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RecipeActivity.this, ProfileActivity.class);
                intent.putExtra("user_id", authorId);
                startActivity(intent);
            }
        });




        recipeId = getIntent().getStringExtra("RECIPE_ID");

        ingredientsList = new ArrayList<>();
        directionsList = new ArrayList<>();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        ingredientsRecyclerView.setLayoutManager(linearLayoutManager);
        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(getApplicationContext());
        directionsRecyclerView.setLayoutManager(linearLayoutManager2);


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(RecipeApi.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RecipeApi recipeApi = retrofit.create(RecipeApi.class);

        Call<Recipe> recipeCall = recipeApi.getRecipe(recipeId);

        recipeCall.enqueue(new Callback<Recipe>() {
            @Override
            public void onResponse(Call<Recipe> call, Response<Recipe> response) {
                // Toast.makeText(getApplicationContext(), "Yahoo!", Toast.LENGTH_SHORT).show();

                if (response.body() == null) {
                    Toast.makeText(getApplicationContext(), "body = null", Toast.LENGTH_SHORT).show();
                    return;
                }

                String recipePhotoUrl = response.body().getRecipePhotoUrl();
                String recipeTitle = response.body().getTitle();
                String recipeCreatedBy = response.body().getAuthorUsername();
                ingredientsList =  response.body().getIngredients();
                directionsList = response.body().getDirections();
                int likesAmount = response.body().getLikes().size();
                int commentsAmount = response.body().getComments().size();
                String likesAmountString = Integer.toString(likesAmount);
                authorId = response.body().getAuthorId();

                ArrayList<String> likesArray = response.body().getLikes();

                if (likesArray.contains(currentUserId)) {
                    likeButtonImageView.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_action_like));
                    isLikeClicked = true;
                }

                Picasso.get()
                        .load(UrlConverter.convert(recipePhotoUrl))
                        .into(recipePhotoImageView);

                recipeTitleTextView.setText(recipeTitle);
                recipeCreatedByTextView.setText("Created by: " + recipeCreatedBy);

                IngredientAdapter ingredientAdapter = new IngredientAdapter(ingredientsList, getApplicationContext());
                ingredientsRecyclerView.setAdapter(ingredientAdapter);
                IngredientAdapter directionAdapter = new IngredientAdapter(directionsList, getApplicationContext());
                directionsRecyclerView.setAdapter(directionAdapter);


                likesCountTextView.setText(likesAmountString);

                likeButtonImageView.setClickable(true);
                recipeCreatedByTextView.setClickable(true);
            }

            @Override
            public void onFailure(Call<Recipe> call, Throwable t) {

            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (isLikeStateChanged) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(RecipeApi.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            RecipeApi recipeApi = retrofit.create(RecipeApi.class);
            HashMap<String, String> headers = new HashMap<>();
            headers.put("Content-Type", "application/x-www-form-urlencoded");
            headers.put("x-access-token", authToken);

            if (isLikeClicked) {
                headers.put("action", "add");
            } else {
                headers.put("action", "remove");
            }


            Call<Recipe> recipeCall = recipeApi.patchRecipe(recipeId, headers);

            recipeCall.enqueue(new Callback<Recipe>() {
                @Override
                public void onResponse(Call<Recipe> call, Response<Recipe> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(getApplicationContext(), "Your like/dislike is saved", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Recipe> call, Throwable t) {

                }
            });

        }
    }
}
