package com.Nuptist;

import static com.Nuptist.DetailsActivity.packageDetailsInterface;
import static com.Nuptist.DetailsActivity.package_set_date;
import static com.Nuptist.RetrofitApis.BaseUrls.IMAGE_URL;
import static com.Nuptist.adpaters.AddOnsAdapterNew2.addOnceModels;
import static com.Nuptist.adpaters.OfferAdapter2.offersModel;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.Nuptist.Models.BookedDatesModel;
import com.Nuptist.Models.BookedModel;
import com.Nuptist.Models.BookingModels.BookingAddOnceModel;
import com.Nuptist.Models.BookingModels.BookingOffersModel;
import com.Nuptist.Models.LogOutModel;
import com.Nuptist.Models.PackageDetailsModel;
import com.Nuptist.Models.ProgressDialog;
import com.Nuptist.Models.RecentPackagesModel;
import com.Nuptist.Models.ReviewModel;
import com.Nuptist.Models.VenueImagesModel;
import com.Nuptist.RetrofitApis.ApiInterface;
import com.Nuptist.RetrofitApis.BookPackageInterface;
import com.Nuptist.RetrofitApis.MapInterface;
import com.Nuptist.RetrofitApis.RetrofitClient;
import com.Nuptist.Session.Session;
import com.Nuptist.adpaters.RecentPackageAdapter;
import com.Nuptist.adpaters.ReviewAdapter;
import com.Nuptist.adpaters.VenueImagesAdapter;
import com.Nuptist.databinding.FragmentVenueBinding;

