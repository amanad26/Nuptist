package com.Nuptist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;

import com.Nuptist.Models.ProgressDialog;
import com.Nuptist.Models.TermsAndConditionModel;
import com.Nuptist.RetrofitApis.ApiInterface;
import com.Nuptist.RetrofitApis.RetrofitClient;
import com.Nuptist.databinding.ActivityTemsAndConditionBinding;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TemsAndConditionActivity extends AppCompatActivity {

    ActivityTemsAndConditionBinding binding;
    TemsAndConditionActivity activity;
    String typeId = "Partner";
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTemsAndConditionBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        activity = this;

        pd = new ProgressDialog(activity);

        typeId = getIntent().getStringExtra("type");

        getTermsAndCondition();

    }

    private void getTermsAndCondition() {
        pd.show();
        ApiInterface apiInterface = RetrofitClient.getclient(activity);
        apiInterface.termsAndCondition(typeId).enqueue(new Callback<TermsAndConditionModel>() {
            @Override
            public void onResponse(@NonNull Call<TermsAndConditionModel> call, @NonNull Response<TermsAndConditionModel> response) {
                pd.dismiss();
                if (response.code() == 200)
                    if (response.body() != null)
                        if (response.body().getResult().equalsIgnoreCase("true")) {
                            List<TermsAndConditionModel.TermsData> data = response.body().getData();


                            String terms = data.get(0).getDescription();

                            binding.privacyPolicyTv.setText(Html.fromHtml(terms));

                        }


            }

            @Override
            public void onFailure(@NonNull Call<TermsAndConditionModel> call, @NonNull Throwable t) {
                pd.dismiss();
                Log.e("TAG", "onFailure() called with: call = [" + call + "], t = [" + t + "]");
            }
        });


    }

}