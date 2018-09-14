package com.example.vitalyyurenya.appetizer.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.vitalyyurenya.appetizer.R;
import com.example.vitalyyurenya.appetizer.adapters.ActivityAdapter;
import com.example.vitalyyurenya.appetizer.models.ActivityItem;

import java.util.ArrayList;

public class ActivityFragment extends Fragment {
    RecyclerView activityRecyclerView;
    ActivityAdapter activityAdapter;
    ArrayList<ActivityItem> activityItemList;

    public static ActivityFragment newInstance() {

        Bundle args = new Bundle();

        ActivityFragment fragment = new ActivityFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activityItemList = new ArrayList<>();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_activity, container, false);

        activityRecyclerView = view.findViewById(R.id.fragment_activity_recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        activityRecyclerView.setLayoutManager(linearLayoutManager);





        activityAdapter = new ActivityAdapter(getContext(), activityItemList);
        activityRecyclerView.setAdapter(activityAdapter);

        return view;
    }
}
