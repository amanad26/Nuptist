package com.Nuptist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.Nuptist.Chat.ChatWithUsers.ChatWithVendorsActivity;
import com.Nuptist.Models.ProgressDialog;
import com.Nuptist.Models.VendorProfileModel;
import com.Nuptist.Models.VenuModel;
import com.Nuptist.Models.VenueImagesModel;
import com.Nuptist.RetrofitApis.ApiInterface;
import com.Nuptist.RetrofitApis.MapInterface;
import com.Nuptist.RetrofitApis.RetrofitClient;
import com.Nuptist.adpaters.VenueImagesAdapter;
import com.Nuptist.databinding.ActivityViewVendorProfileBinding;
import com.Nuptist.frags.VenueMapFragment;
import com.Nuptist.venue.AddVanueImagesActivity;
import com.Nuptist.venue.AddVenueActivity;
import com.Nuptist.venue.VenueActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewVendorProfileActivity extends AppCompatActivity implements MapInterface {

   private ActivityViewVendorProfileBinding binding ;
   private ViewVendorProfileActivity activity ;
   private String vendor_id = "" , type = "0" , f_name = "", f_image = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityViewVendorProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        activity = this ;

        vendor_id = getIntent().getStringExtra("vendor_id");
        type = getIntent().getStringExtra("type");
        f_name  = getIntent().getStringExtra("f_name");
        f_image  = getIntent().getStringExtra("f_image");
        getVendorProfile();
        getVenueDetails();
        getVenueImages();

        if(type.equalsIgnoreCase("1")){
            binding.chatWithVendor.setVisibility(View.VISIBLE);
        }



        binding.chatWithVendor.setOnClickListener(view -> startActivity(new Intent(ViewVendorProfileActivity.this, ChatWithVendorsActivity.class)
                           .putExtra("f_id",vendor_id)
                           .putExtra("f_name",f_name)
                           .putExtra("f_image",f_image)
                   ));

    }

    private  void getVendorProfile(){

        ProgressDialog pd = new ProgressDialog(activity);
        pd.show();

        ApiInterface apiInterface = RetrofitClient.getclient(activity);

        apiInterface.get_vendor_profile(vendor_id).enqueue(new Callback<VendorProfileModel>() {
            @Override
            public void onResponse(@NonNull Call<VendorProfileModel> call, @NonNull Response<VendorProfileModel> response) {

                pd.dismiss();
                try {

                    if (response.body() != null && response.body().getResult().equalsIgnoreCase("true")) {

                        binding.emailEdit.setText(response.body().getData().getEmail());
                        binding.vendorEmail.setText(response.body().getData().getEmail());
                        binding.fName.setText(response.body().getData().getName());
                        binding.vendorName.setText(response.body().getData().getName());
                        binding.lName.setText(response.body().getData().getLname());
                        binding.serviceAddress.setText(response.body().getData().getServicesAddress());
                        binding.serviceAvailable.setText(response.body().getData().getServiceAbailble());
                        binding.phoneNumber.setText(response.body().getData().getMobile());
                        Log.e("TAG", "Vendor Profile Image  = [" + response.body().getPath() + response.body().getData().getImage() + "]");


                        Log.d("TAG", "Vendor Profile Image  = [" + response.body().getPath() + response.body().getData().getImage() + "]");
                        Picasso.get().load(response.body().getPath() + response.body().getData().getImage()).placeholder(R.drawable.user).into(binding.vendorProfile);
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

    public void getVenueDetails() {

        ApiInterface apiInterface = RetrofitClient.getclient(activity);

        apiInterface.get_venue(vendor_id).enqueue(new Callback<VenuModel>() {
            @Override
            public void onResponse(@NonNull Call<VenuModel> call, @NonNull Response<VenuModel> response) {

                try {

                    if (response.body() != null) {
                        if (response.body().getResult().equalsIgnoreCase("true")) {
                           binding.vanueName.setText(response.body().getData().get(0).getVenueName());
                           binding.minEdit.setText(response.body().getData().get(0).getGuestCapycityMin());
                           binding.maxEdit.setText(response.body().getData().get(0).getGuestCapycityMix());


                           if(!response.body().getData().get(0).getLat().equalsIgnoreCase("") && !response.body().getData().get(0).getLang().equalsIgnoreCase("")){
                               FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                               transaction.add(R.id.fragment_container, new VenueMapFragment(Double.parseDouble(response.body().getData().get(0).getLat()), Double.parseDouble(response.body().getData().get(0).getLang()), false, activity));
                               transaction.commit();
                           }

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

    private void getVenueImages() {
        ApiInterface apiInterface = RetrofitClient.getclient(activity);

        apiInterface.getVenueImages(vendor_id).enqueue(new Callback<VenueImagesModel>() {
            @Override
            public void onResponse(@NonNull Call<VenueImagesModel> call, @NonNull Response<VenueImagesModel> response) {

                try {

                    if (response.code() == 200)
                        if (response.body() != null) {
                            if (response.body().getResult().equalsIgnoreCase("true")) {
                                Log.e("TAG", "onResponse() called with: call = [" + call + "], response = [" + response.body().getMsg() + "]");

                                VenueImagesModel.VenueImagesData data = response.body().getData().get(0);

                                String path = response.body().getPath();

                                ArrayList<String> imaegs = new ArrayList<>();

                                if (!data.getGallaryImage().equalsIgnoreCase("")) {


                                    try {

                                        if (!data.getGallaryImage2().equalsIgnoreCase("")) {
                                            imaegs.add(path + data.getGallaryImage2());
                                        }

                                        if (!data.getGallaryImage3().equalsIgnoreCase("")) {
                                            imaegs.add(path + data.getGallaryImage3());
                                        }

                                        if (!data.getGallaryImage4().equalsIgnoreCase("")) {
                                            imaegs.add(path + data.getGallaryImage4());
                                        }
                                        if (!data.getGallaryImage5().equalsIgnoreCase("")) {
                                            imaegs.add(path + data.getGallaryImage5());
                                        }

                                        if (!data.getGallaryImage6().equalsIgnoreCase("")) {
                                            imaegs.add(path + data.getGallaryImage6());
                                        }

                                        if (!data.getGallaryImage7().equalsIgnoreCase("")) {
                                            imaegs.add(path + data.getGallaryImage7());
                                        }
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }

                                    GridLayoutManager gridLayoutManager = new GridLayoutManager(activity, 2);
                                    binding.venueImagesRecycler.setLayoutManager(gridLayoutManager);
                                    binding.venueImagesRecycler.setAdapter(new VenueImagesAdapter(activity, imaegs));

//                                    Picasso.get().load(path + data.getGallaryImage3()).placeholder(R.drawable.ic_nature_svg).into(binding.galleryImage3);
//                                    Picasso.get().load(path + data.getGallaryImage4()).placeholder(R.drawable.ic_nature_svg).into(binding.galleryImage4);
//                                    Picasso.get().load(path + data.getGallaryImage5()).placeholder(R.drawable.ic_nature_svg).into(binding.galleryImage5);
//                                    Picasso.get().load(path + data.getGallaryImage6()).placeholder(R.drawable.ic_nature_svg).into(binding.galleryImage6);
//                                    Picasso.get().load(path + data.getGallaryImage7()).placeholder(R.drawable.ic_nature_svg).into(binding.galleryImage7);

                                }


                            } else {
                                Log.e("TAG", "onResponse() called with: call = [" + call + "], response = [" + response.body().getMsg() + "]");
                            }
                        }

                } catch (Exception e) {
                    e.getLocalizedMessage();
                }


            }

            @Override
            public void onFailure(@NonNull Call<VenueImagesModel> call, @NonNull Throwable t) {

                Log.e("TAG", "onFailure() called with: call = [" + call + "], t = [" + t.getLocalizedMessage() + "]");
            }
        });

    }

    @Override
    public void getAddress(double lat, double lang, String address) {

    }
}