package com.Nuptist;

import static com.Nuptist.RetrofitApis.BaseUrls.IMAGE_URL;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.Nuptist.Models.PackageDetailsModel;
import com.Nuptist.Models.PackageViewModel;
import com.Nuptist.Models.ProgressDialog;
import com.Nuptist.Models.ReviewModel;
import com.Nuptist.MyBookings.MyBookingAddonsAdapter;
import com.Nuptist.MyBookings.MyBookingDetailsModel;
import com.Nuptist.MyBookings.MyBookingOffersAdapter;
import com.Nuptist.RetrofitApis.ApiInterface;
import com.Nuptist.RetrofitApis.RetrofitClient;
import com.Nuptist.Session.Session;
import com.Nuptist.VendorPackages.VendorPackagedetailsActivity;
import com.Nuptist.adpaters.BookedBidsVendorAdapter;
import com.Nuptist.databinding.ActivityBarDetailsBinding;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BarDetailsActivity extends AppCompatActivity {

    ActivityBarDetailsBinding binding;

    String TAG = "BarDetailsActivity";
    String booking_id ;

    Session session ;
    ProgressDialog pd;
    boolean isaddonceseleced = false ;
    boolean isofferseleced = false ;
    boolean isvendorseleced = false ;
    private String package_id;
    private boolean is_liked= false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBarDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        session = new Session(BarDetailsActivity.this);

        pd = new ProgressDialog(BarDetailsActivity.this);
        pd.show();

        try {
             booking_id  =  getIntent().getStringExtra("booking_id");
             String type =  getIntent().getStringExtra("type");


           if(!type.equalsIgnoreCase("user")){
               binding.vendorLinaer2.setVisibility(View.GONE);
               binding.noRevireAdded.setVisibility(View.GONE);
            }

            getBookingDetails();
        } catch (Exception e) {
            e.printStackTrace();
        }


        binding.icSelectdAddons.setOnClickListener(v -> {

            if (isaddonceseleced){
                isaddonceseleced = false ;
                binding.icSelectdAddons.setImageResource(R.drawable.ic_arrow_up);
                binding.addonsRecler.setVisibility(View.VISIBLE);
            }else {
                isaddonceseleced = true ;
                binding.icSelectdAddons.setImageResource(R.drawable.ic_arrow_down);
                binding.addonsRecler.setVisibility(View.GONE);
            }

        });


        binding.likedImage.setOnClickListener(view -> {

            if (is_liked) {
                binding.likedImage.setImageResource(R.drawable.ic_baseline_favorite_24);
                is_liked = true;
            } else {
                binding.likedImage.setImageResource(R.drawable.ic_like);
                is_liked = false;
            }

            likePackage();

        });

        binding.icSelectdOffers.setOnClickListener(v -> {
            if(isofferseleced){
                isofferseleced = false;
                binding.icSelectdOffers.setImageResource(R.drawable.ic_arrow_up);
                binding.offerRecler.setVisibility(View.VISIBLE);
            }else {
                isofferseleced = true;
                binding.icSelectdOffers.setImageResource(R.drawable.ic_arrow_down);
                binding.offerRecler.setVisibility(View.GONE);
            }
        });


        binding.vendorLinear.setOnClickListener(v -> {

            if(isvendorseleced){
                isvendorseleced = false;
                binding.vendorsRecler.setVisibility(View.VISIBLE);
                binding.icSelectdVendor.setImageResource(R.drawable.ic_arrow_up);

            }else {
                binding.icSelectdVendor.setImageResource(R.drawable.ic_arrow_down);
                binding.vendorsRecler.setVisibility(View.GONE);
                isvendorseleced = true ;
            }

        });

        binding.addReview.setOnClickListener(v -> startActivity(new Intent(BarDetailsActivity.this,AddwebCameActivity.class)
                .putExtra("p_id",package_id)
                .putExtra("b_id",booking_id)
        ));

    }

    private void likePackage() {
        ApiInterface apiInterface = RetrofitClient.getclient(BarDetailsActivity.this);
        String userid;
        if (session.getType().equalsIgnoreCase("Vendor")) {
            userid = session.getUserId_Vendor();
        } else {
            userid = session.getUserId();
        }

        apiInterface.favoritePackage(session.getUserId(), package_id).enqueue(new Callback<PackageViewModel>() {
            @Override
            public void onResponse(@NonNull Call<PackageViewModel> call, @NonNull Response<PackageViewModel> response) {

                try {

                    if (response.body() != null && response.body().getResult().equalsIgnoreCase("true")) {
                        if (response.body().getMsg().equalsIgnoreCase("favorite success")) {
                            is_liked = true;
                            binding.likedImage.setImageResource(R.drawable.ic_baseline_favorite_24);
                            Log.e("TAG", "Like Package  =  response = [" + response.body().getMsg() + "]");
                        } else {
                            is_liked = false;
                            binding.likedImage.setImageResource(R.drawable.ic_like);
                            Log.e("TAG", "Like Package  =  response = [" + response.body().getMsg() + "]");
                        }

                    }

                } catch (Exception e) {
                    e.getLocalizedMessage();
                }

            }

            @Override
            public void onFailure(@NonNull Call<PackageViewModel> call, @NonNull Throwable t) {
                Log.e("TAG", "onFailure() called with: call = [" + call + "], t = [" + t + "]");
            }
        });

    }
    private  void getBookingDetails(){

        ApiInterface apiInterface = RetrofitClient.getclient(BarDetailsActivity.this);

        apiInterface.getMyBookingDetails(booking_id).enqueue(new Callback<MyBookingDetailsModel>() {
            @Override
            public void onResponse(@NonNull Call<MyBookingDetailsModel> call, @NonNull Response<MyBookingDetailsModel> response) {
                try {
                    if(response.code() == 200)
                        if(response.body()!= null)
                            if(response.body().getResult().equalsIgnoreCase("true")){

                                binding.day.setText(response.body().getData().getPackageName());
//                                binding.day.setText(response.body().getData().getTotalAmount());
                                binding.day.setText("Rs. "+ NumberFormat.getIntegerInstance().format(Integer.parseInt(response.body().getData().getTotalAmount())));

                                package_id = response.body().getData().getPackageId();
                                getPackageDetials(response.body().getData().getPackageId());

                                binding.totalPrice.setText("Rs. "+ NumberFormat.getIntegerInstance().format(Integer.parseInt(response.body().getData().getTotalAmount())));
                               // binding.totalPrice.setText("Rs. "+response.body().getData().getTotalAmount());

                                binding.dateId2.setText("Your booking is ready and suitable for the specified date"+"("+formatDate(response.body().getData().getStartDate())+")");


                                List<MyBookingDetailsModel.MyBookingdata.ProductOfferIdList> product_list = response.body().getData().getProductOfferDetailsId();
                                List<MyBookingDetailsModel.MyBookingdata.ServiceOfferIdList> service_list = response.body().getData().getServiceOfferDetailsId();
                                List<MyBookingDetailsModel.MyBookingdata.ServiceIdList> service_list2 = response.body().getData().getServiceId();
                                List<MyBookingDetailsModel.MyBookingdata.ProductIdList> product_list2 = response.body().getData().getProductId();

                                List<MyBookingDetailsModel.MyBookingdata.BidsAddonce> filterd_addonsBid = new ArrayList<>();

                                List<MyBookingDetailsModel.MyBookingdata.BidsAddonce> bidAddonsList = response.body().getData().getBidsAddonce();
                                List<MyBookingDetailsModel.MyBookingdata.BidsOffer> bidOfferList = response.body().getData().getBidsOffer();

                                if(bidAddonsList == null){
                                    bidAddonsList = new ArrayList<>();
                                }

                                if(bidOfferList == null){
                                    bidOfferList = new ArrayList<>();
                                }


                                if (product_list == null) {
                                    product_list = new ArrayList<>();
                                }
                                if (service_list == null) {
                                    service_list = new ArrayList<>();
                                }

                                if (product_list2 == null) {
                                    product_list2 = new ArrayList<>();
                                }
                                if (service_list2 == null) {
                                    service_list2 = new ArrayList<>();
                                }


                                int totalofferpandS = product_list.size()+service_list.size() ;

                                binding.offerCount.setText("("+totalofferpandS+")");
                                binding.offerRecler.setLayoutManager(new LinearLayoutManager(BarDetailsActivity.this));
                                binding.offerRecler.setAdapter(new MyBookingOffersAdapter(BarDetailsActivity.this,product_list,service_list));


                                if(response.body().getData().getBookingStatus().equalsIgnoreCase("pending")){
                                    binding.bookingNow.setVisibility(View.VISIBLE);
                                    binding.bookingNow2.setVisibility(View.GONE);
                                    binding.addedReview.setVisibility(View.GONE);
                                    binding.addReview.setVisibility(View.GONE);
                                    binding.bookingNow.setOnClickListener(v -> cancelMyBooking(response.body().getData().getId()));
                                }else if(response.body().getData().getBookingStatus().equalsIgnoreCase("inprogress")){
                                    binding.bookingNow.setVisibility(View.GONE);
                                    binding.bookingNow2.setVisibility(View.VISIBLE);
                                    binding.bookingNow2.setText("In Progress");
                                    binding.addedReview.setVisibility(View.GONE);
                                    binding.addReview.setVisibility(View.GONE);
                                }else if(response.body().getData().getBookingStatus().equalsIgnoreCase("completed")){
                                    binding.bookingNow.setVisibility(View.GONE);
                                    binding.bookingNow2.setVisibility(View.VISIBLE);
                                    binding.bookingNow2.setText("Completed");
                                    binding.addedReview.setVisibility(View.GONE);
                                    binding.addReview.setVisibility(View.VISIBLE);
                                }else {
                                    binding.bookingNow.setVisibility(View.GONE);
                                    binding.bookingNow2.setVisibility(View.VISIBLE);
                                    binding.bookingNow2.setText("Cancelled");
                                }

                                if(response.body().getData().getReviewStatus().equalsIgnoreCase("1")){
                                    binding.addedReview.setVisibility(View.VISIBLE);
                                    binding.addReview.setVisibility(View.GONE);
                                }else {
                                    binding.addedReview.setVisibility(View.GONE);
                                }
//
                                int totalofferpandS2 = product_list2.size()+service_list2.size() ;

                                binding.addonsCount.setText("("+totalofferpandS2+")");
                                binding.addonsRecler.setLayoutManager(new LinearLayoutManager(BarDetailsActivity.this));
                                binding.addonsRecler.setAdapter(new MyBookingAddonsAdapter(BarDetailsActivity.this,product_list2,service_list2));

                                int totalVendors = bidAddonsList.size() + bidOfferList.size() ;

                                if(totalVendors == 0)
                                    binding.vendorLinaer2.setVisibility(View.GONE);

                                binding.vendorsCount.setText("("+String.valueOf(totalVendors)+")");
                                binding.vendorsRecler.setLayoutManager(new LinearLayoutManager(BarDetailsActivity.this));
                                Log.e(TAG, "onResponse: model1 " + bidAddonsList.size());
                                Log.e(TAG, "onResponse: model2 " + bidOfferList.size());
                                ////Log.e(TAG, "onResponse: model2 " + bidOfferList.get(0).toString());
                                binding.vendorsRecler.setAdapter(new BookedBidsVendorAdapter(BarDetailsActivity.this,bidAddonsList,bidOfferList));

                            }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(@NonNull Call<MyBookingDetailsModel> call, @NonNull Throwable t) {
                pd.dismiss();
                Log.e(TAG, "onFailure() called with: call = [" + call + "], t = [" + t.getLocalizedMessage() + "]");
            }
        });

    }

    private void cancelMyBooking(String id) {
        pd.show();
        ApiInterface apiInterface = RetrofitClient.getclient(BarDetailsActivity.this);
        apiInterface.cancelMyBooking(id, session.getUserId()).enqueue(new Callback<MyBookingDetailsModel>() {
            @Override
            public void onResponse(@NonNull Call<MyBookingDetailsModel> call, @NonNull Response<MyBookingDetailsModel> response) {

                pd.dismiss();
                try {

                    if (response.body() != null) {
                        if (response.body().getResult().equalsIgnoreCase("true")) {
                            Toast.makeText(BarDetailsActivity.this, "Booking Canceled..", Toast.LENGTH_SHORT).show();
                            startActivity( new Intent(BarDetailsActivity.this,BookingActivity.class));
                            finish();
                        }else {
                            Toast.makeText(BarDetailsActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                        }
                    }

                } catch (Exception e) {
                    pd.dismiss();
                    Log.e("Date--------->", "onResponse() called with: Start Date = [" + call + "], response = [" + e.getLocalizedMessage() + "]");
                    e.getLocalizedMessage();
                }

            }

            @Override
            public void onFailure(@NonNull Call<MyBookingDetailsModel> call, @NonNull Throwable t) {
                pd.dismiss();
                Log.d(TAG, "onFailure() called with: call = [" + call + "], t = [" + t + "]");
            }
        });

    }

    private void getPackageDetials(String packageId) {
//
        pd.dismiss();

        ApiInterface apiInterface = RetrofitClient.getclient(BarDetailsActivity.this);

        apiInterface.getPackagesDetails(packageId, session.getUserId()).enqueue(new Callback<PackageDetailsModel>() {
            @Override
            public void onResponse(@NonNull Call<PackageDetailsModel> call, @NonNull Response<PackageDetailsModel> response) {

                try {

                    if (response.body() != null) {
                        if (response.body().getResult().equalsIgnoreCase("true")) {

                            binding.packagePeople.setText(response.body().getData().getVenue().get(0).getGuestCapycityMix()+" People");
                            binding.bigAndLux.setText(response.body().getData().getPackages().getPackageName());

                            String res = response.body().getData().getPackages().getCity().substring(0, 1).toUpperCase() + response.body().getData().getPackages().getCity().substring(1);
                            binding.packageAddress.setText(res);

                            try {

                                if(String.valueOf(response.body().getData().getPackages().getI_liked()).equalsIgnoreCase("1"))
                                    binding.likedImage.setImageResource(R.drawable.ic_baseline_favorite_24);


                                Picasso.get().load(IMAGE_URL+response.body().getData().getPackages().getImage()).placeholder(R.drawable.ic_nature_svg).into(binding.packageImage);
                                binding.packageCreatedDate.setText(formatDate(response.body().getData().getPackages().getStartDate()));

                                getReviewData(package_id);
                            }catch (Exception e){
                                e.getLocalizedMessage();
                                Log.e("TAG", "Start Date Error  = [" + e.getLocalizedMessage() + "]");
                            }

                        }
                    }

                } catch (Exception e) {

                    Log.e("Date--------->", "onResponse() called with: Start Date = [" + call + "], response = [" + e.getLocalizedMessage() + "]");
                    e.getLocalizedMessage();
                }

            }

            @Override
            public void onFailure(@NonNull Call<PackageDetailsModel> call, @NonNull Throwable t) {
                pd.dismiss();
                Log.e("TAG", "onFailure() called with: call = [" + call + "], t = [" + t.getLocalizedMessage() + "]");

            }
        });


    }


    public String formatDate(String s) {
        //  s = "2022-10-21 08:08:18";

        String[] dateTime = s.split(" ");

        String dateS = dateTime[0];
//        String timeS = dateTime[1];
//
//        timeS = timeS.substring(0, 5);

        String[] datess = dateS.split("-");

        String year = datess[0];
        String month = datess[1];
        String day = datess[2];

        return day + " " + getMonthName(Integer.parseInt(month)) + " " + year + " ";
    }

    public String getMonthName(int month) {

        String monthString;
        switch (month) {
            case 1:
                monthString = "January";
                break;
            case 2:
                monthString = "February";
                break;
            case 3:
                monthString = "March";
                break;
            case 4:
                monthString = "April";
                break;
            case 5:
                monthString = "May";
                break;
            case 6:
                monthString = "June";
                break;
            case 7:
                monthString = "July";
                break;
            case 8:
                monthString = "August";
                break;
            case 9:
                monthString = "September";
                break;
            case 10:
                monthString = "October";
                break;
            case 11:
                monthString = "November";
                break;
            case 12:
                monthString = "December";
                break;
            default:
                monthString = "Invalid month";
                break;
        }
        System.out.println(monthString);

        return monthString;
    }


    private void getReviewData(String package_id) {
        ApiInterface apiInterface = RetrofitClient.getclient(BarDetailsActivity.this);

        apiInterface.packageReview(package_id).enqueue(new Callback<ReviewModel>() {
            @Override
            public void onResponse(@NonNull Call<ReviewModel> call, @NonNull Response<ReviewModel> response) {

                if (response.code() == 200)
                    if (response.body() != null)
                        if (response.body().getResult().equalsIgnoreCase("true")) {
                            float review = 0 ;

                            int total_5 = 0 ;
                            int total_4 = 0 ;
                            int total_3 = 0 ;
                            int total_2 = 0 ;
                            int total_1 = 0 ;


                            for(int i =0 ; i<response.body().getData().size(); i++){
                                float re = Float.parseFloat(response.body().getData().get(i).getRating());

//                                if(re == 1) total_1 = total_1+1 ;
//                                else if(re ==2 ) total_2 = total_2 +1 ;
//                                else if(re ==3 ) total_3 = total_3 +1 ;
//                                else if(re ==4 ) total_4 = total_4 +1 ;
//                                else  total_5 = total_5+1  ;
//
                                review = review+re ;
                            }

//                            Log.e("TAG", "onResponse() called with: Pos "+holder.getAdapterPosition());
//
//                            Log.e("TAG", "onResponse() called with: total_1"+total_1);
//                            Log.e("TAG", "onResponse() called with: total_2"+total_2);
//                            Log.e("TAG", "onResponse() called with: total_3"+total_3);
//                            Log.e("TAG", "onResponse() called with: total_4"+total_4);
//                            Log.e("TAG", "onResponse() called with: total_5"+total_5);
//
//                            float total_user_count = total_1+total_2+total_3+total_4+total_5 ;
//                            Log.e("TAG", "onResponse() called with: total rating "+total_user_count );

//                            if (total_user_count > 0) {
//                                float total =  (total_1*1 + total_2*2 + total_3*3 + total_4*4 +total_5*5)/total_user_count ;
//
//                                Log.e("TAG", "onResponse() called with: total rating "+total );
//                            }else {
//                                Log.e("TAG", "onResponse() called with: total rating 0" );
//                            }


                            if (response.body().getData().size() != 0 ) {
                                binding.someId.setText(String.valueOf(review/response.body().getData().size()));
                            }else {
                                binding.someId.setText(String.valueOf(0));
                            }



                        }

            }

            @Override
            public void onFailure(@NonNull Call<ReviewModel> call, @NonNull Throwable t) {

            }
        });

    }

}

