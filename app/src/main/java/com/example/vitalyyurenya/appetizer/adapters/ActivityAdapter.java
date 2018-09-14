package com.example.vitalyyurenya.appetizer.adapters;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.example.vitalyyurenya.appetizer.R;
import com.example.vitalyyurenya.appetizer.models.ActivityItem;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ActivityAdapter extends RecyclerView.Adapter<ActivityAdapter.ActivityHolder> {
    private Context context;
    private List<ActivityItem> activityItemList;

    public ActivityAdapter(Context context, List<ActivityItem> activityItemList) {
        this.context = context;
        this.activityItemList = activityItemList;
    }

    public class ActivityHolder extends RecyclerView.ViewHolder {
        CircleImageView circleImageView;
        TextView eventTextView;
        TextView timeAgoTextView;

        public ActivityHolder(View itemView) {
            super(itemView);

            circleImageView = itemView.findViewById(R.id.fragment_activity_item_image_view);
            eventTextView = itemView.findViewById(R.id.fragment_activity_item_event_text_view);
            timeAgoTextView = itemView.findViewById(R.id.fragment_activity_item_time_ago_text_view);
        }
    }

    @NonNull
    @Override
    public ActivityHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.fragment_activity_item, null);
        return new ActivityHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ActivityHolder holder, int position) {
        ActivityItem activityItem = activityItemList.get(position);

        holder.circleImageView.setImageDrawable(ContextCompat.getDrawable(context, activityItem.getDrawableId()));
        holder.eventTextView.setText(activityItem.getEvent());
        holder.timeAgoTextView.setText(activityItem.getTimeAgo());
    }

    @Override
    public int getItemCount() {
        return activityItemList.size();
    }


}
