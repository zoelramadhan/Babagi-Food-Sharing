package com.example.babagi.network;

import com.example.babagi.models.Food;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

public interface ApiService {
    @GET("/")
    Call<List<Food>> getFoods(
            @Header("X-RapidAPI-Key") String apiKey,
            @Header("X-RapidAPI-Host") String apiHost
    );

    @GET("/{id}")
    Call<Food> getFoodById(
            @Path("id") int foodId,
            @Header("X-RapidAPI-Key") String apiKey,
            @Header("X-RapidAPI-Host") String apiHost
    );
}
