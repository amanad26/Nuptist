package com.Nuptist;

import static com.Nuptist.VenueFragment.selectedDate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.Nuptist.Models.DateModel;
import com.Nuptist.Models.ProgressDialog;
import com.Nuptist.Models.SearchPackageModel;
import com.Nuptist.RetrofitApis.ApiInterface;
import com.Nuptist.RetrofitApis.RetrofitClient;
import com.Nuptist.Session.Session;
import com.Nuptist.databinding.ActivityResultSearchBinding;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchResultActivity extends AppCompatActivity {

    String date, number_of_guest, city;
    List<SearchPackageModel.SearchData> data = new ArrayList<>();
    List<SearchPackageModel.SearchData> dataByEndDate = new ArrayList<>();
    List<SearchPackageModel.SearchData> filterdData = new ArrayList<>();

    ActivityResultSearchBinding binding;
    SearchResultActivity activity ;

    Session session ;
    private   List<DateModel.DateData> list = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityResultSearchBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        Window window = getWindow();
        window.setStatusBarColor(getResources().getColor(R.color.white));

        activity = this;

        session = new Session(activity);
        ////// Toast.makeText(activity, session.getselectedDate(), Toast.LENGTH_SHORT).show();


        binding.icBack.setOnClickListener(view -> {
            startActivity(new Intent(activity, HomeActivity.class)
                    .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
              finish();
        });


        try {
            if (getIntent() != null) {
                data = (List<SearchPackageModel.SearchData>) getIntent().getSerializableExtra("searched_package_list");
                number_of_guest = getIntent().getStringExtra("number_of_guest");
                city = getIntent().getStringExtra("city");
                date = getIntent().getStringExtra("date");

                binding.cityTv.setText(city);

                if (!number_of_guest.equalsIgnoreCase("")) {
                    binding.guestTv.setText("(Max : " + number_of_guest + ")");
                } else {
                    binding.guestTv.setVisibility(View.GONE);
                }

                if (!date.equalsIgnoreCase("")) {
                    binding.dateTv.setText(formatDate(date));
                } else {
                    binding.dateTv.setVisibility(View.GONE);
                }
                binding.recySearchresult2.setLayoutManager(new LinearLayoutManager(activity));
                binding.recySearchresult2.setAdapter(new RecySearchresultAdapter(activity, data));
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

        binding.changeLinear.setOnClickListener(v -> {
           selectDate(binding.changeDateTextview);
        });



    }


    private void getAvailableDates(String myDate) {
        ProgressDialog pd = new ProgressDialog(SearchResultActivity.this);
        pd.show();

        ApiInterface apiInterface = RetrofitClient.getclient(SearchResultActivity.this);
        apiInterface.getPackageByDate(myDate, city).enqueue(new Callback<SearchPackageModel>() {
            @Override
            public void onResponse(@NonNull Call<SearchPackageModel> call, @NonNull Response<SearchPackageModel> response) {

                pd.dismiss();
                if (response.code() == 200)
                    if (response.body() != null)
                        if (response.body().getResult().equalsIgnoreCase("true")) {
                            data = response.body().getData();

                            binding.dateTv.setVisibility(View.VISIBLE);
                            binding.dateTv.setText(formatDate(session.getselectedDate()));
                            Log.e("TAG", "onResponse() called with: date = [" + formatDate(session.getselectedDate()) + "]");
                            binding.recySearchresult2.setLayoutManager(new LinearLayoutManager(activity));
                            binding.recySearchresult2.setAdapter(new RecySearchresultAdapter(activity, data));

                            Log.e("TAG", "onResponse() called with: call = [" + call + "], response = [" + response.body().getData().size() + "]");
                        }

            }

            @Override
            public void onFailure(@NonNull Call<SearchPackageModel> call, @NonNull Throwable t) {
                pd.dismiss();
                Log.d("TAG", "onFailure() called with: call = [" + call + "], t = [" + t + "]");
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

    private void selectDate(TextView DateBtn) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, (datePicker, year1, month1, day1) -> {
            String date = year1 + "-" + (month1 + 1) + "-" + day1;
            //    datePicker.setBackgroundResource(R.drawable.edit_background);

            Log.e("TAG", "selectDate() called with: DateBtn = [" + date + "]");
            ///filterData(date);
            session.setSelectedDate(date);
            getAvailableDates(date);
        }, year, month, day);

        datePickerDialog.show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    private void filterData(String date) {
        ProgressDialog pd = new ProgressDialog(activity);
        pd.show();
        filterdData.clear();
        binding.dateTv.setVisibility(View.VISIBLE);
        binding.dateTv.setText(formatDate(date));
        Log.e("TAG", "filterData() called with: date = [" + date + "]");
        Log.e("TAG", "filterData() called with: date = [" + formatDate(date) + "]");

        if(data.size() != 0 )
        {
            Log.e("TAG", "data.size() != 0");
            Calendar date2 = formatCalender(date);
            for (int i = 0; i < data.size(); i++) {
                String startDate = data.get(i).getStartDate();
                String endDate = data.get(i).getDate();

                Calendar startDateCal = formatCalender(startDate);
               // Calendar startDateCal = Calendar.getInstance();
                Calendar endDateCal = formatCalender(endDate);

                if (date2.getTimeInMillis() >= startDateCal.getTimeInMillis() && date2.getTimeInMillis() <= endDateCal.getTimeInMillis()) {
                    filterdData.add(data.get(i));
                }

            }
            pd.dismiss();
            if(filterdData.size() != 0){
                binding.recySearchresult2.setLayoutManager(new LinearLayoutManager(activity));
                binding.recySearchresult2.setAdapter(new RecySearchresultAdapter(activity, filterdData));
            }else {
                pd.dismiss();
                Toast.makeText(activity, "Packages Not Found ", Toast.LENGTH_SHORT).show();
                binding.recySearchresult2.setVisibility(View.GONE);
            }
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
}