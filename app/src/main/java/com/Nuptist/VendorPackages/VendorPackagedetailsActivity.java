package com.Nuptist.VendorPackages;


import static com.Nuptist.RetrofitApis.BaseUrls.IMAGE_URL;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.Nuptist.AddOnceFragment;
import com.Nuptist.DetailsActivity;
import com.Nuptist.DetailsActivityAdapter;
import com.Nuptist.Models.LogOutModel;
import com.Nuptist.Models.PackageDetailsModel;
import com.Nuptist.Models.PackageViewModel;
import com.Nuptist.Models.ProgressDialog;
import com.Nuptist.Models.RecentPackagesModel;
import com.Nuptist.Models.ReviewModel;
import com.Nuptist.OfferFragment;
import com.Nuptist.OfferVendorFragment;
import com.Nuptist.R;
import com.Nuptist.RetrofitApis.ApiInterface;
import com.Nuptist.RetrofitApis.BidInterface;
import com.Nuptist.RetrofitApis.RetrofitClient;
import com.Nuptist.Session.Session;
import com.Nuptist.SucessActivity;
import com.Nuptist.VenueFragment;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VendorPackagedetailsActivity extends AppCompatActivity implements BidInterface {

    private final int VENUE_FRAG = 0;
    private final int OFFER_FRAG = 1;
    private final int ADD_ONS_FRAG = 2;
    ImageView ic_back;

    TabLayout tab_lay;
    TextView venue, offer, addonce;

    String package_id;
    TextView package_name, package_price, package_address, package_created_date, package_people, some_id ;
    ImageView package_image, liked_image;
    private final String[] titles = new String[]{"Venue", "Offers", "Add Ons "};
    private ViewPager2 product_viewpager;
    private PackageDetailsModel.PacjkageDetailsData alldata;
    private PackageDetailsModel detailsData;
    Session session;
    boolean is_liked = false;

    View view_1, view_2, view_3;
    private String package_image_string = "";
    BidInterface bidInterface ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendor_packagedetails);

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        Window window = getWindow();
        window.setStatusBarColor(getResources().getColor(R.color.white));

        session = new Session(VendorPackagedetailsActivity.this);
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

        getRecentPackages();
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


        getReviewData();
        ic_back.setOnClickListener(view -> onBackPressed());

        
    }

    private void adddPackageView() {

        ApiInterface apiInterface = RetrofitClient.getclient(VendorPackagedetailsActivity.this);

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


        ApiInterface apiInterface = RetrofitClient.getclient(VendorPackagedetailsActivity.this);
        String userid;
        if (session.getType().equalsIgnoreCase("Vendor")) {
            userid = session.getUserId_Vendor();
        } else {
            userid = session.getUserId();
        }

        apiInterface.favoritePackage(userid, package_id).enqueue(new Callback<PackageViewModel>() {
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

        ApiInterface apiInterface = RetrofitClient.getclient(VendorPackagedetailsActivity.this);

        String userid;
        if (session.getType().equalsIgnoreCase("Vendor")) {
            userid = session.getUserId_Vendor();
        } else {
            userid = session.getUserId();
        }


        apiInterface.packageView(
                userid,
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

        ApiInterface apiInterface = RetrofitClient.getclient(VendorPackagedetailsActivity.this);

        apiInterface.getPackagesDetails(package_id, session.getUserId()).enqueue(new Callback<PackageDetailsModel>() {
            @Override
            public void onResponse(@NonNull Call<PackageDetailsModel> call, @NonNull Response<PackageDetailsModel> response) {

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
                                package_created_date.setText(formatDate(data.getStartDate()));
                                Log.e("TAG", "Start Date  = [" + data.getStartDate() + "]");
                            }catch (Exception e){
                                e.getLocalizedMessage();
                                Log.e("TAG", "Start Date Error  = [" + e.getLocalizedMessage() + "]");
                            }

                            package_people.setText(alldata.getVenue().get(0).getGuestCapycityMix());

                            if(data.getPrice().equalsIgnoreCase("")){
                                package_price.setText("Rs. 0 /Day");
                            }else {
                                package_price.setText("Rs. "+data.getPrice());
                            }

                            // package_created_date.setText(formatDate(data.getStartDate()));

//                            product_viewpager.setAdapter(new DetailsActivityAdapter(VendorPackagedetailsActivity.this, detailsData, package_id));
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

//
//                            new TabLayoutMediator(tab_lay, product_viewpager, (tab, position) -> {
//                                tab.setText(titles[position]);
//                                //  tab.setIcon(mTabIconsSelected[position]);
//                            }).attach();

                            Log.e("Date--------->", "onResponse() called with: Start Date = [" + call + "], response = [" + formatDate(data.getStartDate()) + "]");

                            Picasso.get().load(IMAGE_URL+data.getImage()).into(package_image);
                        } else {
                            Toast.makeText(VendorPackagedetailsActivity.this, " Details Not  Found", Toast.LENGTH_SHORT).show();
                        }
                    }

                } catch (Exception e) {

                    Toast.makeText(VendorPackagedetailsActivity.this, " Details Not  Found", Toast.LENGTH_SHORT).show();

                    Log.e("Date--------->", "onResponse() called with: Start Date = [" + call + "], response = [" + e.getLocalizedMessage() + "]");
                    e.getLocalizedMessage();
                }

            }

            @Override
            public void onFailure(@NonNull Call<PackageDetailsModel> call, @NonNull Throwable t) {
                Log.e("TAG", "onFailure() called with: call = [" + call + "], t = [" + t.getLocalizedMessage() + "]");
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
                fragmentManager.beginTransaction().add(R.id.container_main, new VendorVenueFragment(detailsData), "one").commit();
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
                fragmentManager.beginTransaction().add(R.id.container_main, new OfferVendorFragment(detailsData,VendorPackagedetailsActivity.this), "two").commit();
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
                fragmentManager.beginTransaction().add(R.id.container_main, new VendorAddonceFragment(detailsData, VendorPackagedetailsActivity.this), "three").commit();
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

    private void getRecentPackages() {

        ApiInterface apiInterface = RetrofitClient.getclient(VendorPackagedetailsActivity.this);

        apiInterface.getRecentPackaes(session.getUserId()).enqueue(new Callback<RecentPackagesModel>() {
            @Override
            public void onResponse(@NonNull Call<RecentPackagesModel> call, @NonNull Response<RecentPackagesModel> response) {

                try {
                    if (response.code() == 200)
                        if (response.body() != null)
                            if (response.body().getResult().equalsIgnoreCase("true")) {

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
    public void addBid(String minPrice, String MaxPrice, String date, String pid, String servie_id) {

        Log.e("TAG", "addBid() called with: minPrice = [" + minPrice + "], MaxPrice = [" + MaxPrice + "], date = [" + date + "]");

        ProgressDialog pd = new ProgressDialog(VendorPackagedetailsActivity.this);
        pd.show();

        ApiInterface apiInterface = RetrofitClient.getclient(VendorPackagedetailsActivity.this);

        apiInterface.addBid(
              package_id,
                session.getUserId(),
                session.getUserId_Vendor(),
                "",
                minPrice,
                MaxPrice,
                servie_id,
                "",
                "",
                ""
        ).enqueue(new Callback<LogOutModel>() {
            @Override
            public void onResponse(@NonNull Call<LogOutModel> call, @NonNull Response<LogOutModel> response) {

                pd.dismiss();
                try {
                    if(response.code()==200)
                        if(response.body() != null)
                            if(response.body().getResult().equalsIgnoreCase("true")){
                                Toast.makeText(VendorPackagedetailsActivity.this, "Quote Added", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(VendorPackagedetailsActivity.this, SucessActivity.class).putExtra("text","Quote Added Successfully....!").setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP));
                                finish();
                            }else {
                                Toast.makeText(VendorPackagedetailsActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                            }
                } catch (Exception e) {
                    pd.dismiss();
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

    @Override
    public void addBid2(String minPrice, String MaxPrice, String date, String pid, String product_id) {



        Log.e("TAG", "addBid() called with: minPrice = [" + minPrice + "], MaxPrice = [" + MaxPrice + "], date = [" + date + "]");

        ProgressDialog pd = new ProgressDialog(VendorPackagedetailsActivity.this);
        pd.show();

        ApiInterface apiInterface = RetrofitClient.getclient(VendorPackagedetailsActivity.this);

        apiInterface.addBid(
                package_id,
                session.getUserId(),
                session.getUserId_Vendor(),
                "",
                minPrice,
                MaxPrice,
                "",
                product_id,
                        "",
                ""
        ).enqueue(new Callback<LogOutModel>() {
            @Override
            public void onResponse(@NonNull Call<LogOutModel> call, @NonNull Response<LogOutModel> response) {

                pd.dismiss();
                try {
                    if(response.code()==200)
                        if(response.body() != null)
                            if(response.body().getResult().equalsIgnoreCase("true")){
                                Toast.makeText(VendorPackagedetailsActivity.this, "Quote Added", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(VendorPackagedetailsActivity.this, SucessActivity.class).putExtra("text","Quote Added Successfully....!").setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP));
                                finish();
                            }else {
                                Toast.makeText(VendorPackagedetailsActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                            }
                } catch (Exception e) {
                    pd.dismiss();
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

    @Override
    public void addOfferBid(String minPrice, String MaxPrice, String date, String pid, String service_id) {


        Log.e("TAG", "addBid() called with: minPrice = [" + minPrice + "], MaxPrice = [" + MaxPrice + "], date = [" + date + "]");

        ProgressDialog pd = new ProgressDialog(VendorPackagedetailsActivity.this);
        pd.show();

        ApiInterface apiInterface = RetrofitClient.getclient(VendorPackagedetailsActivity.this);

        apiInterface.addBid(
                package_id,
                session.getUserId(),
                session.getUserId_Vendor(),
                "",
                minPrice,
                MaxPrice,
                "",
                "",
                "",
                service_id
        ).enqueue(new Callback<LogOutModel>() {
            @Override
            public void onResponse(@NonNull Call<LogOutModel> call, @NonNull Response<LogOutModel> response) {

                pd.dismiss();
                try {
                    if(response.code()==200)
                        if(response.body() != null)
                            if(response.body().getResult().equalsIgnoreCase("true")){
                                Toast.makeText(VendorPackagedetailsActivity.this, "Quote Added", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(VendorPackagedetailsActivity.this, SucessActivity.class).putExtra("text","Quote Added Successfully....!").setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP));
                                finish();
                            }else {
                                Toast.makeText(VendorPackagedetailsActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                            }
                } catch (Exception e) {
                    pd.dismiss();
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

    @Override
    public void addOfferBid2(String minPrice, String MaxPrice, String date, String pid, String service_id) {

        Log.e("TAG", "addBid() called with: minPrice = [" + minPrice + "], MaxPrice = [" + MaxPrice + "], date = [" + date + "]");

        ProgressDialog pd = new ProgressDialog(VendorPackagedetailsActivity.this);
        pd.show();

        ApiInterface apiInterface = RetrofitClient.getclient(VendorPackagedetailsActivity.this);

        apiInterface.addBid(
                package_id,
                session.getUserId(),
                session.getUserId_Vendor(),
                "",
                minPrice,
                MaxPrice,
                "",
                "",
                service_id,
                ""
        ).enqueue(new Callback<LogOutModel>() {
            @Override
            public void onResponse(@NonNull Call<LogOutModel> call, @NonNull Response<LogOutModel> response) {

                pd.dismiss();
                try {
                    if(response.code()==200)
                        if(response.body() != null)
                            if(response.body().getResult().equalsIgnoreCase("true")){
                                Toast.makeText(VendorPackagedetailsActivity.this, "Quote Added", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(VendorPackagedetailsActivity.this, SucessActivity.class).putExtra("text","Quote Added Successfully....!").setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP));
                                finish();
                            }else {
                                Toast.makeText(VendorPackagedetailsActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                            }
                } catch (Exception e) {
                    pd.dismiss();
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

    private void getReviewData() {
        ApiInterface apiInterface = RetrofitClient.getclient(VendorPackagedetailsActivity.this);

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

                            if (response.body().getData().size() >= 2) {
                                review = review/5;
                                some_id.setText(String.valueOf(review));
                                Log.e("TAG", "Total avg rating is "+review);
                            }else if(response.body().getData().size() == 0 ){
                                some_id.setText(String.valueOf(0));
                            }else {
                                some_id.setText(String.valueOf(4.6));
                            }

                        }
            }
            @Override
            public void onFailure(@NonNull Call<ReviewModel> call, @NonNull Throwable t) {

            }
        });

    }

}