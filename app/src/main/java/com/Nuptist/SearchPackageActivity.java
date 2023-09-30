package com.Nuptist;

import static com.Nuptist.VenueFragment.selectedDate;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.Nuptist.Models.DateModel;
import com.Nuptist.Models.GetPackageModel;
import com.Nuptist.Models.ProgressDialog;
import com.Nuptist.Models.SearchPackageModel;
import com.Nuptist.RetrofitApis.ApiInterface;
import com.Nuptist.RetrofitApis.RetrofitClient;
import com.Nuptist.Session.Session;
import com.Nuptist.VendorPackages.VendorSearchResultActivity;
import com.Nuptist.mainscreens.HomeUserFragment;
import com.Nuptist.venue.SearchVenueActivity;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateLongClickListener;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.prolificinteractive.materialcalendarview.OnMonthChangedListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SearchPackageActivity extends AppCompatActivity implements OnDateSelectedListener, OnMonthChangedListener, OnDateLongClickListener {


    ImageView set_date;
    TextView search, search2;
    TextView selectDate, search_city;
    EditText number_of_guest;
    ImageView back;
    Session session;
    String selectedDate;
    String city = "", address;
    ProgressDialog pd;
    String type = "user";
    TextView title_text;
    private List<SearchPackageModel.SearchData> mainModel = new ArrayList<>();
    private List<SearchPackageModel.SearchData> searchedModelResult = new ArrayList<>();
    private List<DateModel.DateData> list = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_package_activity);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        Window window = getWindow();
        window.setStatusBarColor(getResources().getColor(R.color.white));

        search_city = findViewById(R.id.search_city);
        selectDate = findViewById(R.id.select_date);
        set_date = findViewById(R.id.set_date);
        back = findViewById(R.id.back);
        search = findViewById(R.id.search);
        title_text = findViewById(R.id.title_text);
        search2 = findViewById(R.id.search2);
        number_of_guest = findViewById(R.id.number_of_guest);

        pd = new ProgressDialog(SearchPackageActivity.this);
        session = new Session(SearchPackageActivity.this);


        if (session.getUserType().equalsIgnoreCase("Vendor")) {
            if (session.getIsPersonal()) {
                title_text.setText("Plan My Event");
            } else {
                title_text.setText("Set The Criteria");
            }

        } else {
            title_text.setText("Plan My Event");
        }

        try {
            if (getIntent() != null) {
                city = getIntent().getStringExtra("city");
                address = getIntent().getStringExtra("address");
                search_city.setText(address);
                if (!city.equalsIgnoreCase("")) {
                    ///  getSearchedPackage(city);
                    search2.setVisibility(View.GONE);
                    search.setVisibility(View.VISIBLE);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        selectDate.setOnClickListener(view -> selectDate(selectDate));

        /// showButton(number_of_guest);

        search_city.setOnClickListener(view -> {
            Intent intent = new Intent(SearchPackageActivity.this, SearchVenueActivity.class);
            intent.putExtra("where", "search");
            startActivity(intent);
        });

        search.setOnClickListener(view -> {

            if (session.getUserType().equalsIgnoreCase("Vendor")) {
                if (session.getIsPersonal()) {
                    type = "user";
                } else {
                    type = "vendor";
                }

            } else {
                type = "user";
            }


            if (type.equalsIgnoreCase("user")) {
                getSearchedPackage(city, "1");
            } else {
                getSearchedPackage(city, "0");
            }

        });


        back.setOnClickListener(view -> onBackPressed());

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(SearchPackageActivity.this, HomeActivity.class));
        finishAffinity();
    }

    private void showButton(EditText email) {

        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (s.toString().equalsIgnoreCase("")) {
                    search2.setVisibility(View.VISIBLE);
                    search.setVisibility(View.GONE);
                } else {
                    search2.setVisibility(View.GONE);
                    search.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


    }


    private void showdialog() {

        final Dialog dialog = new Dialog(SearchPackageActivity.this);

        // setting content view to dialog
        dialog.setContentView(R.layout.calender_layout);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(false);
        dialog.getWindow().getAttributes().windowAnimations = R.style.animation;
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


        MaterialCalendarView widget = dialog.findViewById(R.id.calendarView);

        // getting reference of TextView
        // TextView dialogButtonYes = (TextView) dialog.findViewById(R.id.btn_yes);
        ImageView set_date = (ImageView) dialog.findViewById(R.id.set_date);


        widget.setOnDateChangedListener(this);
        widget.setOnDateLongClickListener(this);
        widget.setOnMonthChangedListener(this);


        // click listener for No
        set_date.setOnClickListener(v -> {
            // dismiss the dialog
            selectDate.setText(selectedDate);
            dialog.dismiss();

        });

        dialog.show();

    }

    private void selectDate(TextView DateBtn) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, (datePicker, year1, month1, day1) -> {
            String date = year1 + "-" + (month1 + 1) + "-" + day1;
            //    datePicker.setBackgroundResource(R.drawable.edit_background);
            DateBtn.setText(date);
        }, year, month, day);

        datePickerDialog.show();
    }


    @Override
    public void onDateLongClick(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date) {
        Log.e("TAG", "onDateLongClick() called with: widget = [" + widget + "], date = [" + date + "]");
    }

    @Override
    public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {

        Log.e("TAG", "onDateSelected() called with: widget = [" + widget + "], date = [" + date + "], selected = [" + selected + "]");
        selectedDate = String.valueOf(date.getYear() + "-" + date.getMonth() + "-" + date.getDay());
    }

    @Override
    public void onMonthChanged(MaterialCalendarView widget, CalendarDay date) {

        Log.e("TAG", "onMonthChanged() called with: widget = [" + widget + "], date = [" + date + "]");
    }

    private void getSearchedPackage(String city, String status) {

        pd.show();

        ApiInterface apiInterface = RetrofitClient.getclient(SearchPackageActivity.this);

        apiInterface.getSearchPackageByCity(city).enqueue(new Callback<SearchPackageModel>() {
            @Override
            public void onResponse(@NonNull Call<SearchPackageModel> call, @NonNull Response<SearchPackageModel> response) {

                pd.dismiss();
                if (response.code() == 200)
                    if (response.body() != null && response.body().getResult().equalsIgnoreCase("true")) {

                        for (int i = 0; i < response.body().getData().size(); i++) {
                            if (response.body().getData().get(i).getUsers_status().equalsIgnoreCase(status)) {
                                mainModel.add(response.body().getData().get(i));
                            }
                        }

                        if (mainModel.size() != 0) {
                            if (type.equalsIgnoreCase("user")) seacrhPackages();
                            else seacrhPackagesVendor();

                        } else {
                            pd.dismiss();
                            Toast.makeText(SearchPackageActivity.this, "No Packages Found..!", Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        pd.dismiss();
                        Toast.makeText(SearchPackageActivity.this, "No Packages Found..!", Toast.LENGTH_SHORT).show();
                    }


            }

            @Override
            public void onFailure(@NonNull Call<SearchPackageModel> call, @NonNull Throwable t) {
                pd.dismiss();
                Log.e("TAG", "onFailure() called with: call = [" + call + "], t = [" + t.getLocalizedMessage() + "]");
            }
        });

    }

    private void seacrhPackages() {

        Log.e("TAG", "seacrhPackages() called");

        searchedModelResult.clear();
        if (mainModel.size() != 0) {
            Log.e("TAG", "mainModel.size() != 0");

            if (number_of_guest.getText().toString().equalsIgnoreCase("")) {
                Log.e("TAG", "mainModel.size() != 0");
                if (selectDate.getText().toString().equalsIgnoreCase("")) {
                    Log.e("TAG", "mainModel.size() != 0");
                    searchedModelResult.addAll(mainModel);

                    if (searchedModelResult.size() != 0) {
                        Intent intent = new Intent(SearchPackageActivity.this, SearchResultActivity.class);
                        intent.putExtra("searched_package_list", (Serializable) searchedModelResult);
                        intent.putExtra("city", city);
                        intent.putExtra("number_of_guest", number_of_guest.getText().toString());
                        intent.putExtra("date", selectDate.getText().toString());
                        startActivity(intent);
                        pd.dismiss();
                    } else {
                        mainModel.clear();
                        searchedModelResult.clear();
                        Toast.makeText(this, "No Packages Found..!", Toast.LENGTH_SHORT).show();
                        pd.dismiss();
                    }


                } else {
                    //list = getAvailableDates(selectDate.getText().toString());
                    String date = selectDate.getText().toString();
                    session.setSelectedDate(date);
                     getAvailableDates(date);
//                    Calendar date2 = formatCalender(date);
//                    for (int i = 0; i < mainModel.size(); i++) {
//                        String startDate = mainModel.get(i).getStartDate();
//                        String endDate = mainModel.get(i).getDate();
//
//                        Calendar startDateCal = formatCalender(startDate);
//                        Calendar endDateCal = formatCalender(endDate);
//
//                        if (date2.getTimeInMillis() >= startDateCal.getTimeInMillis() && date2.getTimeInMillis() <= endDateCal.getTimeInMillis()) {
//                            searchedModelResult.add(mainModel.get(i));
//                        }
//
//                    }

                }


            } else {
                int numberofguest = 0;
                try {
                    numberofguest = Integer.parseInt(number_of_guest.getText().toString());
                    Log.e("TAG", "seacrhPackages() called numberofguest" + numberofguest);
                } catch (NumberFormatException e) {
                    Log.e("TAG", "seacrhPackages() called exception e" + e.getLocalizedMessage());
                    e.printStackTrace();
                }
                if (selectDate.getText().toString().equalsIgnoreCase("")) {
                    Log.e("TAG", "mainModel.size() != 0");

                    for (int i = 0; i < mainModel.size(); i++) {
                        Log.e("TAG", "seacrhPackages() called exception i" + i + mainModel.get(i).getGuestCapycityMix());
                        if (numberofguest >= Integer.parseInt(mainModel.get(i).getGuestCapycityMix())) {
                            searchedModelResult.add(mainModel.get(i));
                        }
                    }

                    if (searchedModelResult.size() != 0) {
                        Intent intent = new Intent(SearchPackageActivity.this, SearchResultActivity.class);
                        intent.putExtra("searched_package_list", (Serializable) searchedModelResult);
                        intent.putExtra("city", city);
                        intent.putExtra("number_of_guest", number_of_guest.getText().toString());
                        intent.putExtra("date", selectDate.getText().toString());
                        startActivity(intent);
                        pd.dismiss();
                    } else {
                        mainModel.clear();
                        searchedModelResult.clear();
                        Toast.makeText(this, "No Packages Found..!", Toast.LENGTH_SHORT).show();
                        pd.dismiss();
                    }

                } else {
                    String date = selectDate.getText().toString();
                    session.setSelectedDate(date);
                    getAvailableDates(date);
//                    Calendar date2 = formatCalender(date);
//                    for (int i = 0; i < mainModel.size(); i++) {
//                        String startDate = mainModel.get(i).getStartDate();
//                        String endDate = mainModel.get(i).getDate();
//
//                        Calendar startDateCal = formatCalender(startDate);
//                        Calendar endDateCal = formatCalender(endDate);
//
//                        if (date2.getTimeInMillis() >= startDateCal.getTimeInMillis() && date2.getTimeInMillis() <= endDateCal.getTimeInMillis()) {
//
//                            if (numberofguest >= Integer.parseInt(mainModel.get(i).getGuestCapycityMix())) {
//                                searchedModelResult.add(mainModel.get(i));
//                            }
//
//                        }
//
//                    }
                }

            }

        }

//        if (searchedModelResult.size() != 0) {
//            Intent intent = new Intent(SearchPackageActivity.this, SearchResultActivity.class);
//            intent.putExtra("searched_package_list", (Serializable) searchedModelResult);
//            intent.putExtra("city", city);
//            intent.putExtra("number_of_guest", number_of_guest.getText().toString());
//            intent.putExtra("date", selectDate.getText().toString());
//            startActivity(intent);
//            pd.dismiss();
//        } else {
//            mainModel.clear();
//            searchedModelResult.clear();
//            Toast.makeText(this, "No Packages Found..!", Toast.LENGTH_SHORT).show();
//            pd.dismiss();
//        }


    }

    private void seacrhPackagesVendor() {

        Log.e("TAG", "seacrhPackages() called");

        searchedModelResult.clear();
        if (mainModel.size() != 0) {

            Log.e("TAG", "mainModel.size() != 0");

            if (number_of_guest.getText().toString().equalsIgnoreCase("")) {
                Log.e("TAG", "mainModel.size() != 0");
                if (selectDate.getText().toString().equalsIgnoreCase("")) {
                    Log.e("TAG", "mainModel.size() != 0");
                    searchedModelResult.addAll(mainModel);
                } else {
                    String date = selectDate.getText().toString();
                    Calendar date2 = formatCalender(date);
                    for (int i = 0; i < mainModel.size(); i++) {
                        String startDate = mainModel.get(i).getStartDate();
                        String endDate = mainModel.get(i).getDate();

                        Calendar startDateCal = formatCalender(startDate);
                        Calendar endDateCal = formatCalender(endDate);

                        if (date2.getTimeInMillis() >= startDateCal.getTimeInMillis() && date2.getTimeInMillis() <= endDateCal.getTimeInMillis()) {
                            searchedModelResult.add(mainModel.get(i));
                        }

                    }
                }

            } else {

                int numberofguest = 0;
                try {
                    numberofguest = Integer.parseInt(number_of_guest.getText().toString());
                    Log.e("TAG", "seacrhPackages() called numberofguest" + numberofguest);
                } catch (NumberFormatException e) {
                    Log.e("TAG", "seacrhPackages() called exception e" + e.getLocalizedMessage());
                    e.printStackTrace();
                }
                if (selectDate.getText().toString().equalsIgnoreCase("")) {
                    Log.e("TAG", "mainModel.size() != 0");

                    for (int i = 0; i < mainModel.size(); i++) {
                        Log.e("TAG", "seacrhPackages() called exception i" + i + mainModel.get(i).getGuestCapycityMix());
                        if (numberofguest >= Integer.parseInt(mainModel.get(i).getGuestCapycityMix())) {
                            searchedModelResult.add(mainModel.get(i));
                        }
                    }

                } else {
                    String date = selectDate.getText().toString();
                  //  getAvailableDates(date);
                    Calendar date2 = formatCalender(date);
                    for (int i = 0; i < mainModel.size(); i++) {
                        String startDate = mainModel.get(i).getStartDate();
                        String endDate = mainModel.get(i).getDate();

                        Calendar startDateCal = formatCalender(startDate);
                        Calendar endDateCal = formatCalender(endDate);

                        if (date2.getTimeInMillis() >= startDateCal.getTimeInMillis() && date2.getTimeInMillis() <= endDateCal.getTimeInMillis()) {

                            if (numberofguest >= Integer.parseInt(mainModel.get(i).getGuestCapycityMix())) {
                                searchedModelResult.add(mainModel.get(i));
                            }

                        }

                    }


                }

            }

        }

        if (searchedModelResult.size() != 0) {
            Intent intent = new Intent(SearchPackageActivity.this, VendorSearchResultActivity.class);
            intent.putExtra("searched_package_list", (Serializable) searchedModelResult);
            intent.putExtra("city", city);
            intent.putExtra("number_of_guest", number_of_guest.getText().toString());
            intent.putExtra("date", selectDate.getText().toString());
            startActivity(intent);
            pd.dismiss();
        } else {
            mainModel.clear();
            searchedModelResult.clear();
            Toast.makeText(this, "No Packages Found..!", Toast.LENGTH_SHORT).show();
            pd.dismiss();
        }


    }

    public Calendar formatCalender(String s) {

        //  s = "2022-10-21 08:08:18";

        Calendar calendar = Calendar.getInstance();
        String[] dateTime = s.split(" ");

        String dateS = dateTime[0];


        String[] datess = dateS.split("-");

        String year = datess[0];
        String month = datess[1];
        String day = datess[2];

        calendar.set(Integer.parseInt(year), Integer.parseInt(month), Integer.parseInt(day));

        //// return   day + " " + getMonthName(Integer.parseInt(month))  + " " +  year + " "+timeS;
        calendar.add(Calendar.DATE, 1);

        return calendar;
    }


    private void getAvailableDates(String myDate) {
        ApiInterface apiInterface = RetrofitClient.getclient(SearchPackageActivity.this);
        apiInterface.getPackageByDate(myDate, city).enqueue(new Callback<SearchPackageModel>() {
            @Override
            public void onResponse(@NonNull Call<SearchPackageModel> call, @NonNull Response<SearchPackageModel> response) {

                if (response.code() == 200)
                    if (response.body() != null)
                        if (response.body().getResult().equalsIgnoreCase("true")) {
                            searchedModelResult.clear();
                            searchedModelResult.addAll(response.body().getData());

                            if (searchedModelResult.size() != 0) {
                                Intent intent = new Intent(SearchPackageActivity.this, SearchResultActivity.class);
                                intent.putExtra("searched_package_list", (Serializable) searchedModelResult);
                                intent.putExtra("city", city);
                                intent.putExtra("number_of_guest", number_of_guest.getText().toString());
                                intent.putExtra("date", selectDate.getText().toString());
                                startActivity(intent);
                                pd.dismiss();

                            } else {
                                mainModel.clear();
                                searchedModelResult.clear();
                                Toast.makeText(SearchPackageActivity.this, "No Packages Found..!", Toast.LENGTH_SHORT).show();
                                pd.dismiss();
                            }



                            Log.e("TAG", "onResponse() called with: call = [" + call + "], response = [" + response.body().getData().size() + "]");
                        }

            }

            @Override
            public void onFailure(@NonNull Call<SearchPackageModel> call, @NonNull Throwable t) {
                Log.d("TAG", "onFailure() called with: call = [" + call + "], t = [" + t + "]");
            }
        });
    }

}
