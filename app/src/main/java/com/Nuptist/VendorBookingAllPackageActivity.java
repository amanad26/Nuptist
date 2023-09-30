package com.Nuptist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.Nuptist.Models.MyBookingModel;
import com.Nuptist.RetrofitApis.ApiInterface;
import com.Nuptist.RetrofitApis.RetrofitClient;
import com.Nuptist.Session.Session;
import com.Nuptist.adpaters.VendorsBookingAdapter;
import com.Nuptist.databinding.ActivityVendorBookingAllPackageBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VendorBookingAllPackageActivity extends AppCompatActivity {

    ActivityVendorBookingAllPackageBinding binding;
    Session session ;

    VendorBookingAllPackageActivity activity ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityVendorBookingAllPackageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        activity = this ;
        session = new Session(activity);

        getVendorBookingPackage();

     }

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
}