package com.Nuptist.VendorPackages;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.Nuptist.Models.SearchPackageModel;
import com.Nuptist.R;
import com.Nuptist.RecySearchresultAdapter;
import com.Nuptist.SearchResultActivity;
import com.Nuptist.databinding.ActivityResultSearchBinding;
import com.Nuptist.databinding.ActivityVendorSearchResult2Binding;
import com.Nuptist.databinding.ActivityVendorSearchResultBinding;

import java.util.ArrayList;
import java.util.List;

public class VendorSearchResultActivity extends AppCompatActivity {

    ActivityVendorSearchResult2Binding binding ;
    String date, number_of_guest, city;
    List<SearchPackageModel.SearchData> data = new ArrayList<>();
    List<SearchPackageModel.SearchData> dataByEndDate = new ArrayList<>();
    List<SearchPackageModel.SearchData> filterdData = new ArrayList<>();

    VendorSearchResultActivity activity ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityVendorSearchResult2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        activity = this;


        try {
            if (getIntent() != null) {
                data = (List<SearchPackageModel.SearchData>) getIntent().getSerializableExtra("searched_package_list");
                number_of_guest = getIntent().getStringExtra("number_of_guest");
                city = getIntent().getStringExtra("city");
                date = getIntent().getStringExtra("date");

                Log.e("TAG", "onCreate() called with: City Name  = [" + city + "]");
                Log.e("TAG", "onCreate() called with: data Size  = [" + data.size() + "]");

                for (int i = 0; i < data.size(); i++) {
                    Log.e("TAG", "onCreate() called with: Package Name = [" + data.get(i).getPackageName() + "]");
                }

                binding.recySearchresult2.setLayoutManager(new LinearLayoutManager(activity));
                binding.recySearchresult2.setAdapter(new VendorSearchPackageAdapter(activity, data));
            }


        }catch (Exception e){
            e.getLocalizedMessage();
        }
    }

}