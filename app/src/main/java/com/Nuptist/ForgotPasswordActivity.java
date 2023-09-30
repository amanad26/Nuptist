package com.Nuptist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.Nuptist.Models.ForgotPasswordModel;
import com.Nuptist.Models.ProgressDialog;
import com.Nuptist.RetrofitApis.ApiInterface;
import com.Nuptist.RetrofitApis.RetrofitClient;
import com.Nuptist.databinding.ActivityForgotPasswordBinding;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import soup.neumorphism.NeumorphCardView;

public class ForgotPasswordActivity extends AppCompatActivity {

    ActivityForgotPasswordBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityForgotPasswordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        showLogin(binding.editEmail,binding.signUpButton);

        binding.getNewPassword.setOnClickListener(view -> {

            if(binding.editEmail.getText().toString().equalsIgnoreCase("")){
                binding.editEmail.setError("Enter mobile");
                binding.editEmail.requestFocus();
            }

            else if(!emailValidator(binding.editEmail.getText().toString())){
                binding.editEmail.setError("invalid Email!");
                binding.editEmail.requestFocus();
            }
            else {

                ProgressDialog pd = new ProgressDialog(ForgotPasswordActivity.this);
                pd.show();
                ApiInterface apiInterface = RetrofitClient.getclient(ForgotPasswordActivity.this);

                apiInterface.forgot_password(binding.editEmail.getText().toString().trim()).enqueue(new Callback<ForgotPasswordModel>() {
                    @Override
                    public void onResponse(@NonNull Call<ForgotPasswordModel> call, @NonNull Response<ForgotPasswordModel> response) {

                        pd.dismiss();
                        try {

                            if (response.code() == 200)
                                if (response.body() != null) {
                                    if (response.body().getResult().equalsIgnoreCase("true")) {
                                        startActivity(new Intent(ForgotPasswordActivity.this, LoginSecondActivity.class));
                                        finish();
                                        Toast.makeText(ForgotPasswordActivity.this, "Password Changed New Password is...", Toast.LENGTH_SHORT).show();
                                        Toast.makeText(ForgotPasswordActivity.this, response.body().getNew_password(), Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(ForgotPasswordActivity.this, response.body().getMsg(), Toast.LENGTH_SHORT).show();
                                        pd.dismiss();
                                        Log.e("TAG", "onResponse() called with: call = [" + call + "], response = [" + response.body().getMsg() + "]");
                                    }
                                } else
                                    Log.e("TAG", "onResponse() called with: call =  response = [" + response + "]");
                            else
                                Log.e("TAG", "onResponse() called with: call =  response = [" + response.code() + "]");
                        } catch (Exception e) {
                            Log.e("TAG", "onResponse() called with: call = [" + call + "], response = [" + e.getLocalizedMessage() + "]");
                            e.printStackTrace();
                        }


                    }

                    @Override
                    public void onFailure(@NonNull Call<ForgotPasswordModel> call, @NonNull Throwable t) {
                        pd.dismiss();
                        Log.e("TAG", "onFailure() called with: call = [" + call + "], t = [" + t + "]");
                    }
                });
            }

        });

    }



    public boolean emailValidator(String email) {
        Pattern pattern;
        Matcher matcher;
        final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email);
        return matcher.matches();
    }

    private  void showLogin(EditText email , NeumorphCardView card ){

        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(s.toString().equalsIgnoreCase("")){
                    card.setBackgroundColor(Color.parseColor("#75828E"));
                    binding.signUpButton2.setVisibility(View.VISIBLE);
                    binding.signUpButton.setVisibility(View.GONE);
                }else {
                    email.setClickable(true);
                    card.setBackgroundColor(Color.parseColor("#D83973"));
                    binding.signUpButton2.setVisibility(View.GONE);
                    binding.signUpButton.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


    }
}