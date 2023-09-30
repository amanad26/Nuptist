package com.Nuptist;

import static com.Nuptist.RetrofitApis.BaseUrls.IMAGE_URL;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.Nuptist.Models.OfferProductModel;
import com.Nuptist.Models.OfferServiceModel;
import com.Nuptist.Models.ServiceOfferModel;
import com.Nuptist.Models.ServiceProductModel;
import com.Nuptist.RetrofitApis.ApiInterface;
import com.Nuptist.RetrofitApis.RetrofitClient;
import com.Nuptist.databinding.ActivityCreateOfferServiceBinding;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateOfferServiceActivity extends AppCompatActivity {

    ImageView back;

    ActivityCreateOfferServiceBinding binding;
    String type = "";
    String id = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCreateOfferServiceBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        type = getIntent().getStringExtra("type");
        id = getIntent().getStringExtra("id");

        if (type.equalsIgnoreCase("0"))
            getAddServiceDetails(id);
        else if (type.equalsIgnoreCase("1"))
            getAddProductDetails(id);
        else if (type.equalsIgnoreCase("2"))
            getOfferServiceDetails(id);
        else getOfferProductDetails();


        binding.back.setOnClickListener(view -> onBackPressed());


    }

    private void getOfferProductDetails() {
        ApiInterface apiInterface = RetrofitClient.getclient(CreateOfferServiceActivity.this);
        apiInterface.getOfferProduct(
                "",
                "",
                "",
                id
        ).enqueue(new Callback<OfferProductModel>() {
            @Override
            public void onResponse(@NonNull Call<OfferProductModel> call, @NonNull Response<OfferProductModel> response) {


                if (response.code() == 200 && response.body() != null)
                    if (response.body().getResult().equalsIgnoreCase("true")) {
                        if (response.body().getOfferProductData() != null) {
                            OfferProductModel.OfferProductData data = response.body().getOfferProductData();
                            binding.serviceName.setText(data.getProductName());
                            binding.maineService1.setText(data.getMainProduct1());
                            binding.maineService2.setText(data.getMainProduct2());
                            binding.maineService3.setText(data.getMainProduct3());
                            binding.serviceDescription.setText(data.getDescription());
                            binding.productLinaer.setVisibility(View.VISIBLE);
                            binding.serviceLinear.setVisibility(View.GONE);
                            binding.productMainCat.setText(data.getOfferDetailsId());
                            binding.productQunt.setText(data.getMaterialQty());
                            binding.productPrice.setText(data.getRecommendedPrice());
                            binding.productUnit.setText(data.getUnit());
                            Picasso.get().load(IMAGE_URL+data.getImage()).into(binding.serviceProductImage);
                        }
                    }


            }

            @Override
            public void onFailure(@NonNull Call<OfferProductModel> call, @NonNull Throwable t) {
                Log.e("TAG", "onFailure() called with: call = [" + call + "], t = [" + t + "]");
            }
        });
    }

    private void getOfferServiceDetails(String id) {

        ApiInterface apiInterface = RetrofitClient.getclient(CreateOfferServiceActivity.this);
        apiInterface.getOfferService(
                "",
                "",
                id,
                ""
        ).enqueue(new Callback<OfferServiceModel>() {
            @Override
            public void onResponse(@NonNull Call<OfferServiceModel> call, @NonNull Response<OfferServiceModel> response) {

                if (response.code() == 200 && response.body() != null)
                    if (response.body().getResult().equalsIgnoreCase("true")) {
                        if (response.body().getOfferServiceData() != null) {
                            OfferServiceModel.OfferServiceData data = response.body().getOfferServiceData();
                            binding.serviceName.setText(data.getName());
                            binding.maineService1.setText(data.getMainService1());
                            binding.maineService2.setText(data.getMainService2());
                            binding.maineService3.setText(data.getMainService3());
                            binding.serviceDescription.setText(data.getDescription());
                            binding.productLinaer.setVisibility(View.GONE);
                            binding.serviceLinear.setVisibility(View.VISIBLE);
                            binding.productPrice.setText(data.getRecommendedPrice());
                            binding.productMainCat.setText(data.getOfferDetailsId());
//                        binding.productQunt.setText(data.getMaterialQty());
//                        binding.productUnit.setText(data.getUnit());
                            binding.serviceNumberOfWorker.setText(data.getNoOfWorkers());
                            Picasso.get().load(IMAGE_URL+data.getImage()).into(binding.serviceProductImage);
                        }
                    }

            }

            @Override
            public void onFailure(@NonNull Call<OfferServiceModel> call, @NonNull Throwable t) {
                Log.e("TAG", "onFailure() called with: call = [" + call + "], t = [" + t + "]");
            }
        });

    }

    private void getAddProductDetails(String id) {
        ApiInterface apiInterface = RetrofitClient.getclient(CreateOfferServiceActivity.this);
        apiInterface.getServiceProduct(
                "",
                id,
                "",
                ""
        ).enqueue(new Callback<ServiceOfferModel>() {
            @Override
            public void onResponse(@NonNull Call<ServiceOfferModel> call, @NonNull Response<ServiceOfferModel> response) {

                if (response.code() == 200 && response.body() != null)
                    if (response.body().getResult().equalsIgnoreCase("true")) {
                        if (response.body().getData() != null) {
                            ServiceOfferModel.AddProductData data = response.body().getData();
                            binding.serviceName.setText(data.getProductName());
                            binding.maineService1.setText(data.getMainProduct1());
                            binding.maineService2.setText(data.getMainProduct2());
                            binding.maineService3.setText(data.getMainProduct3());
                            binding.serviceDescription.setText(data.getDescription());
                            binding.productLinaer.setVisibility(View.VISIBLE);
                            binding.serviceLinear.setVisibility(View.GONE);
                            binding.productQunt.setText(data.getMaterialQty());
                            binding.productPrice.setText(data.getRecommendedPrice());
                            binding.productUnit.setText(data.getUnit());
                            binding.productMainCat.setText(data.getAddOnceDetailsId());
                            Picasso.get().load(IMAGE_URL+data.getImage()).into(binding.serviceProductImage);

                        }
                    }


            }

            @Override
            public void onFailure(@NonNull Call<ServiceOfferModel> call, @NonNull Throwable t) {
                Log.e("TAG", "onFailure() called with: call = [" + call + "], t = [" + t + "]");
            }
        });
    }

    private void getAddServiceDetails(String id) {
        ApiInterface apiInterface = RetrofitClient.getclient(CreateOfferServiceActivity.this);
        apiInterface.getServiceOffer(
                id,
                "",
                "",
                ""
        ).enqueue(new Callback<ServiceProductModel>() {
            @Override
            public void onResponse(@NonNull Call<ServiceProductModel> call, @NonNull Response<ServiceProductModel> response) {
                if (response.code() == 200 && response.body() != null)
                    if (response.body().getResult().equalsIgnoreCase("true")) {
                        ServiceProductModel.AddService data = response.body().getData();
                        if (response.body().getData() != null) {
                            binding.serviceName.setText(data.getName());
                            binding.maineService1.setText(data.getMainService1());
                            binding.maineService2.setText(data.getMainService2());
                            binding.maineService3.setText(data.getMainService3());
                            binding.serviceDescription.setText(data.getDescription());
                            binding.productLinaer.setVisibility(View.GONE);
                            binding.serviceLinear.setVisibility(View.VISIBLE);
                            binding.productPrice.setText(data.getRecommendedPrice());
//                          binding.productQunt.setText(data.getMaterialQty());
//                          binding.productUnit.setText(data.getUnit());
                            binding.serviceNumberOfWorker.setText(data.getNoOfWorkers());
                            binding.productMainCat.setText(data.getAddOnceDetailsId());
                            Picasso.get().load(IMAGE_URL+data.getImage()).into(binding.serviceProductImage);

                        }
                    }

            }

            @Override
            public void onFailure(@NonNull Call<ServiceProductModel> call, @NonNull Throwable t) {
                Log.e("TAG", "onFailure() called with: call = [" + call + "], t = [" + t + "]");
            }
        });
    }


}