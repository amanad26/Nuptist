package com.Nuptist.mainscreens;

import android.content.Context;
import android.content.Intent;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;


import com.Nuptist.BookingActivity;
import com.Nuptist.CreatePackageDetailsActivity;
import com.Nuptist.DetailsActivity;
import com.Nuptist.Models.CountryPackageModel;
import com.Nuptist.Models.GetPackageModel;
import com.Nuptist.Models.MyBookingModel;
import com.Nuptist.Models.PackageByRatingModel;
import com.Nuptist.R;
import com.Nuptist.RetrofitApis.ApiInterface;
import com.Nuptist.RetrofitApis.RetrofitClient;
import com.Nuptist.SearchPackageActivity;
import com.Nuptist.Session.Session;
import com.Nuptist.adpaters.CountryPackageAdapter;
import com.Nuptist.adpaters.MyBookingAdapter;
import com.Nuptist.adpaters.PackageByRatingAdapter;
import com.Nuptist.adpaters.PackageListAdapter;
import com.Nuptist.adpaters.PopularPackageAdapter;
import com.skydoves.elasticviews.ElasticCardView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeUserFragment extends Fragment {
    ElasticCardView details;
    CardView AG_SErach;
    LinearLayout tochech  , booking_linear ;
    View root;
    LinearLayout no_data__linear, all_data_linear;
    RecyclerView packge_recycler, polular_recycler, booking_recycler;
    List<GetPackageModel.PackageData> packageData = new ArrayList<>();
    List<CountryPackageModel.PackageCountryData> packageData2 = new ArrayList<>();
    Session session;
    TextView see_more_btn, see_more_btn_2,see_more_btn_booking;
    SwipeRefreshLayout swipe_refresh;

    public HomeUserFragment() {
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_home_user, container, false);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        // AG_details = root.findViewById(R.id.AG_details);
        AG_SErach = root.findViewById(R.id.AG_SErach);
        // details_pg = root.findViewById(R.id.details_pg);
        details = root.findViewById(R.id.details);
        tochech = root.findViewById(R.id.tochech);
        packge_recycler = root.findViewById(R.id.packge_recycler);
        polular_recycler = root.findViewById(R.id.polular_recycler);
        no_data__linear = root.findViewById(R.id.no_data__linear);
        all_data_linear = root.findViewById(R.id.all_data_linear);
        see_more_btn = root.findViewById(R.id.see_more_btn);
        see_more_btn_2 = root.findViewById(R.id.see_more_btn_2);
        swipe_refresh = root.findViewById(R.id.swipe_refresh);
        booking_linear = root.findViewById(R.id.booking_linear);
        booking_recycler = root.findViewById(R.id.booking_recycler);
        see_more_btn_booking = root.findViewById(R.id.see_more_btn_booking);

        if (getContext() != null) {
            session = new Session(getContext());
        }

        ///getPackageByCity();

        getPackageByCountry();

        getMyBooking();

        see_more_btn.setOnClickListener(v -> startActivity(new Intent(getContext(), CreatePackageDetailsActivity.class)));
        see_more_btn_2.setOnClickListener(v -> startActivity(new Intent(getContext(), CreatePackageDetailsActivity.class)));
        see_more_btn_booking.setOnClickListener(v -> startActivity(new Intent(getContext(), BookingActivity.class)));

        tochech.setOnClickListener(view -> {
            Intent intent = new Intent(getContext(), DetailsActivity.class);
            startActivity(intent);
        });

        AG_SErach.setOnClickListener(view -> {
            Intent intent = new Intent(getActivity(), SearchPackageActivity.class);
            startActivity(intent);
        });

         swipe_refresh.setOnRefreshListener(() -> {
            ///getPackageByCity();
             getMyBooking();
             getPackageByRating();
             getPackageByCountry();
        });


        return root;

    }

    private  void getPackageByRating(){

        ApiInterface apiInterface = RetrofitClient.getclient(getContext());

        apiInterface.getPackageByRating().enqueue(new Callback<PackageByRatingModel>() {
            @Override
            public void onResponse(@NonNull Call<PackageByRatingModel> call, @NonNull Response<PackageByRatingModel> response) {
                swipe_refresh.setRefreshing(false);
                List<PackageByRatingModel.PackageRatingData> list = new ArrayList<>();

                if(response.code() == 200)
                    if(response.body() != null)
                        if(response.body().getResult().equalsIgnoreCase("true")){

                            for (int i = 0; i < response.body().getData().size(); i++) {
                                if(response.body().getData().get(i).getUsers_status().equalsIgnoreCase("1"))
                                    list.add(response.body().getData().get(i));
                            }

                            if (list.size() != 0) {
                                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
                                linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
                                polular_recycler.setLayoutManager(linearLayoutManager);
                                polular_recycler.setAdapter(new PackageByRatingAdapter(getContext(),list, response.body().getPath()));
                                no_data__linear.setVisibility(View.GONE);
                                all_data_linear.setVisibility(View.VISIBLE);
                            } else {
                                no_data__linear.setVisibility(View.VISIBLE);
                                all_data_linear.setVisibility(View.GONE);
                                Toast.makeText(getContext(), "No Data Found", Toast.LENGTH_SHORT).show();
                            }

                        }

            }

            @Override
            public void onFailure(@NonNull Call<PackageByRatingModel> call, @NonNull Throwable t) {
                swipe_refresh.setRefreshing(false);
                Log.e("TAG", "onFailure() called with: call = [" + call + "], t = [" + t + "]");
            }
        });

    }

   /*
    private void getPackageByCity() {

        ApiInterface apiInterface = RetrofitClient.getclient(getContext());

        apiInterface.getPackageByCity(session.getMycity()).enqueue(new Callback<GetPackageModel>() {
            @Override
            public void onResponse(@NonNull Call<GetPackageModel> call, @NonNull Response<GetPackageModel> response) {
                swipe_refresh.setRefreshing(false);
                try {

                    if (response.code() == 200) {
                        if (response.body() != null)
                            if (response.body().getResult().equalsIgnoreCase("true")) {

                                packageData.clear();
                                for (int i = 0; i < response.body().getData().size(); i++) {

                                    if (response.body().getData().get(i).getUsers_status().equalsIgnoreCase("1"))
                                        packageData.add(response.body().getData().get(i));
                                 }

                                if (packageData.size() != 0) {
                                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
                                    linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
                                    polular_recycler.setLayoutManager(linearLayoutManager);
                                    polular_recycler.setAdapter(new PopularPackageAdapter(getContext(), packageData, response.body().getPath()));
                                    no_data__linear.setVisibility(View.GONE);
                                    all_data_linear.setVisibility(View.VISIBLE);
                                } else {
                                    all_data_linear.setVisibility(View.GONE);
                                    //   no_data__linear.setVisibility(View.VISIBLE);
                                    no_data__linear.setVisibility(View.VISIBLE);
                                    Toast.makeText(getContext(), "No Packages Found", Toast.LENGTH_SHORT).show();
                                }

                            } else {
                                // no_data__linear.setVisibility(View.VISIBLE);
                                all_data_linear.setVisibility(View.GONE);
                                //   no_data__linear.setVisibility(View.VISIBLE);
                                no_data__linear.setVisibility(View.VISIBLE);
                                Toast.makeText(getContext(), "No Packages Found", Toast.LENGTH_SHORT).show();
                            }

                    }


                } catch (Exception e) {
                    e.getLocalizedMessage();
                }

            }

            @Override
            public void onFailure(@NonNull Call<GetPackageModel> call, @NonNull Throwable t) {
                Log.e("TAG", "onFailure() called with: call = [" + call + "], t = [" + t.getLocalizedMessage() + "]");
                swipe_refresh.setRefreshing(false);
                //  no_data__linear.setVisibility(View.VISIBLE);
            }
        });

    }
    */


    public static String getCountryDialCode(String CountryId, Context context){
        String contryDialCode = null;

        String[] arrContryCode=context.getResources().getStringArray(R.array.DialingCountryCode);
        for(int i=0; i<arrContryCode.length; i++){
            String[] arrDial = arrContryCode[i].split(",");
            if(arrDial[1].trim().equals(CountryId.trim())){
                contryDialCode = arrDial[0];
                break;
            }
        }
        return "+"+contryDialCode;
    }

    private void getPackageByCountry() {

        Log.e("TAG", "getPackageByCountry() called"+session.getCounty());

        ApiInterface apiInterface = RetrofitClient.getclient(getContext());
        apiInterface.getPackageByCountry(session.getCounty()).enqueue(new Callback<CountryPackageModel>() {
            @Override
            public void onResponse(@NonNull Call<CountryPackageModel> call, @NonNull Response<CountryPackageModel> response) {
                swipe_refresh.setRefreshing(false);
                session.setCounty("");
                try {

                    if (response.code() == 200) {
                        if (response.body() != null)
                            if (response.body().getResult().equalsIgnoreCase("true")) {

                                packageData2.clear();
                                for (int i = 0; i < response.body().getData().size(); i++) {

                                    if (response.body().getData().get(i).getUsers_status().equalsIgnoreCase("1"))
                                        packageData2.add(response.body().getData().get(i));
                                }

                                if (packageData2.size() != 0) {
                                    LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(getContext());
                                    linearLayoutManager2.setReverseLayout(true);
                                    packge_recycler.setLayoutManager(linearLayoutManager2);
                                    packge_recycler.setAdapter(new CountryPackageAdapter(getContext(), packageData2,10));



                                    ////////// rating adapter

//                                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
//                                    linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
//                                    polular_recycler.setLayoutManager(linearLayoutManager);
//                                    polular_recycler.setAdapter(new PopularPackageAdapter(getContext(),packageData2,""));
//                                    no_data__linear.setVisibility(View.GONE);
//                                    all_data_linear.setVisibility(View.VISIBLE);

                                    no_data__linear.setVisibility(View.GONE);
                                    all_data_linear.setVisibility(View.VISIBLE);
                                } else {
                                    no_data__linear.setVisibility(View.VISIBLE);
                                    all_data_linear.setVisibility(View.GONE);
                                    Toast.makeText(getContext(), "No Data Found", Toast.LENGTH_SHORT).show();
                                }

                            } else {
                                all_data_linear.setVisibility(View.GONE);
                                //   no_data__linear.setVisibility(View.VISIBLE);
                                no_data__linear.setVisibility(View.VISIBLE);
                                Toast.makeText(getContext(), "No Packages Found", Toast.LENGTH_SHORT).show();
                            }

                    }


                } catch (Exception e) {
                    e.getLocalizedMessage();
                }

            }

            @Override
            public void onFailure(@NonNull Call<CountryPackageModel> call, @NonNull Throwable t) {
                Log.e("TAG", "onFailure() called with: call = [" + call + "], t = [" + t.getLocalizedMessage() + "]");
                swipe_refresh.setRefreshing(false);
                //  no_data__linear.setVisibility(View.VISIBLE);
            }
        });

    }


    private  void getMyBooking(){
        ApiInterface apiInterface = RetrofitClient.getclient(getContext());

        apiInterface.getMyBooking(session.getUserId()).enqueue(new Callback<MyBookingModel>() {
            @Override
            public void onResponse(@NonNull Call<MyBookingModel> call, @NonNull Response<MyBookingModel> response) {
                swipe_refresh.setRefreshing(false);
                if(response.code() == 200)
                    if(response.body() != null)
                        if(response.body().getResult().equalsIgnoreCase("true")){

                            List<MyBookingModel.MyBookingData> bookingList = new ArrayList<>();

                            for (int i = 0; i < response.body().getData().size(); i++) {
                               if(response.body().getData().get(i).getBookingStatus().equalsIgnoreCase("pending") || response.body().getData().get(i).getBookingStatus().equalsIgnoreCase("inprogress")  )
                                   bookingList.add(response.body().getData().get(i));
                            }

                            if(bookingList.size() != 0){
                                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
                                linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
                                linearLayoutManager.setReverseLayout(true);
                                linearLayoutManager.setStackFromEnd(true);
                                booking_recycler.setLayoutManager(linearLayoutManager);
                                MyBookingAdapter adapter = new MyBookingAdapter(getContext(), bookingList,0,session);
                                booking_recycler.setAdapter(adapter);
                                booking_linear.setVisibility(View.VISIBLE);
                            }else {
                                //////No Pending Booking Found..
                                booking_linear.setVisibility(View.GONE);
                            }


                        }else {
                            booking_linear.setVisibility(View.GONE);
                        }

            }

            @Override
            public void onFailure(@NonNull Call<MyBookingModel> call, @NonNull Throwable t) {
                swipe_refresh.setRefreshing(false);
                booking_linear.setVisibility(View.GONE);
                Log.e("TAG", "onFailure() called with: call = [" + call + "], t = [" + t.getLocalizedMessage() + "]");
            }
        });

    }


    @Override
    public void onResume() {
        super.onResume();
        getPackageByRating();
    }
}
