package com.Nuptist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.Toast;

import com.Nuptist.Models.NotificationModel;
import com.Nuptist.RetrofitApis.ApiInterface;
import com.Nuptist.RetrofitApis.RetrofitClient;
import com.Nuptist.adpaters.NotificationAdapter;
import com.Nuptist.databinding.ActivityNotificationBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationActivity extends AppCompatActivity {

    ActivityNotificationBinding binding ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNotificationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        Window window = getWindow();
        window.setStatusBarColor(getResources().getColor(R.color.white));

        getNotification();

        binding.back.setOnClickListener(view -> onBackPressed());


        binding.swipeRefresh.setOnRefreshListener(() -> getNotification());

    }

    private  void getNotification (){

        ApiInterface apiInterface = RetrofitClient.getclient(NotificationActivity.this);
        apiInterface.getNotifications().enqueue(new Callback<NotificationModel>() {
            @Override
            public void onResponse(@NonNull Call<NotificationModel> call, @NonNull Response<NotificationModel> response) {

                binding.swipeRefresh.setRefreshing(false);
                if(response.code() == 200 && response.body() != null)
                    if(response.body().getResult().equalsIgnoreCase("true")){

                        binding.notiRecycler.setLayoutManager(new LinearLayoutManager(NotificationActivity.this));
                        binding.notiRecycler.setAdapter(new NotificationAdapter(NotificationActivity.this,response.body().getData(), response.body().getPath()));
                        binding.progressBar.setVisibility(View.GONE);
                    }else {
                        binding.progressBar.setVisibility(View.GONE);
                        Toast.makeText(NotificationActivity.this, "No Notification ", Toast.LENGTH_SHORT).show();
                    }


            }

            @Override
            public void onFailure(@NonNull Call<NotificationModel> call, @NonNull Throwable t) {
                binding.swipeRefresh.setRefreshing(false);
                binding.progressBar.setVisibility(View.GONE);
                Log.e("TAG", "onFailure() called with: call = [" + call + "], t = [" + t.getLocalizedMessage() + "]");
            }
        });

    }

}