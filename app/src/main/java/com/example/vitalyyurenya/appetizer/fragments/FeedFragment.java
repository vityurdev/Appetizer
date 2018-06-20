package com.example.vitalyyurenya.appetizer.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.vitalyyurenya.appetizer.R;
import com.example.vitalyyurenya.appetizer.adapters.RecipeAdapter;
import com.example.vitalyyurenya.appetizer.api.FeedApi;
import com.example.vitalyyurenya.appetizer.models.Recipe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FeedFragment extends Fragment {
    RecyclerView recipeRecyclerView;
    RecipeAdapter recipeAdapter;
    boolean isScrolling = false;

    List<Recipe> recipeList;

    LinearLayoutManager linearLayoutManager;
    SwipeRefreshLayout swipeRefreshLayout;

    int currentItems, totalItems, scrollOutItems;

    String authToken;

    ProgressBar progressBar;


    public static FeedFragment newInstance(String authToken) {
        
        Bundle args = new Bundle();
        args.putString("AUTH_TOKEN", authToken);
        
        FeedFragment fragment = new FeedFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        recipeList = new ArrayList<>();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_feed, container, false);

        recipeRecyclerView = view.findViewById(R.id.fragment_feed_recycler_view);
        progressBar = view.findViewById(R.id.fragment_feed_progress_bar);
        swipeRefreshLayout = view.findViewById(R.id.fragment_feed_swipe_refresh_layout);
        linearLayoutManager = new LinearLayoutManager(getContext());
        recipeRecyclerView.setLayoutManager(linearLayoutManager);

        recipeRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                    isScrolling = true;
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                currentItems = linearLayoutManager.getChildCount();
                totalItems = linearLayoutManager.getItemCount();
                scrollOutItems = linearLayoutManager.findFirstVisibleItemPosition();

                if (isScrolling && (currentItems + scrollOutItems == totalItems)) {
                    isScrolling = false;
                    fetchMoreRecipes();
                }
            }
        });
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshFeed();


            }
        });

        recipeList = new ArrayList<>();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(FeedApi.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        authToken = getArguments().getString("AUTH_TOKEN");

        FeedApi feedApi = retrofit.create(FeedApi.class);

        HashMap<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/x-www-form-urlencoded");
        headers.put("x-access-token", authToken);

        Call<List<Recipe>> feedCall = feedApi.getFeed(headers);

        feedCall.enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                Log.i("lol", "Yaaaaaaay!");
                Log.i("lol", Integer.toString(response.body().get(0).getComments().size()));

                for (Recipe recipe: response.body()) {
                    recipeList.add(recipe);


                }

                recipeAdapter = new RecipeAdapter(getContext(), recipeList);
                recipeRecyclerView.setAdapter(recipeAdapter);


            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {
                Log.i("lol", "feedfragment: " + t.getClass().toString());
            }
        });

        /*

        recipeList.add(new Recipe(
                "1", "lol", "Hello", null, null, null,null,null,null,null,null)
        );

        recipeList.add(new Recipe(
                "1", "lol", "Hello", null, null, null,null,null,null,null,null)
        );

        recipeList.add(new Recipe(
                "1", "lol", "Hello", null, null, null,null,null,null,null,null)
        );

        recipeList.add(new Recipe(
                "1", "lol", "Hello", null, null, null,null,null,null,null,null)
        );

        recipeList.add(new Recipe(
                "1", "lol", "Hello", null, null, null,null,null,null,null,null)
        );

        recipeList.add(new Recipe(
                "1", "lol", "Hello", null, null, null,null,null,null,null,null)
        );


        recipeList.add(new Recipe(
                "1", "lol", "Hello", null, null, null,null,null,null,null,null)
        );

        recipeList.add(new Recipe(
                "1", "lol", "Hello", null, null, null,null,null,null,null,null)
        );

        recipeList.add(new Recipe(
                "1", "lol", "Hello", null, null, null,null,null,null,null,null)
        );

        recipeList.add(new Recipe(
                "1", "lol", "Hello", null, null, null,null,null,null,null,null)
        );
        */
        /*
        recipeAdapter = new RecipeAdapter(getContext(), recipeList);
        recipeRecyclerView.setAdapter(recipeAdapter);*/

        return view;
    }

    private void refreshFeed() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(FeedApi.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        FeedApi feedApi = retrofit.create(FeedApi.class);

        HashMap<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/x-www-form-urlencoded");
        headers.put("x-access-token", authToken);
        headers.put("newestpostid", recipeList.get(0).getId());
        Call<List<Recipe>> feedCall = feedApi.getFeed(headers);

        feedCall.enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                for (int i = response.body().size()-1; i >= 0; i--) {
                    recipeList.add(0, response.body().get(i));
                }

                recipeAdapter.notifyDataSetChanged();
                swipeRefreshLayout.setRefreshing(false);
                Toast.makeText(getContext(), "Refreshed!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {

            }
        });
    }

    private void fetchMoreRecipes() {
        progressBar.setVisibility(View.VISIBLE);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(FeedApi.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        FeedApi feedApi = retrofit.create(FeedApi.class);

        HashMap<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/x-www-form-urlencoded");
        headers.put("x-access-token", authToken);
        headers.put("qtyofpostsloaded", Integer.toString(totalItems));
        Call<List<Recipe>> feedCall = feedApi.getFeed(headers);

        feedCall.enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                for (Recipe recipe: response.body()) {
                    recipeList.add(recipe);

                    recipeAdapter.notifyDataSetChanged();
                }

                progressBar.setVisibility(View.GONE);


             }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {

            }
        });
    }
}
