package com.example.babagi.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.babagi.R;
import com.example.babagi.config.DbConfig;
import com.example.babagi.models.Food;
import com.example.babagi.network.ApiService;

public class FoodDetailFragment extends Fragment {
    private String apiKey = "";
    private String apiHost = "";
    private int foodId;
    private View foodDetailloadingView;
    private LinearLayout linearLayoutFoodDetailMainView;
    private ImageView ivFoodImage;
    private TextView tvFoodTitle;
    private TextView tvFoodTime;
    private TextView tvFoodPlace;
    private TextView tvFoodIngredients;
    private Food food;
    private ApiService service;
    private ImageView ivFavoriteButton;
    private boolean isSave = false;
    private DbConfig dbconfig;
    private SharedPreferences preferences;

    public FoodDetailFragment(int recipeId) {
        this.foodId = recipeId;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_food_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        foodDetailLoadingView = view.findViewById(R.id.food_detail_loading_view);
        linearLayoutFoodDetailMainView = view.findViewById(R.id.recipe_detail_main_view);

    }
}