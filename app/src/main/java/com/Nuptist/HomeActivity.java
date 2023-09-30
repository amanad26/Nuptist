package com.Nuptist;

import static com.Nuptist.VenueFragment.selectedDate;
import static com.Nuptist.adpaters.AddOnsAdapterNew2.addOnceModels;
import static com.Nuptist.adpaters.OfferAdapter2.offersModel;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.Nuptist.Chat.ChatActivity;
import com.Nuptist.Models.GPSTracker;
import com.Nuptist.RetrofitApis.MapInterface;
import com.Nuptist.Session.Session;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.Nuptist.mainscreens.HomeUserFragment;
import com.Nuptist.mainscreens.FavoriteFragment;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class HomeActivity extends AppCompatActivity implements MapInterface {

    String city;

    RelativeLayout profile;
    LinearLayout header_linear;
    TextView my_address, country_location;
    private final int[] mTabIconsSelected = {R.drawable.dashborad, R.drawable.sale, R.drawable.sell, R.drawable.more};
    public BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {

        Session session;
        public ImageView imageView;

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_homeuser:
                    Fragment fragment_home;
                    Session session2 = new Session(HomeActivity.this);

                    if (session2.getUserType().equalsIgnoreCase("Vendor")) {
                        if (session2.getIsPersonal()) {
                            fragment_home = new HomeUserFragment();
                            header_linear.setVisibility(View.VISIBLE);
                        } else {
                           // header_linear.setVisibility(View.GONE);
                            //fragment_home = new VendorHomeFragment();
                            fragment_home = new VendorHomePackagesFragment();
                        }

                    } else {
                        header_linear.setVisibility(View.VISIBLE);
                        fragment_home = new HomeUserFragment();
                    }
                    ////// fragment_home = new HomeUserFragment();
                    FragmentTransaction ft_home = getSupportFragmentManager().beginTransaction();
                    ft_home.replace(R.id.nav_host_fragment_activity_homeuser, fragment_home);
                    ft_home.commit();
                    return true;

                case R.id.navigation_saleuser:

                    Session session = new Session(HomeActivity.this);

                    if (session.getUserType().equalsIgnoreCase("Vendor")) {

                        if (session.getIsPersonal()) {
                            startActivity(new Intent(HomeActivity.this, ProfileActivity.class));
                        } else {
                            startActivity(new Intent(HomeActivity.this, VendorProfileActivity.class));
                        }

                    } else {
                        Intent intent = new Intent(HomeActivity.this, ProfileActivity.class);
                        startActivity(intent);
                    }
                    return false;

                case R.id.navigation_purchaseuser:
//                    Fragment fragment_my_work = new MoreFragment();
//                    FragmentTransaction ft_add = getSupportFragmentManager().beginTransaction();
//                    ft_add.replace(R.id.nav_host_fragment_activity_homeuser, fragment_my_work);
//                    ft_add.commit();
                    Intent intent = new Intent(HomeActivity.this, ChatActivity.class);
                    startActivity(intent);
                    return false;

                case R.id.navigation_moreuser:

                    Fragment fragment_my_work1 = new FavoriteFragment();
                    FragmentTransaction ft_add1 = getSupportFragmentManager().beginTransaction();
                    ft_add1.replace(R.id.nav_host_fragment_activity_homeuser, fragment_my_work1);
                    ft_add1.commit();

                    return true;

            }
            return false;

        }


    };

    BottomNavigationView bottom_nav_viewuser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);

        BottomNavigationView bottom_nav_view = findViewById(R.id.nav_viewuser);

        /*AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_sale, R.id.navigation_purchase, R.id.navigation_more)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_home);

        NavigationUI.setupWithNavController(navView, navController);
*/

        Session session = new Session(HomeActivity.this);

        ImageView img = findViewById(R.id.user_image_new);
        TextView country_location = findViewById(R.id.country_location);
        header_linear = findViewById(R.id.header_linear);
        ///Toast.makeText(this, session.getVendorImage(), Toast.LENGTH_SHORT).show();

        if (session.getUserType().equalsIgnoreCase("Vendor")) {
            if (!session.getVendorImage().equalsIgnoreCase(""))
                Picasso.get().load(session.getVendorImage()).placeholder(R.drawable.user).into(img);
        } else {
            if (!session.getUserImage().equalsIgnoreCase(""))
                Picasso.get().load(session.getUserImage()).placeholder(R.drawable.user).into(img);
        }

        profile = findViewById(R.id.profile);
        my_address = findViewById(R.id.my_address);
        country_location = findViewById(R.id.country_location);

        ///request_permission(session);

        try {
            request_permission(session , country_location);
            String add = getIntent().getStringExtra("address");
            String city = getIntent().getStringExtra("city");
            String country = getIntent().getStringExtra("country_location");

            Log.e("TAG", "onCreate() called with: Address = [" + add + "]");
            Log.e("TAG", "onCreate() called with: city = [" + city + "]");
            Log.e("TAG", "onCreate() called with: country = [" + country + "]");

            if (add != null) {
                my_address.setText(add);
                if (country.equalsIgnoreCase("")) {
                   /// country_location.setText("India");
                    country_location.setText("Location");
                } else {
                    country_location.setText(country);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

//        try {
//            String add = getIntent().getStringExtra("address");
//
//            if(add != null && add.equalsIgnoreCase("")){
//            }else {
//                my_address.setText(add);
//            }
//        }catch (Exception e){
//            e.printStackTrace();
//        }

        my_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this, SearchLocationActivity.class));
            }
        });