import com.applandeo.materialcalendarview.CalendarView;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class VenueFragment extends Fragment implements PaymentResultListener , BookPackageInterface {

    PackageDetailsModel data;
    FragmentVenueBinding binding;
    int finalAddOncePrice = 0;
    int finalOfferPrice = 0;
    Session session;
    ProgressDialog pd;
    String package_startDate = "";
    String package_endDate = "";
    int finalPackagePrice = 0;
    String selectedStartDate = "", selectedEndDate = "", packageId = "";
    private long daySelected = 0;
    String selectedAddonce = "", selectedOffers = "";
    private List<ReviewModel.ReviewData> filterdreviewModel = new ArrayList<>();

    MapInterface mapInterface;
   public  static  String selectedDate = "";
   public static  BookPackageInterface bookPackageInterface ;

    ArrayList<BookedModel> bookedModelArrayList = new ArrayList<>();
    private ArrayList<Calendar> disabledDatesList = new ArrayList<>();
    private List<ReviewModel.ReviewData> reviewData = new ArrayList<>();

    public VenueFragment(PackageDetailsModel data, MapInterface mapInterface) {
        this.data = data;
        this.mapInterface = mapInterface;
    }

    private ArrayList<Calendar> alreadyBookedDate = new ArrayList<>();

    List<BookingOffersModel> offersModelNew = new ArrayList<>();
    List<BookingAddOnceModel> addOnceModelsNew = new ArrayList<>();

    public  VenueFragment(){

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentVenueBinding.inflate(inflater, container, false);

        if (getContext() != null) {
            session = new Session(getContext());
        }

        offersModelNew.clear();
        addOnceModelsNew.clear();

        bookPackageInterface = this ;

        getVenueImages();
        getLocation();
        setReviewData();

        packageId = data.getData().getPackages().getId();

        try {

            if (!session.getUserId_Vendor().equalsIgnoreCase(""))
                if (data.getData().getVenue().get(0).getUserId().equalsIgnoreCase(session.getUserId_Vendor())) {
                    binding.bookingNow2.setVisibility(View.VISIBLE);
                    binding.bookingNow.setVisibility(View.GONE);
                }

//            double lat = Double.parseDouble(data.getData().getVenue().get(0).getLat());
//            double lang = Double.parseDouble(data.getData().getVenue().get(0).getLang());

          //  binding.venuePrice.setText("Rs. " + data.getData().getVenue().get(0).getVenue_price());
            binding.venuePrice.setText("Rs. " + NumberFormat.getIntegerInstance().format(Integer.parseInt(data.getData().getVenue().get(0).getVenue_price())));
            binding.veneuName.setText(data.getData().getVenue().get(0).getVenueName());


            Picasso.get().load(IMAGE_URL + data.getData().getPackages().getImage()).into(binding.packegImae2);

            binding.callBtn.setOnClickListener(v -> {

                Dexter.withContext(getContext())
                        .withPermission(Manifest.permission.CALL_PHONE)
                        .withListener(new PermissionListener() {
                            @Override
                            public void onPermissionGranted(PermissionGrantedResponse response) {
                                String posted_by = data.getData().getVenue().get(0).getPhone();
                                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + posted_by));
                                startActivity(intent);
                            }

                            @Override
                            public void onPermissionDenied(PermissionDeniedResponse response) {/* ... */}

                            @Override
                            public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {/* ... */}
                        }).check();

            });


        } catch (Exception e) {
            e.printStackTrace();
        }


        Log.e("TAG", "onCreateView() called with: Package Id [" + packageId + "]");
        binding.changeAndDate.setOnClickListener(view -> showdialogNew());

        binding.bookingNow.setOnClickListener(view -> {

            if (addOnceModels.size() == 0) {
                showDialog();
            } else if (offersModel.size() == 0) {
                showDialog();
            } else {
                try {
                    calculateTotalPackagePrice();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        package_startDate = data.getData().getPackages().getStartDate();
        package_endDate = data.getData().getPackages().getDate();

        getRecentPackages();

        getBookedDates2();

        binding.review5.setOnClickListener(v -> {
            binding.allreview.setTextColor(getContext().getColor(R.color.black));
            binding.review4Text.setTextColor(getContext().getColor(R.color.black));
            binding.review5Text.setTextColor(getContext().getColor(R.color.pinkiess));

            binding.review5.setBackgroundResource(R.drawable.category_photography_vactor);
            binding.review4.setBackgroundResource(R.color.white);
            binding.allreview.setBackgroundResource(R.color.white);

            filterdreviewModel.clear();
            if (reviewData.size() != 0) {

                for (int i = 0; i < reviewData.size(); i++) {
                    if (reviewData.get(i).getRating().equalsIgnoreCase("5"))
                        filterdreviewModel.add(reviewData.get(i));
                }

                binding.reviewRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
                binding.reviewRecycler.setAdapter(new ReviewAdapter(getContext(), filterdreviewModel));
            }

        });

        binding.review4.setOnClickListener(v -> {
            binding.allreview.setTextColor(getContext().getColor(R.color.black));
            binding.review4Text.setTextColor(getContext().getColor(R.color.pinkiess));
            binding.review5Text.setTextColor(getContext().getColor(R.color.black));

            binding.review4.setBackgroundResource(R.drawable.category_photography_vactor);
            binding.review5.setBackgroundResource(R.color.white);
            binding.allreview.setBackgroundResource(R.color.white);
            filterdreviewModel.clear();
            if (reviewData.size() != 0) {

                for (int i = 0; i < reviewData.size(); i++) {
                    if (reviewData.get(i).getRating().equalsIgnoreCase("4"))
                        filterdreviewModel.add(reviewData.get(i));
                }

                binding.reviewRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
                binding.reviewRecycler.setAdapter(new ReviewAdapter(getContext(), filterdreviewModel));
            }

        });

        binding.allreview.setOnClickListener(v -> {
            binding.allreview.setTextColor(getContext().getColor(R.color.pinkiess));
            binding.review4Text.setTextColor(getContext().getColor(R.color.black));
            binding.review5Text.setTextColor(getContext().getColor(R.color.black));

            binding.allreview.setBackgroundResource(R.drawable.category_photography_vactor);
            binding.review4.setBackgroundResource(R.color.white);
            binding.review5.setBackgroundResource(R.color.white);

            filterdreviewModel.clear();
            if (reviewData.size() != 0) {
                binding.reviewRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
                binding.reviewRecycler.setAdapter(new ReviewAdapter(getContext(), reviewData));
            }

        });

        return binding.getRoot();
    }

    private void bookPackageNow(String addOnceId, String offerId, String total_addons, String total_offers, String serviceaddonsId, String productaddonsId, String productofferId, String servieoffersId, String addons_vendor, String offer_id_vendor) {

        selectedStartDate = selectedDate ;
        selectedEndDate = selectedDate ;
        session.setSelectedDate("");

        if (session.getUserType().equalsIgnoreCase("Vendor")) {

            if (session.getIsPersonal()) {

                if (selectedDate.equalsIgnoreCase("")) {
                    Toast.makeText(getContext(), "Select  date before customizing...! ", Toast.LENGTH_SHORT).show();
                } else {
                    if (getContext() != null) {
                        pd = new ProgressDialog(getContext());
                        pd.show();
                    }


                    int p_price = 0;
//                    try {
//                        p_price = Integer.parseInt(data.getData().getPackages().getPrice());
//                    } catch (NumberFormatException e) {
//                        e.printStackTrace();
//                    }

                    p_price = (int) (p_price * daySelected);

                    int venuePrice = 0;
                    int pricePerPlet = 0 ;
                    int numberOfGuest =  0 ;
                    try {
                        venuePrice = 0;
                        if (!data.getData().getVenue().get(0).getVenue_price().equalsIgnoreCase("")) {
                            venuePrice = Integer.parseInt(data.getData().getVenue().get(0).getVenue_price());
                        }

                        if (!data.getData().getVenue().get(0).getPrice_per_plate().equalsIgnoreCase("")) {
                            pricePerPlet = Integer.parseInt(data.getData().getVenue().get(0).getPrice_per_plate());
                        }
                         if (!data.getData().getVenue().get(0).getGuestCapycityMix().equalsIgnoreCase("")) {
                            numberOfGuest = Integer.parseInt(data.getData().getVenue().get(0).getGuestCapycityMix());
                        }


                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                        Log.e("TAG", "bookPackageNow: Exception"+e.getLocalizedMessage() );
                    }

                    int p = 0;
                        p  =   pricePerPlet * numberOfGuest ;

                    finalPackagePrice = finalOfferPrice + finalAddOncePrice + p_price + venuePrice + p;

                    Log.e("TAG", "bookPackageNow() called with: addOnceId = [" + addOnceId + "], offerId = [" + offerId + "] Final Price " + finalPackagePrice);
                    Log.e("TAG", "bookPackageNow() called with: addonce service id  = [" + serviceaddonsId + "], addonce product id  = [" + productaddonsId + "]");
                    Log.e("TAG", "bookPackageNow() called with: Offer service id  = [" + servieoffersId + "], Offer product id  = [" + productofferId + "]");

                    Log.e("TAG", "bookPackageNow() price per Plate Data   = [" +data.getData().getVenue().get(0).getPrice_per_plate() + "]");
                    Log.e("TAG", "bookPackageNow() number of guest data  = [" +data.getData().getVenue().get(0).getGuestCapycityMix() + "]");
                    Log.e("TAG", "bookPackageNow() price per Number of guest   = [" + numberOfGuest + "]");
                    Log.e("TAG", "bookPackageNow() price per Plate  Total= [" + p + "]");


                    selectedAddonce = addOnceId;
                    selectedOffers = offerId;

//

                    Intent intent = new Intent(getContext(), ConfirmBookingActivity.class);
                    intent.putExtra("package_id", data.getData().getPackages().getId());
                    intent.putExtra("total_offers", total_offers);
                    intent.putExtra("total_addons", total_addons);
                    intent.putExtra("selectedAddonce", selectedAddonce);
                    intent.putExtra("selectedOffers", selectedOffers);
                    intent.putExtra("start_date", selectedStartDate);
                    intent.putExtra("end_date", selectedEndDate);
                    intent.putExtra("finalPackagePrice", String.valueOf(finalPackagePrice));
                    intent.putExtra("serviceaddonsId", serviceaddonsId);
                    intent.putExtra("productaddonsId", productaddonsId);
                    intent.putExtra("servieoffersId", servieoffersId);
                    intent.putExtra("productofferId", productofferId);
                    intent.putExtra("venue_price", data.getData().getVenue().get(0).getVenue_price());
                    intent.putExtra("offermodel", (Serializable) offersModelNew);
                    intent.putExtra("addonsmodel", (Serializable) addOnceModelsNew);
                    intent.putExtra("addons_vendor", addons_vendor);
                    intent.putExtra("offer_id_vendor", offer_id_vendor);
                    intent.putExtra("price_per_plet_total", String.valueOf(p));
                    intent.putExtra("price_per_plet", String.valueOf(pricePerPlet));
                    intent.putExtra("numofguest", String.valueOf(numberOfGuest));
                    intent.putExtra("venue_vendor_id", data.getData().getVenue().get(0).getUserId());
                    startActivity(intent);

                    finalPackagePrice = 0;
                    pd.dismiss();
//            Use this commented code when you want to add payment for booking

                    ///makePayment();

//            Intent intent = new Intent(getContext(),PaymentActivity.class);
//            intent.putExtra("package_id", data.getData().getPackages().getId());
//            intent.putExtra("total_offers", total_offers);
//            intent.putExtra("total_addons", total_addons);
//            intent.putExtra("selectedAddonce", selectedAddonce);
//            intent.putExtra("selectedOffers", selectedOffers);
//            intent.putExtra("start_date", selectedStartDate);
//            intent.putExtra("end_date", selectedEndDate);
//            intent.putExtra("finalPackagePrice", String.valueOf(finalPackagePrice));
//            intent.putExtra("serviceaddonsId",serviceaddonsId.toString());
//            intent.putExtra("productaddonsId",productaddonsId.toString());
//            intent.putExtra("servieoffersId",servieoffersId.toString());
//            intent.putExtra("productofferId",productofferId.toString());
//            startActivity(intent);

                    ///   addData();
                }

            } else {
                Toast.makeText(getContext(), "Only Users Can Booked Packages", Toast.LENGTH_SHORT).show();
            }

        } else {
            if (selectedDate.equalsIgnoreCase("") ) {
                Toast.makeText(getContext(), "Select  date before customizing...! ", Toast.LENGTH_SHORT).show();
            } else {
                if (getContext() != null) {
                    pd = new ProgressDialog(getContext());
                    pd.show();
                }


                int p_price = 0;
                try {
                  ////  p_price = Integer.parseInt(data.getData().getPackages().getPrice());
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }


                p_price = (int) (p_price * daySelected);

                int venuePrice = 0;
                int pricePerPlet = 0 ;
                int numberOfGuest =  0 ;
                try {
                    venuePrice = 0;
                    if (!data.getData().getVenue().get(0).getVenue_price().equalsIgnoreCase("")) {
                        venuePrice = Integer.parseInt(data.getData().getVenue().get(0).getVenue_price());
                    }

                    if (!data.getData().getVenue().get(0).getPrice_per_plate().equalsIgnoreCase("")) {
                        pricePerPlet = Integer.parseInt(data.getData().getVenue().get(0).getPrice_per_plate());
                    }
                    if (!data.getData().getVenue().get(0).getGuestCapycityMix().equalsIgnoreCase("")) {
                        numberOfGuest = Integer.parseInt(data.getData().getVenue().get(0).getGuestCapycityMix());
                    }


                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
                int p = 0;
                p  =   pricePerPlet * numberOfGuest ;

                finalPackagePrice = finalOfferPrice + finalAddOncePrice + p_price + venuePrice + p;

                Log.e("TAG", "bookPackageNow() called with: addOnceId = [" + addOnceId + "], offerId = [" + offerId + "] Final Price " + finalPackagePrice);
                Log.e("TAG", "bookPackageNow() called with: addonce service id  = [" + serviceaddonsId + "], addonce product id  = [" + productaddonsId + "]");
                Log.e("TAG", "bookPackageNow() called with: Offer service id  = [" + servieoffersId + "], Offer product id  = [" + productofferId + "]");

                Log.e("TAG", "bookPackageNow() price per plet Data   = [" +data.getData().getVenue().get(0).getPrice_per_plate() + "]");
                Log.e("TAG", "bookPackageNow() number of guest data  = [" +data.getData().getVenue().get(0).getGuestCapycityMix() + "]");
                Log.e("TAG", "bookPackageNow() price per Number of guest   = [" + numberOfGuest + "]");
                Log.e("TAG", "bookPackageNow() price per plet  Total= [" + p + "]");


                selectedAddonce = addOnceId;
                selectedOffers = offerId;


                Intent intent = new Intent(getContext(), ConfirmBookingActivity.class);
                intent.putExtra("package_id", data.getData().getPackages().getId());
                intent.putExtra("total_offers", total_offers);
                intent.putExtra("total_addons", total_addons);
                intent.putExtra("selectedAddonce", selectedAddonce);
                intent.putExtra("selectedOffers", selectedOffers);
                intent.putExtra("start_date", selectedStartDate);
                intent.putExtra("end_date", selectedEndDate);
                intent.putExtra("finalPackagePrice", String.valueOf(finalPackagePrice));
                intent.putExtra("serviceaddonsId", serviceaddonsId);
                intent.putExtra("productaddonsId", productaddonsId);
                intent.putExtra("servieoffersId", servieoffersId);
                intent.putExtra("productofferId", productofferId);
                intent.putExtra("venue_price", data.getData().getVenue().get(0).getVenue_price());
                intent.putExtra("offermodel", (Serializable) offersModelNew);
                intent.putExtra("addonsmodel", (Serializable) addOnceModelsNew);
                intent.putExtra("addons_vendor", addons_vendor);
                intent.putExtra("offer_id_vendor", offer_id_vendor);
                intent.putExtra("price_per_plet_total", String.valueOf(p));
                intent.putExtra("price_per_plet", String.valueOf(pricePerPlet));
                intent.putExtra("numofguest", String.valueOf(numberOfGuest));
                intent.putExtra("venue_vendor_id", data.getData().getVenue().get(0).getUserId());
                startActivity(intent);

                finalPackagePrice = 0;
                pd.dismiss();
//            Use this commented code when you want to add payment for booking

                ///makePayment();

//            Intent intent = new Intent(getContext(),PaymentActivity.class);
//            intent.putExtra("package_id", data.getData().getPackages().getId());
//            intent.putExtra("total_offers", total_offers);
//            intent.putExtra("total_addons", total_addons);
//            intent.putExtra("selectedAddonce", selectedAddonce);
//            intent.putExtra("selectedOffers", selectedOffers);
//            intent.putExtra("start_date", selectedStartDate);
//            intent.putExtra("end_date", selectedEndDate);
//            intent.putExtra("finalPackagePrice", String.valueOf(finalPackagePrice));
//            intent.putExtra("serviceaddonsId",serviceaddonsId.toString());
//            intent.putExtra("productaddonsId",productaddonsId.toString());
//            intent.putExtra("servieoffersId",servieoffersId.toString());
//            intent.putExtra("productofferId",productofferId.toString());
//            startActivity(intent);

                ///   addData();
            }
        }

    }

    private void makePayment() {

        int amount = Math.round(Float.parseFloat(String.valueOf(finalPackagePrice)) * 100);

        // initialize Razorpay account.
        Checkout checkout = new Checkout();

        // set your id as below
        checkout.setKeyID("rzp_test_W7bnxjP4xqCYzS");

        // set image
        checkout.setImage(R.drawable.nuptistnew_old);

        // initialize json object
        JSONObject object = new JSONObject();
        try {
            // to put name

            object.put("name", "Nuptist");

            // put description
            object.put("description", "Package Booking");

            // to set theme color
//            object.put("theme.color", "");

            // put the currency
            object.put("currency", "INR");

            // put amount
            object.put("amount", amount);

            // put mobile number
            object.put("prefill.contact", "000000000");

            // put email
            object.put("prefill.email", "nuptist@gmail.com");


            // open razorpay to checkout activity
            checkout.open(getActivity(), object);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void addData() {

        Random random = new Random();
        int transectionId = random.nextInt(1000000);

        ApiInterface apiInterface = RetrofitClient.getclient(getContext());

        apiInterface.addBookingDates(
                packageId,
                session.getUserId(),
                "1",
                String.valueOf(finalPackagePrice),
                selectedStartDate,
                selectedEndDate,
                String.valueOf(transectionId),
                "UPI",
                selectedOffers,
                selectedAddonce,
                data.getData().getPackages().getPackageName(),
                "0,0",
                "0,0",
                "0,0",
                "0,0",
                "0,0",
                "0,0",
                ""
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
                            Toast.makeText(getContext(), "Booking Successful..", Toast.LENGTH_SHORT).show();
                            if (getActivity() != null) {
                                getActivity().finish();
                            }


                        } else {
                            pd.dismiss();
                            Toast.makeText(getContext(), "Booking Failed..", Toast.LENGTH_SHORT).show();
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

    private void calculateTotalPackagePrice() {
        finalPackagePrice = 0;
        finalOfferPrice = 0;
        finalAddOncePrice = 0;
        StringBuilder addOnceId = new StringBuilder();
        StringBuilder offerId = new StringBuilder();
        StringBuilder serviceaddonsId = new StringBuilder();
        StringBuilder productaddonsId = new StringBuilder();
        StringBuilder productofferId = new StringBuilder();
        StringBuilder servieoffersId = new StringBuilder();

        String prod_id_new_addonce = "";
        String service_id_new_addonce = "";
        String addons_vendor = "";

        String prod_id_new_offer = "";
        String service_id_new_offer = "";
        String offer_id_vendor = "";

        ArrayList<String> list3 = new ArrayList<>();
        ArrayList<String> list4 = new ArrayList<>();
        ArrayList<String> add_vendor_ser = new ArrayList<>();
        ArrayList<String> offer_vendor_ser = new ArrayList<>();
        String total_offers = "0";
        String total_addons = "0";

        //// Calculate AddOnce Price
        if (addOnceModels.size() != 0) {
            addOnceModelsNew = addOnceModels;
            total_addons = String.valueOf(addOnceModels.size());
            for (int i = 0; i < addOnceModels.size(); i++) {

                int p = Integer.parseInt(addOnceModels.get(i).getAddOnceMaPrice());

                finalAddOncePrice = finalAddOncePrice + p;
                Log.e("TAG---------->", "onCreateView() called with: Add once Price  = [" + p + "]");
                Log.e("TAG---------->", " Add once Service Id   = [" + addOnceModels.get(i).getService_id() + "]");
                Log.e("TAG---------->", " Add once Product Id  = [" + addOnceModels.get(i).getProduct_id() + "]");

                if (!addOnceModels.get(i).getProduct_id().equalsIgnoreCase("0"))
                    list3.add(addOnceModels.get(i).getProduct_id());

                if (!addOnceModels.get(i).getService_id().equalsIgnoreCase("0"))
                    list4.add(addOnceModels.get(i).getService_id());

                if (!addOnceModels.get(i).getVendor_id().equalsIgnoreCase("0"))
                    add_vendor_ser.add(addOnceModels.get(i).getVendor_id());


                //////// Get AddOnce Id
//                addOnceId.append(",").append(addOnceModels.get(i).getAddOnceId());
//                addOnceId = new StringBuilder(addOnceId.substring(1));

                /////// get add ons service and product id

//                prod_id_new_addonce =  prod_id_new_addonce + addOnceModels.get(i).getProduct_id()+",";
//                service_id_new_addonce = service_id_new_addonce + addOnceModels.get(i).getService_id()+",";

//                productaddonsId.append(",").append(addOnceModels.get(i).getProduct_id());
//                productaddonsId = new StringBuilder(productaddonsId.substring(1));
//
//                serviceaddonsId.append(",").append(addOnceModels.get(i).getService_id());
//                serviceaddonsId = new StringBuilder(serviceaddonsId.substring(1));

            }

            prod_id_new_addonce = list3.toString().replaceAll("\\[", "").replace("]", "");
            service_id_new_addonce = list4.toString().replaceAll("\\[", "").replace("]", "");
            addons_vendor = add_vendor_ser.toString().replaceAll("\\[", "").replace("]", "");


            Log.e("TAG", "calculateTotalPackagePrice() called Service id new ok " + service_id_new_addonce);
            Log.e("TAG", "calculateTotalPackagePrice() called Product id new ok " + prod_id_new_addonce);
            Log.e("TAG", "calculateTotalPackagePrice() called Add Vendor Id new ok " + addons_vendor);


        }


        ///// Calculate Offers Price

        if (offersModel.size() != 0) {
            offersModelNew = offersModel;
            total_offers = String.valueOf(offersModel.size());
            ArrayList<String> list1 = new ArrayList<>();
            ArrayList<String> list2 = new ArrayList<>();
            for (int i = 0; i < offersModel.size(); i++) {
                int p = Integer.parseInt(offersModel.get(i).getOfferPrice());
                finalOfferPrice = finalOfferPrice + p;

                Log.e("TAG---------->", " Offer  Service Id   = [" + offersModel.get(i).getService_id() + "]");
                Log.e("TAG---------->", " Offer Product Id  = [" + offersModel.get(i).getProduct_id() + "]");


//                prod_id_new_offer =  prod_id_new_offer + offersModel.get(i).getProduct_id()+",";
//                service_id_new_offer = service_id_new_offer + offersModel.get(i).getService_id()+",";


                if (!offersModel.get(i).getProduct_id().equalsIgnoreCase("0"))
                    list1.add(offersModel.get(i).getProduct_id());

                if (!offersModel.get(i).getService_id().equalsIgnoreCase("0"))
                    list2.add(offersModel.get(i).getService_id());

                if (!offersModel.get(i).getVendor_id().equalsIgnoreCase("0"))
                    offer_vendor_ser.add(offersModel.get(i).getVendor_id());

                //                offerId.append(",").append(offersModel.get(i).getOfferId());
//                offerId = new StringBuilder(offerId.substring(1));
//
//                productofferId.append(",").append(offersModel.get(i).getProduct_id());
//                productofferId = new StringBuilder(productofferId.substring(1));
//
//                 servieoffersId.append(",").append(offersModel.get(i).getService_id());
//                 servieoffersId = new StringBuilder(servieoffersId.substring(1));

            }

            prod_id_new_offer = list1.toString().replaceAll("\\[", "").replace("]", "");
            service_id_new_offer = list2.toString().replaceAll("\\[", "").replace("]", "");
            offer_id_vendor = offer_vendor_ser.toString().replaceAll("\\[", "").replace("]", "");


        }


        Log.e("TAG", "onClick() called with: Add One   Final Price = [" + finalAddOncePrice + "]");
        Log.e("TAG", "onClick() called with: Offer    Final Price = [" + finalOfferPrice + "]");
        Log.e("TAG", "onClick() called with: prod_id_new_offer s  = [" + prod_id_new_offer + "]");
        Log.e("TAG", "onClick() called with: service_id_new_offer   = [" + service_id_new_offer + "]");
        Log.e("TAG", "onClick() called with: offer_id_vendor   = [" + offer_id_vendor + "]");

        bookPackageNow(addOnceId.toString(), offerId.toString(), total_addons, total_offers, service_id_new_addonce, prod_id_new_addonce, prod_id_new_offer, service_id_new_offer, addons_vendor, offer_id_vendor);


    }

    private void getLocation() {

        String lat = data.getData().getVenue().get(0).getLat();
        String lang = data.getData().getVenue().get(0).getLang();


        Log.e("TAG", "getLocation() called Lat " + lat);
        Log.e("TAG", "getLocation() called Lang " + lang);

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

    private void showDialog() {


        final Dialog dialog = new Dialog(getContext());

        dialog.setContentView(R.layout.confirm_booking_layout);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(false);
        dialog.getWindow().getAttributes().windowAnimations = R.style.animation;

        LinearLayout close = dialog.findViewById(R.id.dissmiss2);
        LinearLayout vendor_profile = dialog.findViewById(R.id.skip);
        TextView viewPrivacyPolicy = dialog.findViewById(R.id.continue_anyway);

        close.setOnClickListener(view -> dialog.dismiss());

        viewPrivacyPolicy.setOnClickListener(v -> {
            try {
                calculateTotalPackagePrice();
            } catch (Exception e) {
                e.printStackTrace();
            }
            dialog.dismiss();

        });

        vendor_profile.setOnClickListener(view -> dialog.dismiss());

        dialog.show();


    }

    ////// This  code is very important for set disabled dates in calender
    public Calendar formatCalender(String s) {

        //  s = "2022-10-21 08:08:18";

        Calendar calendar = Calendar.getInstance();
        String[] dateTime = s.split(" ");

        String dateS = dateTime[0];


        String[] datess = dateS.split("-");

        String year = datess[0];
        String month = datess[1];
        String day = datess[2];

        calendar.set(Integer.parseInt(year), Integer.parseInt(month) - 1, Integer.parseInt(day));

        //// return   day + " " + getMonthName(Integer.parseInt(month))  + " " +  year + " "+timeS;
        calendar.add(Calendar.DATE, 1);

        return calendar;
    }
    /////////////////////////////

    ////// Please Uncomment this code when you want multiple date selection in our calender
    private void showdialog() {

        final Dialog dialog = new Dialog(getContext());

        // setting content view to dialog
        dialog.setContentView(R.layout.bookpackage_calendar_layout);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(true);
        dialog.getWindow().getAttributes().windowAnimations = R.style.animation;
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


        TextView confirm_date = dialog.findViewById(R.id.confirm_date);

        ImageView close_calender = dialog.findViewById(R.id.close_calender);


        close_calender.setOnClickListener(v -> dialog.dismiss());

//        com.Nuptist.CustomCalendar.CalendarView calendarView = dialog.findViewById(R.id.calendar);
        CalendarView calendarView = dialog.findViewById(R.id.calendar);

        //Calendar min = formatCalender(package_startDate);
        DateFormat formatter = new SimpleDateFormat("dd-MM-yy");
        Calendar min = Calendar.getInstance();

        Log.e("TAG", "showdialog() called current date is " + min.getTime());


        Calendar max = formatCalender(package_endDate);

        calendarView.setMinimumDate(min);
        calendarView.setMaximumDate(max);
        disabledDatesList.clear();

        for (int i = 0; i < bookedModelArrayList.size(); i++) {
            Calendar calendar = formatCalender(bookedModelArrayList.get(i).getDate());
            disabledDatesList.add(calendar);
            Log.e("TAG", "Calender  = [" + calendar.getTime().getTime() + "]");
        }
        calendarView.setDisabledDays(disabledDatesList);

        confirm_date.setOnClickListener(view -> {
                    List<Calendar> dates = calendarView.getSelectedDates();
                    boolean flag = true;

                    if (dates.size() == 0) {
                        Toast.makeText(getContext(), "Select at least 1 date", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (dates.size() > 1) {
                        for (int i = 0; i < dates.size() - 1; i++) {
                            Calendar current = dates.get(i);
                            Calendar forward = dates.get(i + 1);
                            current.add(Calendar.DATE, 1);

                            boolean sameDay = forward.get(Calendar.DAY_OF_YEAR) == current.get(Calendar.DAY_OF_YEAR) &&
                                    forward.get(Calendar.YEAR) == current.get(Calendar.YEAR);

                            if (!sameDay) {
                                flag = false;
                                break;
                            }

                            current.add(Calendar.DATE, -1);
                        }

                        if (flag) {
                            Calendar first = dates.get(0);
                            Calendar last = dates.get(dates.size() - 1);


                            if (first != null && last != null) {
                                selectedStartDate = first.get(Calendar.YEAR) + "-" + (first.get(Calendar.MONTH) + 1) + "-" + first.get(Calendar.DAY_OF_MONTH);
                                selectedEndDate = last.get(Calendar.YEAR) + "-" + (last.get(Calendar.MONTH) + 1) + "-" + last.get(Calendar.DAY_OF_MONTH);

                                long time = last.getTime().getTime() - first.getTime().getTime();

                                daySelected = time / 86400000;
                                daySelected = daySelected + 1;

                                Log.e("TAG", "showdialog() called start Date " + selectedStartDate);
                                Log.e("TAG", "showdialog() called end Date " + selectedEndDate);
                                Log.e("TAG", "showdialog() called total  Date " + daySelected);
                                dialog.dismiss();
                            } else
                                Toast.makeText(getContext(), "Select start and end date!", Toast.LENGTH_SHORT).show();
                        } else
                            Toast.makeText(getContext(), "Select Consistent Dates!", Toast.LENGTH_SHORT).show();

                    } else {
                        Calendar first = dates.get(0);
                        Calendar last = dates.get(0);

                        if (first != null && last != null) {
                            selectedStartDate = first.get(Calendar.YEAR) + "-" + (first.get(Calendar.MONTH) + 1) + "-" + first.get(Calendar.DAY_OF_MONTH);
                            selectedEndDate = last.get(Calendar.YEAR) + "-" + (last.get(Calendar.MONTH) + 1) + "-" + last.get(Calendar.DAY_OF_MONTH);

                            try {
                                long time = last.getTime().getTime() - first.getTime().getTime();

                                daySelected = time / 86400000;
                                daySelected = daySelected + 1;
                                Log.e("TAG", "showdialog() called total  Date " + daySelected);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                            Log.e("TAG", "showdialog() called start Date " + selectedStartDate);
                            Log.e("TAG", "showdialog() called end Date " + selectedEndDate);
                            dialog.dismiss();
                        } else
                            Toast.makeText(getContext(), "Select date!", Toast.LENGTH_SHORT).show();
                    }

                }
        );
        dialog.show();

    }
    ////////////////////////

    public void showdialogNew() {

        final Dialog dialog = new Dialog(getContext());

        // setting content view to dialog
        dialog.setContentView(R.layout.bookpackage_calendar_layout);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(true);
        dialog.getWindow().getAttributes().windowAnimations = R.style.animation;
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


        TextView confirm_date = dialog.findViewById(R.id.confirm_date);
        ImageView close_calender = dialog.findViewById(R.id.close_calender);


        close_calender.setOnClickListener(v -> dialog.dismiss());

//        com.Nuptist.CustomCalendar.CalendarView calendarView = dialog.findViewById(R.id.calendar);
        CalendarView calendarView = dialog.findViewById(R.id.calendar);

        DateFormat formatter = new SimpleDateFormat("dd-MM-yy");
        Calendar min = Calendar.getInstance();

        Log.e("TAG", "showdialog() called current date is " + min.getTime());


        Calendar max = formatCalender(package_endDate);
//
        calendarView.setMinimumDate(min);

        Log.e("TAG", "showdialogNew() called end date is " + max.getTime());

        calendarView.setMaximumDate(max);


        /////// Uncomment this code  when you have multiple dates one to multiple

//        disabledDatesList.clear();
//
//        for (int i = 0; i < bookedModelArrayList.size(); i++) {
//            Calendar calendar = formatCalender(bookedModelArrayList.get(i).getDate());
//            disabledDatesList.add(calendar);
//            Log.e("TAG", "Calender  = [" + calendar.getTime().getTime() + "]");
//        }


        calendarView.setDisabledDays(alreadyBookedDate);

        confirm_date.setOnClickListener(view -> {
                    List<Calendar> dates = calendarView.getSelectedDates();
                    boolean flag = true;

                    if (dates.size() == 0) {
                        Toast.makeText(getContext(), "Select at least 1 date", Toast.LENGTH_SHORT).show();

                    } else if (dates.size() <= 1) {
                        Calendar first = dates.get(0);
                        selectedStartDate = first.get(Calendar.YEAR) + "-" + (first.get(Calendar.MONTH) + 1) + "-" + first.get(Calendar.DAY_OF_MONTH);
                        selectedEndDate = first.get(Calendar.YEAR) + "-" + (first.get(Calendar.MONTH) + 1) + "-" + first.get(Calendar.DAY_OF_MONTH);

                        daySelected = 1;
                        Log.e("TAG", "showdialogNew() called start date " + selectedStartDate);
                        Log.e("TAG", "showdialogNew() called end date " + selectedEndDate);
                        Log.e("TAG", "showdialogNew() called day count  " + daySelected);

                        binding.ourPlaceI1.setText("our place is match for your date (" + selectedEndDate + ") ");
                        selectedDate = selectedEndDate ;
                        packageDetailsInterface.onDateChange();
                        try {
                            package_set_date.setText("Change date");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        dialog.dismiss();
                    } else {
                        Toast.makeText(getContext(), "Maximum one date can be selected...", Toast.LENGTH_SHORT).show();
                    }


                }
        );

        dialog.show();

    }

    @Override
    public void onPaymentSuccess(String s) {
        addData();
    }

    @Override
    public void onPaymentError(int i, String s) {

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
                            reviewData.addAll(response.body().getData());
                        }

            }

            @Override
            public void onFailure(@NonNull Call<ReviewModel> call, @NonNull Throwable t) {

            }
        });

    }

    /////////////  Uncomment this code  when you have multiple dates one to multiple
    private void getBookedDates() {

        ApiInterface apiInterface = RetrofitClient.getclient(getContext());

        apiInterface.getPackageBookedDates(data.getData().getPackages().getId()).enqueue(new Callback<BookedDatesModel>() {
            @Override
            public void onResponse(@NonNull Call<BookedDatesModel> call, @NonNull Response<BookedDatesModel> response) {

                try {
                    if (response.code() == 200) {
                        if (response.body() != null) {
                            if (response.body().getResult().equalsIgnoreCase("true")) {
                                bookedModelArrayList.clear();
                                List<BookedDatesModel.BookedDateData> list = response.body().getData();

                                for (int i = 0; i < list.size(); i++) {
                                    String startDate = list.get(i).getStartDate();
                                    String endDate = list.get(i).getEndDate();

                                    Calendar startDateCalendar = formatCalender(startDate);
                                    Calendar endDateCalendar = formatCalender(endDate);
                                    long daysDifference = TimeUnit.MILLISECONDS.toDays(Math.abs(startDateCalendar.getTimeInMillis() - endDateCalendar.getTimeInMillis()));

                                    for (int j = 0; j < daysDifference; j++) {
                                        if (j != 0)
                                            startDateCalendar.add(Calendar.DATE, 1);

                                        String currentDate = startDateCalendar.get(Calendar.YEAR) + "-" + (startDateCalendar.get(Calendar.MONTH) + 1) + "-" + startDateCalendar.get(Calendar.DAY_OF_MONTH);

                                        boolean flag = false;

                                        for (int k = 0; k < bookedModelArrayList.size(); k++) {
                                            if (bookedModelArrayList.get(k).getDate().equalsIgnoreCase(currentDate)) {
                                                flag = true;
                                                break;
                                            }
                                        }

                                        if (!flag) {
                                            BookedModel model = new BookedModel();
                                            Log.e("TAG", "Calender  = [" + startDateCalendar.getTime().getTime() + "]");

                                            model.setDate(currentDate);
                                            model.setCalendar(startDateCalendar);
                                            bookedModelArrayList.add(model);

                                        }
                                    }
                                }
                            } else {
                                Log.e("TAG", "onResponse() called with: call = [" + call + "], response msg = [" + response.body().getMsg() + "]");
                            }
                        }
                    }
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onFailure(@NonNull Call<BookedDatesModel> call, @NonNull Throwable t) {
                Log.e("TAG", "onFailure() called with: call = [" + call + "], t = [" + t.getLocalizedMessage() + "]");
            }
        });


    }

    private void getBookedDates2() {

        ApiInterface apiInterface = RetrofitClient.getclient(getContext());

        apiInterface.getPackageBookedDates(data.getData().getPackages().getId()).enqueue(new Callback<BookedDatesModel>() {
            @Override
            public void onResponse(@NonNull Call<BookedDatesModel> call, @NonNull Response<BookedDatesModel> response) {

                try {
                    if (response.code() == 200) {
                        if (response.body() != null) {
                            if (response.body().getResult().equalsIgnoreCase("true")) {
                                bookedModelArrayList.clear();
                                List<BookedDatesModel.BookedDateData> list = response.body().getData();

                                alreadyBookedDate.clear();
                                for (int i = 0; i < list.size(); i++) {
                                    String startDate = list.get(i).getStartDate();

                                    Calendar startDateCalendar = formatCalender(startDate);
                                    startDateCalendar.add(Calendar.DATE, -1);
                                    alreadyBookedDate.add(startDateCalendar);
                                }
                            } else {
                                Log.e("TAG", "onResponse() called with: call = [" + call + "], response msg = [" + response.body().getMsg() + "]");
                            }
                        }
                    }
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onFailure(@NonNull Call<BookedDatesModel> call, @NonNull Throwable t) {
                Log.e("TAG", "onFailure() called with: call = [" + call + "], t = [" + t.getLocalizedMessage() + "]");
            }
        });


    }

    @Override
    public void onResume() {
        super.onResume();
        finalPackagePrice = 0;
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

                                    List<RecentPackagesModel.RecentPackageData> data2 = new ArrayList<>();
                                    for (int i = 0; i < response.body().getData().size(); i++) {

                                        if (response.body().getData().get(i).getUsersStatus() != null)
                                            if (response.body().getData().get(i).getUsersStatus().equalsIgnoreCase("1")) {
                                                data2.add(response.body().getData().get(i));
                                                Log.e("TAG", "onResponse() called with: call = [" + call + "], data = [" + response.body().getData().get(i).toString() + "]");
                                            }

                                    }

                                    LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(getContext());
                                    linearLayoutManager2.setReverseLayout(true);
                                    binding.recentRecylerView.setLayoutManager(linearLayoutManager2);
                                    binding.recentRecylerView.setAdapter(new RecentPackageAdapter(getContext(), data2));
                                } else {
                                    binding.linearRecent.setVisibility(View.GONE);
                                }

                            } else {
                                binding.linearRecent.setVisibility(View.GONE);
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

    @Override
    public void onDateChange() {
        showdialogNew();
    }

    @Override
    public void onBooking() {
        calculateTotalPackagePrice();
    }
}