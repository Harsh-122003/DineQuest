package com.example.dinequest.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.example.dinequest.Domain.Foods;
import com.example.dinequest.Helper.ManagmentCart;
import com.example.dinequest.R;
import com.example.dinequest.databinding.ActivityDetailBinding;

public class DetailActivity extends AppCompatActivity {
    ActivityDetailBinding binding;
    private Foods object;
    private int num = 1;
    private ManagmentCart managmentCart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Window w = getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);

        getIntentExtra();
        setValues();
    }

    private void getIntentExtra(){
        object = (Foods) getIntent().getSerializableExtra("object");
    }

    private void setValues(){
        managmentCart = new ManagmentCart(this);

        binding.backBtn.setOnClickListener(v -> finish());

        Glide.with(this)
                .load(object.getImagePath())
                .transform(new CenterCrop(), new RoundedCorners(60))
                .into(binding.pic);

        binding.priceTxt.setText("$" + object.getPrice());

        binding.titleTxt.setText(object.getTitle());

        binding.descriptionTxt.setText(object.getDescription());

        binding.ratingBar.setRating((float) object.getStar());
        binding.ratingTxt.setText(object.getStar() + " Rating");

        binding.timeTxt.setText(object.getTimeValue() + "");

        binding.totalTxt.setText("$" + (num * object.getPrice()));

        binding.plusBtn.setOnClickListener(v -> {
            num += 1;
            binding.numTxt.setText(num+"");
            binding.totalTxt.setText("$" + String.format("%.2f", num * object.getPrice()));
        });

        binding.minusBtn.setOnClickListener(v -> {
            if(num > 1)
            {
                num -= 1;
                binding.numTxt.setText(num+"");
                binding.totalTxt.setText("$" + String.format("%.2f", num * object.getPrice()));
            }
        });

        binding.cartBtn.setOnClickListener(v -> {
            object.setNumberInCart(num);
            managmentCart.insertFood(object);
        });
    }
}