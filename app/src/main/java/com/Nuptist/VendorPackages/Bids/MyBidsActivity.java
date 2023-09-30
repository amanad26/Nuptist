package com.Nuptist.VendorPackages.Bids;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.Nuptist.databinding.ActivityMyBidsBinding;
import com.google.android.material.tabs.TabLayoutMediator;

public class MyBidsActivity extends AppCompatActivity {

    ActivityMyBidsBinding binding ;
    String[] items = {"All Quote","Accepted Quote"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         binding  = ActivityMyBidsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        binding.back.setOnClickListener(view -> onBackPressed());


        binding.productViewpager.setAdapter(new BidPageAdapter(MyBidsActivity.this));


        new TabLayoutMediator(binding.tabLay, binding.productViewpager, (tab, position) -> tab.setText(items[position])).attach();
    }
}