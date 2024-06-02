package com.example.babagi.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.babagi.R;
import com.example.babagi.fragments.FoodDetailFragment;
import com.example.babagi.models.Food;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class SearchFoodsAdapter extends RecyclerView.Adapter<SearchFoodsAdapter.SearchFoodsViewHolder> {

    private FragmentManager fragmentManager;
    private List<Food> foods;
    private List<Food> filteredFoods;

    public SearchFoodsAdapter(FragmentManager fragmentManager, List<Food> foods) {
        this.fragmentManager = fragmentManager;
        this.foods = foods;
        this.filteredFoods = new ArrayList<>(foods); // Salin data ke filteredFoods
    }

    public void searchFood(String query) {
        filteredFoods.clear();
        if (query.isEmpty()) {
            filteredFoods.addAll(foods); // Jika query kosong, tampilkan semua data
        } else {
            for (Food food : foods) {
                if (food.getTitle().toLowerCase().contains(query.toLowerCase())) {
                    filteredFoods.add(food); // Tambahkan makanan yang sesuai dengan query ke filteredFoods
                }
            }
        }
        notifyDataSetChanged(); // Perbarui tampilan RecyclerView
    }

    @NonNull
    @Override
    public SearchFoodsAdapter.SearchFoodsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search_food, parent, false);
        return new SearchFoodsViewHolder(view);
    }

    // Implementasikan method onBindViewHolder untuk menetapkan data pada ViewHolder
    @Override
    public void onBindViewHolder(@NonNull SearchFoodsViewHolder holder, int position) {
        Food food = filteredFoods.get(position);
        holder.bind(food);

        holder.linearLayoutSearchFoodItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FoodDetailFragment foodDetailFragment = new FoodDetailFragment(food.getId());
                fragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, foodDetailFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return filteredFoods.size(); // Mengembalikan ukuran filteredFoods
    }
    public void updateFoods(List<Food> foodsFull) {
        this.foods.clear();
        this.foods.addAll(foodsFull);
        notifyDataSetChanged();
    }

    public static class SearchFoodsViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout linearLayoutSearchFoodItem;
        private ImageView ivSearchFoodImage;
        private TextView tvSearchFoodTitle;

        public SearchFoodsViewHolder(@NonNull View itemView) {
            super(itemView);
            linearLayoutSearchFoodItem = itemView.findViewById(R.id.linearLayout_search_food_item);
            ivSearchFoodImage = itemView.findViewById(R.id.iv_search_food_image);
            tvSearchFoodTitle = itemView.findViewById(R.id.tv_search_food_title);
        }


        public void bind(Food food) {
            Picasso.get().load(food.getImage()).into(ivSearchFoodImage);
            tvSearchFoodTitle.setText(food.getTitle());
        }
    }
}
