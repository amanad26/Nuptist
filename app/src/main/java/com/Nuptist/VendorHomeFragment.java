package com.Nuptist;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.Nuptist.Models.MyBookingModel;
import com.Nuptist.RetrofitApis.ApiInterface;
import com.Nuptist.RetrofitApis.RetrofitClient;
import com.Nuptist.adpaters.VendorHomePageAdapter;
import com.Nuptist.adpaters.VendorsBookingAdapter;
import com.Nuptist.databinding.FragmentVendorHomeBinding;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class VendorHomeFragment extends Fragment {


    FragmentVendorHomeBinding binding;
    private  String[] items = {"Packages" , "Upcoming Events"};

    public VendorHomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentVendorHomeBinding.inflate(getLayoutInflater());

        VendorHomePageAdapter adapter = new VendorHomePageAdapter(getActivity());
        binding.viewPager.setAdapter(adapter);

        new TabLayoutMediator(binding.tab, binding.viewPager, (tab, position) -> tab.setText(items[position])).attach();

        return binding.getRoot();
    }




}