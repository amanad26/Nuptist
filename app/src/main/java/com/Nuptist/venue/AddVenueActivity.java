package com.Nuptist.venue;

import static com.Nuptist.RetrofitApis.BaseUrls.ADD_VENUE;
import static com.Nuptist.venue.SearchVenueActivity.selected_address;
import static com.Nuptist.venue.SearchVenueActivity.selected_lang_1;
import static com.Nuptist.venue.SearchVenueActivity.selected_lat_1;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;

import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.Nuptist.Models.GPSTracker;
import com.Nuptist.Models.ProgressDialog;
import com.Nuptist.Models.VenuModel;
import com.Nuptist.R;
import com.Nuptist.RetrofitApis.ApiInterface;
import com.Nuptist.RetrofitApis.BaseUrls;
import com.Nuptist.RetrofitApis.MapInterface;
import com.Nuptist.RetrofitApis.RetrofitClient;
import com.Nuptist.Session.Session;
import com.Nuptist.TemsAndConditionActivity;
import com.Nuptist.VendorCreateAccountActivity;
import com.Nuptist.VendorProfileActivity;
import com.Nuptist.frags.VenueMapFragment;
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.ANRequest;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import com.Nuptist.databinding.ActivityAddVenueBinding;
import com.github.dhaval2404.imagepicker.ImagePicker;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AddVenueActivity extends AppCompatActivity implements MapInterface {

    private Session session;
    private String seletcted_address;
    private String selected_lat = "";
    private String selected_lang = "";
    private ActivityAddVenueBinding binding;
    private File user_image = null, cover_image = null, id_proof_image = null;
    private Uri filepath;
    private String packageVanu = "";
    private String workWithNuptist = "";
    private double lcat;
    private double lang;
    private boolean isChecked = false;

    private String search_lat;
    private String search_lang;

    private String address;

    private Bitmap bitmap;
    private String city, country_location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddVenueBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        request_permission();


        binding.back.setOnClickListener(v -> {
            startActivity(new Intent(AddVenueActivity.this, VendorProfileActivity.class));
            finish();
        });

        binding.venueTermsAndCondition.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                isChecked = b;
            }
        });

        session = new Session(AddVenueActivity.this);

        binding.brawoseUserImage.setOnClickListener(view -> {
            ImagePicker.Companion.with(AddVenueActivity.this)
                    .crop()
                    .compress(200) //Final image size will be less than 1 MB(Optional)
                    .start(100);

//            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
//            intent.setType("image/*");
//            startActivityForResult(intent,100);

        });

        binding.browseImageRegistrationProof.setOnClickListener(view -> {
            ImagePicker.Companion.with(AddVenueActivity.this)
                    .crop()
                    .compress(200) //Final image size will be less than 1 MB(Optional)
                    .start(300);

//            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
//            intent.setType("image/*");
//            startActivityForResult(intent,300);

        });

        binding.brawoseCoverPhoto.setOnClickListener(view -> {
            ImagePicker.Companion.with(AddVenueActivity.this)
                    .crop()
                    .compress(200) //Final image size will be less than 1 MB(Optional)
                    .start(200);

//            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
//            intent.setType("image/*");
//            startActivityForResult(intent,200);

        });

        binding.termsConditionTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AddVenueActivity.this, TemsAndConditionActivity.class).putExtra("type","Venue_Manager"));
            }
        });



        binding.createVenueBtn.setOnClickListener(view -> {

            if (binding.vanueName.getText().toString().equalsIgnoreCase("")) {
                binding.vanueName.setError("Enter Venue Name ");
                binding.vanueName.requestFocus();
            } else if (binding.phoneNumber.getText().toString().equalsIgnoreCase("")) {
                binding.phoneNumber.setError("Enter Mobile Number");
                binding.phoneNumber.requestFocus();
            } else if (binding.emailEdit.getText().toString().equalsIgnoreCase("")) {
                binding.emailEdit.setError(" Enter Email");
                binding.emailEdit.requestFocus();
            } else if (binding.address.getText().toString().equalsIgnoreCase("")) {
                binding.address.setError(" Enter Address");
                binding.address.requestFocus();
            } else if (binding.minEdit.getText().toString().equalsIgnoreCase("")) {
                binding.minEdit.setError(" Enter Min Guest Capacity");
                binding.minEdit.requestFocus();
            } else if (binding.pricePerPlatEdit.getText().toString().equalsIgnoreCase("")) {
                binding.pricePerPlatEdit.setError("Enter Food Price Per Plate");
                binding.pricePerPlatEdit.requestFocus();
            } else if (binding.maxEdit.getText().toString().equalsIgnoreCase("")) {
                binding.maxEdit.setError(" Enter Max Guest Capacity");
                binding.maxEdit.requestFocus();
            } else if (binding.priceEdit.getText().toString().equalsIgnoreCase("")) {
                binding.priceEdit.setError(" Enter Venue Price");
                binding.priceEdit.requestFocus();
            } else if (binding.diningEdit.getText().toString().equalsIgnoreCase("")) {
                binding.diningEdit.setError(" Enter Dining Capacity");
                binding.diningEdit.requestFocus();
            } else if (binding.parking2Wheelers.getText().toString().equalsIgnoreCase("")) {
                binding.parking2Wheelers.setError(" How Many Parking Available for 2 wheelers");
                binding.parking2Wheelers.requestFocus();
            } else if (binding.parking3Wheelers.getText().toString().equalsIgnoreCase("")) {
                binding.parking3Wheelers.setError(" How Many Parking Available for 3 wheelers");
                binding.parking3Wheelers.requestFocus();
            } else if (binding.parking4Wheelers.getText().toString().equalsIgnoreCase("")) {
                binding.parking4Wheelers.setError(" How Many Parking Available for 4 wheelers");
                binding.parking4Wheelers.requestFocus();
            } else if (binding.description.getText().toString().equalsIgnoreCase("")) {
                binding.description.setError("Enter Description");
                binding.description.requestFocus();
            } else if (id_proof_image == null)
                Toast.makeText(AddVenueActivity.this, "Select Image", Toast.LENGTH_SHORT).show();
            else if (cover_image == null)
                Toast.makeText(AddVenueActivity.this, "Select Cover Image", Toast.LENGTH_SHORT).show();
            else if (user_image == null)
                Toast.makeText(AddVenueActivity.this, "Select User Image", Toast.LENGTH_SHORT).show();
            else {
                if (isChecked)
                    add_intodatabase();
                else
                    Toast.makeText(this, "Please Accept Terms And Conditions..!", Toast.LENGTH_SHORT).show();

            }
        });

        binding.addressSelectecd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AddVenueActivity.this, SearchVenueActivity.class)
                        .putExtra("where", "venue"));
            }
        });

        getvenueDetails();

    }


    private Bitmap setBitmap(ImageView imageView) {

        try {
            bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filepath);
            imageView.setImageBitmap(bitmap);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return bitmap;
    }

    public static File bitmapToFile(Context mContext, Bitmap bitmap) {
        try {
            String name = System.currentTimeMillis() + ".png";
            File file = new File(mContext.getCacheDir(), name);

            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 60, bos);
            byte[] bArr = bos.toByteArray();
            bos.flush();
            bos.close();

            FileOutputStream fos = new FileOutputStream(file);
            fos.write(bArr);
            fos.flush();
            fos.close();

            return file;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            assert data != null;
            filepath = data.getData();

            switch (requestCode) {

                case 100:
                    bitmap = setBitmap(binding.gallery);
                    user_image = bitmapToFile(AddVenueActivity.this, bitmap);
                    binding.gallery.setImageBitmap(bitmap);
                    break;

                case 200:
                    bitmap = setBitmap(binding.coverImage);
                    cover_image = bitmapToFile(AddVenueActivity.this, bitmap);
                    binding.coverImage.setImageBitmap(bitmap);
                    break;

                case 300:
                    bitmap = setBitmap(binding.userImageNew);
                    id_proof_image = bitmapToFile(AddVenueActivity.this, bitmap);
                    binding.userImageNew.setImageBitmap(bitmap);
                    break;

            }

        }


    }

    private void request_permission() {

        if (ContextCompat.checkSelfPermission(AddVenueActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(AddVenueActivity.this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                ActivityCompat.requestPermissions(AddVenueActivity.this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            } else {
                ActivityCompat.requestPermissions(AddVenueActivity.this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            }
        } else {

            GPSTracker gpsTracker = new GPSTracker(AddVenueActivity.this);

//            lcat = gpsTracker.getLatitude();
//            lang = gpsTracker.getLongitude();
//
//            selected_lat = String.valueOf(lcat);
//            selected_lang = String.valueOf(lang);
//
//
//            Log.e("TAG", "request_permission() lat " + selected_lat);
//            Log.e("TAG", "request_permission() lang  " + selected_lang);
//
//            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//            transaction.add(R.id.fragment_container, new VenueMapFragment(lcat, lang, false, AddVenueActivity.this));
//            transaction.commit();

        }


    }

    private void add_intodatabase() {

        getSelectedrecommnededvendors();
        getSelectedpackagevendors();


        if (packageVanu.equalsIgnoreCase(""))
            packageVanu = "no";

        if (workWithNuptist.equalsIgnoreCase(""))
            workWithNuptist = "no";


        Log.e("TAG", "add_intodatabase() user_image" + user_image.getName());
        Log.e("TAG", "add_intodatabase() Cover Image" + cover_image.getName());
        Log.e("TAG", "add_intodatabase() galley image" + id_proof_image.getName());


        ProgressDialog pd = new ProgressDialog(AddVenueActivity.this);

        pd.show();

        ANRequest.MultiPartBuilder anAdd = AndroidNetworking.upload((BaseUrls.URL + ADD_VENUE));
        anAdd.addMultipartParameter("user_id", session.getUserId_Vendor());
        anAdd.addMultipartParameter("venue_name", binding.vanueName.getText().toString());
        anAdd.addMultipartParameter("phone", binding.phoneNumber.getText().toString());
        anAdd.addMultipartParameter("email", binding.emailEdit.getText().toString());
        anAdd.addMultipartParameter("service_address", binding.address.getText().toString());
        anAdd.addMultipartParameter("guest_capycity_min", binding.minEdit.getText().toString());
        anAdd.addMultipartParameter("guest_capycity_mix", binding.maxEdit.getText().toString());
        anAdd.addMultipartParameter("dining_capycity", binding.diningEdit.getText().toString());
        anAdd.addMultipartParameter("parking_available_2wheeler", binding.parking2Wheelers.getText().toString());
        anAdd.addMultipartParameter("parking_available_4wheeler", binding.parking4Wheelers.getText().toString());
        anAdd.addMultipartParameter("parking_available_3wheeler", binding.parking3Wheelers.getText().toString());
        if (!binding.venueFeature1.getText().toString().equalsIgnoreCase(""))
            anAdd.addMultipartParameter("venue_feature1", binding.venueFeature1.getText().toString());
        if (!binding.venueFeature2.getText().toString().equalsIgnoreCase(""))
            anAdd.addMultipartParameter("venue_feature2", binding.venueFeature2.getText().toString());
        if (!binding.venueFeature3.getText().toString().equalsIgnoreCase(""))
            anAdd.addMultipartParameter("venue_feature3", binding.venueFeature3.getText().toString());
        if (!binding.venueFeature4.getText().toString().equalsIgnoreCase(""))
            anAdd.addMultipartParameter("venue_feature4", binding.venueFeature4.getText().toString());
        if (!binding.venueFeature5.getText().toString().equalsIgnoreCase(""))
            anAdd.addMultipartParameter("venue_feature5", binding.venueFeature5.getText().toString());
        anAdd.addMultipartParameter("description", binding.description.getText().toString());
        anAdd.addMultipartParameter("lang", selected_lang);
        anAdd.addMultipartParameter("lat", selected_lat);
        anAdd.addMultipartParameter("venue_packages", packageVanu);
        anAdd.addMultipartParameter("venue_fore_packages", workWithNuptist);
        if (user_image != null) anAdd.addMultipartFile("image", id_proof_image);
        if (cover_image != null) anAdd.addMultipartFile("cover_image", cover_image);
        if (id_proof_image != null) anAdd.addMultipartFile("gallary_image", user_image);
        anAdd.addMultipartParameter("venue_price", binding.priceEdit.getText().toString());
        anAdd.addMultipartParameter("country_code", binding.numberCountyCode.getTextView_selectedCountry().getText().toString());
        anAdd.addMultipartParameter("price_per_plate", binding.pricePerPlatEdit.getText().toString());

        anAdd.setPriority(Priority.HIGH);
        anAdd.build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        pd.dismiss();
                        try {
                            Log.d("---rrrProfile", "save_postsave_post" + jsonObject.toString());
                            // JSONObject obj = new JSONObject(response);
                            String result = jsonObject.getString("result");
                            pd.dismiss();

                            if (result.equalsIgnoreCase("true")) {
                                Toast.makeText(AddVenueActivity.this, "Venue  Created", Toast.LENGTH_SHORT).show();
                                session.setVenue();
                                startActivity(new Intent(AddVenueActivity.this, AddVanueImagesActivity.class));
                                finish();
                            } else {
                                Toast.makeText(AddVenueActivity.this, jsonObject.getString("msg"), Toast.LENGTH_SHORT).show();

                            }

                        } catch (JSONException e) {
                            pd.dismiss();
                            Toast.makeText(AddVenueActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onError(ANError anError) {
                        pd.dismiss();
                        Log.e("TAG", "onError: " + anError.getErrorBody());

                    }
                });

        ///  Toast.makeText(this, "Okay", Toast.LENGTH_SHORT).show();

    }

    private void getSelectedpackagevendors() {

        int id = binding.forPackagesVenueGroup.getCheckedRadioButtonId();

        if (id != 0) {
            RadioButton a = findViewById(id);
            packageVanu = a.getText().toString();

        } else {
            Toast.makeText(this, "No", Toast.LENGTH_SHORT).show();
        }


    }

    private void getSelectedrecommnededvendors() {

        int id = binding.workWithNuptistVendorGroup.getCheckedRadioButtonId();

        if (id != 0) {
            RadioButton a = findViewById(id);
            workWithNuptist = a.getText().toString();
        } else {
            Toast.makeText(this, "No", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void getAddress(double lat, double lang, String address) {
        selected_lang = String.valueOf(lang);
        selected_lat = String.valueOf(lat);
        seletcted_address = address;
        binding.addressSelectecd.setText(address);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (selected_address.equalsIgnoreCase("")) {
            GPSTracker gpsTracker = new GPSTracker(AddVenueActivity.this);
            lcat = gpsTracker.getLatitude();
            lang = gpsTracker.getLongitude();

            selected_lat = String.valueOf(lcat);
            selected_lang = String.valueOf(lang);

            Log.e("TAG", "request_permission() lat " + selected_lat);
            Log.e("TAG", "request_permission() lang  " + selected_lang);

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.add(R.id.fragment_container, new VenueMapFragment(lcat, lang, false, AddVenueActivity.this));
            transaction.commit();

        } else {

            binding.addressSelectecd.setText(selected_address);
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.add(R.id.fragment_container, new VenueMapFragment(selected_lat_1, selected_lang_1, false, AddVenueActivity.this));
            transaction.commit();

            selected_lang = String.valueOf(selected_lang_1);
            selected_lat = String.valueOf(selected_lat_1);

        }
    }


    public void getvenueDetails() {

        ApiInterface apiInterface = RetrofitClient.getclient(AddVenueActivity.this);

        apiInterface.get_venue(session.getUserId_Vendor()).enqueue(new Callback<VenuModel>() {
            @Override
            public void onResponse(@NonNull Call<VenuModel> call, @NonNull Response<VenuModel> response) {

                try {

                    if (response.body() != null) {
                        if (response.body().getResult().equalsIgnoreCase("true")) {
                            session.setVenue();
                            startActivity(new Intent(AddVenueActivity.this, VenueActivity.class));
                            finish();
                        }

                    }

                } catch (Exception e) {
                    e.getLocalizedMessage();
                }

            }

            @Override
            public void onFailure(@NonNull Call<VenuModel> call, @NonNull Throwable t) {
                Log.e("TAG", "onFailure() called with: call = [" + call + "], t = [" + t.getLocalizedMessage() + "]");
            }
        });


    }

}