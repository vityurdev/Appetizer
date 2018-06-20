package com.example.vitalyyurenya.appetizer.adapters;

import android.app.Activity;
import android.content.Context;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.vitalyyurenya.appetizer.R;
import com.example.vitalyyurenya.appetizer.api.FeedApi;
import com.example.vitalyyurenya.appetizer.interfaces.OnLoadMoreListener;
import com.example.vitalyyurenya.appetizer.models.Recipe;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {

    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;

    private Context context;
    private List<Recipe> recipes;

    private boolean isLoading;
    private Activity activity;
    private int visibleThreshold = 5;
    private int lastVisibleItem, totalItemCount;



    private OnLoadMoreListener onLoadMoreListener;

    private ProgressBar progressBar;

    public RecipeAdapter(Context context, List<Recipe> recipes) {
        this.context = context;
        this.recipes = recipes;


    }

    class RecipeViewHolder extends RecyclerView.ViewHolder {
        private ImageView recipeCardImage;
        private TextView recipeCardAuthor, recipeCardTitle, recipeCardLikeCount, recipeCardCommentCount;

        public RecipeViewHolder(View itemView) {
            super(itemView);

            recipeCardImage = itemView.findViewById(R.id.recipe_card_image_view);
            recipeCardAuthor = itemView.findViewById(R.id.recipe_card_author);
            recipeCardTitle = itemView.findViewById(R.id.recipe_card_title);
            recipeCardLikeCount = itemView.findViewById(R.id.recipe_card_like_count);
            recipeCardCommentCount = itemView.findViewById(R.id.recipe_card_comment_count);
        }
    }

    class LoadingViewHolder extends RecyclerView.ViewHolder {
        public LoadingViewHolder(View itemView) {
            super(itemView);

            progressBar = itemView.findViewById(R.id.fragment_item_loading_progressBar);
        }
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.fragment_recipe_card_view, null);
        return new RecipeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder holder, int position) {
        Recipe recipe = recipes.get(position);

        Picasso.get()
                .load(FeedApi.BASE_URL + "/" + recipe.getRecipePhotoUrl())
                .into(holder.recipeCardImage);

        holder.recipeCardTitle.setText(recipe.getTitle());
        holder.recipeCardAuthor.setText(recipe.getAuthorUsername());
        holder.recipeCardLikeCount.setText(Integer.toString(recipe.getLikes().size()));
        holder.recipeCardCommentCount.setText(Integer.toString(recipe.getComments().size()));

        // holder.textViewTitle.setText(recipe.getTitle());







    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }

    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener;
    }
}

