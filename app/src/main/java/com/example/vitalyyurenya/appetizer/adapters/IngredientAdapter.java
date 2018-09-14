package com.example.vitalyyurenya.appetizer.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.vitalyyurenya.appetizer.R;

import java.util.ArrayList;

public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.IngredientHolder> {
    ArrayList<String> ingredientsList;
    Context context;



    public IngredientAdapter(ArrayList<String> ingredientsList, Context context) {
        this.ingredientsList = ingredientsList;
        this.context = context;
    }

    class IngredientHolder extends RecyclerView.ViewHolder {
        TextView itemTextView;

        public IngredientHolder(View itemView) {
            super(itemView);

            itemTextView = itemView.findViewById(R.id.ingredient_item_title);
        }
    }

    @NonNull
    @Override
    public IngredientHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.ingredient_item, null);

        return new IngredientHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientHolder holder, int position) {
        String str = ingredientsList.get(position);

        holder.itemTextView.setText(str);

    }

    @Override
    public int getItemCount() {
        return ingredientsList.size();
    }




}
