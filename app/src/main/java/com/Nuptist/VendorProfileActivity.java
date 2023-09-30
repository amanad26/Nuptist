package com.Nuptist;


import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.Nuptist.Chat.ChatActivity;
import com.Nuptist.Models.LogOutModel;
import com.Nuptist.Models.ProgressDialog;
import com.Nuptist.Models.VendorProfileModel;
import com.Nuptist.RetrofitApis.ApiInterface;
import com.Nuptist.RetrofitApis.RetrofitClient;
import com.Nuptist.Session.Session;
import com.Nuptist.VendorPackages.Bids.MyBidsActivity;
import com.Nuptist.VendorPackages.Bids.OffersBid.MyOffersBidsActivity;
import com.Nuptist.VendorPackages.VendorPackagesActivity;
import com.Nuptist.venue.AddVenueActivity;
import com.Nuptist.venue.VenueActivity;

import com.facebook.AccessToken;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VendorProfileActivity extends AppCompatActivity {

    ImageView ic_back;
    LinearLayout all_packages,upcoming_events,notification , my_schedule_linear, inbox_linear, allbids, offer_allbids, create_offer, my_profile_linear, logout_btn, pravicy_policy, swicth_personal, venue_add;

    Session session;

    VendorProfileActivity activity ;
    TextView vendor_email, vendor_name, update_venue;
    RoundedImageView vendor_profile;
    String status = "0";
    private String user_profile = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendor_profile);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        Window window = getWindow();
        window.setStatusBarColor(getResources().getColor(R.color.white));

        activity = this;
        FacebookSdk.sdkInitialize(activity);
        session = new Session(activity);

       /// create_offer = findViewById(R.id.create_offer);
        my_profile_linear = findViewById(R.id.my_profile_linear);
        ic_back = findViewById(R.id.ic_back);
        vendor_email = findViewById(R.id.vendor_email);
        vendor_name = findViewById(R.id.vendor_name);
        swicth_personal = findViewById(R.id.swicth_personal);
        venue_add = findViewById(R.id.venue_add);
        vendor_profile = findViewById(R.id.vendor_profile);
        pravicy_policy = findViewById(R.id.pravicy_policy);
        logout_btn = findViewById(R.id.logout_btn);
        all_packages = findViewById(R.id.all_packages2);
        allbids = findViewById(R.id.allbids);
        offer_allbids = findViewById(R.id.offer_allbids);
        inbox_linear = findViewById(R.id.inbox_linear);
        my_schedule_linear = findViewById(R.id.my_schedule_linear);
        upcoming_events = findViewById(R.id.upcoming_events);
        notification = findViewById(R.id.notification_linear);
        update_venue = findViewById(R.id.update_venue);

        vendor_email.setText(session.getVendorEmail());
        vendor_name.setText(session.getUserVendorName());

        if (!session.getVendorImage().equalsIgnoreCase("")) {
            Picasso.get().load(session.getVendorImage()).placeholder(R.drawable.user).into(vendor_profile);
        }

        if (session.getUserType().equalsIgnoreCase("Customer")) {
            startActivity(new Intent(activity, ProfileActivity.class));
            finish();
        }

        get_profile();

        try {
            if (session.getVenue().equalsIgnoreCase("venue_added")) {
                update_venue.setText("Edit Venue Details");
            } else {
              update_venue.setText("Add Venue");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        vendor_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                profileDialog();
            }
        });

        swicth_personal.setOnClickListener(view -> {
            session.setUserType("Customer");
            session.setIsPersonal(true);
            startActivity(new Intent(activity, ProfileActivity.class));
            finish();
        });

        notification.setOnClickListener(view -> {
            Intent intent = new Intent(VendorProfileActivity.this, NotificationActivity.class);
            startActivity(intent);
        });


        pravicy_policy.setOnClickListener(view -> {
            Intent intent = new Intent(activity, PrivacyActivity.class);
            startActivity(intent);
        });

        inbox_linear.setOnClickListener(view -> {
            Intent intent = new Intent(activity, ChatActivity.class);
            startActivity(intent);
        });

        my_schedule_linear.setOnClickListener(view -> {
            Intent intent = new Intent(activity, VendorBookingAllPackageActivity.class);
            startActivity(intent);
        });

        upcoming_events.setOnClickListener(view -> {
            Intent intent = new Intent(activity, UpcommingEventsActivity.class);
            startActivity(intent);
        });

        venue_add.setOnClickListener(view -> {

            if (status.equalsIgnoreCase("1")) {

                if (session.getVenue().equalsIgnoreCase("venue_added")) {
                    startActivity(new Intent(activity, VenueActivity.class));
                    finish();
                } else {
                    startActivity(new Intent(activity, AddVenueActivity.class));
                    finish();
                }

            } else {
                showDialog();
                Toast.makeText(activity, "Not Verified..!", Toast.LENGTH_SHORT).show();
            }

        });

        ic_back.setOnClickListener(view -> {
            onBackPressed();
        });

        allbids.setOnClickListener(view -> {
            Intent intent = new Intent(activity, MyBidsActivity.class);
            startActivity(intent);
        });

        offer_allbids.setOnClickListener(view -> {
            Intent intent = new Intent(activity, MyOffersBidsActivity.class);
            startActivity(intent);
        });

        my_profile_linear.setOnClickListener(view -> {
            Intent intent = new Intent(activity, UpdateVendorProfile.class);
            startActivity(intent);
        });

        logout_btn.setOnClickListener(view -> logout());

        all_packages.setOnClickListener(view -> startActivity(new Intent(activity, VendorPackagesActivity.class)));


    }

    public void profileDialog() {


        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.upload_image_layout);

        ImageView image = (ImageView) dialog.findViewById(R.id.image);
        ImageView close = (ImageView) dialog.findViewById(R.id.cancel_image);
        TextView upload_btn = (TextView) dialog.findViewById(R.id.upload_btn);


        Picasso.get().load(user_profile).placeholder(R.drawable.user).into(image);
        close.setOnClickListener(view -> dialog.dismiss());


        upload_btn.setVisibility(View.GONE);

        dialog.show();

    }

    private void showDialog() {


        final Dialog dialog = new Dialog(activity);

        dialog.setContentView(R.layout.conformation_layout);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(false);
        dialog.getWindow().getAttributes().windowAnimations = R.style.animation;

        LinearLayout close = dialog.findViewById(R.id.dissmiss2);
        LinearLayout vendor_profile = dialog.findViewById(R.id.vendor_profile);
        TextView viewPrivacyPolicy = dialog.findViewById(R.id.view_privacy_policy);

        close.setOnClickListener(view -> dialog.dismiss());

        viewPrivacyPolicy.setOnClickListener(view -> startActivity(new Intent(activity, PrivacyActivity.class)));

        vendor_profile.setOnClickListener(view -> dialog.dismiss());

        dialog.show();


    }


    private void logout() {

        ProgressDialog pd = new ProgressDialog(activity);
        pd.show();

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

        if (firebaseAuth.getCurrentUser() != null) {
            firebaseAuth.signOut();

            GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestEmail()
                    .build();
            GoogleSignInClient mGoogleSignInClient = GoogleSignIn.getClient(getApplicationContext(), gso);
            mGoogleSignInClient.signOut();

        }


        try {
            if(AccessToken.getCurrentAccessToken() != null){
                Log.e("TAG", "logout() called with facebook ");
                LoginManager.getInstance().logOut();
            }

        } catch (Exception e) {
            Log.e("TAG", "logout() called with facebook Error"+e.getLocalizedMessage());
            e.printStackTrace();
        }

        Log.e("TAG", "onResponse() called with:  Session id " + session.getUserId());

        ApiInterface apiInterface = RetrofitClient.getclient(activity);

        apiInterface.logout(session.getUserId()).enqueue(new Callback<LogOutModel>() {
            @Override
            public void onResponse(@NonNull Call<LogOutModel> call, @NonNull Response<LogOutModel> response) {

                try {
                    if (response.code() == 200)
                        if (response.body() != null) {
                            if (response.body().getResult().equalsIgnoreCase("true")) {
                                session.logOut();
                                startActivity(new Intent(activity, LoginSecondActivity.class));
                                finish();
                                pd.dismiss();
                                Toast.makeText(activity, "Logged out successfully", Toast.LENGTH_SHORT).show();
                            } else {
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
            public void onFailure(@NonNull Call<LogOutModel> call, @NonNull Throwable t) {
                pd.dismiss();
                Log.e("TAG", "onFailure() called with: call = [" + call + "], t = [" + t + "]");
            }
        });

    }

    private void get_profile() {

        ProgressDialog pd = new ProgressDialog(activity);
        pd.show();

        ApiInterface apiInterface = RetrofitClient.getclient(activity);

        apiInterface.get_vendor_profile(session.getUserId_Vendor()).enqueue(new Callback<VendorProfileModel>() {
            @Override
            public void onResponse(@NonNull Call<VendorProfileModel> call, @NonNull Response<VendorProfileModel> response) {

                pd.dismiss();
                try {

                    if (response.body() != null && response.body().getResult().equalsIgnoreCase("true")) {

                        vendor_email.setText(response.body().getData().getEmail());
                        vendor_name.setText(response.body().getData().getName());
                        Log.e("TAG", "Vendor Profile Image  = [" + response.body().getPath() + response.body().getData().getImage() + "]");

                        session.setUserVendorEmail(response.body().getData().getEmail());
                        session.setUserVendorName(response.body().getData().getName());
                        session.setVendorImage(response.body().getPath() + response.body().getData().getImage());
                        session.setUserAddress(response.body().getPath() + response.body().getData().getImage());

                        status = response.body().getData().getProfile();

                        user_profile = response.body().getPath() + response.body().getData().getImage();

                        Log.d("TAG", "Vendor Profile Image  = [" + response.body().getPath() + response.body().getData().getImage() + "]");
                        Picasso.get().load(response.body().getPath() + response.body().getData().getImage()).placeholder(R.drawable.user).into(vendor_profile);
                        pd.dismiss();
                    }

                } catch (Exception e) {
                    Log.e("TAG", "Vendor Profile Image  = [" + e.getLocalizedMessage() + "]");

                    pd.dismiss();
                    e.getLocalizedMessage();
                }

            }

            @Override
            public void onFailure(@NonNull Call<VendorProfileModel> call, @NonNull Throwable t) {
                pd.dismiss();
                Log.e("TAG", "onFailure() called with: call = [" + call + "], t = [" + t + "]");
            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        get_profile();
    }
}










