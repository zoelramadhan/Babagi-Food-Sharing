package com.example.babagi.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.babagi.R;
import com.example.babagi.adapters.FoodsAdapter;
import com.example.babagi.config.DbConfig;
import com.example.babagi.models.Food;
import com.example.babagi.network.ApiService;
import com.example.babagi.network.RetrofitClient;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SavedFragment extends Fragment {
    private String apiKey = "3a5e778230mshaa0d62d3e713967p101f9ejsne048ac4cfc0f";
    private String apiHost = "the-vegan-recipes-db.p.rapidapi.com";
    private View savesLoadingView;
    private ImageView ivSavesNotFound;
    private RecyclerView rvSavesFoods;
    private FoodsAdapter foodsAdapter;
    private List<Food> foods;
    private ApiService service;
    private DbConfig dbConfig;
    private SharedPreferences preferences;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_saved, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        savesLoadingView = view.findViewById(R.id.saves_loading_view);
        ivSavesNotFound = view.findViewById(R.id.iv_saves_not_found);
        rvSavesFoods = view.findViewById(R.id.rv_saves_foods);
        preferences = getActivity().getSharedPreferences("your_preference_name", Context.MODE_PRIVATE);
        dbConfig = new DbConfig(getActivity());

        service = RetrofitClient.getClient().create(ApiService.class);

        int userId = preferences.getInt("user_id", 0);

        Cursor cursor = dbConfig.getSavesByUserId(userId);
        ArrayList<Integer> savesFoodId = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                int foodId = cursor.getInt(cursor.getColumnIndexOrThrow("food_id"));
                savesFoodId.add(foodId);
            } while (cursor.moveToNext());
        }

        Handler handler = new Handler(Looper.getMainLooper());
        ExecutorService executor = Executors.newSingleThreadExecutor();

        savesLoadingView.setVisibility(View.VISIBLE);
        rvSavesFoods.setVisibility(View.GONE);
        executor.execute(new Runnable() {
            @Override
            public void run() {
                Call<List<Food>> call = service.getFoods(apiKey,apiHost);
                call.enqueue(new Callback<List<Food>>() {
                    @Override
                    public void onResponse(Call<List<Food>> call, Response<List<Food>> response) {
                        if (response.isSuccessful()) {
                            foods = response.body();

                            for (int i = 0; i < foods.size(); i++) {
                                if (!savesFoodId.contains(foods.get(i).getId())) {
                                    foods.remove(i);
                                    i--;
                                }
                            }
                            foodsAdapter = new FoodsAdapter(getParentFragmentManager(), foods, userId);
                            rvSavesFoods.setAdapter(foodsAdapter);

                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    savesLoadingView.setVisibility(View.GONE);
                                    if(foodsAdapter.getItemCount() == 0) {
                                        ivSavesNotFound.setVisibility(View.VISIBLE);
                                        rvSavesFoods.setVisibility(View.GONE);
                                    } else {
                                        ivSavesNotFound.setVisibility(View.GONE);
                                        rvSavesFoods.setVisibility(View.VISIBLE);
                                    }
                                }
                            });
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Food>> call, Throwable t) {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                savesLoadingView.setVisibility(View.GONE);
                                getParentFragmentManager()
                                        .beginTransaction()
                                        .replace(R.id.fragment_container, new ErrorConnectionFragment())
                                        .commit();
                            }
                        });
                    }
                });
            }
        });
    }
}