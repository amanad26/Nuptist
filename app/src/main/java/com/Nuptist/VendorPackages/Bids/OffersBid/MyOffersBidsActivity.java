package com.Nuptist.VendorPackages.Bids.OffersBid;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;


import com.Nuptist.databinding.ActivityMyOffersBidsBinding;
import com.google.android.material.tabs.TabLayoutMediator;

public class MyOffersBidsActivity extends AppCompatActivity {

    ActivityMyOffersBidsBinding binding ;
    String[] items = {"All Quote","Accepted Quote"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMyOffersBidsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.back.setOnClickListener(view -> onBackPressed());


        binding.productViewpager.setAdapter(new OfferBidsPageAdapter(MyOffersBidsActivity.this));


        new TabLayoutMediator(binding.tabLay, binding.productViewpager, (tab, position) -> tab.setText(items[position])).attach();
    }
}