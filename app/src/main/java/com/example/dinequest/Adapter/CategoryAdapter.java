package com.example.dinequest.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.dinequest.Activity.ListFoodActivity;
import com.example.dinequest.Domain.Category;
import com.example.dinequest.R;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {
    Context context;
    ArrayList<Category> list;

    public CategoryAdapter(Context context, ArrayList<Category> list){
        this.context = context;
        this.list = list;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.category_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.categoryName.setText(list.get(position).getName());
        Glide.with(context)
                .load(list.get(position).getImagePath())
                .into(holder.categoryImg);

        holder.itemView.setOnClickListener(v -> {
            Intent i =new Intent(context, ListFoodActivity.class);
            i.putExtra("CategoryId", list.get(position).getId());
            i.putExtra("CategoryName", list.get(position).getName());
            context.startActivity(i);
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView categoryImg;
        TextView categoryName;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryImg = itemView.findViewById(R.id.categoryImg);
            categoryName = itemView.findViewById(R.id.categoryName);
        }
    }
}
