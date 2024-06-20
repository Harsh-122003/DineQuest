package com.example.dinequest.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.example.dinequest.Adapter.CategoryAdapter;
import com.example.dinequest.Adapter.SliderAdapter;
import com.example.dinequest.Domain.Category;
import com.example.dinequest.Domain.SliderItems;
import com.example.dinequest.R;
import com.example.dinequest.databinding.ActivityMainBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Window w = getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);

        initBanner();
        setCategory();

        binding.bottomMenu.setItemSelected(R.id.home, true);
        binding.bottomMenu.setOnItemSelectedListener(i -> {
            if(i == R.id.cart)
            {
                startActivity(new Intent(MainActivity.this, CartActivity.class));
            }
        });
    }

    private void initBanner(){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Banners");
        binding.progressBarBanner.setVisibility(View.VISIBLE);

        ArrayList<SliderItems> listSlider = new ArrayList<>();
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists())
                {
                    for (DataSnapshot item : snapshot.getChildren())
                    {
                        listSlider.add(item.getValue(SliderItems.class));
                    }
                    if(listSlider.size()>0)
                    {
                        binding.progressBarBanner.setVisibility(View.GONE);
                        setBanner(listSlider);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("Firebase Banner Error", error.toString());
            }
        });
    }

    private void setBanner(ArrayList<SliderItems> listSlider){
        binding.viewPager.setAdapter(new SliderAdapter(this, listSlider));
        binding.viewPager.setClipChildren(false);
        binding.viewPager.setClipToPadding(false);
        binding.viewPager.setOffscreenPageLimit(3);
        binding.viewPager.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);

        CompositePageTransformer pageTransformer = new CompositePageTransformer();
        pageTransformer.addTransformer(new MarginPageTransformer(40));

        binding.viewPager.setPageTransformer(pageTransformer);
    }

    private void setCategory(){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Category");
        binding.progressBarCategory.setVisibility(View.VISIBLE);

        ArrayList<Category> listCategory = new ArrayList<>();
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists())
                {
                    for (DataSnapshot item : snapshot.getChildren())
                    {
                        listCategory.add(item.getValue(Category.class));
                    }
                    if(listCategory.size()>0)
                    {
                        binding.progressBarCategory.setVisibility(View.GONE);
                        binding.recyclerViewCategory.setLayoutManager(new GridLayoutManager(MainActivity.this, 3));
                        binding.recyclerViewCategory.setAdapter(new CategoryAdapter(MainActivity.this, listCategory));
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("Firebase Category Error", error.toString());
            }
        });
    }
}