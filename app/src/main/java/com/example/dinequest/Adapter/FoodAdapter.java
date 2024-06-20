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
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.FitCenter;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.example.dinequest.Activity.DetailActivity;
import com.example.dinequest.Domain.Foods;
import com.example.dinequest.R;

import java.util.ArrayList;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.ViewHolder> {
    Context context;
    ArrayList<Foods> list;

    public FoodAdapter(Context context, ArrayList<Foods> list){
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.food_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.titleTxt.setText(list.get(position).getTitle());
        holder.priceTxt.setText("$" + list.get(position).getPrice());
        holder.ratingTxt.setText(list.get(position).getStar() + "");
        holder.timeTxt.setText(list.get(position).getTimeValue() + " min");

        Glide.with(context)
                .load(list.get(position).getImagePath())
                .transform(new CenterCrop(), new RoundedCorners(50))
                .into(holder.img);

        holder.itemView.setOnClickListener(v -> {
            Intent i = new Intent(context, DetailActivity.class);
            i.putExtra("object", list.get(position));
            context.startActivity(i);
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView img;
        TextView titleTxt, priceTxt, ratingTxt, timeTxt;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.img);
            titleTxt = itemView.findViewById(R.id.titleTxt);
            priceTxt = itemView.findViewById(R.id.priceTxt);
            ratingTxt = itemView.findViewById(R.id.ratingTxt);
            timeTxt = itemView.findViewById(R.id.timeTxt);
        }
    }
}
