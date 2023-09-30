package com.Nuptist.venue;

import static com.Nuptist.RetrofitApis.BaseUrls.ADD_VENUE;
import static com.Nuptist.RetrofitApis.BaseUrls.IMAGE_URL;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentTransaction;

import com.Nuptist.ChangeAddresActivity;
import com.Nuptist.Models.GPSTracker;
import com.Nuptist.Models.VenuModel;
import com.Nuptist.PrivacyActivity;
import com.Nuptist.R;
import com.Nuptist.RetrofitApis.ApiInterface;
import com.Nuptist.RetrofitApis.BaseUrls;
import com.Nuptist.RetrofitApis.MapInterface;
import com.Nuptist.RetrofitApis.RetrofitClient;
import com.Nuptist.Session.Session;
import com.Nuptist.UpdateVenueActivity;
import com.Nuptist.VendorProfileActivity;
import com.Nuptist.databinding.ActivityVenueBinding;
import com.Nuptist.frags.VenueMapFragment;
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.ANRequest;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.List;

import me.rosuh.filepicker.config.FilePickerManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VenueActivity extends AppCompatActivity implements MapInterface {

    private Session session;
    private String id;
    private String seletcted_address;
    private String selected_lat = "";
    private String selected_lang = "";
    private ActivityVenueBinding binding;
    private ProgressDialog pd;
    private File user_image = null, cover_image = null, id_proof_image = null;
    private final int USER_IMAGE_CODE = 200;
    private final int REGISTRATION_ID_CODE = 300;
    private final int COVER_IMAGE_CODE = 400;
    private String packageVanu = "";
    private String workWithNuptist = "";
    private double lcat;
    private double lang;
    private String status = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityVenueBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        session = new Session(VenueActivity.this);

        binding.back.setOnClickListener(view -> finish());

        if (session.getVenue().equalsIgnoreCase("venue_added")) viewVenueAvailableOnlyView(false);
        else viewVenueAvailable();

        getvenueDetails();

        pd = new ProgressDialog(VenueActivity.this);
        pd.setCancelable(false);
        pd.setMessage("Adding  Venue....");


        binding.back.setOnClickListener(v -> {
            startActivity(new Intent(VenueActivity.this, VendorProfileActivity.class));
            finish();
        });

        binding.linearAllVenueImages.setOnClickListener(view -> startActivity(new Intent(VenueActivity.this, UpdateVenueImagesActivity.class)));

        binding.addressSelectecd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(VenueActivity.this, SearchVenueActivity.class)
                        .putExtra("where", "update_venue"));
            }
        });

    }

    private void request_permission() {

        if (ContextCompat.checkSelfPermission(VenueActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(VenueActivity.this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                ActivityCompat.requestPermissions(VenueActivity.this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            } else {
                ActivityCompat.requestPermissions(VenueActivity.this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            }
        } else {

            GPSTracker gpsTracker = new GPSTracker(VenueActivity.this);

            lcat = gpsTracker.getLatitude();
            lang = gpsTracker.getLongitude();

            selected_lat = String.valueOf(lcat);
            selected_lang = String.valueOf(lang);


            Log.e("TAG", "request_permission() lat " + selected_lat);
            Log.e("TAG", "request_permission() lang  " + selected_lang);

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.add(R.id.fragment_container, new VenueMapFragment(lcat, lang, false, VenueActivity.this));
            transaction.commit();

        }


    }

    private void viewVenueAvailable() {

        request_permission();

        workWithNuptist = "";
        packageVanu = "";
        id_proof_image = null;
        user_image = null;
        cover_image = null;


        binding.createVenueBtn.setOnClickListener(view -> {

            if (binding.vanueName.getText().toString().equalsIgnoreCase(""))
                binding.vanueName.setError("Enter Venue Name ");
            else if (binding.phoneNumber.getText().toString().equalsIgnoreCase(""))
                binding.phoneNumber.setError("Enter Mobile Number");
            else if (binding.phoneNumber.getText().toString().length() < 10)
                binding.phoneNumber.setError(" Mobile Number must be greater then 10 digits ");
            else if (binding.emailEdit.getText().toString().equalsIgnoreCase(""))
                binding.emailEdit.setError(" Enter Email");
            else if (binding.address.getText().toString().equalsIgnoreCase(""))
                binding.address.setError(" Enter Address");
            else if (binding.minEdit.getText().toString().equalsIgnoreCase(""))
                binding.minEdit.setError(" Enter Min Guest Capacity");
            else if (binding.maxEdit.getText().toString().equalsIgnoreCase(""))
                binding.maxEdit.setError(" Enter Max Guest Capacity");
            else if (binding.diningEdit.getText().toString().equalsIgnoreCase(""))
                binding.diningEdit.setError(" Enter Dining Capacity");
            else if (binding.parking2Wheelers.getText().toString().equalsIgnoreCase(""))
                binding.parking2Wheelers.setError(" How Many Parking Available for 2 wheelers");
            else if (binding.parking3Wheelers.getText().toString().equalsIgnoreCase(""))
                binding.parking3Wheelers.setError(" How Many Parking Available for 2 wheelers");
            else if (binding.parking4Wheelers.getText().toString().equalsIgnoreCase(""))
                binding.parking4Wheelers.setError(" How Many Parking Available for 2 wheelers");
            else if (binding.description.getText().toString().equalsIgnoreCase(""))
                binding.description.setError("Enter Description");
            else add_intodatabase();
//                else if (id_proof_image == null)
//                    Toast.makeText(VenueActivity.this, "Select Image", Toast.LENGTH_SHORT).show();
//                else if (cover_image == null)
//                    Toast.makeText(VenueActivity.this, "Select Cover Image", Toast.LENGTH_SHORT).show();
//                else if (user_image == null)
//                    Toast.makeText(VenueActivity.this, "Select User Image", Toast.LENGTH_SHORT).show();
//                else if (packageVanu.equalsIgnoreCase(""))
//                    Toast.makeText(VenueActivity.this, R.string.can_nuptist_include_venue_for_packages, Toast.LENGTH_SHORT).show();
//                else if (workWithNuptist.equalsIgnoreCase(""))
//                    Toast.makeText(VenueActivity.this, R.string.will_you_work_with_nuptist_s_recommended_vendor_s, Toast.LENGTH_SHORT).show();
//


        });


        binding.venueTitle.setText(R.string.update_venue);

        binding.mapLinear.setOnClickListener(view -> startActivity(new Intent(VenueActivity.this, ChangeAddresActivity.class)));

        binding.brawoseUserImage.setOnClickListener(view -> FilePickerManager
                .from(VenueActivity.this)
                .maxSelectable(1)
                .forResult(USER_IMAGE_CODE));

        binding.browseImageRegistrationProof.setOnClickListener(view -> FilePickerManager
                .from(VenueActivity.this)
                .maxSelectable(1)
                .forResult(REGISTRATION_ID_CODE));

        binding.brawoseCoverPhoto.setOnClickListener(view -> FilePickerManager
                .from(VenueActivity.this)
                .maxSelectable(1)
                .forResult(COVER_IMAGE_CODE));

       /* binding.createVenueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getSelectedrecommnededvendors();
                getSelectedpackagevendors();

                if (binding.vanueName.getText().toString().equalsIgnoreCase(""))
                    binding.vanueName.setError("Enter Venue Name ");
                else if (binding.phoneNumber.getText().toString().equalsIgnoreCase(""))
                    binding.phoneNumber.setError("Enter Mobile Number");
                else if (binding.phoneNumber.getText().toString().length() < 10)
                    binding.phoneNumber.setError(" Mobile Number must be greater then 10 digits ");
                else if (binding.emailEdit.getText().toString().equalsIgnoreCase(""))
                    binding.emailEdit.setError(" Enter Email");
                else if (binding.address.getText().toString().equalsIgnoreCase(""))
                    binding.address.setError(" Enter Address");
                else if (binding.minEdit.getText().toString().equalsIgnoreCase(""))
                    binding.minEdit.setError(" Enter Min Guest Capacity");
                else if (binding.maxEdit.getText().toString().equalsIgnoreCase(""))
                    binding.maxEdit.setError(" Enter Max Guest Capacity");
                else if (binding.diningEdit.getText().toString().equalsIgnoreCase(""))
                    binding.diningEdit.setError(" Enter Dining Capacity");
                else if (binding.parking2Wheelers.getText().toString().equalsIgnoreCase(""))
                    binding.parking2Wheelers.setError(" How Many Parking Available for 2 wheelers");
                else if (binding.parking3Wheelers.getText().toString().equalsIgnoreCase(""))
                    binding.parking3Wheelers.setError(" How Many Parking Available for 2 wheelers");
                else if (binding.parking4Wheelers.getText().toString().equalsIgnoreCase(""))
                    binding.parking4Wheelers.setError(" How Many Parking Available for 2 wheelers");
                else if (packageVanu.equalsIgnoreCase(""))
                    Toast.makeText(VenueActivity.this, R.string.can_nuptist_include_venue_for_packages, Toast.LENGTH_SHORT).show();
                else if (workWithNuptist.equalsIgnoreCase(""))
                    Toast.makeText(VenueActivity.this, R.string.will_you_work_with_nuptist_s_recommended_vendor_s, Toast.LENGTH_SHORT).show();
                else if (binding.description.getText().toString().equalsIgnoreCase(""))
                    binding.description.setError("Enter Description");
                else if (id_proof_image == null)
                    Toast.makeText(VenueActivity.this, "Select Image", Toast.LENGTH_SHORT).show();
                else if (cover_image == null)
                    Toast.makeText(VenueActivity.this, "Select Cover Image", Toast.LENGTH_SHORT).show();
                else if (user_image == null)
                    Toast.makeText(VenueActivity.this, "Select User Image", Toast.LENGTH_SHORT).show();
                else add_intodatabase();


            }
        });
*/

    }

    private void viewVenueAvailableOnlyView(boolean isvisible) {

        binding.description.setFocusable(isvisible);
        binding.vanueName.setFocusable(isvisible);
        binding.phoneNumber.setFocusable(isvisible);
        binding.emailEdit.setFocusable(isvisible);
        binding.address.setFocusable(isvisible);
        binding.minEdit.setFocusable(isvisible);
        binding.maxEdit.setFocusable(isvisible);
        binding.diningEdit.setFocusable(isvisible);
        binding.parking2Wheelers.setFocusable(isvisible);
        binding.parking3Wheelers.setFocusable(isvisible);
        binding.parking4Wheelers.setFocusable(isvisible);
        binding.venueFeature1.setFocusable(isvisible);
        binding.venueFeature2.setFocusable(isvisible);
        binding.venueFeature3.setFocusable(isvisible);
        binding.venueFeature4.setFocusable(isvisible);
        binding.venueFeature5.setFocusable(isvisible);
        binding.createVenueBtn.setClickable(isvisible);
        binding.numberCountyCode.setClickable(isvisible);

        binding.numberCountyCode.setVisibility(View.INVISIBLE);

        binding.workWithNuptistVendorNo.setClickable(isvisible);
        binding.workWithNuptistVendorYes.setClickable(isvisible);


        binding.forPackagesVenueNo.setClickable(isvisible);
        binding.forPackagesVenueYes.setClickable(isvisible);
        binding.brawoseUserImage.setClickable(isvisible);
        binding.browseImageRegistrationProof.setClickable(isvisible);
        binding.brawoseCoverPhoto.setClickable(isvisible);

        workWithNuptist = "";
        packageVanu = "";
        id_proof_image = null;
        user_image = null;
        cover_image = null;
        binding.createVenueBtn.setText(R.string.update_venue);

        binding.venueTitle.setText(R.string.your_venue);


        binding.createVenueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.e("TAG", "onClick() called with: view = [" + status + "]");
                if (status.equalsIgnoreCase("0")) {
                    showDialog();
                } else {
                    Intent intent = new Intent(VenueActivity.this, UpdateVenueActivity.class);
                    intent.putExtra("venue_id", id);
                    startActivity(intent);

                }

            }
        });


    }

    private void add_intodatabase() {

        getSelectedrecommnededvendors();
        getSelectedpackagevendors();
        pd.show();

        ANRequest.MultiPartBuilder anAdd = AndroidNetworking.upload((BaseUrls.URL + ADD_VENUE));
        anAdd.addMultipartParameter("user_id", session.getUserId_Vendor());
        anAdd.addMultipartParameter("venue_name", binding.vanueName.getText().toString());
        anAdd.addMultipartParameter("phone", binding.numberCountyCode.getTextView_selectedCountry().getText().toString() + binding.phoneNumber.getText().toString());
        anAdd.addMultipartParameter("email", binding.emailEdit.getText().toString());
        anAdd.addMultipartParameter("service_address", binding.address.getText().toString());
        anAdd.addMultipartParameter("guest_capycity_min", binding.minEdit.getText().toString());
        anAdd.addMultipartParameter("guest_capycity_mix", binding.maxEdit.getText().toString());
        anAdd.addMultipartParameter("dining_capycity", binding.diningEdit.getText().toString());
        anAdd.addMultipartParameter("parking_available_2wheeler", binding.parking2Wheelers.getText().toString());
        anAdd.addMultipartParameter("parking_available_4wheeler", binding.parking4Wheelers.getText().toString());
        anAdd.addMultipartParameter("parkings_available_2wheeler", binding.parking3Wheelers.getText().toString());
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
                                Toast.makeText(VenueActivity.this, "Venue details have been updated", Toast.LENGTH_SHORT).show();
                                session.setVenue();
                                startActivity(new Intent(VenueActivity.this, AddVanueImagesActivity.class));
                                finish();
                            } else {
                                Toast.makeText(VenueActivity.this, jsonObject.getString("msg"), Toast.LENGTH_SHORT).show();

                            }

                        } catch (JSONException e) {
                            pd.dismiss();
                            Toast.makeText(VenueActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onError(ANError anError) {
                        pd.dismiss();
                        Log.e("TAG", "onError: " + anError);

                    }
                });


        ///  Toast.makeText(this, "Okay", Toast.LENGTH_SHORT).show();

    }

    private void getSelectedpackagevendors() {

        int id = binding.forPackagesVenueGroup.getCheckedRadioButtonId();

        if (id != 0) {
            RadioButton a = (RadioButton) findViewById(id);
            packageVanu = a.getText().toString();

        } else {
            Toast.makeText(this, "No", Toast.LENGTH_SHORT).show();
        }

    }

    private void getSelectedrecommnededvendors() {

        int id = binding.workWithNuptistVendorGroup.getCheckedRadioButtonId();

        if (id != 0) {
            RadioButton a = (RadioButton) findViewById(id);
            workWithNuptist = (String) a.getText().toString();
        } else {
            Toast.makeText(this, "No", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == USER_IMAGE_CODE && resultCode == RESULT_OK) {

            List<String> image = FilePickerManager.obtainData();
            user_image = new File(image.get(0));
            Log.e("TAG", "onActivityResult() called with: requestCode = [" + requestCode + "], resultCode = [" + resultCode + "], data = [" + user_image + "]");
            if (user_image != null) binding.brawoseUserImage.setText(user_image.getName());

        } else if (requestCode == REGISTRATION_ID_CODE && resultCode == RESULT_OK) {
            List<String> image = FilePickerManager.obtainData();
            id_proof_image = new File(image.get(0));
            Log.e("TAG", "onActivityResult() called with: requestCode = [" + requestCode + "], resultCode = [" + resultCode + "], data = [" + id_proof_image + "]");
            if (id_proof_image != null)
                binding.browseImageRegistrationProof.setText(id_proof_image.getName());

        } else if (requestCode == COVER_IMAGE_CODE && resultCode == RESULT_OK) {
            List<String> image = FilePickerManager.obtainData();
            cover_image = new File(image.get(0));
            Log.e("TAG", "onActivityResult() called with: requestCode = [" + requestCode + "], resultCode = [" + resultCode + "], data = [" + cover_image + "]");
            if (cover_image != null) binding.brawoseCoverPhoto.setText(cover_image.getName());

        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(VenueActivity.this,
                            Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();

                        GPSTracker gpsTracker = new GPSTracker(VenueActivity.this);

                        lcat = gpsTracker.getLatitude();
                        lang = gpsTracker.getLongitude();

                        selected_lat = String.valueOf(lcat);
                        selected_lang = String.valueOf(lang);

                        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                        transaction.add(R.id.fragment_container, new VenueMapFragment(lcat, lang, false, VenueActivity.this));
                        transaction.commit();
                    }
                } else {
                    Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }

    }


    @Override
    public void getAddress(double lat, double lang, String address) {
        selected_lang = String.valueOf(lang);
        selected_lat = String.valueOf(lat);
        seletcted_address = address;
        binding.addressSelectecd.setText(address);
    }


    public void getvenueDetails() {

        ApiInterface apiInterface = RetrofitClient.getclient(VenueActivity.this);

        apiInterface.get_venue(session.getUserId_Vendor()).enqueue(new Callback<VenuModel>() {
            @Override
            public void onResponse(@NonNull Call<VenuModel> call, @NonNull Response<VenuModel> response) {

                try {

                    if (response.body() != null) {
                        if (response.body().getResult().equalsIgnoreCase("true")) {
                            viewVenueAvailableOnlyView(false);

                            setAllData(response.body().getData());
                            binding.linearAllVenueImages.setVisibility(View.VISIBLE);

                            status = response.body().getData().get(0).getStatus();

                            //////// check if vanue images not added
                            if (session.getGalleryImage().equalsIgnoreCase("")) {
                                startActivity(new Intent(VenueActivity.this, AddVanueImagesActivity.class));
                            }

                        } else {
                            startActivity(new Intent(VenueActivity.this, AddVenueActivity.class));
                            finish();
                            viewVenueAvailable();
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

    private void setAllData(List<VenuModel.VenueData> data) {

        id = data.get(0).getId();

        binding.vanueName.setText(data.get(0).getVenueName());
        binding.description.setText(data.get(0).getDescription());
        binding.phoneNumber.setText(data.get(0).getPhone());
        binding.emailEdit.setText(data.get(0).getEmail());
        binding.address.setText(data.get(0).getServiceAddress());
        binding.minEdit.setText(data.get(0).getGuestCapycityMin());
        binding.maxEdit.setText(data.get(0).getGuestCapycityMix());
        binding.diningEdit.setText(data.get(0).getDiningCapycity());
        binding.parking2Wheelers.setText(data.get(0).getParkingAvailable2wheeler());
        binding.parking3Wheelers.setText(data.get(0).getParkingsAvailable2wheeler());
        binding.parking4Wheelers.setText(data.get(0).getParkingAvailable4wheeler());
        binding.venueFeature1.setText(data.get(0).getVenueFeature1());
        binding.venueFeature2.setText(data.get(0).getVenueFeature2());
        binding.venueFeature3.setText(data.get(0).getVenueFeature3());
        binding.venueFeature4.setText(data.get(0).getVenueFeature4());
        binding.venueFeature5.setText(data.get(0).getVenueFeature5());
        binding.priceEdit.setText(data.get(0).getVenue_price());
        binding.pricePerPlet.setText(data.get(0).getPrice_per_plate());

        //binding.numberCountyCode.setText(data.get(0).getVenueName());

        binding.brawoseUserImage.setText(data.get(0).getImage());
        binding.browseImageRegistrationProof.setText(data.get(0).getGallaryImage());
        binding.brawoseCoverPhoto.setText(data.get(0).getCoverImage());

//        user_ 3 . co 2 , id 1


        Picasso.get().load(IMAGE_URL + data.get(0).getImage()).into(binding.image3);
        Picasso.get().load(IMAGE_URL + data.get(0).getGallaryImage()).into(binding.image2);
        Picasso.get().load(IMAGE_URL + data.get(0).getCoverImage()).into(binding.image1);


        String pckage = data.get(0).getVenuePackages();
        String for_with = data.get(0).getVenueForePackages();

        if (pckage.equalsIgnoreCase("No")) {
            binding.forPackagesVenueNo.setChecked(true);
        } else {
            binding.forPackagesVenueYes.setChecked(true);

        }


        if (for_with.equalsIgnoreCase("No")) {
            binding.workWithNuptistVendorNo.setChecked(true);
        } else {
            binding.workWithNuptistVendorYes.setChecked(true);

        }

        if (ContextCompat.checkSelfPermission(VenueActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(VenueActivity.this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                ActivityCompat.requestPermissions(VenueActivity.this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            } else {
                ActivityCompat.requestPermissions(VenueActivity.this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            }
        } else {

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.add(R.id.fragment_container, new VenueMapFragment(Double.valueOf(data.get(0).getLat()), Double.valueOf(data.get(0).getLang()), true, VenueActivity.this));
            transaction.commit();
        }

        binding.linearImage1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showdialogbox(BaseUrls.IMAGE_URL + data.get(0).getGallaryImage());
            }
        });

        binding.linearImage2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showdialogbox(BaseUrls.IMAGE_URL + data.get(0).getImage());
            }
        });

        binding.linearImage3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showdialogbox(BaseUrls.IMAGE_URL + data.get(0).getCoverImage());
            }
        });


    }


    @Override
    protected void onResume() {
        super.onResume();
        getvenueDetails();
    }


    public void showdialogbox(String url) {


        final Dialog dialog = new Dialog(VenueActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.view_venue_image);

        ImageView image = (ImageView) dialog.findViewById(R.id.image_view);
        ImageView close = (ImageView) dialog.findViewById(R.id.close_image);

        Picasso.get().load(url).placeholder(R.drawable.loading).into(image);

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();

    }

    private void showDialog() {


        final Dialog dialog = new Dialog(VenueActivity.this);

        dialog.setContentView(R.layout.venue_confirmation_layout);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(false);
        dialog.getWindow().getAttributes().windowAnimations = R.style.animation;

        LinearLayout close = dialog.findViewById(R.id.dissmiss2);
        LinearLayout vendor_profile = dialog.findViewById(R.id.vendor_profile);
        TextView viewPrivacyPolicy = dialog.findViewById(R.id.view_privacy_policy);

        close.setOnClickListener(view -> dialog.dismiss());

        viewPrivacyPolicy.setOnClickListener(view -> startActivity(new Intent(VenueActivity.this, PrivacyActivity.class)));

        vendor_profile.setOnClickListener(view -> dialog.dismiss());

        dialog.show();


    }
}

