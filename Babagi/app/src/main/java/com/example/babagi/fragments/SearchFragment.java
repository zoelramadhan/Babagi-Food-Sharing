package com.example.babagi.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.babagi.R;
import com.example.babagi.adapters.SearchFoodsAdapter;
import com.example.babagi.models.Food;
import com.example.babagi.network.ApiService;
import com.example.babagi.network.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchFragment extends Fragment {
    private String apiKey = "3a5e778230mshaa0d62d3e713967p101f9ejsne048ac4cfc0f";
    private String apiHost = "the-vegan-recipes-db.p.rapidapi.com";
    private EditText etSearchFoods;
    private ImageView ivNotFound;
    private View searchLoadingView;
    private RecyclerView rvSearchFoods;
    private List<Food> foodsFull = new ArrayList<>();
    private SearchFoodsAdapter searchFoodsAdapter;
    private ApiService service;
    private boolean isSearch = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        etSearchFoods = view.findViewById(R.id.et_search_foods);
        ivNotFound = view.findViewById(R.id.iv_search_not_found);
        searchLoadingView = view.findViewById(R.id.search_loading_view);
        rvSearchFoods = view.findViewById(R.id.rv_search_foods);

        // Initialize the adapter with an empty list
        searchFoodsAdapter = new SearchFoodsAdapter(getParentFragmentManager(), new ArrayList<>());
        rvSearchFoods.setLayoutManager(new LinearLayoutManager(getContext()));
        rvSearchFoods.setAdapter(searchFoodsAdapter);

        service = RetrofitClient.getClient().create(ApiService.class);  // Ensure the URL is valid

        ErrorConnectionFragment errorConnectionFragment = new ErrorConnectionFragment();
        Handler handler = new Handler(Looper.getMainLooper());

        Call<List<Food>> call = service.getFoods(apiKey, apiHost);
        call.enqueue(new Callback<List<Food>>() {
            @Override
            public void onResponse(Call<List<Food>> call, Response<List<Food>> response) {
                if (response.isSuccessful()) {
                    foodsFull = response.body();
                    searchFoodsAdapter.updateFoods(foodsFull);  // Perbarui adapter dengan data makanan baru
                }
            }

            @Override
            public void onFailure(Call<List<Food>> call, Throwable t) {
                getParentFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, errorConnectionFragment)
                        .commit();
            }
        });

        etSearchFoods.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                searchLoadingView.setVisibility(View.VISIBLE);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        searchFoodsAdapter.searchFood(s.toString());
                        searchLoadingView.setVisibility(View.GONE);
                        isSearch = !s.toString().isEmpty();
                        if (searchFoodsAdapter.getItemCount() == 0 && isSearch) {
                            ivNotFound.setVisibility(View.VISIBLE);
                        } else {
                            ivNotFound.setVisibility(View.GONE);
                        }
                    }
                }, 500);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }
}
