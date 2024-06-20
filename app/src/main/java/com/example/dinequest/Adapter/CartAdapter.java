package com.example.dinequest.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.example.dinequest.Domain.Foods;
import com.example.dinequest.Helper.ChangeNumberItemsListener;
import com.example.dinequest.Helper.ManagmentCart;
import com.example.dinequest.R;

import java.util.ArrayList;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {
    Context context;
    ArrayList<Foods> list;
    ManagmentCart managmentCart;
    ChangeNumberItemsListener listener;

    public CartAdapter(Context context, ArrayList<Foods> list, ManagmentCart managmentCart, ChangeNumberItemsListener listener) {
        this.context = context;
        this.list = list;
        this.managmentCart = managmentCart;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cart_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.titleTxt.setText(list.get(position).getTitle());
        holder.priceTxt.setText("$" + String.format("%.2f", list.get(position).getNumberInCart() * list.get(position).getPrice()));
        holder.numberTxt.setText(list.get(position).getNumberInCart() + "");

        Glide.with(context)
                .load(list.get(position).getImagePath())
                .transform(new CenterCrop(), new RoundedCorners(30))
                .into(holder.pic);

        holder.plusBtn.setOnClickListener(v -> managmentCart.plusNumberItem(list, position, () -> {
            notifyDataSetChanged();
            listener.change();
        }));

        holder.minusBtn.setOnClickListener(v -> managmentCart.minusNumberItem(list, position, () -> {
            notifyDataSetChanged();
            listener.change();
        }));

        holder.trashBtn.setOnClickListener(v -> managmentCart.removeItem(list, position, () -> {
            notifyDataSetChanged();
            listener.change();
        }));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView pic;
        TextView titleTxt, priceTxt, plusBtn, minusBtn, numberTxt;
        ConstraintLayout trashBtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            pic = itemView.findViewById(R.id.pic);
            titleTxt = itemView.findViewById(R.id.titleTxt);
            priceTxt = itemView.findViewById(R.id.priceTxt);
            plusBtn = itemView.findViewById(R.id.plusBtn);
            minusBtn = itemView.findViewById(R.id.minusBtn);
            numberTxt = itemView.findViewById(R.id.numberTxt);
            trashBtn = itemView.findViewById(R.id.trashBtn);
        }
    }
}
