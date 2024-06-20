package com.example.dinequest.Adapter;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.FitCenter;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.dinequest.Domain.SliderItems;
import com.example.dinequest.R;

import java.util.ArrayList;

public class SliderAdapter extends RecyclerView.Adapter<SliderAdapter.ViewHolder> {
    private Context context;
    private ArrayList<SliderItems> list;
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            list.addAll(list);
            notifyDataSetChanged();
        }
    };

    public SliderAdapter(Context context, ArrayList<SliderItems> list){
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.slider_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        RequestOptions requestOptions = new RequestOptions();
        requestOptions = requestOptions.transform(new RoundedCorners(60), new FitCenter());

        Glide.with(context)
                .load(list.get(position).getImage())
                .apply(requestOptions)
                .into(holder.image);

        if(position == list.size()-2)
        {
            new Handler().post(runnable);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.sliderImage);
        }
    }
}