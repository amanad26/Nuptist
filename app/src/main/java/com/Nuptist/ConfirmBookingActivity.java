package com.Nuptist;

import static com.Nuptist.RetrofitApis.BaseUrls.IMAGE_URL;
import static com.Nuptist.VenueFragment.selectedDate;
import static com.Nuptist.adpaters.AddOnsAdapterNew2.addOnceModels;
import static com.Nuptist.adpaters.OfferAdapter2.offersModel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.Nuptist.Models.BookingModels.BookingAddOnceModel;
import com.Nuptist.Models.BookingModels.BookingOffersModel;
import com.Nuptist.Models.LogOutModel;
import com.Nuptist.Models.PackageDetailsModel;
import com.Nuptist.Models.PackageViewModel;
import com.Nuptist.Models.ProgressDialog;

import com.Nuptist.RetrofitApis.ApiInterface;
import com.Nuptist.RetrofitApis.RetrofitClient;
import com.Nuptist.Session.Session;
import com.Nuptist.databinding.ActivityConfirmBookingBinding;
import com.Nuptist.databinding.OfferAddonceLayoutBinding;
import com.Nuptist.venue.AddVenueActivity;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ConfirmBookingActivity extends AppCompatActivity {

    private ActivityConfirmBookingBinding binding;
    private String TAG = "ConfirmBookingActivity";
    private String package_id, package_name, pricePerPlet, numberOfGuest, pricePerPletTotal, venue_price, finalPackagePrice, total_addons, total_offers, start_date, end_date, selectedOffers, selectedAddonce;

    private Session session;
    private ProgressDialog pd;
    private boolean isaddonceseleced = false;
    private boolean isofferseleced = false;
    private String venue_vendor_id = "";
    private  boolean isChecked = false ;

    private String serviceaddonsId, productaddonsId, servieoffersId, productofferId, offer_id_vendor, addons_vendor;
    private List<BookingOffersModel> offersModelNew = new ArrayList<>();
    private List<BookingAddOnceModel> addOnceModelsNew = new ArrayList<>();
    private boolean is_liked= false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityConfirmBookingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        session = new Session(ConfirmBookingActivity.this);

        pd = new ProgressDialog(ConfirmBookingActivity.this);
        pd.show();


        binding.icBack.setOnClickListener(view -> onBackPressed());

        try {
            package_id = getIntent().getStringExtra("package_id");
            total_addons = getIntent().getStringExtra("total_addons");
            total_offers = getIntent().getStringExtra("total_offers");
            start_date = getIntent().getStringExtra("start_date");
            end_date = getIntent().getStringExtra("end_date");
            selectedOffers = getIntent().getStringExtra("selectedOffers");
            selectedAddonce = getIntent().getStringExtra("selectedAddonce");
            selectedAddonce = getIntent().getStringExtra("selectedAddonce");
            finalPackagePrice = getIntent().getStringExtra("finalPackagePrice");
            serviceaddonsId = getIntent().getStringExtra("serviceaddonsId");
            productaddonsId = getIntent().getStringExtra("productaddonsId");
            servieoffersId = getIntent().getStringExtra("servieoffersId");
            productofferId = getIntent().getStringExtra("productofferId");
            venue_price = getIntent().getStringExtra("venue_price");
            offersModelNew = (List<BookingOffersModel>) getIntent().getSerializableExtra("offermodel");
            addOnceModelsNew = (List<BookingAddOnceModel>) getIntent().getSerializableExtra("addonsmodel");
            offer_id_vendor = getIntent().getStringExtra("offer_id_vendor");
            pricePerPlet = getIntent().getStringExtra("price_per_plet");
            pricePerPletTotal = getIntent().getStringExtra("price_per_plet_total");
            addons_vendor = getIntent().getStringExtra("addons_vendor");
            venue_vendor_id = getIntent().getStringExtra("venue_vendor_id");
            numberOfGuest = getIntent().getStringExtra("numofguest");


            Log.e(TAG, "onCreate() called with: package_id = [" + package_id + "]");
            Log.e(TAG, "onCreate() called with: total_addons = [" + total_addons + "]");
            Log.e(TAG, "onCreate() called with: total_offers = [" + total_offers + "]");
            Log.e(TAG, "onCreate() called with: start_date = [" + start_date + "]");
            Log.e(TAG, "onCreate() called with: end_date = [" + end_date + "]");
            Log.e(TAG, "onCreate() called with: price per Plate = [" + pricePerPlet + "]");
            Log.e(TAG, "onCreate() called with: price per Plate Total  = [" + pricePerPletTotal + "]");
            Log.e(TAG, "onCreate() called with: Number of guest = [" + numberOfGuest + "]");

//            binding.pricePerPlat.setText("Price Per Plate (" + pricePerPlet + ")");
            binding.numberOfGuest.setText("Number Of Guest (" + numberOfGuest + ")");
          //   binding.pericePerPletTotal.setText(pricePerPletTotal);
            binding.pricePerPlat.setText("Price Per Plate (" +NumberFormat.getIntegerInstance().format(Integer.parseInt(pricePerPlet))+ ")");
            binding.pericePerPletTotal.setText(NumberFormat.getIntegerInstance().format(Integer.parseInt(pricePerPletTotal)));


            binding.tottalOffers.setText("(" + total_offers + ")");
            binding.tottalAddons.setText("(" + total_addons + ")");

            binding.dateId.setText("Your booking is ready and suitable for the specified date " + "(" + formatDate(start_date) + ")");
//          binding.dateId.setText("Your booking is ready and suitable for the specified date " + "(" + formatDate(start_date) + " To " + formatDate(end_date) + ")");

          //  binding.totalPrice.setText("Rs. " + finalPackagePrice);
            binding.totalPrice.setText(NumberFormat.getIntegerInstance().format(Integer.parseInt(finalPackagePrice)));
            binding.venuePrice.setText(NumberFormat.getIntegerInstance().format(Integer.parseInt(venue_price)));

           //  binding.venuePrice.setText("Rs. " + venue_price);

            binding.addonsRecler.setLayoutManager(new LinearLayoutManager(ConfirmBookingActivity.this));
            binding.addonsRecler.setAdapter(new OfferInnerAdapter(ConfirmBookingActivity.this, offersModelNew, addOnceModelsNew, 1));

            binding.offerRecler.setLayoutManager(new LinearLayoutManager(ConfirmBookingActivity.this));
            binding.offerRecler.setAdapter(new OfferInnerAdapter(ConfirmBookingActivity.this, offersModelNew, addOnceModelsNew, 0));


            binding.venueTermsAndCondition.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    isChecked = b ;
                }
            });

            binding.vewAddOnceRecy.setOnClickListener(v -> {
                if (binding.addonsRecler.getVisibility() == View.VISIBLE) {
                    binding.addonsRecler.setVisibility(View.GONE);
                } else {
                    binding.addonsRecler.setVisibility(View.VISIBLE);
                }
            });

            binding.vewAddOnceRecy.setOnClickListener(v -> {
                if (binding.offerRecler.getVisibility() == View.VISIBLE) {
                    binding.offerRecler.setVisibility(View.GONE);
                } else {
                    binding.offerRecler.setVisibility(View.VISIBLE);
                }
            });


            getPackageDetials(package_id);
        } catch (Exception e) {
            e.printStackTrace();
        }

        binding.termsConditionTv.setOnClickListener(view -> startActivity(new Intent(ConfirmBookingActivity.this, TemsAndConditionActivity.class).putExtra("type","Partner")));


        binding.icSelectdAddons.setOnClickListener(v -> {

            if (isaddonceseleced) {
                isaddonceseleced = false;
                binding.icSelectdAddons.setImageResource(R.drawable.ic_arrow_up);
                binding.addonsRecler.setVisibility(View.VISIBLE);
            } else {
                isaddonceseleced = true;
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
            if (isofferseleced) {
                isofferseleced = false;
                binding.icSelectdOffers.setImageResource(R.drawable.ic_arrow_up);
                binding.offerRecler.setVisibility(View.VISIBLE);
            } else {
                isofferseleced = true;
                binding.icSelectdOffers.setImageResource(R.drawable.ic_arrow_down);
                binding.offerRecler.setVisibility(View.GONE);
            }
        });

        binding.bookingNow.setOnClickListener(v -> {
           if(isChecked)  addData();
           else Toast.makeText(ConfirmBookingActivity.this, "Please Accept Terms and Conditions", Toast.LENGTH_SHORT).show();
        });

    }

    private void likePackage() {
        ApiInterface apiInterface = RetrofitClient.getclient(ConfirmBookingActivity.this);
        String userid;
//        if (session.getType().equalsIgnoreCase("Vendor")) {
//            userid = session.getUserId_Vendor();
//        } else {
//            userid = session.getUserId();
//        }

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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    private void addData() {

        pd.show();

        Random random = new Random();
        int transectionId = random.nextInt(1000000);


        if (serviceaddonsId.equalsIgnoreCase("")) {
            serviceaddonsId = "0,0";
        }

        if (servieoffersId.equalsIgnoreCase("")) {
            servieoffersId = "0,0";
        }
        if (productofferId.equalsIgnoreCase("")) {
            productofferId = "0,0";
        }

        if (productaddonsId.equalsIgnoreCase("")) {
            productaddonsId = "0,0";
        }

        if (addons_vendor.equalsIgnoreCase(""))
            addons_vendor = "0,0";

        if (offer_id_vendor.equalsIgnoreCase(""))
            offer_id_vendor = "0,0";


        Log.e(TAG, "addData() called package_id" + package_id);
        Log.e(TAG, "addData() called session.getUserId()" + session.getUserId());
        Log.e(TAG, "addData() called venue_vendor_id" + venue_vendor_id);
        Log.e(TAG, "addData() called total pack" + "1");
        Log.e(TAG, "addData() called finalPackagePrice" + String.valueOf(finalPackagePrice));
        Log.e(TAG, "addData() called UPI");
        Log.e(TAG, "addData() called selectedOffers" + selectedOffers);
        Log.e(TAG, "addData() called selectedAddonce" + selectedAddonce);
        Log.e(TAG, "addData() called package_name" + package_name);
        Log.e(TAG, "addData() called serviceaddonsId" + serviceaddonsId);
        Log.e(TAG, "addData() called servieoffersId" + servieoffersId);
        Log.e(TAG, "addData() called productofferId" + productofferId);
        Log.e(TAG, "addData() called addons_vendor" + addons_vendor);
        Log.e(TAG, "addData() called offer_id_vendor" + offer_id_vendor);


        ApiInterface apiInterface = RetrofitClient.getclient(ConfirmBookingActivity.this);

        apiInterface.addBookingDates(
                package_id,
                session.getUserId(),
                venue_vendor_id,
                "1",
                String.valueOf(finalPackagePrice),
                start_date,
                end_date,
                String.valueOf(transectionId),
                "UPI",
                selectedOffers,
                selectedAddonce,
                package_name,
                serviceaddonsId,
                productaddonsId,
                servieoffersId,
                productofferId,
                addons_vendor,
                offer_id_vendor
        ).enqueue(new Callback<LogOutModel>() {
            @Override
            public void onResponse(@NonNull Call<LogOutModel> call, @NonNull Response<LogOutModel> response) {

                pd.dismiss();
                try {
                    if (response.code() == 200 && response.body() != null)
                        if (response.body().getResult().equalsIgnoreCase("true")) {
                            pd.dismiss();

                            addOnceModels.clear();
                            offersModel.clear();
                            selectedDate = "";
                            Toast.makeText(ConfirmBookingActivity.this, "Booking Successful..", Toast.LENGTH_SHORT).show();
                            session.setSelectedDate("");
                            startActivity(new Intent(ConfirmBookingActivity.this, SucessActivity.class)
                                    .putExtra("text", "Booking successfully  please wait for Admin Approval"));
                            finish();

                        } else {


                            pd.dismiss();
                            Toast.makeText(ConfirmBookingActivity.this, "Booking Failed..", Toast.LENGTH_SHORT).show();
                        }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(@NonNull Call<LogOutModel> call, @NonNull Throwable t) {
                pd.dismiss();
                Log.e("TAG", "onFailure() called with: call = [" + call + "], t = [" + t.getLocalizedMessage() + "]");
            }
        });

    }


    private void getPackageDetials(String packageId) {

        ApiInterface apiInterface = RetrofitClient.getclient(ConfirmBookingActivity.this);

        apiInterface.getPackagesDetails(packageId, session.getUserId()).enqueue(new Callback<PackageDetailsModel>() {
            @Override
            public void onResponse(@NonNull Call<PackageDetailsModel> call, @NonNull Response<PackageDetailsModel> response) {
                pd.dismiss();
                try {

                    if (response.body() != null) {
                        if (response.body().getResult().equalsIgnoreCase("true")) {

                            binding.packagePeople.setText(response.body().getData().getVenue().get(0).getGuestCapycityMix() + " People");
                            binding.bigAndLux.setText(response.body().getData().getPackages().getPackageName());
                            binding.day.setText(response.body().getData().getPackages().getPrice());

                            package_name = response.body().getData().getPackages().getPackageName();

                            String res = response.body().getData().getPackages().getCity().substring(0, 1).toUpperCase() + response.body().getData().getPackages().getCity().substring(1);
                            binding.packageAddress.setText(res);

                            try {

                                if (String.valueOf(response.body().getData().getPackages().getI_liked()).equalsIgnoreCase("1"))
                                    binding.likedImage.setImageResource(R.drawable.ic_baseline_favorite_24);

                                Picasso.get().load(IMAGE_URL + response.body().getData().getPackages().getImage()).placeholder(R.drawable.ic_nature_svg).into(binding.packageImage);


                                binding.packageCreatedDate.setText(formatDate(start_date));


                            } catch (Exception e) {
                                pd.dismiss();
                                e.getLocalizedMessage();
                                Log.e("TAG", "Start Date Error  = [" + e.getLocalizedMessage() + "]");
                            }

                        }
                    }

                } catch (Exception e) {
                    pd.dismiss();
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

    public class OfferInnerAdapter extends RecyclerView.Adapter<OfferInnerAdapter.ViewHolder> {

        Context context;
        List<BookingOffersModel> model;
        List<BookingAddOnceModel> model2;
        int TYPE = 0;

        public OfferInnerAdapter(Context context, List<BookingOffersModel> model, List<BookingAddOnceModel> model2, int TYPE) {
            this.context = context;
            this.model = model;
            this.model2 = model2;
            this.TYPE = TYPE;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.offer_addonce_layout, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

            try {

                if (TYPE == 0) {
                    holder.binding.offerName.setText(model.get(position).getOfferName());
                   // holder.binding.offerPrice.setText("Rs. " + model.get(position).getOfferPrice());
                    holder.binding.offerPrice.setText("Rs. " + NumberFormat.getIntegerInstance().format(Integer.parseInt(model.get(position).getOfferPrice())));

                    if (!model.get(position).getVendor_name().equalsIgnoreCase("")) {
                        holder.binding.offerVendorName.setText(model.get(position).getVendor_name() +"(Vendor)");
                        holder.binding.offerVendorName.setVisibility(View.VISIBLE);
                    }

                } else {
                    holder.binding.offerName.setText(model2.get(position).getAddOnceName());
                   // holder.binding.offerPrice.setText("Rs. " + model2.get(position).getAddOnceMaPrice());
                    holder.binding.offerPrice.setText("Rs. " + NumberFormat.getIntegerInstance().format(Integer.parseInt(model2.get(position).getAddOnceMaPrice())));

                    if (!model2.get(position).getVendor_name().equalsIgnoreCase("")) {
                        holder.binding.offerVendorName.setText(model2.get(position).getVendor_name() +"(Vendor)");
                        holder.binding.offerVendorName.setVisibility(View.VISIBLE);
                    }

                }

            } catch (Exception e) {
                e.getLocalizedMessage();
            }


        }

        @Override
        public int getItemCount() {
            if (TYPE == 0) return model.size();
            else return model2.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            OfferAddonceLayoutBinding binding;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                binding = OfferAddonceLayoutBinding.bind(itemView);
            }
        }

    }
}

