package com.Nuptist;

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
import android.widget.Toast;

import com.Nuptist.Models.GetPackageModel;
import com.Nuptist.RetrofitApis.ApiInterface;
import com.Nuptist.RetrofitApis.RetrofitClient;
import com.Nuptist.adpaters.PackageListAdapter;
import com.Nuptist.databinding.ActivityCreatePackageDetailsBinding;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreatePackageDetailsActivity extends AppCompatActivity {

    private ActivityCreatePackageDetailsBinding binding;
    private List<GetPackageModel.PackageData> packageData = new ArrayList<>();
    private List<GetPackageModel.PackageData> filterPackageData = new ArrayList<>();
    private String imgaePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCreatePackageDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getPackage();

        binding.back.setOnClickListener(view -> onBackPressed());

        binding.imgSearch.setOnClickListener(v -> {
            binding.imgCnacelSearch.setVisibility(View.VISIBLE);
            binding.imgSearch.setVisibility(View.GONE);
            binding.imgCnacelSearch.setVisibility(View.VISIBLE);
            binding.titelText.setVisibility(View.GONE);
            binding.serchEdit.setVisibility(View.VISIBLE);
            binding.serchEdit.requestFocus();
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
        });

        binding.imgCnacelSearch.setOnClickListener(v -> {
            binding.imgCnacelSearch.setVisibility(View.GONE);
            binding.imgSearch.setVisibility(View.VISIBLE);
            binding.imgCnacelSearch.setVisibility(View.GONE);
            binding.titelText.setVisibility(View.VISIBLE);
            binding.serchEdit.setVisibility(View.GONE);
            ///// hideKeyboard();
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

    }

    private void filterPackage(String key) {
        filterPackageData.clear();

        if (key.equalsIgnoreCase("")) {
            filterPackageData.addAll(packageData);
        } else {
            for (int i = 0; i < packageData.size(); i++) {
                boolean flag = packageData.get(i).getPackageName().toLowerCase().contains(key.toLowerCase()) || packageData.get(i).getAddress().toLowerCase().contains(key.toLowerCase());


                if (flag)
                    filterPackageData.add(packageData.get(i));

            }
        }


        binding.allPackageRecycler.setLayoutManager(new LinearLayoutManager(CreatePackageDetailsActivity.this));
        binding.allPackageRecycler.setAdapter(new PackageListAdapter(CreatePackageDetailsActivity.this, filterPackageData, imgaePath, 3));
    }

    private void getPackage() {

        ApiInterface apiInterface = RetrofitClient.getclient(CreatePackageDetailsActivity.this);

        apiInterface.getPackage().enqueue(new Callback<GetPackageModel>() {
            @Override
            public void onResponse(@NonNull Call<GetPackageModel> call, @NonNull Response<GetPackageModel> response) {

                try {

                    if (response.code() == 200) {
                        if (response.body() != null)
                            if (response.body().getResult().equalsIgnoreCase("true")) {

                                for (int i = 0; i < response.body().getData().size(); i++) {

                                    if (response.body().getData().get(i).getUsers_status().equalsIgnoreCase("1"))
                                        packageData.add(response.body().getData().get(i));

                                }

                                if (packageData.size() != 0) {
                                    binding.allPackageRecycler.setLayoutManager(new LinearLayoutManager(CreatePackageDetailsActivity.this));
                                    binding.allPackageRecycler.setAdapter(new PackageListAdapter(CreatePackageDetailsActivity.this, packageData, response.body().getPath(), 3));
                                    binding.pProgress.setVisibility(View.GONE);
                                    imgaePath = response.body().getPath();
                                } else {
                                    binding.animationView.setVisibility(View.VISIBLE);
                                    binding.pProgress.setVisibility(View.GONE);
                                    Toast.makeText(CreatePackageDetailsActivity.this, "No Data Found", Toast.LENGTH_SHORT).show();
                                }

                            } else {
                                binding.animationView.setVisibility(View.VISIBLE);
                                binding.pProgress.setVisibility(View.GONE);
                                Toast.makeText(CreatePackageDetailsActivity.this, "No Packages Found", Toast.LENGTH_SHORT).show();
                            }

                    }


                } catch (Exception e) {
                    binding.animationView.setVisibility(View.VISIBLE);
                    binding.pProgress.setVisibility(View.GONE);
                    e.getLocalizedMessage();
                }

            }

            @Override
            public void onFailure(@NonNull Call<GetPackageModel> call, @NonNull Throwable t) {
                binding.animationView.setVisibility(View.VISIBLE);
                binding.pProgress.setVisibility(View.GONE);
                Log.e("TAG", "onFailure() called with: call = [" + call + "], t = [" + t.getLocalizedMessage() + "]");

            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();


        if (addOnceModels.size() != 0) {
            addOnceModels.clear();
        }

        if (offersModel.size() != 0) {
            offersModel.clear();
        }

    }
}