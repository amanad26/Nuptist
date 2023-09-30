package com.Nuptist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;


import android.app.Dialog;
import android.content.Intent;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.Nuptist.Models.AccountModel;
import com.Nuptist.Models.LogOutModel;
import com.Nuptist.Models.ProgressDialog;
import com.Nuptist.RetrofitApis.ApiInterface;
import com.Nuptist.RetrofitApis.RetrofitClient;
import com.Nuptist.Session.Session;

import com.facebook.AccessToken;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivity extends AppCompatActivity {

    LinearLayout update_profile_customer,to_appr_baccount,to_see_events,our_services,notification,pravicy_policy;

    ImageView ic_back ;
    RoundedImageView user_profile ;
    LinearLayout to_chat , change_password_linear;
    TextView  logout_btn , user_name , user_email , create_business_account_tv;
    Session session ;
    String user_profile2 ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        session =new Session(ProfileActivity.this);

        FacebookSdk.sdkInitialize(ProfileActivity.this);

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        Window window =getWindow();
        window.setStatusBarColor(getResources().getColor(R.color.white));
        //dissmiss = findViewById(R.id.dissmiss2);
       //// our_services = findViewById(R.id.our_services);
        pravicy_policy = findViewById(R.id.pravicy_policy);
        notification = findViewById(R.id.notification);
        ic_back = findViewById(R.id.ic_back);
        to_chat = findViewById(R.id.to_chat);
        to_see_events = findViewById(R.id.to_see_events);
        logout_btn = findViewById(R.id.logout_btn);
        user_name = findViewById(R.id.user_name);
        user_email = findViewById(R.id.user_email);
        change_password_linear = findViewById(R.id.change_password_linear);
        create_business_account_tv = findViewById(R.id.create_business_account_tv);
        update_profile_customer = findViewById(R.id.update_profile_customer);
        user_profile = findViewById(R.id.user_profile);

        setUserDeatails();

        get_profile();
        ic_back.setOnClickListener(view -> onBackPressed());


        change_password_linear.setOnClickListener(view -> startActivity(new Intent(ProfileActivity.this,ChangePasswordActivity.class)));



        to_chat.setOnClickListener(view -> startActivity(new Intent(ProfileActivity.this, HomeActivity.class).putExtra("profile", "12345")));

        logout_btn.setOnClickListener(view -> logout());

        to_appr_baccount = findViewById(R.id.to_appr_baccount);

//       if(session.getUserType().equalsIgnoreCase("Vendor")){
//           startActivity(new Intent(ProfileActivity.this,VendorProfileActivity.class));
//           finish();
//       }

        to_appr_baccount.setOnClickListener(view -> {

            if(session.sharedPreferences.contains("user_id_vendor")){
                // session.setUserType("Vendor");
                session.setIsPersonal(false);
                create_business_account_tv.setText(R.string.business_to_personal);
                startActivity(new Intent(ProfileActivity.this,VendorProfileActivity.class));
                finish();
            }else {
                Intent intent = new Intent(ProfileActivity.this,VendorCreateAccountActivity.class);
                startActivity(intent);
            }
        });

        to_see_events.setOnClickListener(view -> {
            Intent intent = new Intent(ProfileActivity.this, BookingActivity.class);
            startActivity(intent);
        });

//        our_services.setOnClickListener(view -> {
//            Intent intent = new Intent(ProfileActivity.this, OurServiceActivity.class);
//            startActivity(intent);
//        });

        notification.setOnClickListener(view -> {
            Intent intent = new Intent(ProfileActivity.this, NotificationActivity.class);
            startActivity(intent);
        });

        pravicy_policy.setOnClickListener(view -> {
            Intent intent = new Intent(ProfileActivity.this, PrivacyActivity.class);
            startActivity(intent);
        });


        update_profile_customer.setOnClickListener(view -> {
            Intent intent = new Intent(ProfileActivity.this,ProfileEditActivity.class);
            startActivity(intent);
        });

        user_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                profileDialog();
            }
        });

    }

    private void setUserDeatails(){
        user_email.setText(session.getEmail());
        user_name.setText(session.getUserName());
        if(!session.getUserImage().equalsIgnoreCase("")){
            Picasso.get().load(session.getUserImage()).placeholder(R.drawable.user).into(user_profile);
        }
    }

    private  void get_profile(){

        ProgressDialog pd = new ProgressDialog(ProfileActivity.this);
        pd.show();

        ApiInterface apiInterface = RetrofitClient.getclient(ProfileActivity.this);

        apiInterface.get_profile(session.getUserId()).enqueue(new Callback<AccountModel>() {
            @Override
            public void onResponse(@NonNull Call<AccountModel> call,@NonNull Response<AccountModel> response) {
                pd.dismiss();
                try {

                    if (response.body() != null && response.body().getResult().equalsIgnoreCase("true")) {
                        user_name.setText(response.body().getData().getName());
                        if (response.body().getData().getEmail().length() <= 7) {
                            Log.d("TAG", "onResponse() called with: call = [" + call + "], response = [" + response + "]");
                        } else {
                            user_email.setText(response.body().getData().getEmail());
                        }
                        session.setEmail(response.body().getData().getEmail());
                        session.setUserName(response.body().getData().getName());
                        session.setUserLastName(response.body().getData().getLname());
                        session.setUserFirsName(response.body().getData().getMname());
                        session.setUserAddress(response.body().getData().getAddress());
                        session.setMobile(response.body().getData().getMobile());

                        user_profile2 = response.body().getPath() + response.body().getData().getImage();
                        session.setUserImage(response.body().getPath() + response.body().getData().getImage());
                        session.setVendorImage(response.body().getPath() + response.body().getData().getImage());
                        Picasso.get().load(response.body().getPath() + response.body().getData().getImage()).placeholder(R.drawable.user).into(user_profile);
                        pd.dismiss();
                    }

                }catch (Exception e){
                    pd.dismiss();
                    e.getLocalizedMessage();
                }

            }

            @Override
            public void onFailure(@NonNull Call<AccountModel> call,@NonNull Throwable t) {
                pd.dismiss();
                Log.e("TAG", "onFailure() called with: call = [" + call + "], t = [" + t + "]");
            }
        });



    }

    private void logout() {

        ProgressDialog pd = new ProgressDialog(ProfileActivity.this);
        pd.show();

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

//        if(firebaseAuth.getCurrentUser() != null){
//            firebaseAuth.signOut();
//           /// Toast.makeText(this, "Firebase Logout..!", Toast.LENGTH_SHORT).show();
//        }


        try {
            if(AccessToken.getCurrentAccessToken() != null){
                Log.e("TAG", "logout() called with facebook ");
                LoginManager.getInstance().logOut();
            }

        } catch (Exception e) {
            Log.e("TAG", "logout() called with facebook Error"+e.getLocalizedMessage());
            e.printStackTrace();
        }


        if (firebaseAuth.getCurrentUser() != null) {
             firebaseAuth.signOut();

            GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestEmail()
                    .build();
            GoogleSignInClient mGoogleSignInClient = GoogleSignIn.getClient(getApplicationContext(), gso);

            mGoogleSignInClient.signOut();
        }

        // LoginManager.getInstance().logOut();




        Log.e("TAG", "onResponse() called with:  Session id "+session.getUserId());

        ApiInterface apiInterface = RetrofitClient.getclient(ProfileActivity.this);

        apiInterface.logout(session.getUserId()).enqueue(new Callback<LogOutModel>() {
            @Override
            public void onResponse(@NonNull Call<LogOutModel> call,@NonNull Response<LogOutModel> response) {

               try {
                   if (response.code() == 200)
                       if (response.body() != null) {
                           if (response.body().getResult().equalsIgnoreCase("true")) {
                               session.logOut();
                               startActivity(new Intent(ProfileActivity.this, LoginSecondActivity.class));
                               finish();
                               finishAffinity();
                               pd.dismiss();
                               Toast.makeText(ProfileActivity.this, "Logged out successfully", Toast.LENGTH_SHORT).show();
                           } else {
                               pd.dismiss();
                               Log.e("TAG", "onResponse() called with: call = [" + call + "], response = [" + response.body().getMsg() + "]");
                           }
                       } else
                           Log.e("TAG", "onResponse() called with: call =  response = [" + response + "]");
                   else
                       Log.e("TAG", "onResponse() called with: call =  response = [" + response.code() + "]");

               }catch (Exception e){
                   pd.dismiss();
                   Log.e("TAG", "onResponse() called with: call = [" + call + "], response = [" + e.getLocalizedMessage() + "]");
                   e.printStackTrace();
               }

            }
            @Override
            public void onFailure(@NonNull Call<LogOutModel> call,@NonNull Throwable t) {
                pd.dismiss();
                Log.e("TAG", "onFailure() called with: call = [" + call + "], t = [" + t + "]");
            }
        });




    }

    @Override
    protected void onResume() {
        super.onResume();

        if(session.sharedPreferences.contains("user_id_vendor")){
            session.setUserType("Vendor");
            create_business_account_tv.setText(R.string.switch_to_business);
        }
        get_profile();
    }

    public void profileDialog() {

        final Dialog dialog = new Dialog(ProfileActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.upload_image_layout);

        ImageView image = (ImageView) dialog.findViewById(R.id.image);
        ImageView close = (ImageView) dialog.findViewById(R.id.cancel_image);
        TextView  upload_btn = (TextView) dialog.findViewById(R.id.upload_btn);


        Picasso.get().load(user_profile2).placeholder(R.drawable.user).into(image);
        close.setOnClickListener(view -> dialog.dismiss());


        upload_btn.setVisibility(View.GONE);

        dialog.show();

    }
}