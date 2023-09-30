package com.Nuptist.VendorPackages;

import static com.Nuptist.adpaters.AddOnsAdapterNew2.addOnceModels;
import static com.Nuptist.adpaters.OfferAdapter2.offersModel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.Nuptist.Models.GetPackageModel;
import com.Nuptist.Models.SearchPackageModel;
import com.Nuptist.RetrofitApis.ApiInterface;
import com.Nuptist.RetrofitApis.RetrofitClient;
import com.Nuptist.adpaters.VendorPackageAdapterNew;
import com.Nuptist.databinding.ActivityVendorPackagesBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VendorPackagesActivity extends AppCompatActivity {

    ActivityVendorPackagesBinding binding;
    List<GetPackageModel.PackageData> filterPackageData = new ArrayList<>();
    private List<GetPackageModel.PackageData> data = new ArrayList<>();
    private String imagePath ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityVendorPackagesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.back.setOnClickListener(view -> onBackPressed());

        getPackage();

        binding.imgSearch.setOnClickListener(v -> {
            binding.imgCnacelSearch.setVisibility(View.VISIBLE);
            binding.imgSearch.setVisibility(View.GONE);
            binding.imgCnacelSearch.setVisibility(View.VISIBLE);
            binding.titelText.setVisibility(View.GONE);
            binding.serchEdit.setVisibility(View.VISIBLE);
            binding.serchEdit.requestFocus();
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,0);
        });


        binding.imgCnacelSearch.setOnClickListener(v -> {
            binding.imgCnacelSearch.setVisibility(View.GONE);
            binding.imgSearch.setVisibility(View.VISIBLE);
            binding.imgCnacelSearch.setVisibility(View.GONE);
            binding.titelText.setVisibility(View.VISIBLE);
            binding.serchEdit.setVisibility(View.GONE);
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(binding.getRoot().getWindowToken(), 0);
        });


        binding.serchEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterPackage(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        try {
            if(offersModel.size() != 0){
                offersModel.clear();
            }

            if(addOnceModels.size() != 0 ){
                addOnceModels.clear();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private  void filterPackage(String key){
        filterPackageData.clear();

        try {
            if(key.equalsIgnoreCase(""))
            {
                filterPackageData.addAll(data);
            }else {
                for(int i = 0 ; i<data.size(); i++){
                    boolean flag = data.get(i).getPackageName().toLowerCase(Locale.ROOT).contains(key.toLowerCase()) || data.get(i).getCity().toLowerCase(Locale.ROOT).contains(key.toLowerCase());


                    if(flag)
                        filterPackageData.add(data.get(i));

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        binding.packgeRecycler.setLayoutManager(new LinearLayoutManager(VendorPackagesActivity.this));
        binding.packgeRecycler.setAdapter(new VendorPackageAdapterNew(VendorPackagesActivity.this,filterPackageData, imagePath));

    }

    private void getPackage() {

        ApiInterface apiInterface = RetrofitClient.getclient(VendorPackagesActivity.this);

        apiInterface.getPackage().enqueue(new Callback<GetPackageModel>() {
            @Override
            public void onResponse(@NonNull Call<GetPackageModel> call, @NonNull Response<GetPackageModel> response) {

                try {

                    if (response.code() == 200)
                        if (response.body() != null)
                            if (response.body().getResult().equalsIgnoreCase("true")) {
                                imagePath = response.body().getPath() ;
                                 data.clear();
                                for (int i =0 ; i<response.body().getData().size(); i++){
                                    if(response.body().getData().get(i).getUsers_status().equalsIgnoreCase("0")){
                                        data.add(response.body().getData().get(i));


                                    }
                                }

                                binding.packgeRecycler.setLayoutManager(new LinearLayoutManager(VendorPackagesActivity.this));
                                binding.packgeRecycler.setAdapter(new VendorPackageAdapterNew(VendorPackagesActivity.this,data, imagePath));
                                binding.progressBar.setVisibility(View.GONE);
                                Log.e("TAG", "onResponse() called with: Data Set  = [" + call + "], response = [" + response + "]");
                            }else {
                                binding.progressBar.setVisibility(View.GONE);
                                binding.animationView.setVisibility(View.VISIBLE);
                            }

                } catch (Exception e) {
                    binding.progressBar.setVisibility(View.GONE);
                    binding.animationView.setVisibility(View.VISIBLE);
                    e.getLocalizedMessage();
                }

            }

            @Override
            public void onFailure(@NonNull Call<GetPackageModel> call, @NonNull Throwable t) {
                binding.progressBar.setVisibility(View.GONE);
                binding.animationView.setVisibility(View.VISIBLE);
                Log.e("TAG", "onFailure() called with: call = [" + call + "], t = [" + t.getLocalizedMessage() + "]");

            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();

        try {
            if(offersModel.size() != 0){
                offersModel.clear();
            }

            if(addOnceModels.size() != 0 ){
                addOnceModels.clear();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}