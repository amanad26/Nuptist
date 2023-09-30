package com.Nuptist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.Nuptist.Models.GPSTracker;
import com.Nuptist.RetrofitApis.MapInterface;
import com.Nuptist.databinding.ActivityChangeAddresBinding;
import com.Nuptist.frags.VenueMapFragment;
import com.Nuptist.venue.VenueActivity;

public class ChangeAddresActivity extends AppCompatActivity implements MapInterface {


    ActivityChangeAddresBinding binding ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         binding = ActivityChangeAddresBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        GPSTracker gpsTracker = new GPSTracker(ChangeAddresActivity.this);

       double lcat = gpsTracker.getLatitude();
       double lang = gpsTracker.getLongitude();



        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.fragment_container, new VenueMapFragment(lcat,lang,false, ChangeAddresActivity.this));
        transaction.commit();



    }

    @Override
    public void getAddress(double lat, double lang, String address) {

    }
}