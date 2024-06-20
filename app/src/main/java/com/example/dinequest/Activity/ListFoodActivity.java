package com.example.dinequest.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.example.dinequest.Adapter.FoodAdapter;
import com.example.dinequest.Domain.Foods;
import com.example.dinequest.R;
import com.example.dinequest.databinding.ActivityListFoodBinding;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ListFoodActivity extends AppCompatActivity {
    ActivityListFoodBinding binding;
    private int categoryId;
    private String categoryName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityListFoodBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        categoryId = getIntent().getIntExtra("CategoryId", 0);
        categoryName = getIntent().getStringExtra("CategoryName");

        binding.titleTxt.setText(categoryName);

        binding.backBtn.setOnClickListener(v -> finish());

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Foods");
        binding.progressBar.setVisibility(View.VISIBLE);
        ArrayList<Foods> list = new ArrayList<>();
        Query query = reference.orderByChild("CategoryId").equalTo(categoryId);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists())
                {
                    for (DataSnapshot object : snapshot.getChildren())
                    {
                        list.add(object.getValue(Foods.class));
                    }
                    if(list.size()>0)
                    {
                        binding.progressBar.setVisibility(View.GONE);
                        binding.foodRecyclerView.setLayoutManager(new LinearLayoutManager(ListFoodActivity.this));
                        binding.foodRecyclerView.setAdapter(new FoodAdapter(ListFoodActivity.this, list));
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("Firebase Food Error", error.toString());
            }
        });
    }
}