package com.Nuptist;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.collection.ArraySet;
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
import com.Nuptist.Session.Session;
import com.Nuptist.adpaters.VendorsBookingAdapter;
import com.Nuptist.databinding.FragmentVendorHomeEventsBinding;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VendorHomeEventsFragment extends Fragment {


    FragmentVendorHomeEventsBinding binding;
    Session session ;
    List<MyBookingModel.MyBookingData> model = new ArrayList<>();

    public VendorHomeEventsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentVendorHomeEventsBinding.inflate(getLayoutInflater());
        session = new Session(getContext());

        getVendorBookingPackage();

        return binding.getRoot();

    }

/*
    private void getVendorBookingPackage() {
        ApiInterface apiInterface = RetrofitClient.getclient(activity);
        apiInterface.getVendorsBooking(session.getUserId_Vendor()).enqueue(new Callback<MyBookingModel>() {
            @Override
            public void onResponse(@NonNull Call<MyBookingModel> call, @NonNull Response<MyBookingModel> response) {

                try {
                    if(response.code() == 200)
                        if (response.body() != null && response.body().getResult().equalsIgnoreCase("true")) {
                            binding.vendorPackageRecyclerview.setLayoutManager(new LinearLayoutManager(activity));
                            binding.vendorPackageRecyclerview.setAdapter(new VendorsBookingAdapter(activity, response.body().getData()));
                            binding.noData.setVisibility(View.GONE);
                            binding.progressBar.setVisibility(View.GONE);
                        }else {
                            binding.noData.setVisibility(View.VISIBLE);
                            binding.progressBar.setVisibility(View.GONE);
                            Toast.makeText(activity, "No Booking Packages Found..!", Toast.LENGTH_SHORT).show();
                        }
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onFailure(@NonNull Call<MyBookingModel> call, @NonNull Throwable t) {
                binding.noData.setVisibility(View.VISIBLE);
                binding.progressBar.setVisibility(View.GONE);
                Log.e("TAG", "onFailure() called with: call = [" + call + "], t = [" + t.getLocalizedMessage() + "]");
            }
        });

    }
    */
    private void getVendorBookingPackage() {
        ApiInterface apiInterface = RetrofitClient.getclient(getContext());
        apiInterface.getVendorsBooking(session.getUserId_Vendor()).enqueue(new Callback<MyBookingModel>() {
            @Override
            public void onResponse(@NonNull Call<MyBookingModel> call, @NonNull Response<MyBookingModel> response) {

                try {
                    if (response.code() == 200)
                        if (response.body() != null && response.body().getResult().equalsIgnoreCase("true")) {

                                binding.vendorPackageRecyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
                                binding.vendorPackageRecyclerview.setAdapter(new VendorsBookingAdapter(getContext(), response.body().getData()));
                                binding.noData.setVisibility(View.GONE);
                                binding.progressBar.setVisibility(View.GONE);

                        } else {
                            binding.noData.setVisibility(View.VISIBLE);
                            binding.progressBar.setVisibility(View.GONE);
                            Toast.makeText(getContext(), "No Booking Packages Found..!", Toast.LENGTH_SHORT).show();

                        }
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onFailure(@NonNull Call<MyBookingModel> call, @NonNull Throwable t) {
                binding.noData.setVisibility(View.VISIBLE);
                binding.progressBar.setVisibility(View.GONE);
                Log.e("TAG", "onFailure() called with: call = [" + call + "], t = [" + t.getLocalizedMessage() + "]");
            }
        });

    }
}