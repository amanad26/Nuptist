package com.Nuptist;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.Nuptist.Models.GetPackageModel;
import com.Nuptist.Models.MyBookingModel;
import com.Nuptist.RetrofitApis.ApiInterface;
import com.Nuptist.RetrofitApis.RetrofitClient;
import com.Nuptist.Session.Session;
import com.Nuptist.VendorPackages.VendorPackagesActivity;
import com.Nuptist.adpaters.VendorPackageAdapterNew;
import com.Nuptist.adpaters.VendorsBookingAdapter;
import com.Nuptist.databinding.FragmentVendorHomePackagesBinding;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VendorHomePackagesFragment extends Fragment {

    private FragmentVendorHomePackagesBinding binding;
    private List<GetPackageModel.PackageData> data = new ArrayList<>();
    private Session session;

    public VendorHomePackagesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentVendorHomePackagesBinding.inflate(getLayoutInflater());
        session = new Session(getContext());


       /// getVendorBookingPackage();
        getPackage();

        binding.AGSErach.setOnClickListener(view -> {
            Intent intent = new Intent(getActivity(), SearchPackageActivity.class);
            startActivity(intent);
        });

//        binding.seeMoreBtnBooking.setOnClickListener(view -> {
//            Intent intent = new Intent(getContext(), UpcommingEventsActivity.class);
//            startActivity(intent);
//        });

        return binding.getRoot();
    }
/*
    private void getVendorBookingPackage() {
        ApiInterface apiInterface = RetrofitClient.getclient(getContext());
        apiInterface.getVendorsBooking(session.getUserId_Vendor()).enqueue(new Callback<MyBookingModel>() {
            @Override
            public void onResponse(@NonNull Call<MyBookingModel> call, @NonNull Response<MyBookingModel> response) {
                binding.progressBar.setVisibility(View.GONE);
                try {
                    if (response.code() == 200)
                        if (response.body() != null && response.body().getResult().equalsIgnoreCase("true")) {

                            binding.bookingLinear.setVisibility(View.VISIBLE);
                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
                            linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
                            binding.bookingRecycler.setLayoutManager(linearLayoutManager);
                            binding.bookingRecycler.setAdapter(new VendorsBookingAdapter(getContext(), response.body().getData()));
                        }else {
                            binding.bookingLinear.setVisibility(View.GONE);
                        }
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onFailure(@NonNull Call<MyBookingModel> call, @NonNull Throwable t) {
                binding.bookingLinear.setVisibility(View.GONE);
                Log.e("TAG", "onFailure() called with: call = [" + call + "], t = [" + t.getLocalizedMessage() + "]");
            }
        });

    }
*/

    private void getPackage() {

        ApiInterface apiInterface = RetrofitClient.getclient(getContext());

        apiInterface.getPackage().enqueue(new Callback<GetPackageModel>() {
            @Override
            public void onResponse(@NonNull Call<GetPackageModel> call, @NonNull Response<GetPackageModel> response) {

                try {

                    if (response.code() == 200)
                        if (response.body() != null)
                            if (response.body().getResult().equalsIgnoreCase("true")) {
                                data.clear();
                                for (int i = 0; i < response.body().getData().size(); i++) {
                                    if (response.body().getData().get(i).getUsers_status().equalsIgnoreCase("0")) {
                                        data.add(response.body().getData().get(i));
                                    }
                                }

                                if(data.size() != 0 ){
                                    binding.recycler.setLayoutManager(new LinearLayoutManager(getContext()));
                                    binding.recycler.setAdapter(new VendorPackageAdapterNew(getContext(), data, "imagePath"));
                                    binding.progressBar.setVisibility(View.GONE);
                                    Log.e("TAG", "onResponse() called with: Data Set  = [" + call + "], response = [" + response + "]");

                                }else {
                                    Toast.makeText(getContext(), "No Packages Found..!", Toast.LENGTH_SHORT).show();
                                    binding.progressBar.setVisibility(View.GONE);
                                }

                            } else {
                                Toast.makeText(getContext(), "No Packages Found..!", Toast.LENGTH_SHORT).show();
                                binding.progressBar.setVisibility(View.GONE);

                            }

                } catch (Exception e) {
                    e.getLocalizedMessage();
                }

            }

            @Override
            public void onFailure(@NonNull Call<GetPackageModel> call, @NonNull Throwable t) {
                binding.progressBar.setVisibility(View.GONE);
                Log.e("TAG", "onFailure() called with: call = [" + call + "], t = [" + t.getLocalizedMessage() + "]");

            }
        });

    }
}