package com.Nuptist;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.Nuptist.Models.GPSTracker;
import com.Nuptist.Models.SignUpModel;
import com.Nuptist.RetrofitApis.ApiInterface;
import com.Nuptist.RetrofitApis.RetrofitClient;
import com.Nuptist.databinding.ActivitySignUpBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import soup.neumorphism.NeumorphCardView;

public class SignUpActivity extends AppCompatActivity {
    TextView AG_SIGNUP, AG_Login, AG_vendorsignup, Otpverify;
    TextView login;

    ActivitySignUpBinding binding;

    double lat, lang;
    String tikenIfcm_id;
    ProgressDialog pd;
    String phone = "" , lastName= "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        pd = new ProgressDialog(SignUpActivity.this);


        // FirebaseApp.initializeApp(SignUpActivity.this);


        showLogin(binding.signupPassword,binding.signUpButton);

        GPSTracker gpsTracker = new GPSTracker(SignUpActivity.this);

        lat = gpsTracker.getLatitude();
        lang = gpsTracker.getLongitude();

        Log.e("TAG", "onCreate() called with: Lat  = [" + lat + "]");
        Log.e("TAG", "onCreate() called with: Lang = [" + lang + "]");


        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(new OnSuccessListener<InstanceIdResult>() {
            @Override
            public void onSuccess(InstanceIdResult instanceIdResult) {
                String fcm_id = instanceIdResult.getToken();
                // send it to server
                tikenIfcm_id = fcm_id;
                Log.e("refresh_tokentoken", fcm_id);
            }
        });

        binding.login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpActivity.this, LoginSecondActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.enter, R.anim.exit);

            }
        });

        binding.signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (binding.signupEmail.getText().toString().equalsIgnoreCase("")) {
                    binding.signupEmail.setError("Enter Email");
                    binding.signupEmail.requestFocus();
                } else if (!emailValidator(binding.signupEmail.getText().toString())) {
                    binding.signupEmail.setError("Invalid Email");
                    binding.signupEmail.requestFocus();
                }else if (binding.signupPassword.getText().toString().equalsIgnoreCase("")) {
                    binding.signupPassword.setError("Enter Password ");
                    binding.signupPassword.requestFocus();
                } else if (binding.signupName.getText().toString().equalsIgnoreCase("")) {
                    binding.signupName.setError("Enter First Name ");
                    binding.signupName.requestFocus();
                } else if (binding.signupPassword.getText().toString().length() < 6) {
                    binding.signupPassword.setError(" Password  Must Be Greater Then 6 digits");
                    binding.signupPassword.requestFocus();
                } else if (binding.signupConfirmPassword.getText().toString().equalsIgnoreCase("")) {
                    binding.signupConfirmPassword.setError("Re Enter  Password ");
                    binding.signupConfirmPassword.requestFocus();
                }else if (!binding.signupConfirmPassword.getText().toString().equalsIgnoreCase(binding.signupPassword.getText().toString())) {
                    binding.signupConfirmPassword.setError("Password do not match.! ");
                    binding.signupConfirmPassword.requestFocus();
                }
                else {
                    signup();
                    pd.show();
                }

            }
        });
        /*AG_SIGNUP= findViewById(R.id.AG_SIGNUP);
        AG_Login= findViewById(R.id.AG_Login);
        AG_vendorsignup= findViewById(R.id.AG_vendorsignup);

        AG_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpActivity.this, SearchPackageActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.enter, R.anim.exit);

            }
        });*/


    }

    private void signup() {

        ApiInterface apiInterface = RetrofitClient.getclient(SignUpActivity.this);

        apiInterface.signup(
                binding.signupName.getText().toString().trim(),
                binding.signupEmail.getText().toString().trim(),
                binding.signupPassword.getText().toString().trim(),
                String.valueOf(lat),
                String.valueOf(lang),
                tikenIfcm_id,
                binding.numberCountyCode.getTextView_selectedCountry().getText().toString()

        ).enqueue(new Callback<SignUpModel>() {
            @Override
            public void onResponse(Call<SignUpModel> call, Response<SignUpModel> response) {

                pd.dismiss();
                try {

                    if (response.code() == 200)
                        if (response.body() != null) {
                            if (response.body().getResult().equalsIgnoreCase("true")) {

                                startActivity(new Intent(SignUpActivity.this, LoginSecondActivity.class));
                                finish();
                                Toast.makeText(SignUpActivity.this, "Account Created..", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(SignUpActivity.this, response.body().getMsg(), Toast.LENGTH_SHORT).show();
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
            public void onFailure(Call<SignUpModel> call, Throwable t) {
                pd.dismiss();
                Log.e("TAG", "onFailure() called with: call = [" + call + "], t = [" + t + "]");
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

    public void ShowHidePass(View view){

        if(view.getId()==R.id.show_hide){

            if(binding.signupPassword.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())){
                ((ImageView)(view)).setImageResource(R.drawable.ic_baseline_remove_red_eye_24);

                //Show Password
                binding.signupPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            }
            else{
                ((ImageView)(view)).setImageResource(R.drawable.ic_baseline_visibility_off_24);

                //Hide Password
                binding.signupPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());

            }
        }else if(view.getId()==R.id.show_hide2){
            if(binding.signupConfirmPassword.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())){
                ((ImageView)(view)).setImageResource(R.drawable.ic_baseline_remove_red_eye_24);

                //Show Password
                binding.signupConfirmPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            }
            else{
                ((ImageView)(view)).setImageResource(R.drawable.ic_baseline_visibility_off_24);

                //Hide Password
                binding.signupConfirmPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());

            }
        }
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