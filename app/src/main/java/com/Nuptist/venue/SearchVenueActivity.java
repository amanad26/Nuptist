package com.Nuptist.venue;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.Nuptist.HomeActivity;
import com.Nuptist.Models.GPSTracker;
import com.Nuptist.R;
import com.Nuptist.SearchPackageActivity;
import com.Nuptist.Session.Session;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.AutocompletePrediction;
import com.google.android.libraries.places.api.model.AutocompleteSessionToken;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.FetchPlaceRequest;
import com.google.android.libraries.places.api.net.FetchPlaceResponse;
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest;
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsResponse;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.mancj.materialsearchbar.MaterialSearchBar;
import com.mancj.materialsearchbar.adapter.SuggestionsAdapter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class SearchVenueActivity extends AppCompatActivity implements OnMapReadyCallback {

    private static final long MIN_TIME_BW_UPDATES = 1000;
    private static final float MIN_DISTANCE_CHANGE_FOR_UPDATES = 1;
    private final float DEFAULT_ZOOM = 15;
    public MaterialSearchBar searchBarDestination;
    GPSTracker gps;
    double latitude, longitude;
    String longi, latt;
    double lattitude;
    double dest_lattitude, dest_longitude;
    ImageView marker_img;
    Location location;
    LocationManager locationManager;
    Session session;
    String suggestion;
    private GoogleMap mMap;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private PlacesClient placesClient;
    private List<AutocompletePrediction> predictionList;
    private Location mLastKnownLocation;
    private LocationCallback locationCallback;
    private View mapView;
    private LinearLayout confirm_location_linear ;
    String From;
    private String fulladress , lat , lang;
    String Mycity = "",country_location ;
    String activity = "";

    public  static String selected_address = "" ;
    public  static  double selected_lat_1 , selected_lang_1 ;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_venue);
        
        marker_img = findViewById(R.id.marker_img);
        confirm_location_linear = findViewById(R.id.confirm_location_linear);

        searchBarDestination = findViewById(R.id.destination);
        session = new Session(getApplicationContext());
        if (getIntent() != null) {
            activity = getIntent().getStringExtra("where");
            From = getIntent().getStringExtra("From");
        }

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
        if (mapFragment != null) {
            mapView = mapFragment.getView();
        }

        /////////////  AIzaSyBVbQq3xiusXdf7RxV0SP2kH6Qvl5ZRTkk   AIzaSyCeOAG6ceVMhLhmG1WPFjlB85J1pY7FRRM
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(SearchVenueActivity.this);
        Places.initialize(SearchVenueActivity.this, getResources().getString(R.string.google_maps_key));
        placesClient = Places.createClient(this);
        final AutocompleteSessionToken token = AutocompleteSessionToken.newInstance();


        searchBarDestination.enableSearch();
        getlatlang();
        getCurrentAddress();


        searchBarDestination.setOnSearchActionListener(new MaterialSearchBar.OnSearchActionListener() {
            @Override
            public void onSearchStateChanged(boolean enabled) {
            }

            @Override
            public void onSearchConfirmed(CharSequence text) {
                startSearch(text.toString(), true, null, true);
            }

            @Override
            public void onButtonClicked(int buttonCode) {

                Toast.makeText(SearchVenueActivity.this, "Yes", Toast.LENGTH_SHORT).show();

              /*
                if (buttonCode == MaterialSearchBar.BUTTON_NAVIGATION) {
                } else if (buttonCode == MaterialSearchBar.BUTTON_BACK) {
                    searchBarDestination.disableSearch();
                }
                */
            }
        });

        confirm_location_linear.setOnClickListener(view -> {

            if(activity.equalsIgnoreCase("venue")){
                selected_lat_1 = Double.parseDouble(lat);
                selected_lang_1 = Double.parseDouble(lang);
                selected_address = fulladress ;
                finish();
            }else if(activity.equalsIgnoreCase("update_venue")){
                selected_lat_1 = Double.parseDouble(lat);
                selected_lang_1 = Double.parseDouble(lang);
                selected_address = fulladress ;
                finish();
            }else if(activity.equalsIgnoreCase("user_profile")){
                selected_lat_1 = Double.parseDouble(lat);
                selected_lang_1 = Double.parseDouble(lang);
                selected_address = fulladress ;
                finish();
            }else if(activity.equalsIgnoreCase("vendor_profile")){
                selected_lat_1 = Double.parseDouble(lat);
                selected_lang_1 = Double.parseDouble(lang);
                selected_address = fulladress ;
                finish();
            }else if(activity.equalsIgnoreCase("create_profile")){
                selected_lat_1 = Double.parseDouble(lat);
                selected_lang_1 = Double.parseDouble(lang);
                selected_address = fulladress ;
                finish();
            }
            else {
                Intent intent = new Intent( SearchVenueActivity.this,  SearchPackageActivity.class);
                intent.putExtra("address",fulladress); startActivity(intent);
                intent.putExtra("city",Mycity); startActivity(intent);
                startActivity(intent);
                finish();
            }


        });

        searchBarDestination.addTextChangeListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                FindAutocompletePredictionsRequest predictionsRequest = FindAutocompletePredictionsRequest.builder()
                        // .setCountry("in")
                        .setSessionToken(token)
                        .setQuery(s.toString())
                        .build();
                placesClient.findAutocompletePredictions(predictionsRequest).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        FindAutocompletePredictionsResponse predictionsResponse = task.getResult();
                        if (predictionsResponse != null) {
                            predictionList = predictionsResponse.getAutocompletePredictions();
                            List<String> suggestionsList = new ArrayList<>();
                            for (int i = 0; i < predictionList.size(); i++) {
                                AutocompletePrediction prediction = predictionList.get(i);
                                suggestionsList.add(prediction.getFullText(null).toString());
                            }
                            searchBarDestination.updateLastSuggestions(suggestionsList);
                            if (!searchBarDestination.isSuggestionsVisible()) {
                                searchBarDestination.showSuggestionsList();
                            }
                        }
                    } else {
                        Log.i("mytag", "prediction fetching task unsuccessful");
                    }
                });
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Toast.makeText(SearchVenueActivity.this, "Okay", Toast.LENGTH_SHORT).show();
            }
        });


        searchBarDestination.setSuggstionsClickListener(new SuggestionsAdapter.OnItemViewClickListener() {
            @Override
            public void OnItemClickListener(int position, View v) {
                if (position >= predictionList.size()) {
                    return;
                }


                View view = SearchVenueActivity.this.getCurrentFocus();
                if (view != null) {
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }

                AutocompletePrediction selectedPrediction = predictionList.get(position);
                suggestion = searchBarDestination.getLastSuggestions().get(position).toString();


                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        searchBarDestination.clearSuggestions();
                    }
                }, 1000);
                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                if (imm != null)
                    imm.hideSoftInputFromWindow(searchBarDestination.getWindowToken(), InputMethodManager.HIDE_IMPLICIT_ONLY);
                final String placeId = selectedPrediction.getPlaceId();
                List<Place.Field> placeFields = Arrays.asList(Place.Field.LAT_LNG);

                FetchPlaceRequest fetchPlaceRequest = FetchPlaceRequest.builder(placeId, placeFields).build();

                placesClient.fetchPlace(fetchPlaceRequest).addOnSuccessListener(new OnSuccessListener<FetchPlaceResponse>() {
                    @Override
                    public void onSuccess(FetchPlaceResponse fetchPlaceResponse) {
                        Place place = fetchPlaceResponse.getPlace();
                        dest_lattitude = place.getLatLng().latitude;

                        dest_longitude = place.getLatLng().longitude;

                        Log.e("Search Address", "onSuccess: " + place.getLatLng().latitude + " " + place.getLatLng().longitude);

                        latt = String.valueOf(dest_lattitude);
                        longi = String.valueOf(dest_longitude);

                        MarkerOptions markerOptions = new MarkerOptions();

                        Geocoder geocoder;
                        geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
                        List<Address> addresses = null;

                        try {


                            addresses = geocoder.getFromLocation(dest_lattitude, dest_longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
                            // session.setAddress(addresses.get(0).getAddressLine(0));


                            String city = addresses.get(0).getLocality();
                            String subLocality = addresses.get(0).getSubLocality();
                            String state = addresses.get(0).getAdminArea();
                            String country = addresses.get(0).getCountryName();
                            String postalCode = addresses.get(0).getPostalCode();


                            Log.e("Search Address", "onSuccess: " + "" + subLocality + " , " + city + " , " + state);


                            lat = latt;
                            lang = longi;

                           //// fulladress = "" + subLocality + " , " + city + " , " + state;


                            if(subLocality != null)
                                fulladress = "" + subLocality + " , " + city + " , " + state;
                            else
                                fulladress = ""+ city + " , " + state;


                            selected_lat_1 = Double.parseDouble(lat);
                            selected_lang_1 = Double.parseDouble(lang);
                            selected_address = fulladress ;

                            Mycity = city ;
                            country_location = country ;
                            confirm_location_linear.setVisibility(View.VISIBLE);
                            markerOptions.title(subLocality + " "+city);


                            Marker marker = mMap.addMarker(new MarkerOptions()
                                    .position(new LatLng(dest_lattitude, dest_longitude))
                                    .title(fulladress));
                             searchBarDestination.setText("");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        // updateuseraddres(session.getUserId(), latt, longi, suggestion);

                        //   session.setLattitude(latt);
                        // session.setLongitude(longi);

                        if (lattitude != 0.0 && longitude != 0.0) {
                            if (dest_longitude != 0.0 && dest_lattitude != 0.0) {

                            }
                        }
                        Log.i("mytag", "Place found: " + place.getName());
                        LatLng latLngOfPlace = place.getLatLng();
                        if (latLngOfPlace != null) {

                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLngOfPlace, DEFAULT_ZOOM));
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        if (e instanceof ApiException) {
                            ApiException apiException = (ApiException) e;
                            apiException.printStackTrace();
                            int statusCode = apiException.getStatusCode();
                            Log.i("mytag", "place not found: " + e.getMessage());
                            Log.i("mytag", "status code: " + statusCode);
                        }
                    }
                });
            }

            @Override
            public void OnItemDeleteListener(int position, View v) {
            }
        });


    }


    public void getCurrentAddress() {
        // Get the location manager
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (locationManager != null) {
            try {
                if (Build.VERSION.SDK_INT >= 23 && checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                locationManager.requestLocationUpdates(
                        LocationManager.NETWORK_PROVIDER,
                        MIN_TIME_BW_UPDATES,
                        MIN_DISTANCE_CHANGE_FOR_UPDATES, (LocationListener) this);
            } catch (Exception ex) {
                Log.i("msg", "fail to request location update, ignore", ex);
            }
            if (locationManager != null) {
                location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            }
            Geocoder gcd = new Geocoder(getBaseContext(),
                    Locale.getDefault());
            List<Address> addresses;
            try {
                addresses = gcd.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                if (addresses.size() > 0) {
                    String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                    String locality = addresses.get(0).getLocality();
                    String subLocality = addresses.get(0).getSubLocality();
                    String state = addresses.get(0).getAdminArea();
                    String country = addresses.get(0).getCountryName();
                    String postalCode = addresses.get(0).getPostalCode();
                    String knownName = addresses.get(0).getFeatureName();

                    Log.e("TAG", "=========get locality " + locality + "====sublocality==" + subLocality);
                    if (subLocality != null) {

                    } else {
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
                //Toast.makeText(SearchVenueActivity.this, Constants.ToastConstatnts.ERROR_FETCHING_LOCATION, Toast.LENGTH_SHORT).show();
            }
        }
    }


    public void getlatlang() {


        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(SearchVenueActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        } else {
            //    Toast.makeText(LoginActivity.this,"You need have granted permission",Toast.LENGTH_SHORT).show();
            gps = new GPSTracker(getApplicationContext());
            if (gps.canGetLocation()) {
                latitude = gps.getLatitude();
                longitude = gps.getLongitude();
                latt = String.valueOf(latitude);
                longi = String.valueOf(longitude);

                List<Address> addresses = null;

                try {
                    Geocoder geocoder;
                    geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());

                    addresses = geocoder.getFromLocation(latitude, longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }
        }
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(true);
        if (mapView != null && mapView.findViewById(Integer.parseInt("1")) != null) {
            View locationButton = ((View) mapView.findViewById(Integer.parseInt("1")).getParent()).findViewById(Integer.parseInt("2"));
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) locationButton.getLayoutParams();
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP, 0);
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
            layoutParams.setMargins(0, 0, 40, 180);

        }


        googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {

                View view = SearchVenueActivity.this.getCurrentFocus();
                if (view != null) {
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }

                confirm_location_linear.setVisibility(View.VISIBLE);

                String lattitudereiceve = String.valueOf(latLng.latitude);
                String longituderecieve = String.valueOf(latLng.longitude);

                MarkerOptions markerOptions = new MarkerOptions();

                markerOptions.position(latLng).title("You are here");

                //  markerOptions.title(latLng.latitude + " : " + latLng.longitude);
                Log.e("TAG", "onSuccess: " + lattitudereiceve + longituderecieve);

                //  txlocation.setText(address);
                try {
                    Geocoder geocoder;
                    geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
                    List<Address> addresses = null;
                    addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
                    //searchBarDestination.setText(addresses.get(0).getAddressLine(0));
                    System.out.println("suggestion+    " + addresses.get(0).getAddressLine(0));
                    Log.e("TAG", "Address: " + addresses.get(0).getAddressLine(0));

                    String city = addresses.get(0).getLocality();
                    String subLocality = addresses.get(0).getSubLocality();
                    String state = addresses.get(0).getAdminArea();
                    String country = addresses.get(0).getCountryName();
                    String postalCode = "" + subLocality + " , " + city + " , " + state+" , "+addresses.get(0).getCountryName();

                    markerOptions.title(subLocality + " "+city);

                  ///  fulladress = "" + subLocality + " , " + city + " , " + state+" , "+addresses.get(0).getCountryName();

                    if(subLocality != null)
                        fulladress = "" + subLocality + " , " + city + " , " + state;
                    else
                        fulladress = ""+ city + " , " + state;



                    // mapInterface.getAddress(gpsTracker.getLatitude(),gpsTracker.getLongitude(),postalCode);
                    // onBackPressed();
                    lat = String.valueOf(latLng.latitude);
                    lang = String.valueOf(latLng.longitude);

                    selected_lat_1 = latitude;
                    selected_lang_1 = longitude;
                    selected_address = fulladress ;

                } catch (IOException e) {
                    e.printStackTrace();
                }

                // CleamarkerOptionsrs the previously touched position
                googleMap.clear();

                // Animating to the touched position
                googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));

                // Placing a marker on the touched position
                googleMap.addMarker(markerOptions);
            }
        });

        //check if gps is enabled or not and then request user to enable it
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(5000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(locationRequest);
        SettingsClient settingsClient = LocationServices.getSettingsClient(SearchVenueActivity.this);
        Task<LocationSettingsResponse> task = settingsClient.checkLocationSettings(builder.build());
        task.addOnSuccessListener(SearchVenueActivity.this, new OnSuccessListener<LocationSettingsResponse>() {
            @Override
            public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                getDeviceLocation();
            }
        });

        task.addOnFailureListener(SearchVenueActivity.this, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                if (e instanceof ResolvableApiException) {
                    ResolvableApiException resolvable = (ResolvableApiException) e;
                    try {
                        resolvable.startResolutionForResult(SearchVenueActivity.this, 51);
                    } catch (IntentSender.SendIntentException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });

        mMap.setOnMyLocationButtonClickListener(new GoogleMap.OnMyLocationButtonClickListener() {
            @Override
            public boolean onMyLocationButtonClick() {

                return false;
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 51) {
            if (resultCode == RESULT_OK) {
                getDeviceLocation();
            }
        }
    }

    private void getDeviceLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mFusedLocationProviderClient.getLastLocation()
                .addOnCompleteListener(new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        if (task.isSuccessful()) {
                            mLastKnownLocation = task.getResult();
                            if (mLastKnownLocation != null) {
                                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(mLastKnownLocation.getLatitude(), mLastKnownLocation.getLongitude()), DEFAULT_ZOOM));
                            } else {
                                final LocationRequest locationRequest = LocationRequest.create();
                                locationRequest.setInterval(30 * 1000);
                                locationRequest.setFastestInterval(1000);
                                locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
                                locationCallback = new LocationCallback() {
                                    @Override
                                    public void onLocationResult(LocationResult locationResult) {
                                        super.onLocationResult(locationResult);
                                        if (locationResult == null) {
                                            return;
                                        }
                                        mLastKnownLocation = locationResult.getLastLocation();
                                        Toast.makeText(SearchVenueActivity.this, "" + mLastKnownLocation.getLatitude() + " " + mLastKnownLocation.getLongitude(), Toast.LENGTH_SHORT).show();
                                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(mLastKnownLocation.getLatitude(), mLastKnownLocation.getLongitude()), DEFAULT_ZOOM));
                                        mFusedLocationProviderClient.removeLocationUpdates(locationCallback);
                                    }
                                };
                                if (ActivityCompat.checkSelfPermission(SearchVenueActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(SearchVenueActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                                    // TODO: Consider calling
                                    //    ActivityCompat#requestPermissions
                                    // here to request the missing permissions, and then overriding
                                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                    //                                          int[] grantResults)
                                    // to handle the case where the user grants the permission. See the documentation
                                    // for ActivityCompat#requestPermissions for more details.
                                    return;
                                }
                                mFusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, null);

                            }
                        } else {
                            Toast.makeText(SearchVenueActivity.this, "unable to get last location", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}