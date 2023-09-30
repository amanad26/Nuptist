package com.Nuptist.frags;

import static com.facebook.FacebookSdk.getApplicationContext;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.Nuptist.R;
import com.Nuptist.RetrofitApis.MapInterface;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class VenueMapFragment extends Fragment {

    double lat , lang ;
    boolean is_activity;
    MapInterface mapInterface ;
    
    public  VenueMapFragment(double lat , double lang, boolean isactiity, MapInterface mapInterface){
        this.lat = lat ;
        this.lang = lang ;
        this.is_activity = isactiity ;
        this.mapInterface = mapInterface ;
    }
    
    

    private OnMapReadyCallback callback = new OnMapReadyCallback() {

        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */
        @Override
        public void onMapReady(GoogleMap googleMap) {

            GoogleMap map = googleMap ;

            float zoom = 15f;
            LatLng indore = new LatLng(lat, lang);
            googleMap.addMarker(new MarkerOptions().position(indore).title("You are here"));
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(indore, zoom));
            if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            googleMap.setMyLocationEnabled(true);


//
//            if(!is_activity){
//                googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
//                    @Override
//                    public void onMapClick(LatLng latLng) {
//
//                        String lattitudereiceve = String.valueOf(latLng.latitude);
//                        String longituderecieve = String.valueOf(latLng.longitude);
//
//                        MarkerOptions markerOptions = new MarkerOptions();
//
//                        markerOptions.position(latLng).title("You are here");
//
//                        markerOptions.title(latLng.latitude + " : " + latLng.longitude);
//                        Log.e("TAG", "onSuccess: " + lattitudereiceve + longituderecieve);
//
//                        //  txlocation.setText(address);
//                        try {
//                            Geocoder geocoder;
//                            geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
//                            List<Address> addresses = null;
//                            addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
//                            //searchBarDestination.setText(addresses.get(0).getAddressLine(0));
//                            System.out.println("suggestion+    " + addresses.get(0).getAddressLine(0));
//                            Log.e("TAG", "Address: " + addresses.get(0).getAddressLine(0));
//
//                            mapInterface.getAddress(Double.valueOf(lattitudereiceve),Double.valueOf(longituderecieve),addresses.get(0).getAddressLine(0));
//
//                            // onBackPressed();
//                     /* Intent intent = new Intent( SearchActivity.this,  UpdateSelectradiusfromhomeActivity .class);
//                    intent.putExtra("type",type); startActivity(intent);
//                    startActivity(intent);
//                   finish();*/
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//
//// CleamarkerOptionsrs the previously touched position
//                        googleMap.clear();
//
//// Animating to the touched position
//                        googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
//
//// Placing a marker on the touched position
//                        googleMap.addMarker(markerOptions);
//                    }
//                });
//            }
//




        }


//
//        LatLng sydney = new LatLng(-34, 151);
//            googleMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//            googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
//        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_venue_map, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }
    }
}