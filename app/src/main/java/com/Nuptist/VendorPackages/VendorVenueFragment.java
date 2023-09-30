package com.Nuptist.VendorPackages;


import static com.Nuptist.RetrofitApis.BaseUrls.IMAGE_URL;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.Nuptist.Models.PackageDetailsModel;
import com.Nuptist.Models.RecentPackagesModel;
import com.Nuptist.Models.ReviewModel;
import com.Nuptist.Models.VenueImagesModel;
import com.Nuptist.R;
import com.Nuptist.RetrofitApis.ApiInterface;
import com.Nuptist.RetrofitApis.RetrofitClient;
import com.Nuptist.Session.Session;
import com.Nuptist.adpaters.RecentPackageAdapter;
import com.Nuptist.adpaters.ReviewAdapter;
import com.Nuptist.adpaters.VenueImagesAdapter;
import com.Nuptist.databinding.FragmentVendorVenueBinding;

import com.squareup.picasso.Picasso;


import java.io.IOException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class VendorVenueFragment extends Fragment {


    PackageDetailsModel data;
    FragmentVendorVenueBinding binding;
    Session session ;


    private List<ReviewModel.ReviewData> reviewData = new ArrayList<>();

    public VendorVenueFragment(PackageDetailsModel data) {
        this.data = data ;
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentVendorVenueBinding.inflate(getLayoutInflater());

       //// getLocation();
        getVenueImages();
        setReviewData();

        session = new Session(getContext());

        getRecentPackages();

        try {
//
//
//            double lat = Double.parseDouble(data.getData().getVenue().get(0).getLat());
//            double lang = Double.parseDouble(data.getData().getVenue().get(0).getLang());
//
//
//            Geocoder geocoder;
//            geocoder = new Geocoder(getActivity(), Locale.getDefault());
//            List<Address> addresses = null;
//
//
//
//
//                addresses = geocoder.getFromLocation(lat, lang, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
//                // session.setAddress(addresses.get(0).getAddressLine(0));
//
//
//                String city = addresses.get(0).getLocality();
//                String subLocality = addresses.get(0).getSubLocality();
//                String state = addresses.get(0).getAdminArea();
//                String country = addresses.get(0).getCountryName();
//                String postalCode = addresses.get(0).getPostalCode();
        //}


            binding.venuePrice.setText("Rs. "+ NumberFormat.getIntegerInstance().format(Integer.parseInt(data.getData().getVenue().get(0).getVenue_price())));
            binding.venueName.setText(data.getData().getVenue().get(0).getVenueName());
            Picasso.get().load(IMAGE_URL+data.getData().getPackages().getImage()).into(binding.venurImaeg);


        } catch (Exception e) {
            e.printStackTrace();
        }

        return  binding.getRoot();
    }



    private void getLocation() {

//        String lat = data.getData().getVenue().get(0).getLat();
//        String lang = data.getData().getVenue().get(0).getLang();
//
//
//        Log.e("TAG", "getLocation() called Lat " + lat);
//        Log.e("TAG", "getLocation() called Lang " + lang);

    }

    private void getVenueImages() {

        String user_id = data.getData().getVenue().get(0).getUserId();

        ApiInterface apiInterface = RetrofitClient.getclient(getContext());

        apiInterface.getVenueImages(user_id).enqueue(new Callback<VenueImagesModel>() {
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
                                            Picasso.get().load(path + data.getGallaryImage2()).placeholder(R.drawable.ic_nature_svg).into(binding.galleryImage2);
                                            imaegs.add(path + data.getGallaryImage2());
                                        }

                                        if (!data.getGallaryImage3().equalsIgnoreCase("")) {
                                            Picasso.get().load(path + data.getGallaryImage3()).placeholder(R.drawable.ic_nature_svg).into(binding.galleryImage3);
                                            imaegs.add(path + data.getGallaryImage3());
                                        }

                                        if (!data.getGallaryImage4().equalsIgnoreCase("")) {
                                            Picasso.get().load(path + data.getGallaryImage4()).placeholder(R.drawable.ic_nature_svg).into(binding.galleryImage4);
                                            imaegs.add(path + data.getGallaryImage4());
                                        }
                                        if (!data.getGallaryImage5().equalsIgnoreCase("")) {
                                            Picasso.get().load(path + data.getGallaryImage5()).placeholder(R.drawable.ic_nature_svg).into(binding.galleryImage5);
                                            imaegs.add(path + data.getGallaryImage5());
                                        }

                                        if (!data.getGallaryImage6().equalsIgnoreCase("")) {
                                            Picasso.get().load(path + data.getGallaryImage6()).placeholder(R.drawable.ic_nature_svg).into(binding.galleryImage6);
                                            imaegs.add(path + data.getGallaryImage6());
                                        }

                                        if (!data.getGallaryImage7().equalsIgnoreCase("")) {
                                            Picasso.get().load(path + data.getGallaryImage7()).placeholder(R.drawable.ic_nature_svg).into(binding.galleryImage7);
                                            imaegs.add(path + data.getGallaryImage7());
                                        }
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }

                                    GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
                                    binding.venueImagesRecycler.setLayoutManager(gridLayoutManager);
                                    binding.venueImagesRecycler.setAdapter(new VenueImagesAdapter(getContext(), imaegs));

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

    private void setReviewData() {
        ApiInterface apiInterface = RetrofitClient.getclient(getContext());

        apiInterface.packageReview(data.getData().getPackages().getId()).enqueue(new Callback<ReviewModel>() {
            @Override
            public void onResponse(@NonNull Call<ReviewModel> call, @NonNull Response<ReviewModel> response) {

                if (response.code() == 200)
                    if (response.body() != null)
                        if (response.body().getResult().equalsIgnoreCase("true")) {
                            binding.reviewRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
                            binding.reviewRecycler.setAdapter(new ReviewAdapter(getContext(), response.body().getData()));
                            reviewData.addAll( response.body().getData());
                        }

            }

            @Override
            public void onFailure(@NonNull Call<ReviewModel> call, @NonNull Throwable t) {

            }
        });

    }

    private void getRecentPackages() {

        ApiInterface apiInterface = RetrofitClient.getclient(getContext());

        apiInterface.getRecentPackaes(session.getUserId()).enqueue(new Callback<RecentPackagesModel>() {
            @Override
            public void onResponse(@NonNull Call<RecentPackagesModel> call, @NonNull Response<RecentPackagesModel> response) {

                try {
                    if (response.code() == 200)
                        if (response.body() != null)
                            if (response.body().getResult().equalsIgnoreCase("true")) {
                                if (response.body().getData().size() != 0) {
                                    LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(getContext());
                                    linearLayoutManager2.setReverseLayout(true);
                                    binding.recentRecylerView.setLayoutManager(linearLayoutManager2);
                                    binding.recentRecylerView.setAdapter(new RecentPackageAdapter(getContext(), response.body().getData()));
                                } else {
                                    binding.recentLinear.setVisibility(View.GONE);
                                }

                            }else {
                                binding.recentLinear.setVisibility(View.GONE);
                            }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(@NonNull Call<RecentPackagesModel> call, @NonNull Throwable t) {
                Log.e("TAG", "onFailure() called with: call = [" + call + "], t = [" + t.getLocalizedMessage() + "]");
            }
        });

    }


}