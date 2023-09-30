package com.Nuptist;

import static android.content.ContentValues.TAG;
import static com.Nuptist.RetrofitApis.BaseUrls.ADD_VENUE;
import static com.Nuptist.RetrofitApis.BaseUrls.UPDATE_VENUE;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Toast;

import com.Nuptist.Models.VenuModel;
import com.Nuptist.RetrofitApis.ApiInterface;
import com.Nuptist.RetrofitApis.BaseUrls;
import com.Nuptist.RetrofitApis.MapInterface;
import com.Nuptist.RetrofitApis.RetrofitClient;
import com.Nuptist.Session.Session;
import com.Nuptist.databinding.ActivityUpdateVenueBinding;
import com.Nuptist.frags.VenueMapFragment;
import com.Nuptist.venue.VenueActivity;
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.ANRequest;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.List;

import me.rosuh.filepicker.config.FilePickerManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateVenueActivity extends AppCompatActivity implements MapInterface {



    private File user_image = null , cover_image = null , id_proof_image = null;
    private int USER_IMAGE_CODE = 200;
    private int REGISTRATION_ID_CODE = 300;
    private int COVER_IMAGE_CODE = 400;
    private Uri filepath;


    ActivityUpdateVenueBinding binding ;
    String id ;
    Session session ;
    private String selected_lang = "";
    private String selected_lat = "";
    private String seletcted_address = "";
    private String packageVanu = "No";
    private String workWithNuptist= "No";
    ProgressDialog pd ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUpdateVenueBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        session = new Session(UpdateVenueActivity.this);

        try {
            id =  getIntent().getStringExtra("venue_id");

        }catch (Exception e){
            e.getLocalizedMessage();
        }

        pd = new ProgressDialog(UpdateVenueActivity.this);
        pd.setMessage("Updating.. Venue");
        pd.setCancelable(false);

        getvenueDetails();


        binding.phoneNumber.setFocusable(false);



        binding.brawoseUserImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FilePickerManager
                        .from(UpdateVenueActivity.this)
                        .maxSelectable(1)
                        .forResult(USER_IMAGE_CODE);

            }
        });


        binding.browseImageRegistrationProof.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FilePickerManager
                        .from(UpdateVenueActivity.this)
                        .maxSelectable(1)
                        .forResult(REGISTRATION_ID_CODE);

            }
        });


        binding.brawoseCoverPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FilePickerManager
                        .from(UpdateVenueActivity.this)
                        .maxSelectable(1)
                        .forResult(COVER_IMAGE_CODE);

            }
        });


        binding.createVenueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                add_intodatabase();


            }
        });





    }

    private void add_intodatabase() {

        getSelectedpackagevendors();
        getSelectedrecommnededvendors();

        pd.show();


        Log.e(TAG, "add_intodatabase() called User id "+ binding.vanueId.getText().toString());
        Log.e(TAG, "add_intodatabase() called  binding.vanueName.getText().toString() "+  binding.vanueName.getText().toString());
        Log.e(TAG, "add_intodatabase() called emailEdit "+ binding.emailEdit.getText().toString());
        Log.e(TAG, "add_intodatabase() called address "+ binding.address.getText().toString());
        Log.e(TAG, "add_intodatabase() called minEdit "+ binding.minEdit.getText().toString());
        Log.e(TAG, "add_intodatabase() called maxEdit "+ binding.maxEdit.getText().toString());
        Log.e(TAG, "add_intodatabase() called diningEdit "+ binding.diningEdit.getText().toString());
        Log.e(TAG, "add_intodatabase() called parking2Wheelers "+ binding.parking2Wheelers.getText().toString());
        Log.e(TAG, "add_intodatabase() called parking4Wheelers "+ binding.parking4Wheelers.getText().toString());
        Log.e(TAG, "add_intodatabase() called parking3Wheelers "+ binding.parking3Wheelers.getText().toString());
        Log.e(TAG, "add_intodatabase() called pricePerPlet "+ binding.pricePerPlet.getText().toString());
        Log.e(TAG, "add_intodatabase() called description "+ binding.description.getText().toString());
        Log.e(TAG, "add_intodatabase() called venueFeature1 "+ binding.venueFeature1.getText().toString());
        Log.e(TAG, "add_intodatabase() called venueFeature2 "+ binding.venueFeature2.getText().toString());
        Log.e(TAG, "add_intodatabase() called venueFeature3 "+ binding.venueFeature3.getText().toString());
        Log.e(TAG, "add_intodatabase() called venueFeature4 "+ binding.venueFeature4.getText().toString());
        Log.e(TAG, "add_intodatabase() called venueFeature5 "+ binding.venueFeature5.getText().toString());
        Log.e(TAG, "add_intodatabase() called selected_lang "+ selected_lang);
        Log.e(TAG, "add_intodatabase() called selected_lat "+ selected_lat);
        Log.e(TAG, "add_intodatabase() called packageVanu "+ packageVanu);
        Log.e(TAG, "add_intodatabase() called workWithNuptist "+ workWithNuptist);
        Log.e(TAG, "add_intodatabase() called id_proof_image "+ id_proof_image);
        Log.e(TAG, "add_intodatabase() called cover_image "+ cover_image);
        Log.e(TAG, "add_intodatabase() called user_image "+ user_image);


        ANRequest.MultiPartBuilder anAdd = AndroidNetworking.upload((BaseUrls.URL+UPDATE_VENUE));
        anAdd.addMultipartParameter("user_id", binding.vanueId.getText().toString());
        anAdd.addMultipartParameter("venue_name", binding.vanueName.getText().toString());
        anAdd.addMultipartParameter("email", binding.emailEdit.getText().toString());
        anAdd.addMultipartParameter("service_address", binding.address.getText().toString());
        anAdd.addMultipartParameter("guest_capycity_min", binding.minEdit.getText().toString());
        anAdd.addMultipartParameter("guest_capycity_mix", binding.maxEdit.getText().toString());
        anAdd.addMultipartParameter("dining_capycity", binding.diningEdit.getText().toString());
        anAdd.addMultipartParameter("parking_available_2wheeler", binding.parking2Wheelers.getText().toString());
        anAdd.addMultipartParameter("parking_available_4wheeler", binding.parking4Wheelers.getText().toString());
        anAdd.addMultipartParameter("parkings_available_3wheeler", binding.parking3Wheelers.getText().toString());
        if(!binding.venueFeature1.getText().toString().equalsIgnoreCase(""))anAdd.addMultipartParameter("venue_feature1", binding.venueFeature1.getText().toString());
        if(!binding.venueFeature2.getText().toString().equalsIgnoreCase(""))anAdd.addMultipartParameter("venue_feature2", binding.venueFeature2.getText().toString());
        if(!binding.venueFeature3.getText().toString().equalsIgnoreCase(""))anAdd.addMultipartParameter("venue_feature3", binding.venueFeature3.getText().toString());
        if(!binding.venueFeature4.getText().toString().equalsIgnoreCase(""))anAdd.addMultipartParameter("venue_feature4", binding.venueFeature4.getText().toString());
        if(!binding.venueFeature5.getText().toString().equalsIgnoreCase(""))anAdd.addMultipartParameter("venue_feature5", binding.venueFeature5.getText().toString());
        anAdd.addMultipartParameter("description", binding.description.getText().toString());
        anAdd.addMultipartParameter("lang", selected_lang);
        anAdd.addMultipartParameter("lat",selected_lat);
        anAdd.addMultipartParameter("venue_packages", packageVanu);
        anAdd.addMultipartParameter("venue_fore_packages", workWithNuptist);
        if(user_image != null)  anAdd.addMultipartFile("image", id_proof_image);
        if(cover_image != null)   anAdd.addMultipartFile("cover_image", cover_image);
        if(id_proof_image != null)    anAdd.addMultipartFile("gallary_image", user_image);
       if(!binding.pricePerPlet.getText().toString().equalsIgnoreCase("")) anAdd.addMultipartParameter("price_per_plate", binding.pricePerPlet.getText().toString());

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

                            if(result.equalsIgnoreCase("true")){
                                Toast.makeText(UpdateVenueActivity.this, "Venue details have been updated", Toast.LENGTH_SHORT).show();
                                finish();
                            }else {
                                Toast.makeText(UpdateVenueActivity.this, jsonObject.getString("msg"), Toast.LENGTH_SHORT).show();

                            }

                        } catch (JSONException e) {
                            pd.dismiss();
                            Toast.makeText(UpdateVenueActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onError(ANError anError) {
                        pd.dismiss();
                        Log.e("TAG", "onError: "+anError.getErrorBody());

                    }
                });



    }


    private void getSelectedpackagevendors() {

        int id = binding.forPackagesVenueGroup.getCheckedRadioButtonId();

        if(id != 0){
            RadioButton a = (RadioButton) findViewById(id);
            packageVanu = a.getText().toString();

        }else {
            Toast.makeText(this, "No", Toast.LENGTH_SHORT).show();
        }


    }
    private void getSelectedrecommnededvendors() {

        int id = binding.workWithNuptistVendorGroup.getCheckedRadioButtonId();

        if(id != 0){
            RadioButton a = (RadioButton) findViewById(id);
            workWithNuptist = (String) a.getText().toString();
        }else {
            Toast.makeText(this, "No", Toast.LENGTH_SHORT).show();
        }


    }




    public void   getvenueDetails(){

        ApiInterface apiInterface = RetrofitClient.getclient(UpdateVenueActivity.this);

        apiInterface.get_venue(session.getUserId_Vendor()).enqueue(new Callback<VenuModel>() {
            @Override
            public void onResponse(Call<VenuModel> call, Response<VenuModel> response) {

                try {

                    if(response.body().getResult().equalsIgnoreCase("true")){

                        setAllData(response.body().getData());

                    }

                }catch (Exception e){
                    e.getLocalizedMessage();
                }

            }

            @Override
            public void onFailure(Call<VenuModel> call, Throwable t) {

                Log.e("TAG", "onFailure() called with: call = [" + call + "], t = [" + t.getLocalizedMessage() + "]");
            }
        });


    }


    private void setAllData(List<VenuModel.VenueData> data) {

        id = data.get(0).getId();

        binding.vanueId.setText(data.get(0).getId());
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
        binding.pricePerPlet.setText(data.get(0).getPrice_per_plate());

        //binding.numberCountyCode.setText(data.get(0).getVenueName());

        binding.brawoseUserImage.setText(data.get(0).getImage());
        binding.browseImageRegistrationProof.setText(data.get(0).getGallaryImage());
        binding.brawoseCoverPhoto.setText(data.get(0).getCoverImage());

        String pckage = data.get(0).getVenuePackages();
        String for_with = data.get(0).getVenueForePackages();

        if(pckage.equalsIgnoreCase("No")){
            binding.forPackagesVenueNo.setChecked(true);
        }else {
            binding.forPackagesVenueYes.setChecked(true);

        }


        if(for_with.equalsIgnoreCase("No")){
            binding.workWithNuptistVendorNo.setChecked(true);
        }else {
            binding.workWithNuptistVendorYes.setChecked(true);

        }

         selected_lang = data.get(0).getLang();
         selected_lat = data.get(0).getLat();

            if (ContextCompat.checkSelfPermission(UpdateVenueActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            if (ActivityCompat.shouldShowRequestPermissionRationale(UpdateVenueActivity.this,
                    Manifest.permission.ACCESS_FINE_LOCATION)){
                ActivityCompat.requestPermissions(UpdateVenueActivity.this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            }else{
                ActivityCompat.requestPermissions(UpdateVenueActivity.this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            }
        }else {

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.add(R.id.fragment_container, new VenueMapFragment(Double.valueOf(data.get(0).getLat()),Double.valueOf(data.get(0).getLang()),false,UpdateVenueActivity.this));
            transaction.commit();
        }


    }

    @Override
    public void getAddress(double lat, double lang, String address) {

        selected_lang = String.valueOf(lang);
        selected_lat = String.valueOf(lat) ;
        seletcted_address = address ;
        binding.addressSelectecd.setText(address);

    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if(requestCode == USER_IMAGE_CODE && resultCode == RESULT_OK){
//            filepath = data.getData();
//              bitmap = setBitmap();
//            user_image = bitmapToFile(VendorCreateAccountActivity.this, bitmap);
//            Log.e("TAG", "onActivityResult() called with: userImage  = [" + user_image + "], resultCode = [" + resultCode + "], data = [" + data + "]");

            List<String> image = FilePickerManager.obtainData();
            user_image = new File(image.get(0));
            Log.e("TAG", "onActivityResult() called with: requestCode = [" + requestCode + "], resultCode = [" + resultCode + "], data = [" + user_image + "]");
            if (user_image != null) binding.brawoseUserImage.setText(user_image.getName());

        }else if(requestCode == REGISTRATION_ID_CODE && resultCode == RESULT_OK){
            List<String> image = FilePickerManager.obtainData();
            id_proof_image = new File(image.get(0));
            Log.e("TAG", "onActivityResult() called with: requestCode = [" + requestCode + "], resultCode = [" + resultCode + "], data = [" + id_proof_image + "]");
            if (id_proof_image != null) binding.browseImageRegistrationProof.setText(id_proof_image.getName());

        }else if(requestCode == COVER_IMAGE_CODE && resultCode == RESULT_OK){
            List<String> image = FilePickerManager.obtainData();
            cover_image = new File(image.get(0));
            Log.e("TAG", "onActivityResult() called with: requestCode = [" + requestCode + "], resultCode = [" + resultCode + "], data = [" + cover_image + "]");
            if (cover_image != null) binding.brawoseCoverPhoto.setText(cover_image.getName());

        }


    }


}