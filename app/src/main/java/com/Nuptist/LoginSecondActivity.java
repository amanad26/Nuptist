package com.Nuptist;

import static com.Nuptist.R.color.*;
import static com.facebook.FacebookSdk.setAutoLogAppEventsEnabled;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.Nuptist.Models.AccountModel;
import com.Nuptist.Models.GPSTracker;
import com.Nuptist.Models.LoginModel;
import com.Nuptist.Models.ProgressDialog;
import com.Nuptist.RetrofitApis.ApiInterface;
import com.Nuptist.RetrofitApis.RetrofitClient;
import com.Nuptist.Session.Session;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.skydoves.elasticviews.ElasticCardView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import soup.neumorphism.NeumorphCardView;


public class LoginSecondActivity extends AppCompatActivity {
    TextView Otpverify, signup, to_check;
    /* TextView AG_SIGNUP;
    ImageView AG_login;
*/
    Session session;
    EditText edt_mail;
    LoginButton login_button;


    String mail = "";
    EditText signin_phone, signin_password;
    TextView signin_forgot_password;

    ProgressDialog pd;
    ElasticCardView google_sign_in;
    private GoogleSignInClient googleSignInClient;
    int RC_SIGN_IN = 200;

    CallbackManager mCallbackManager;

    private FirebaseAuth mAuth;
    ElasticCardView fb;
    NeumorphCardView cardView1, cardView2;
    private String token_fcm_id;
    private String my_address, my_city, country_location;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        Window window = getWindow();
        window.setStatusBarColor(getResources().getColor(colorPrimary));
        Otpverify = findViewById(R.id.Otpverify);
        signup = findViewById(R.id.signup);
        signin_password = findViewById(R.id.signin_password);
        signin_phone = findViewById(R.id.signin_phone);
        fb = findViewById(R.id.facebook);
        signin_forgot_password = findViewById(R.id.signin_forgot_password);
        google_sign_in = findViewById(R.id.google_sign_in);
        cardView1 = findViewById(R.id.signUpButton);
        cardView2 = findViewById(R.id.signUpButton2);
        mAuth = FirebaseAuth.getInstance();

        mCallbackManager = CallbackManager.Factory.create();

        session = new Session(LoginSecondActivity.this);

        login_button = findViewById(R.id.login_button);

        pd = new ProgressDialog(LoginSecondActivity.this);
        /// pd.setMessage("Please Wait..");

        showLogin(signin_password, cardView1);

        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(instanceIdResult -> {
            String fcm_id = instanceIdResult.getToken();
            // send it to server
            token_fcm_id = fcm_id;
            Log.e("refresh_tokentoken", fcm_id);
        });

        request_permission(session);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();


        googleSignInClient = GoogleSignIn.getClient(this, gso);


        google_sign_in.setOnClickListener(view -> {
            pd.show();
            Intent intent = googleSignInClient.getSignInIntent();
            startActivityForResult(intent, RC_SIGN_IN);

        });


        fb.setOnClickListener(view -> {
//             dialogshow();
            login_button.performClick();
        });

        login_button.setOnClickListener(view -> facebookcall());


        Otpverify.setOnClickListener(view -> {


            if (signin_phone.getText().toString().equalsIgnoreCase("")) {
                signin_phone.setError("Enter Email ");
                signin_phone.requestFocus();
            } else if (!emailValidator(signin_phone.getText().toString())) {
                signin_phone.setError("Invalid  Email");
                signin_phone.requestFocus();
            } else if (signin_password.getText().toString().equalsIgnoreCase("")) {
                signin_password.setError("Enter Password ");
                signin_password.requestFocus();
            } else {
                pd.show();
                login();
            }


        });

        signup.setOnClickListener(view -> {
            Intent intent = new Intent(LoginSecondActivity.this, SignUpActivity.class);
            startActivity(intent);
        });

