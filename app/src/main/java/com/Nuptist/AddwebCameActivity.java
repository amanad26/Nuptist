package com.Nuptist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.Nuptist.Models.AccountModel;
import com.Nuptist.Models.LogOutModel;
import com.Nuptist.Models.ProgressDialog;
import com.Nuptist.RetrofitApis.ApiInterface;
import com.Nuptist.RetrofitApis.RetrofitClient;
import com.Nuptist.Session.Session;
import com.Nuptist.databinding.ActivityAddwebcamBinding;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddwebCameActivity extends AppCompatActivity {

    ActivityAddwebcamBinding binding ;
    AddwebCameActivity activity ;
    Session session ;
    String user_image = "";
    String user_name = "";
    String package_id = "";
    String b_id = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddwebcamBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        activity = this;
        session = new Session(activity);

        package_id = getIntent().getStringExtra("p_id");
        b_id = getIntent().getStringExtra("b_id");

        get_profile();

        binding.submit.setOnClickListener(v -> {
            if(binding.etQuery.getText().toString().equalsIgnoreCase("")){
                binding.etQuery.setError("Enter any message ..!");
                binding.etQuery.requestFocus();
            }else if(binding.ratingBar.getRating() == 0.00){
                Toast.makeText(AddwebCameActivity.this, "Minimum rating 1", Toast.LENGTH_SHORT).show();
            }else {
                addReview();
            }
        });

        binding.back.setOnClickListener(v -> finish());
    }

    private void addReview() {

        ProgressDialog pd = new ProgressDialog(activity);
        pd.show();


        Date d = new Date();
        Locale locale = new Locale("fr", "FR");
        DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.DEFAULT, locale);
        String   date =  dateFormat.format(d);


        ApiInterface apiInterface = RetrofitClient.getclient(activity);
        apiInterface.addReview(
                package_id,
                session.getUserId(),
                String.valueOf(binding.ratingBar.getRating()),
                binding.etQuery.getText().toString(),
                user_name,
                user_image,
                session.getUserId_Vendor(),
                date,
                b_id
        ).enqueue(new Callback<LogOutModel>() {
            @Override
            public void onResponse(@NonNull Call<LogOutModel> call, @NonNull Response<LogOutModel> response) {

                pd.dismiss();
                try {

                    if (response.body() != null && response.body().getResult().equalsIgnoreCase("true")) {
                        startActivity(new Intent(activity,SucessActivity.class)
                                .putExtra("text","Thanks for the review...!"));
                        finish();

                    }

                }catch (Exception e){
                    pd.dismiss();
                    e.getLocalizedMessage();
                }


            }

            @Override
            public void onFailure(@NonNull Call<LogOutModel> call, @NonNull Throwable t) {
                pd.dismiss();
                Log.e("TAG", "onFailure() called with: call = [" + call + "], t = [" + t.getLocalizedMessage() + "]");
            }
        });

    }

    private  void get_profile(){

        ApiInterface apiInterface = RetrofitClient.getclient(activity);

        apiInterface.get_profile(session.getUserId()).enqueue(new Callback<AccountModel>() {
            @Override
            public void onResponse(@NonNull Call<AccountModel> call, @NonNull Response<AccountModel> response) {
                try {

                    if (response.body() != null && response.body().getResult().equalsIgnoreCase("true")) {
                       user_image = response.body().getData().getImage();
                       user_name = response.body().getData().getName();
                    }

                }catch (Exception e){
                    e.getLocalizedMessage();
                }

            }

            @Override
            public void onFailure(@NonNull Call<AccountModel> call,@NonNull Throwable t) {
                Log.e("TAG", "onFailure() called with: call = [" + call + "], t = [" + t + "]");
            }
        });



    }
}