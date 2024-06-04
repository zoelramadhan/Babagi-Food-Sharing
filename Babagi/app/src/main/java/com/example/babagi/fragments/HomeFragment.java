package com.example.babagi.fragments;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.babagi.R;
import com.example.babagi.adapters.FoodsAdapter;
import com.example.babagi.config.DbConfig;
import com.example.babagi.models.Food;
import com.example.babagi.network.ApiService;
import com.example.babagi.network.RetrofitClient;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {
    private String apiKey = "7655c9212amshf5aec19489a8205p142135jsnc679c7e52ee7";
    private String apiHost = "the-vegan-recipes-db.p.rapidapi.com";
    private View homeLoadingView;
    private TextView tvHomeFullName;
    private RecyclerView rvHomeFoods;
    private FoodsAdapter foodsAdapter;
    private List<Food> foods;
    private ApiService service;
    private SharedPreferences preferences;
    private DbConfig dbConfig;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        homeLoadingView = view.findViewById(R.id.home_loading_view);
        tvHomeFullName = view.findViewById(R.id.tv_home_full_name);
        rvHomeFoods = view.findViewById(R.id.rv_home_foods);

        // Set the layout manager for the RecyclerView
        rvHomeFoods.setLayoutManager(new GridLayoutManager(getContext(), 2));

        service = RetrofitClient.getClient().create(ApiService.class);
        preferences = requireActivity().getSharedPreferences("user_pref", requireActivity().MODE_PRIVATE);
        int userId = preferences.getInt("user_id", 0);

        dbConfig = new DbConfig(requireActivity());
        Cursor cursor = dbConfig.getUserDataById(userId);
        if (cursor.moveToFirst()) {
            do {
                String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
                tvHomeFullName.setText("Halo, " + name + " !");
            } while (cursor.moveToNext());
        }

        Handler handler = new Handler(Looper.getMainLooper());
        ExecutorService executor = Executors.newSingleThreadExecutor();

        ErrorConnectionFragment errorConnectionFragment = new ErrorConnectionFragment();

        homeLoadingView.setVisibility(View.VISIBLE);
        rvHomeFoods.setVisibility(View.GONE);
        executor.execute(new Runnable() {
            @Override
            public void run() {
                Call<List<Food>> call = service.getFoods(apiKey, apiHost);
                call.enqueue(new Callback<List<Food>>() {
                    @Override
                    public void onResponse(Call<List<Food>> call, Response<List<Food>> response) {
                        if (response.isSuccessful()) {
                            foods = response.body();
                            foodsAdapter = new FoodsAdapter(getParentFragmentManager(), foods, userId);
                            rvHomeFoods.setAdapter(foodsAdapter);

                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    homeLoadingView.setVisibility(View.GONE);
                                    rvHomeFoods.setVisibility(View.VISIBLE);
                                }
                            });
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Food>> call, Throwable t) {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                homeLoadingView.setVisibility(View.GONE);
                                getParentFragmentManager()
                                        .beginTransaction()
                                        .replace(R.id.fragment_container, errorConnectionFragment)
                                        .commit();
                            }
                        });
                    }
                });
            }
        });
    }
}
