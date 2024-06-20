package com.example.dinequest.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.example.dinequest.Adapter.CartAdapter;
import com.example.dinequest.Helper.ChangeNumberItemsListener;
import com.example.dinequest.Helper.ManagmentCart;
import com.example.dinequest.R;
import com.example.dinequest.databinding.ActivityCartBinding;

public class CartActivity extends AppCompatActivity {
    ActivityCartBinding binding;
    ManagmentCart cart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCartBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        cart = new ManagmentCart(this);

        binding.backBtn.setOnClickListener(v -> startActivity(new Intent(CartActivity.this, MainActivity.class)));

        initCart();
        calculateCart();
    }

    private void initCart(){
        if(cart.getListCart().isEmpty())
        {
            binding.emptyTxt.setVisibility(View.VISIBLE);
            binding.scrollView.setVisibility(View.GONE);
        }
        else
        {
            binding.emptyTxt.setVisibility(View.GONE);
            binding.scrollView.setVisibility(View.VISIBLE);
        }
        binding.cartRecycler.setLayoutManager(new LinearLayoutManager(this));
        binding.cartRecycler.setAdapter(new CartAdapter(this, cart.getListCart(), cart, new ChangeNumberItemsListener() {
            @Override
            public void change() {
                calculateCart();
                initCart();
            }
        }));
    }

    private void calculateCart(){
        double percentTax = 0.02;
        double delivery = 10;
        double tax = Double.parseDouble(String.format("%.1f", cart.getTotalFee() * percentTax));
        double total = Math.round((cart.getTotalFee() + tax + delivery) * 100) / 100;
        double itemTotal = Math.round(cart.getTotalFee() * 100) / 100;

        binding.subTotalTxt.setText("$" + itemTotal);
        binding.deliveryTxt.setText("$" + delivery);
        binding.totalTaxTxt.setText("$" + tax);
        binding.totalTxt.setText("$" + total);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(CartActivity.this, MainActivity.class));
        super.onBackPressed();
    }
}