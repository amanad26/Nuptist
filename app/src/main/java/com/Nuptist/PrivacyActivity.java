package com.Nuptist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.Nuptist.Models.PrivacyPolicyModel;
import com.Nuptist.Models.ProgressDialog;
import com.Nuptist.RetrofitApis.ApiInterface;
import com.Nuptist.RetrofitApis.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PrivacyActivity extends AppCompatActivity {

    TextView privacy_policy_tv ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        Window window = getWindow();
        window.setStatusBarColor(getResources().getColor(R.color.white));

        privacy_policy_tv = findViewById(R.id.privacy_policy_tv);

        getPrivacyPolicy();


        ImageView back = findViewById(R.id.back);


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    private void getPrivacyPolicy() {

        ProgressDialog pd = new ProgressDialog(PrivacyActivity.this);
        pd.show();

        ApiInterface apiInterface = RetrofitClient.getclient(PrivacyActivity.this);

        apiInterface.get_privacy().enqueue(new Callback<PrivacyPolicyModel>() {
            @Override
            public void onResponse(Call<PrivacyPolicyModel> call, Response<PrivacyPolicyModel> response) {

                pd.dismiss();
                try {

                    if (response.code() == 200)
                        if (response.body() != null) {
                            if (response.body().getResult().equalsIgnoreCase("true")) {

                                String privacy = response.body().getData().get(0).getContent();

                                privacy_policy_tv.setText(Html.fromHtml(privacy));

                                pd.dismiss();
                            } else {
                                Toast.makeText(PrivacyActivity.this, "Privacy Policy Not Found", Toast.LENGTH_SHORT).show();
                                pd.dismiss();
                                Log.e("TAG", "onResponse() called with: call = [" + call + "], response = [" + response.body().getMsg() + "]");
                            }
                        } else
                            Log.e("TAG", "onResponse() called with: call =  response = [" + response + "]");
                    else
                        Log.e("TAG", "onResponse() called with: call =  response = [" + response.code() + "]");
                } catch (Exception e) {
                    pd.dismiss();
                    Log.e("TAG", "onResponse() called with: call = [" + call + "], response = [" + e.getLocalizedMessage() + "]");
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<PrivacyPolicyModel> call, Throwable t) {
                pd.dismiss();
                Log.e("TAG", "onFailure() called with: call = [" + call + "], t = [" + t.getLocalizedMessage() + "]");
            }
        });



    }
}