//        if(isconnected()){
//            Log.e("TAG", "onCreate() called with: Connected = [" + isconnected() + "]");
//          showdialog();
//        }else {
//            showdialog();
//            Log.e("TAG", "onCreate() called with: Not  Connected = [" + isconnected() + "]");
//        }


        bottom_nav_view.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


        String fromProfile = "";
        if (getIntent() != null) {
            fromProfile = getIntent().getStringExtra("profile");

            if (fromProfile != null && fromProfile.equalsIgnoreCase("12345")) {
                bottom_nav_view.setSelectedItemId(R.id.navigation_purchaseuser);
            }
        }

        profile.setOnClickListener(view -> {

            Session session1 = new Session(HomeActivity.this);

            if (session1.getUserType().equalsIgnoreCase("Vendor")) {

                if (session1.getIsPersonal()) {
                    startActivity(new Intent(HomeActivity.this, ProfileActivity.class));
                } else {
                    startActivity(new Intent(HomeActivity.this, VendorProfileActivity.class));
                }

            } else {
                Intent intent = new Intent(HomeActivity.this, ProfileActivity.class);
                startActivity(intent);
            }


        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        ImageView img = findViewById(R.id.user_image_new);
        Session session = new Session(HomeActivity.this);

        if (session.getUserType().equalsIgnoreCase("Vendor")) {

            if (session.getIsPersonal()) {
                if (!session.getUserImage().equalsIgnoreCase(""))
                    Picasso.get().load(session.getUserImage()).placeholder(R.drawable.user).into(img);
            } else {
                if (!session.getVendorImage().equalsIgnoreCase(""))
                    Picasso.get().load(session.getVendorImage()).placeholder(R.drawable.user).into(img);
            }

        } else {
            if (!session.getUserImage().equalsIgnoreCase(""))
                Picasso.get().load(session.getUserImage()).placeholder(R.drawable.user).into(img);
        }

        if (addOnceModels.size() != 0) {
            addOnceModels.clear();
        }

        if (offersModel.size() != 0) {
            offersModel.clear();
        }

        if(!selectedDate.equalsIgnoreCase(""))
            selectedDate = "";

        if(!session.getselectedDate().equalsIgnoreCase("")){
            session.setSelectedDate("");
        }


        Fragment fragment_home;
        Session session2 = new Session(HomeActivity.this);

        if (session2.getUserType().equalsIgnoreCase("Vendor")) {
            if (session2.getIsPersonal()) {
                fragment_home = new HomeUserFragment();
                header_linear.setVisibility(View.VISIBLE);
            } else {
               // header_linear.setVisibility(View.GONE);
              //  fragment_home = new VendorHomeFragment();
                fragment_home = new VendorHomePackagesFragment();
            }

        } else {
            header_linear.setVisibility(View.VISIBLE);
            fragment_home = new HomeUserFragment();
        }
        ////// fragment_home = new HomeUserFragment();
        FragmentTransaction ft_home = getSupportFragmentManager().beginTransaction();
        ft_home.replace(R.id.nav_host_fragment_activity_homeuser, fragment_home);
        ft_home.commit();
    }

    private boolean isconnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED || connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            //we are connected to a network
            return true;
        } else return false;

    }

    private void request_permission(Session session, TextView country_location2) {

        if (ContextCompat.checkSelfPermission(HomeActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(HomeActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                ActivityCompat.requestPermissions(HomeActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            } else {
                ActivityCompat.requestPermissions(HomeActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            }
        } else {
            GPSTracker gpsTracker = new GPSTracker(HomeActivity.this);

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
                my_address.setText("" + subLocality + " , " + city + " , " + state);
                country_location2.setText(country);

                Log.e("TAG", "Address: " + addresses.get(0).getAddressLine(0));

            } catch (IOException e) {
                e.printStackTrace();
            }
            //searchBarDestination.setText(addresses.get(0).getAddressLine(0));
            //  System.out.println("suggestion+    " + addresses.get(0).getAddressLine(0));

        }

    }


    @Override
    public void getAddress(double lat, double lang, String address) {

    }
}

