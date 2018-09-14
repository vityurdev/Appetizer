package com.example.vitalyyurenya.appetizer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity {
    ProgressBar progressBar;
    RelativeLayout relativeLayout;
    CircleImageView profilePhotoImageView;
    TextView nameAndSurnameTextView;
    TextView usernameTextView;
    TextView bioTextView;
    TextView likeCounterTextView;
    TextView recipeCounterTextView;
    TextView followersCounterTextView;
    TextView followingCounterTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        progressBar = findViewById(R.id.activity_profile_progress_bar);
        relativeLayout = findViewById(R.id.activity_profile_relative_layout);
        profilePhotoImageView = findViewById(R.id.activity_profile_circle_image_view);
        nameAndSurnameTextView = findViewById(R.id.activity_profile_name_surname);
        usernameTextView = findViewById(R.id.activity_profile_username);
        bioTextView = findViewById(R.id.activity_profile_bio);
        likeCounterTextView = findViewById(R.id.activity_profile_like_count_text_view);
        recipeCounterTextView = findViewById(R.id.activity_profile_recipe_count_text_view);
        followersCounterTextView = findViewById(R.id.activity_profile_followers_count_text_view);
        followingCounterTextView = findViewById(R.id.activity_profile_following_count_text_view);

        nameAndSurnameTextView.setText("Loading...");
        usernameTextView.setText("Loading...");
        bioTextView.setText("Loading...");
        likeCounterTextView.setText("–");
        recipeCounterTextView.setText("–");
        followersCounterTextView.setText("–");
        followingCounterTextView.setText("–");
    }
}