        signin_forgot_password.setOnClickListener(view -> {
            Intent intent = new Intent(LoginSecondActivity.this, ForgotPasswordActivity.class);
            startActivity(intent);
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

    public void facebookcall() {
        {
            login_button.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
                @Override
                public void onSuccess(LoginResult loginResult) {
                    Log.d(ContentValues.TAG, "facebook:onSuccess:" + loginResult.toString());
                    GraphRequest request = GraphRequest.newMeRequest(
                            loginResult.getAccessToken(),
                            new GraphRequest.GraphJSONObjectCallback() {
                                @Override
                                public void onCompleted(JSONObject object,
                                                        GraphResponse response) {
                                    Log.e(ContentValues.TAG, "onCompleted: " + AccessToken.getCurrentAccessToken());
                                    Log.e(ContentValues.TAG, "onCompleted: " + AccessToken.getCurrentAccessToken()
                                            .getUserId());

                                    Log.e("SignUpActivity",
                                            response.toString());
                                    Log.e("TAG", "onCompleted: " + response.getJSONObject().
                                            toString().contains("email"));
                                    try {
                                        String fbid = object.getString("id");
                                        String fbname = object.getString("name");

                                        Toast.makeText(LoginSecondActivity.this, fbname, Toast.LENGTH_SHORT).show();
                                        facebook_sign_in(fbid, fbname);


                                        Log.e(ContentValues.TAG, "onCompleted:fbname   " + fbname);
                                        ///  facebook_login(fbname, fbid, token);

                                        if (response.getJSONObject().toString().contains("email") == false) {
                                            Log.e("emailnull", "onCompleted: ");
                                            // facebook_sign_in(fbid,fbname);
                                        } else {
                                            Log.e("email", "onCompleted: ");
                                            String fbemail = object.getString("email");
                                            Log.e("facebookdata", "onCompleted: " + fbemail);
                                            // facebook_sign_in(fbid,fbname);
                                        }


                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });

                    Bundle parameters = new Bundle();
                    parameters.putString("fields", "id,name,email,gender, birthday");
                    request.setParameters(parameters);
                    request.executeAsync();
                }

                @Override
                public void onCancel() {
                    pd.dismiss();
                    Log.d(ContentValues.TAG, "facebook:onCancel");
                }

                @Override
                public void onError(@NonNull FacebookException error) {
                    pd.dismiss();
                    Log.d(ContentValues.TAG, "facebook:onError", error);
                }
            });


            printHashKey();
            setAutoLogAppEventsEnabled(true);

        }
    }

    private void printHashKey() {

        // Add code to print out the key hash
        try {

            PackageInfo info
                    = getPackageManager().getPackageInfo(
                    "com.Nuptist",
                    PackageManager.GET_SIGNATURES);

            for (Signature signature : info.signatures) {

                MessageDigest md
                        = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.e("KeyHash:",
                        Base64.encodeToString(
                                md.digest(),
                                Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {
        } catch (NoSuchAlgorithmException e) {
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {

            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);

            try {
                GoogleSignInAccount account = task.getResult();
                if (account != null) {
                    Log.e("OnREsponse", "Account Id = " + account.getId());
                }
                AuthCredential authCredential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
                mAuth.signInWithCredential(authCredential)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Log.d("TAG", "signInWithCredential:success");
                                    FirebaseUser user = mAuth.getCurrentUser();

                                    if (user != null) {
                                        Log.e("onResponse", "Username :-" + user.getDisplayName());
                                    }
                                    Log.e("onResponse", "Email :-" + user.getEmail());
                                    ;
                                    Log.e("onResponse", "mobile :-" + user.getPhoneNumber());
                                    Log.e("onResponse", "id :-" + user.getUid());

                                    String name, email, mobile, image;
//
                                    name = user.getDisplayName();
                                    email = user.getEmail();
                                    mobile = user.getPhoneNumber();

                                    googlesignIn(name, email, mobile, user.getUid());

                                } else {
                                    Toast.makeText(LoginSecondActivity.this, "Task Not Successful", Toast.LENGTH_SHORT).show();
                                    pd.dismiss();
                                    // If sign in fails, display a message to the user.
                                    Log.e("TAG", "signInWithCredential:failure", task.getException());
                                    //  updateUI(null);
                                }
                            }
                        });
            } catch (Exception e) {
                pd.dismiss();
                /// Toast.makeText(LoginSecondActivity.this, "On error"+e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                Log.e("OnError", "Account Id = Not get" + e.getLocalizedMessage());
            }


        }


    }

    private void googlesignIn(String name, String email, String mobile, String id) {

        //// google sign in api

        ApiInterface apiInterface = RetrofitClient.getclient(LoginSecondActivity.this);

        apiInterface.google_signin(
                email,
                name,
                token_fcm_id,
                id
        ).enqueue(new Callback<AccountModel>() {
            @Override
            public void onResponse(Call<AccountModel> call, Response<AccountModel> response) {
                try {

                    if (response.code() == 200)
                        if (response.body() != null) {
                            if (response.body().getResult().equalsIgnoreCase("true")) {

                                session.setEmail(response.body().getData().getEmail());
                                session.setUser_id(response.body().getData().getId());

                                try {
                                    if (!response.body().getData().getVender_id().equalsIgnoreCase("")) {
                                        session.setUser_id_vendor(response.body().getData().getVender_id());
                                        session.setType("Vendor");
                                        ////session.setVenue();
                                        session.setGalleryImage("Added");
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                                startActivity(new Intent(LoginSecondActivity.this, HomeActivity.class)
                                        .putExtra("address", my_address)
                                        .putExtra("country_location", country_location)
                                        .putExtra("city", my_city));

                                finish();
                                Toast.makeText(LoginSecondActivity.this, "Account Created..", Toast.LENGTH_SHORT).show();
                                pd.dismiss();
                            } else {
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
                    pd.dismiss();
                }

                pd.dismiss();
            }

            @Override
            public void onFailure(Call<AccountModel> call, Throwable t) {
                pd.dismiss();
                Log.e("TAG", "onFailure() called with: call = [" + call + "], t = [" + t + "]");
            }
        });


    }

    private void facebook_sign_in(String fb_id, String fb_name) {

        pd.show();
        ////
        Log.e("TAG", "facebook_sign_in() called with: fb_id = [" + fb_id + "], fb_name = [" + fb_name + "]");

        ////// facebook sign in api here

        ApiInterface apiInterface = RetrofitClient.getclient(LoginSecondActivity.this);

        apiInterface.facebook_sign_in(
                fb_name,
                token_fcm_id,
                fb_id
        ).enqueue(new Callback<AccountModel>() {
            @Override
            public void onResponse(@NonNull Call<AccountModel> call, @NonNull Response<AccountModel> response) {
                pd.dismiss();
                try {

                    if (response.code() == 200)
                        if (response.body() != null) {
                            if (response.body().getResult().equalsIgnoreCase("true")) {

                                session.setUser_id(response.body().getData().getId());
                                session.setUserName(fb_name);
                                session.setEmail("Not Available");

                                try {
                                    if (!response.body().getData().getVender_id().equalsIgnoreCase("")) {
                                        session.setUser_id_vendor(response.body().getData().getVender_id());
                                        session.setType("Vendor");
                                        ////session.setVenue();
                                        session.setGalleryImage("Added");
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }


                                startActivity(new Intent(LoginSecondActivity.this, HomeActivity.class)
                                        .putExtra("address", my_address)
                                        .putExtra("country_location", country_location)
                                        .putExtra("city", my_city));
                                finish();
                                Toast.makeText(LoginSecondActivity.this, "Account Created..", Toast.LENGTH_SHORT).show();
                            } else {
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
            public void onFailure(@NonNull Call<AccountModel> call, @NonNull Throwable t) {
                pd.dismiss();
                Log.e("TAG", "onFailure() called with: call = [" + call + "], t = [" + t + "]");
            }
        });


    }

    private void login() {

        ApiInterface apiInterface = RetrofitClient.getclient(LoginSecondActivity.this);

        apiInterface.login(signin_phone.getText().toString(), signin_password.getText().toString(), token_fcm_id).enqueue(new Callback<LoginModel>() {
            @Override
            public void onResponse(@NonNull Call<LoginModel> call, @NonNull Response<LoginModel> response) {

                try {
                    if (response.body() != null) {
                        if (response.body().getResult().equalsIgnoreCase("true")) {

                            pd.dismiss();

                            Session session = new Session(LoginSecondActivity.this);
                            session.setEmail(response.body().getData().getEmail());
                            session.setUser_id(response.body().getData().getId());

                            if (!response.body().getData().getVender_id().equalsIgnoreCase("")) {
                                session.setUser_id_vendor(response.body().getData().getVender_id());
                                session.setType("Vendor");
                                //// session.setVenue();
                                session.setGalleryImage("Added");
                            }

                            Intent intent = new Intent(LoginSecondActivity.this, HomeActivity.class);
                            intent.putExtra("address", my_address);
                            intent.putExtra("city", my_city);
                            intent.putExtra("country_location", country_location);
                            startActivity(intent);

                            Toast.makeText(LoginSecondActivity.this, response.body().getMsg(), Toast.LENGTH_SHORT).show();

                            finishAffinity();
                        } else {
                            pd.dismiss();
                            Toast.makeText(LoginSecondActivity.this, response.body().getMsg(), Toast.LENGTH_SHORT).show();
                        }
                    }

                } catch (Exception e) {
                    pd.dismiss();
                    e.printStackTrace();
                    Toast.makeText(LoginSecondActivity.this, e.toString(), Toast.LENGTH_SHORT).show();

                }


            }

            @Override
            public void onFailure(@NonNull Call<LoginModel> call, @NonNull Throwable t) {
                pd.dismiss();
                Log.e("TAG", "onFailure() called with: call = [" + call + "], t = [" + t.getLocalizedMessage() + "]");
            }
        });

    }

    private void request_permission(Session session) {

        if (ContextCompat.checkSelfPermission(LoginSecondActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(LoginSecondActivity.this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                ActivityCompat.requestPermissions(LoginSecondActivity.this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            } else {
                ActivityCompat.requestPermissions(LoginSecondActivity.this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            }
        } else {
            GPSTracker gpsTracker = new GPSTracker(LoginSecondActivity.this);

            Geocoder geocoder;
            geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
            List<Address> addresses = null;

            try {
                addresses = geocoder.getFromLocation(gpsTracker.getLatitude(), gpsTracker.getLongitude(), 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
                session.setAddress(addresses.get(0).getAddressLine(0));
                session.setLat(String.valueOf(gpsTracker.getLatitude()));
                session.setLang(String.valueOf(gpsTracker.getLongitude()));


                String city = addresses.get(0).getLocality();
                String subLocality = addresses.get(0).getSubLocality();
                String state = addresses.get(0).getAdminArea();
                String country = addresses.get(0).getCountryName();
                String postalCode = addresses.get(0).getPostalCode();
                my_address = "" + subLocality + " , " + city + " , " + state;
                my_city = city;
                country_location = country;
                session.setMyCity(city);


                Log.e("TAG", "Address: " + addresses.get(0).getAddressLine(0));
            } catch (IOException e) {
                e.printStackTrace();
            }
            //searchBarDestination.setText(addresses.get(0).getAddressLine(0));
            //  System.out.println("suggestion+    " + addresses.get(0).getAddressLine(0));

        }


    }

    public void ShowHidePass(View view) {

        if (view.getId() == R.id.show_hide) {

            if (signin_password.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())) {
                ((ImageView) (view)).setImageResource(R.drawable.ic_baseline_remove_red_eye_24);

                //Show Password
                signin_password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            } else {
                ((ImageView) (view)).setImageResource(R.drawable.ic_baseline_visibility_off_24);

                //Hide Password
                signin_password.setTransformationMethod(PasswordTransformationMethod.getInstance());

            }
        }
    }

    private void showLogin(EditText email, NeumorphCardView card) {

        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (s.toString().equalsIgnoreCase("")) {
                    card.setBackgroundColor(Color.parseColor("#F1F1F1"));
                    cardView2.setVisibility(View.VISIBLE);
                    cardView1.setVisibility(View.GONE);
                } else {
                    email.setClickable(true);
                    card.setBackgroundColor(Color.parseColor("#D83973"));
                    cardView2.setVisibility(View.GONE);
                    cardView1.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


    }

}