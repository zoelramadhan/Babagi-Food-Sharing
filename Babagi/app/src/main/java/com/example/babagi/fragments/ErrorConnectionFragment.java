package com.example.babagi.fragments;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.babagi.R;
import com.example.babagi.models.Food;
import com.example.babagi.network.ApiService;
import com.example.babagi.network.RetrofitClient;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ErrorConnectionFragment extends Fragment {
    private String apiKey = "3a5e778230mshaa0d62d3e713967p101f9ejsne048ac4cfc0f";
    private String apiHost = "the-vegan-recipes-db.p.rapidapi.com";
    private View erorConnectionLoadingView;
    private Button btnRetry;
    private ApiService service;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_error_connection, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        erorConnectionLoadingView = view.findViewById(R.id.eror_connection_loading_view);
        btnRetry = view.findViewById(R.id.btn_retry);

        Handler handler = new Handler(Looper.getMainLooper());
        ExecutorService executor = Executors.newSingleThreadExecutor();

        service = RetrofitClient.getClient().create(ApiService.class);

        btnRetry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                erorConnectionLoadingView.setVisibility(View.VISIBLE);

                executor.execute(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(500);

                            Call<List<Food>> call = service.getFoods(apiKey, apiHost);
                            call.enqueue(new Callback<List<Food>>() {
                                @Override
                                public void onResponse(@NonNull Call<List<Food>> call, @NonNull Response<List<Food>> response) {
                                    if (response.isSuccessful()) {
                                        getParentFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
                                    }
                                }

                                @Override
                                public void onFailure(@NonNull Call<List<Food>> call, @NonNull Throwable t) {
                                    erorConnectionLoadingView.setVisibility(View.GONE);
                                }
                            });
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                });
            }
        });
    }
}