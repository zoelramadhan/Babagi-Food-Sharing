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
import com.example.babagi.config.DbConfig;
import com.example.babagi.fragments.FoodDetailFragment;
import com.example.babagi.models.Food;
import com.squareup.picasso.Picasso;

import java.util.List;

public class FoodsAdapter extends RecyclerView.Adapter<FoodsAdapter.FoodViewHolder> {
    private final FragmentManager fragmentManager;
    public List<Food> foodList;
    private final int userId;

    public FoodsAdapter(FragmentManager fragmentManager, List<Food> foodList, int userId) {
        this.fragmentManager = fragmentManager;
        this.foodList = foodList;
        this.userId = userId;
    }

    @NonNull
    @Override
    public FoodsAdapter.FoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_food, parent, false);
        return new FoodViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodsAdapter.FoodViewHolder holder, int position) {
        Food food = foodList.get(position);
        holder.bind(food, userId);

        holder.linearLayoutFoodItem.setOnClickListener(new View.OnClickListener() {
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
        return foodList.size();
    }

    public static class FoodViewHolder extends RecyclerView.ViewHolder{
        private final LinearLayout linearLayoutFoodItem;
        private final ImageView ivFoodImage;
        private final ImageView ivSaveButton;
        private final TextView tvFoodTitle;
        public FoodViewHolder(@NonNull View itemView) {
            super(itemView);
            linearLayoutFoodItem = itemView.findViewById(R.id.linearlayout_item_food);
            ivFoodImage = itemView.findViewById(R.id.iv_food_image);
            ivSaveButton = itemView.findViewById(R.id.iv_save_button);
            tvFoodTitle = itemView.findViewById(R.id.tv_food_title);
        }
        public void bind(Food food, int userId) {
            Picasso.get().load(food.getImage()).into(ivFoodImage);
            tvFoodTitle.setText(food.getTitle());

            DbConfig dbConfig = new DbConfig(itemView.getContext());
            boolean isSave = dbConfig.isSave(userId, food.getId());
            ivSaveButton.setVisibility(isSave ? View.VISIBLE : View.GONE);
        }
    }
}
