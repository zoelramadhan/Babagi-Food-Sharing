package com.example.babagi.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
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
import com.example.babagi.network.RetrofitClient;
import com.squareup.picasso.Picasso;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FoodDetailFragment extends Fragment {
    private String apiKey = "3a5e778230mshaa0d62d3e713967p101f9ejsne048ac4cfc0f";
    private String apiHost = "the-vegan-recipes-db.p.rapidapi.com";
    private int foodId;
    private View foodDetailLoadingView;
    private LinearLayout linearLayoutFoodDetailMainView;
    private ImageView ivFoodImage;
    private TextView tvFoodTitle;
    private TextView tvFoodDescription;
    private TextView tvFoodIngredients;
    private Food food;
    private ApiService service;
    private ImageView ivSaveButton;
    private boolean isSave = false;
    private DbConfig dbconfig;
    private SharedPreferences preferences;

    public FoodDetailFragment(int foodId) {
        this.foodId = foodId;
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
        linearLayoutFoodDetailMainView = view.findViewById(R.id.food_detail_main_view);
        ivFoodImage = view.findViewById(R.id.iv_food_image);
        tvFoodTitle = view.findViewById(R.id.tv_food_title);
        tvFoodDescription = view.findViewById(R.id.tv_food_description);
        tvFoodIngredients = view.findViewById(R.id.tv_food_ingredients);
        ivSaveButton = view.findViewById(R.id.iv_save_button);

        service = RetrofitClient.getClient().create(ApiService.class);

        Handler handler = new Handler((Looper.getMainLooper()));
        ExecutorService executor = Executors.newSingleThreadExecutor();

        foodDetailLoadingView.setVisibility(View.VISIBLE);
        linearLayoutFoodDetailMainView.setVisibility(View.GONE);
        executor.execute(new Runnable() {
            @Override
            public void run() {
                Call<Food> call = service.getFoodById(foodId, apiKey, apiHost);
                call.enqueue(new Callback<Food>() {
                    @Override
                    public void onResponse(Call<Food> call, @NonNull Response<Food> response) {
                        if(response.isSuccessful()) {
                            food = response.body();

                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    foodDetailLoadingView.setVisibility(View.GONE);
                                    linearLayoutFoodDetailMainView.setVisibility(View.VISIBLE);

                                    Picasso.get().load(food.getImage()).into(ivFoodImage);
                                    tvFoodTitle.setText(food.getTitle());
                                    tvFoodDescription.setText(food.getDescription());

                                    StringBuilder ingredients = new StringBuilder();
                                    for (String ingredient : food.getIngredients()) {
                                        ingredients.append("\u2022").append(ingredient).append("\n");
                                    }
                                    tvFoodIngredients.setText(ingredients.toString().trim());
                                }
                            });
                        }
                    }

                    @Override
                    public void onFailure(Call<Food> call, Throwable t) {
                        Log.e("FoodDetailFragment", "onFailure: " + t.getMessage());
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                foodDetailLoadingView.setVisibility(View.GONE);
                                getParentFragmentManager()
                                        .beginTransaction()
                                        .replace(R.id.fragment_container, new ErrorConnectionFragment())
                                        .commit();
                            }
                        });
                    }
                });

                dbconfig = new DbConfig(requireActivity());
                preferences = requireActivity().getSharedPreferences("user_pref", Context.MODE_PRIVATE);
                int userId = preferences.getInt("user_id", 0);
                isSave = dbconfig.isSave(userId,foodId);

                updateSaveIcon();

                ivSaveButton.setOnClickListener(v -> {
                    if (isSave) {
                        dbconfig.deleteSave(userId, foodId);
                    } else {
                        dbconfig.insertSave(userId, foodId);
                    }
                    isSave = !isSave;
                    updateSaveIcon();
                });
            }
        });
    }
    private void updateSaveIcon() {
        if (isSave) {
            ivSaveButton.setImageResource(R.drawable.save);
        } else {
            ivSaveButton.setImageResource(R.drawable.unsave);
        }
    }
}