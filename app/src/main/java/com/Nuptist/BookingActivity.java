package com.Nuptist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;


import com.Nuptist.Models.MyBookingModel;
import com.Nuptist.RetrofitApis.ApiInterface;
import com.Nuptist.RetrofitApis.RetrofitClient;
import com.Nuptist.Session.Session;
import com.Nuptist.adpaters.MyBookingAdapter;
import com.Nuptist.databinding.ActivityBookingBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookingActivity extends AppCompatActivity {

    Session session ;
    ActivityBookingBinding binding ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBookingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        Window window = getWindow();
        window.setStatusBarColor(getResources().getColor(R.color.white));

        session = new Session(BookingActivity.this);
        getMyBooking();

        binding.swipeRefresh.setOnRefreshListener(() -> getMyBooking());

    }


    private  void getMyBooking(){
        ApiInterface apiInterface = RetrofitClient.getclient(BookingActivity.this);

        apiInterface.getMyBooking(session.getUserId()).enqueue(new Callback<MyBookingModel>() {
            @Override
            public void onResponse(@NonNull Call<MyBookingModel> call, @NonNull Response<MyBookingModel> response) {
                binding.swipeRefresh.setRefreshing(false);
                if(response.code() == 200)
                    if(response.body() != null)
                        if(response.body().getResult().equalsIgnoreCase("true")){
                            binding.mainProgress.setVisibility(View.GONE);
                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(BookingActivity.this);
                            linearLayoutManager.setReverseLayout(true);
                            linearLayoutManager.setStackFromEnd(true);
                            binding.bookingRecycler.setLayoutManager(linearLayoutManager);
                            MyBookingAdapter adapter = new MyBookingAdapter(BookingActivity.this, response.body().getData(),1,session);
                            binding.bookingRecycler.setAdapter(adapter);
                        }else {
                            binding.mainProgress.setVisibility(View.GONE);
                             binding.noBooking.setVisibility(View.VISIBLE);
                        }

            }

            @Override
            public void onFailure(@NonNull Call<MyBookingModel> call, @NonNull Throwable t) {
                binding.swipeRefresh.setRefreshing(false);
                binding.mainProgress.setVisibility(View.GONE);
                binding.noBooking.setVisibility(View.VISIBLE);
                Log.e("TAG", "onFailure() called with: call = [" + call + "], t = [" + t.getLocalizedMessage() + "]");
            }
        });

    }
}