package com.Nuptist;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.Nuptist.Chat.SendNotification;
import com.Nuptist.Models.GPSTracker;
import com.Nuptist.Session.Session;

import java.io.IOException;
import java.text.BreakIterator;
import java.util.List;
import java.util.Locale;


public class SplashActivity extends AppCompatActivity {
    private static int SPLASH_SCREEN_TIME_OUT = 2000;
    private String my_address = "";
    private String my_city = "",country_location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        Window window = getWindow();

        Session session = new Session(SplashActivity.this);

        if(!session.getselectedDate().equalsIgnoreCase("")){
            session.setSelectedDate("");
        }

       /// request_permission(session);
        Log.e("TAG", "onCreate() called with: User id  = [" + session.getUserId() + "]");
        Log.e("TAG", "onCreate() called with: Vendor id  = [" + session.getUserId_Vendor() + "]");
        Log.e("TAG", "onCreate() called with: Type  = [" + session.getUserType() + "]");
        Log.e("TAG", "onCreate() called with: email  = [" + session.getEmail() + "]");
        Log.e("TAG", "onCreate() called with: Name  = [" + session.getUserName() + "]");


      ///  Toast.makeText(this, session.getVenue(), Toast.LENGTH_SHORT).show();

//          session.setUser_id("230");
//          session.setUserType("Vendor");
//          session.setUser_id_vendor("231");
//          session.setEmail("khareaman528@gmail.com");
//          session.setUserName("Aman khare");



//        String fcm =  "dC-uAeblTYOa6k-t_Zvqor:APA91bFCLxrX0Xv3557vwoLqq0XURfCri1Jq3B-RVBgGoYqaqU7P5tuyFg0Vh7W1LTO5W4ynweZDVGWtU_HY3Vhgi0K-EwMW48xJRxPlZw4-YHXEph7v-i72j2RPuURqQeOeqbmS75b3";
//        SendNotification.sendNotification(fcm,"Hello Moiz");



        window.setStatusBarColor(getResources().getColor(R.color.colorPrimary));

        new Handler().postDelayed(() -> {

            if(session.sharedPreferences.contains("user_id")){
                try {
                    request_permission(session);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Intent i = new Intent(SplashActivity.this, HomeActivity.class);
                  i.putExtra("address",my_address);
                  i.putExtra("city",my_city);
                  i.putExtra("country_location",country_location);
                 startActivity(i);
                 overridePendingTransition(R.anim.enter, R.anim.exit);
                 finish();

            }else {
            Intent i = new Intent(SplashActivity.this, LoginSecondActivity.class);
            startActivity(i);
            overridePendingTransition(R.anim.enter, R.anim.exit);
            finish();
            }
        }, SPLASH_SCREEN_TIME_OUT);
    }

    private void request_permission(Session session) {

        if (ContextCompat.checkSelfPermission(SplashActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(SplashActivity.this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                ActivityCompat.requestPermissions(SplashActivity.this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            } else {
                ActivityCompat.requestPermissions(SplashActivity.this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            }
        } else {
            GPSTracker gpsTracker = new GPSTracker(SplashActivity.this);

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

}