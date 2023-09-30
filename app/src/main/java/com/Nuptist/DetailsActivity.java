package com.Nuptist;

import static com.Nuptist.RetrofitApis.BaseUrls.IMAGE_URL;
import static com.Nuptist.VenueFragment.bookPackageInterface;
import static com.Nuptist.VenueFragment.selectedDate;
import static com.Nuptist.adpaters.AddOnsAdapterNew2.addOnceModels;
import static com.Nuptist.adpaters.OfferAdapter2.offersModel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.Nuptist.Models.LogOutModel;
import com.Nuptist.Models.PackageDetailsModel;
import com.Nuptist.Models.PackageViewModel;
import com.Nuptist.Models.ProgressDialog;
import com.Nuptist.Models.RecentPackagesModel;
import com.Nuptist.Models.ReviewModel;
import com.Nuptist.RetrofitApis.ApiInterface;
import com.Nuptist.RetrofitApis.MapInterface;
import com.Nuptist.RetrofitApis.PackageDetailsInterface;
import com.Nuptist.RetrofitApis.RetrofitClient;
import com.Nuptist.Session.Session;
import com.Nuptist.adpaters.PackageListAdapter;
import com.Nuptist.adpaters.ReviewAdapter;
import com.Nuptist.mainscreens.HomeUserFragment;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailsActivity extends AppCompatActivity implements MapInterface , PackageDetailsInterface {

    private final int VENUE_FRAG = 0;
    private final int OFFER_FRAG = 1;
    private final int ADD_ONS_FRAG = 2;
    private ImageView ic_back;

    private  TabLayout tab_lay;
    private TextView venue, offer, addonce;

    private  String package_id;
    public static TextView  package_set_date ;
    private  TextView package_name , booking_now, booking_now2, set_date , set_offers ,set_addons , package_price, package_address, package_created_date, package_people , some_id;
    private  ImageView package_image , date_img , date_checked  ,addons_checked ,offer_checked ,  offer_img  , add_ons_img, liked_image;
    private final String[] titles = new String[]{"Venue", "Offers", "Add Ons "};
    private ViewPager2 product_viewpager;
    private PackageDetailsModel.PacjkageDetailsData alldata;
    private PackageDetailsModel detailsData;
    private   Session session;

    boolean is_liked = false;
    public  static  PackageDetailsInterface packageDetailsInterface  ;

    private  View view_1, view_2, view_3;
    private String package_image_string = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        Window window = getWindow();
        window.setStatusBarColor(getResources().getColor(R.color.white));

        packageDetailsInterface = this ;

        session = new Session(DetailsActivity.this);
        ic_back = findViewById(R.id.ic_back);
        product_viewpager = findViewById(R.id.product_viewpager);
        liked_image = findViewById(R.id.liked_image);
        package_name = findViewById(R.id.big_and_lux);
        package_price = findViewById(R.id.day);
        package_image = findViewById(R.id.package_image);
        package_address = findViewById(R.id.package_address);
        package_created_date = findViewById(R.id.package_created_date);
        package_people = findViewById(R.id.package_people);
        venue = findViewById(R.id.venue);
        offer = findViewById(R.id.offer);
        addonce = findViewById(R.id.addonce);
        view_1 = findViewById(R.id.view_1);
        view_2 = findViewById(R.id.view_2);
        view_3 = findViewById(R.id.view_3);
        some_id = findViewById(R.id.some_id);
        set_offers = findViewById(R.id.set_offers);
        set_addons = findViewById(R.id.set_addons);
        add_ons_img = findViewById(R.id.add_ons_img);
        addons_checked = findViewById(R.id.addons_checked);
        offer_img = findViewById(R.id.offer_img);
        offer_checked = findViewById(R.id.offer_checked);
        package_set_date = findViewById(R.id.package_set_date);
        date_checked = findViewById(R.id.date_checked);
        set_date = findViewById(R.id.set_date);
        date_img = findViewById(R.id.date_img);
        booking_now = findViewById(R.id.booking_now);
        booking_now2 = findViewById(R.id.booking_now2);

        try {
            package_id = getIntent().getStringExtra("p_id");
            package_image_string = getIntent().getStringExtra("image");

            getPackageDetials();
            addPackageView();
            Picasso.get().load(package_image_string).into(package_image);
            adddPackageView();

        } catch (Exception e) {
            e.getLocalizedMessage();
        }

        liked_image.setOnClickListener(view -> {

            if (is_liked) {
                liked_image.setImageResource(R.drawable.ic_baseline_favorite_24);
                is_liked = true;
            } else {
                liked_image.setImageResource(R.drawable.ic_like);
                is_liked = false;
            }

            likePackage();

        });

        package_set_date.setOnClickListener(view -> bookPackageInterface.onDateChange());

        getReviewData();
        ic_back.setOnClickListener(view -> onBackPressed());


        booking_now2.setOnClickListener(view -> bookPackageInterface.onBooking());
    }

    private void adddPackageView() {

        ApiInterface apiInterface = RetrofitClient.getclient(DetailsActivity.this);

        apiInterface.addView(
                "",
                package_id,
                session.getUserId()
        ).enqueue(new Callback<LogOutModel>() {
            @Override
            public void onResponse(@NonNull Call<LogOutModel> call, @NonNull Response<LogOutModel> response) {

                try {
                    if (response.code() == 200) {
                        if (response.body() != null && response.body().getResult().equalsIgnoreCase("true"))
                            Log.e("TAG", "onResponse() called with: call = [" + call + "], response = [" + response.body().getMsg() + "]");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(@NonNull Call<LogOutModel> call, @NonNull Throwable t) {
                Log.e("TAG", "onFailure() called with: call = [" + call + "], t = [" + t + "]");
            }
        });

    }

    private void likePackage() {


        ApiInterface apiInterface = RetrofitClient.getclient(DetailsActivity.this);
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
                            liked_image.setImageResource(R.drawable.ic_baseline_favorite_24);
                            Log.e("TAG", "Like Package  =  response = [" + response.body().getMsg() + "]");
                        } else {
                            is_liked = false;
                            liked_image.setImageResource(R.drawable.ic_like);
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

    private void addPackageView() {

        ApiInterface apiInterface = RetrofitClient.getclient(DetailsActivity.this);

        String userid;



        apiInterface.packageView(
                session.getUserId(),
                package_id,
                ""
        ).enqueue(new Callback<PackageViewModel>() {
            @Override
            public void onResponse(@NonNull Call<PackageViewModel> call, @NonNull Response<PackageViewModel> response) {

                try {
                    if (response.body() != null) {
                        if (response.body().getResult().equalsIgnoreCase("true")) {
                            Log.e("TAG", "Package View Added  = response = [" + response.body().getMsg() + "]");

                        } else {
                            Log.e("TAG", "Package View Not Added  = response = [" + response.body().getMsg() + "]");
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(@NonNull Call<PackageViewModel> call, @NonNull Throwable t) {
                Log.e("TAG", "onFailure() called with: call = [" + call + "], t = [" + t + "]");
            }
        });

    }

    private void getPackageDetials() {

        ProgressDialog pd = new ProgressDialog(DetailsActivity.this);
        pd.show();

        ApiInterface apiInterface = RetrofitClient.getclient(DetailsActivity.this);

        apiInterface.getPackagesDetails(package_id, session.getUserId()).enqueue(new Callback<PackageDetailsModel>() {
            @Override
            public void onResponse(@NonNull Call<PackageDetailsModel> call, @NonNull Response<PackageDetailsModel> response) {

                pd.dismiss();
                try {

                    if (response.body() != null) {
                        if (response.body().getResult().equalsIgnoreCase("true")) {
                            detailsData = response.body();
                            alldata = response.body().getData();

                            PackageDetailsModel.PacjkageDetailsData.Packages data = alldata.getPackages();

                            String res = data.getCity().substring(0, 1).toUpperCase() + data.getCity().substring(1);

                            package_name.setText(data.getPackageName());
                            package_address.setText(res);

                         //   package_created_date.setText(formatDate(data.getStartDate()));

                            try {
                                Picasso.get().load(IMAGE_URL+response.body().getData().getPackages().getImage()).into(package_image);
                             ///   package_created_date.setText(formatDate(data.getStartDate()));
                                Log.e("TAG", "Start Date  = [" + data.getStartDate() + "]");
                            }catch (Exception e){
                                e.getLocalizedMessage();
                                Log.e("TAG", "Start Date Error  = [" + e.getLocalizedMessage() + "]");
                            }

                            package_people.setText(alldata.getVenue().get(0).getGuestCapycityMix()+" People");

                            if(data.getPrice().equalsIgnoreCase("")){
                                package_price.setText("Rs. 0 /Day");
                            }else {
                            package_price.setText("Rs. "+data.getPrice());
                            }

                            // package_created_date.setText(formatDate(data.getStartDate()));

//                            product_viewpager.setAdapter(new DetailsActivityAdapter(DetailsActivity.this, detailsData, package_id));
//                            tab_lay = findViewById(R.id.tab_lay);

                            if(String.valueOf(data.getI_liked()).equalsIgnoreCase("1"))
                                 liked_image.setImageResource(R.drawable.ic_baseline_favorite_24);

                            replaceFrag(VENUE_FRAG);

                            offer.setOnClickListener(view -> {

                                replaceFrag(OFFER_FRAG);
                                view_1.setVisibility(View.GONE);
                                view_2.setVisibility(View.VISIBLE);
                                view_3.setVisibility(View.GONE);
                                venue.setTextColor(getResources().getColor(R.color.black));
                                offer.setTextColor(getResources().getColor(R.color.colorPrimary));
                                addonce.setTextColor(getResources().getColor(R.color.black));
                            });


                            venue.setOnClickListener(view -> {

                                replaceFrag(VENUE_FRAG);
                                view_1.setVisibility(View.VISIBLE);
                                view_2.setVisibility(View.GONE);
                                view_3.setVisibility(View.GONE);
                                venue.setTextColor(getResources().getColor(R.color.colorPrimary));
                                offer.setTextColor(getResources().getColor(R.color.black));
                                addonce.setTextColor(getResources().getColor(R.color.black));
                            });


                            addonce.setOnClickListener(view -> {
                                replaceFrag(ADD_ONS_FRAG);
                                view_1.setVisibility(View.GONE);
                                view_2.setVisibility(View.GONE);
                                view_3.setVisibility(View.VISIBLE);
                                venue.setTextColor(getResources().getColor(R.color.black));
                                offer.setTextColor(getResources().getColor(R.color.black));
                                addonce.setTextColor(getResources().getColor(R.color.colorPrimary));
                            });


//                            new TabLayoutMediator(tab_lay, product_viewpager, (tab, position) -> {
//                                tab.setText(titles[position]);
//                                //  tab.setIcon(mTabIconsSelected[position]);
//                            }).attach();

                            Log.e("Date--------->", "onResponse() called with: Start Date = [" + call + "], response = [" + formatDate(data.getStartDate()) + "]");

                            if (!session.getUserId_Vendor().equalsIgnoreCase(""))
                                if (alldata.getVenue().get(0).getUserId().equalsIgnoreCase(session.getUserId_Vendor())) {
                                    booking_now2.setVisibility(View.GONE);
                                    booking_now.setVisibility(View.VISIBLE);

                                    booking_now.setText("Not For You");
                                }


                        } else {
                            pd.dismiss();
                            Toast.makeText(DetailsActivity.this, "No Data Found", Toast.LENGTH_SHORT).show();
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
                Log.e("TAG", "onFailure() called with: call = [" + call + "], t = [" + t.getLocalizedMessage() + "]");
                pd.dismiss();
            }
        });


    }

    private void replaceFrag(int mode) {
        FragmentManager fragmentManager = getSupportFragmentManager();

        if (mode == VENUE_FRAG) {
            if (fragmentManager.findFragmentByTag("one") != null) {
                getSupportFragmentManager().beginTransaction().show(fragmentManager.findFragmentByTag("one")).commit();
//                fragmentManager.beginTransaction().add(R.id.container_main, new VenueFragment(detailsData), "one").commit();
            } else {
                fragmentManager.beginTransaction().add(R.id.container_main, new VenueFragment(detailsData, DetailsActivity.this), "one").commit();
            }
            //we hide all other visible fragments
            if (fragmentManager.findFragmentByTag("two") != null) {
                fragmentManager.beginTransaction().hide(fragmentManager.findFragmentByTag("two")).commit();
            }
            if (fragmentManager.findFragmentByTag("three") != null) {
                fragmentManager.beginTransaction().hide(fragmentManager.findFragmentByTag("three")).commit();
            }
        }
        if (mode == OFFER_FRAG) {
            if (fragmentManager.findFragmentByTag("two") != null) {
                getSupportFragmentManager().beginTransaction().show(fragmentManager.findFragmentByTag("two")).commit();
//                fragmentManager.beginTransaction().add(R.id.container_main, new OfferFragment(detailsData), "two").commit();
            } else {
                fragmentManager.beginTransaction().add(R.id.container_main, new OfferFragment(detailsData), "two").commit();
            }
            //we hide all other visible fragments
            if (fragmentManager.findFragmentByTag("one") != null) {
                fragmentManager.beginTransaction().hide(fragmentManager.findFragmentByTag("one")).commit();
            }
            if (fragmentManager.findFragmentByTag("three") != null) {
                fragmentManager.beginTransaction().hide(fragmentManager.findFragmentByTag("three")).commit();
            }
        }
        if (mode == ADD_ONS_FRAG) {
            if (fragmentManager.findFragmentByTag("three") != null) {
                getSupportFragmentManager().beginTransaction().show(fragmentManager.findFragmentByTag("three")).commit();
//                fragmentManager.beginTransaction().add(R.id.container_main, new AddOnceFragment(detailsData), "three").commit();
            } else {
                fragmentManager.beginTransaction().add(R.id.container_main, new AddOnceFragment(detailsData), "three").commit();
            }
            //we hide all other visible fragments
            if (fragmentManager.findFragmentByTag("two") != null) {
                fragmentManager.beginTransaction().hide(fragmentManager.findFragmentByTag("two")).commit();
            }
            if (fragmentManager.findFragmentByTag("one") != null) {
                fragmentManager.beginTransaction().hide(fragmentManager.findFragmentByTag("one")).commit();
            }
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
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

    @Override
    protected void onResume() {
        super.onResume();

        if(!selectedDate.equalsIgnoreCase("")){
            package_created_date.setText(formatDate(selectedDate));
            set_date.setText(formatDate(selectedDate));
            set_date.setTextColor(getResources().getColor(R.color.blackk));
            date_img.setColorFilter(ContextCompat.getColor(DetailsActivity.this, R.color.colorPrimary), android.graphics.PorterDuff.Mode.SRC_IN);
            date_checked.setImageResource(R.drawable.added_img);

        }else {
            if(!session.getselectedDate().equalsIgnoreCase("")){
                package_set_date.setText("Change Date");
                Log.e("TAG", "onResume() called Select date By Session " + session.getselectedDate());
                selectedDate = session.getselectedDate();
                Log.e("TAG", "onResume() called Select date By Static Var " + selectedDate);
                set_date.setText(formatDate(selectedDate));
                set_date.setTextColor(getResources().getColor(R.color.blackk));
                date_img.setColorFilter(ContextCompat.getColor(DetailsActivity.this, R.color.colorPrimary), android.graphics.PorterDuff.Mode.SRC_IN);
                date_checked.setImageResource(R.drawable.added_img);
                package_created_date.setText(formatDate(selectedDate));
            }else {
               package_set_date.setText("Set Date");
            }
        }
    }

    private void getReviewData() {
        ApiInterface apiInterface = RetrofitClient.getclient(DetailsActivity.this);

        apiInterface.packageReview(package_id).enqueue(new Callback<ReviewModel>() {
            @Override
            public void onResponse(@NonNull Call<ReviewModel> call, @NonNull Response<ReviewModel> response) {

                if (response.code() == 200)
                    if (response.body() != null)
                        if (response.body().getResult().equalsIgnoreCase("true")) {
                            float review = 0 ;

                            for(int i =0 ; i<response.body().getData().size(); i++){
                                float re = Float.parseFloat(response.body().getData().get(i).getRating());
                                review = review+re;
                            }

                            if (response.body().getData().size() != 0 ) {
                                review = review/response.body().getData().size();
                                some_id.setText(String.valueOf(review));
                                Log.e("TAG", "Total avg rating is "+review);
                            }else{
                                some_id.setText(String.valueOf(0));
                            }

                        }

            }

            @Override
            public void onFailure(@NonNull Call<ReviewModel> call, @NonNull Throwable t) {

            }
        });

    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//        getPackageDetials();
//    }

    @Override
    public void getAddress(double lat, double lang, String address) {

    }

    @Override
    public void onAddonsAdd() {
        Log.e("TAG", "onAddonsAdd() called with interface");
        set_addons.setText(addOnceModels.size() +" Addon's Selected");
        set_addons.setTextColor(getResources().getColor(R.color.blackk));
        add_ons_img.setColorFilter(ContextCompat.getColor(DetailsActivity.this, R.color.colorPrimary), android.graphics.PorterDuff.Mode.SRC_IN);
        addons_checked.setImageResource(R.drawable.added_img);

        if(addOnceModels.size() != 0 && offersModel.size() != 0 && !selectedDate.equalsIgnoreCase("")){
            booking_now.setVisibility(View.GONE);
            booking_now2.setVisibility(View.VISIBLE);
        }else {
            booking_now.setVisibility(View.VISIBLE);
            booking_now2.setVisibility(View.GONE);
        }
    }

    @Override
    public void onAddonsRemove() {

        if(addOnceModels.size() == 0){
            set_addons.setText("Addon's");
            set_addons.setTextColor(getResources().getColor(R.color.grey));
            add_ons_img.setColorFilter(ContextCompat.getColor(DetailsActivity.this, R.color.grey), android.graphics.PorterDuff.Mode.SRC_IN);
            addons_checked.setImageResource(R.drawable.not_added_img);

        }else {
            set_addons.setText(addOnceModels.size() + " Addon's Selected");
            set_addons.setTextColor(getResources().getColor(R.color.blackk));
            add_ons_img.setColorFilter(ContextCompat.getColor(DetailsActivity.this, R.color.colorPrimary), android.graphics.PorterDuff.Mode.SRC_IN);
            addons_checked.setImageResource(R.drawable.added_img);

        }

        if(addOnceModels.size() != 0 && offersModel.size() != 0 && !selectedDate.equalsIgnoreCase("")){
            booking_now.setVisibility(View.GONE);
            booking_now2.setVisibility(View.VISIBLE);
        }else {
            booking_now.setVisibility(View.VISIBLE);
            booking_now2.setVisibility(View.GONE);
        }
    }

    @Override
    public void onOfferAdd() {
        Log.e("TAG", "onOfferAdd() called with interface");
//        if(offersModel.size() != 0){
//
//        }else {
//            set_offers.setText("Offer's");
//            set_offers.setTextColor(getResources().getColor(R.color.grey));
//            offer_img.setColorFilter(ContextCompat.getColor(DetailsActivity.this, R.color.grey), android.graphics.PorterDuff.Mode.SRC_IN);
//            offer_checked.setImageResource(R.drawable.not_added_img);
//
//        }

        if(addOnceModels.size() != 0 && offersModel.size() != 0 && !selectedDate.equalsIgnoreCase("")){
            booking_now.setVisibility(View.GONE);
            booking_now2.setVisibility(View.VISIBLE);
        }else {
            booking_now.setVisibility(View.VISIBLE);
            booking_now2.setVisibility(View.GONE);
        }

        set_offers.setText(offersModel.size()+ " Offer Selected");
        set_offers.setTextColor(getResources().getColor(R.color.blackk));
        offer_img.setColorFilter(ContextCompat.getColor(DetailsActivity.this, R.color.colorPrimary), android.graphics.PorterDuff.Mode.SRC_IN);
        offer_checked.setImageResource(R.drawable.added_img);

    }

    @Override
    public void offerRemove() {
        if(offersModel.size() == 0){
            set_offers.setText("Offer's");
            set_offers.setTextColor(getResources().getColor(R.color.grey));
            offer_img.setColorFilter(ContextCompat.getColor(DetailsActivity.this, R.color.grey), android.graphics.PorterDuff.Mode.SRC_IN);
            offer_checked.setImageResource(R.drawable.not_added_img);
        }else {
            set_offers.setText(offersModel.size()+ " Offer Selected");
            set_offers.setTextColor(getResources().getColor(R.color.blackk));
            offer_img.setColorFilter(ContextCompat.getColor(DetailsActivity.this, R.color.colorPrimary), android.graphics.PorterDuff.Mode.SRC_IN);
            offer_checked.setImageResource(R.drawable.added_img);
        }

        if(addOnceModels.size() != 0 && offersModel.size() != 0 && !selectedDate.equalsIgnoreCase("")){
            booking_now.setVisibility(View.GONE);
            booking_now2.setVisibility(View.VISIBLE);
        }else {
            booking_now.setVisibility(View.VISIBLE);
            booking_now2.setVisibility(View.GONE);
        }
    }

    @Override
    public void onDateChange() {
        if(!selectedDate.equalsIgnoreCase("")){
            Log.e("TAG", "onDateChange() called"+selectedDate);
            package_created_date.setText(formatDate(selectedDate));
            set_date.setText(formatDate(selectedDate));
            set_date.setTextColor(getResources().getColor(R.color.blackk));
            date_img.setColorFilter(ContextCompat.getColor(DetailsActivity.this, R.color.colorPrimary), android.graphics.PorterDuff.Mode.SRC_IN);
            date_checked.setImageResource(R.drawable.added_img);

            if(addOnceModels.size() != 0 && offersModel.size() != 0 && !selectedDate.equalsIgnoreCase("")){
                booking_now.setVisibility(View.GONE);
                booking_now2.setVisibility(View.VISIBLE);
            }

        }else {
            Log.e("TAG", "onDateChange() called"+selectedDate);
            set_date.setText("Set date");
            set_date.setTextColor(getResources().getColor(R.color.grey));
            date_img.setColorFilter(ContextCompat.getColor(DetailsActivity.this, R.color.colorPrimary), android.graphics.PorterDuff.Mode.SRC_IN);
            date_checked.setImageResource(R.drawable.not_added_img);
        }
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        selectedDate = "" ;
        addOnceModels.clear();
        offersModel.clear();
    }
}