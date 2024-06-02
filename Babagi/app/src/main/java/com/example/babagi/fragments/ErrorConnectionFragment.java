package com.example.babagi.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.babagi.R;
import com.example.babagi.network.ApiService;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ErrorConnectionFragment extends Fragment {
    private String apiKey = "634417f2b5f747548d9b312f01afce9f";
    private String apiHost = "";
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

                            Call<List<Recipe>> call = service.getRecipes(apiKey, apiHost);
                            call.enqueue(new Callback<List<Recipe>>() {
                                @Override
                                public void onResponse(@NonNull Call<List<Recipe>> call, @NonNull Response<List<Recipe>> response) {
                                    if (response.isSuccessful()) {
                                        getParentFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
                                    }
                                }

                                @Override
                                public void onFailure(@NonNull Call<List<Recipe>> call, @NonNull Throwable t) {
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