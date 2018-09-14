package com.example.vitalyyurenya.appetizer.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vitalyyurenya.appetizer.AddRecipeActivity;
import com.example.vitalyyurenya.appetizer.R;
import com.example.vitalyyurenya.appetizer.api.ProfileApi;
import com.example.vitalyyurenya.appetizer.models.User;
import com.example.vitalyyurenya.appetizer.utils.UrlConverter;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProfileFragment extends Fragment {

    private ViewGroup fragmentContainer;
    private String authToken;

    private ImageButton addRecipeImageButton;

    public static ProfileFragment newInstance(String authToken) {

        Bundle args = new Bundle();
        args.putString("AUTH_TOKEN", authToken);

        ProfileFragment fragment = new ProfileFragment();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        fragmentContainer = null;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        final ProgressBar progressBar = view.findViewById(R.id.fragment_profile_progress_bar);
        final RelativeLayout relativeLayout = view.findViewById(R.id.fragment_profile_relative_layout);
        final CircleImageView profilePhotoImageView = view.findViewById(R.id.fragment_profile_circle_image_view);
        final TextView nameAndSurnameTextView = view.findViewById(R.id.fragment_profile_name_surname);
        final TextView usernameTextView = view.findViewById(R.id.fragment_profile_username);
        final TextView bioTextView = view.findViewById(R.id.fragment_profile_bio);
        final TextView likeCounterTextView = view.findViewById(R.id.fragment_profile_like_count_text_view);
        final TextView recipeCounterTextView = view.findViewById(R.id.fragment_profile_recipe_count_text_view);
        final TextView followersCounterTextView = view.findViewById(R.id.fragment_profile_followers_count_text_view);
        final TextView followingCounterTextView = view.findViewById(R.id.fragment_profile_following_count_text_view);

        nameAndSurnameTextView.setText("Loading...");
        usernameTextView.setText("Loading...");
        bioTextView.setText("Loading...");
        likeCounterTextView.setText("–");
        recipeCounterTextView.setText("–");
        followersCounterTextView.setText("–");
        followingCounterTextView.setText("–");

        addRecipeImageButton = view.findViewById(R.id.fragment_profile_add_recipe);
        addRecipeImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AddRecipeActivity.class);
                intent.putExtra("AUTH_TOKEN", authToken);
                startActivity(intent);

            }
        });



        fragmentContainer = container;

        authToken = getArguments().getString("AUTH_TOKEN");

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ProfileApi.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        final ProfileApi profileApi = retrofit.create(ProfileApi.class);

        HashMap<String, String> headers = new HashMap<>();
        headers.put("x-access-token", authToken);
        headers.put("Content-Type", "application/x-www-form-urlencoded");

        Call<User> getUserCall = profileApi.getOwnProfile(headers);

        getUserCall.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.code() == 500) {
                    Toast.makeText(getContext(), "Own Profile Fragment: Server error", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 404) {
                    Toast.makeText(getContext(), "Own Profile Fragment: No user found", Toast.LENGTH_SHORT).show();
                } else if (response.code() != 200) {
                    Toast.makeText(getContext(), "Own Profile Fragment: Some other shit", Toast.LENGTH_SHORT).show();
                } else {


                    String username = response.body().getUsername();
                    String email = response.body().getEmail();
                    String password = response.body().getPassword();
                    String firstName = response.body().getFirstName();
                    String lastName = response.body().getLastName();
                    String bio = response.body().getBio();
                    String profilePhotoUrl = response.body().getProfilePhotoUrl();
                    String likesCount = Integer.toString(response.body().getLikes().size());
                    String postsCount = Integer.toString(response.body().getPosts().size());
                    String followersCount = Integer.toString(response.body().getFollowers().size());
                    String followingCount = Integer.toString(response.body().getFollowing().size());

                    Log.i("lol", "username: " + username);
                    Log.i("lol", "email: " + email);
                    Log.i("lol", "password: " + password);
                    Log.i("lol", "firstName: " + firstName);
                    Log.i("lol", "lastName: " + lastName);
                    Log.i("lol", "bio: " + bio);
                    Log.i("lol", "profilePhotoUrl: " + profilePhotoUrl);
                    Log.i("lol", "likes: " + response.body().getLikes().size());

                    progressBar.setVisibility(View.GONE);
                    relativeLayout.setVisibility(View.VISIBLE);

                    Picasso.get()
                            .load(UrlConverter.convert(profilePhotoUrl))
                            .into(profilePhotoImageView);

                    nameAndSurnameTextView.setText(firstName + " " + lastName);
                    usernameTextView.setText("@" + username);
                    bioTextView.setText(bio);

                    likeCounterTextView.setText(likesCount);
                    recipeCounterTextView.setText(postsCount);
                    followersCounterTextView.setText(followersCount);
                    followingCounterTextView.setText(followingCount);

                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.e("lol", t.getMessage());
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();


    }

    @Override
    public void onDestroyView() {

        super.onDestroyView();

        fragmentContainer.removeAllViews();
    }
}
