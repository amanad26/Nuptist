package com.Nuptist;

import static com.Nuptist.DetailsActivity.packageDetailsInterface;
import static com.Nuptist.adpaters.AddOnsAdapterNew2.addOnceModels;
import static com.Nuptist.adpaters.OfferAdapter2.offersModel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.GridLayoutManager;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.Nuptist.calender.AddOnceNew.FinalAddonsNewModel;
import com.Nuptist.Models.BookingModels.BookingAddOnceModel;
import com.Nuptist.Models.BookingModels.BookingOffersModel;
import com.Nuptist.Models.VendorBidsModel;
import com.Nuptist.Models.VendorInterface;
import com.Nuptist.RetrofitApis.ApiInterface;
import com.Nuptist.RetrofitApis.RetrofitClient;
import com.Nuptist.adpaters.VendorBidsAdapter;
import com.Nuptist.databinding.ActivityPhotographyOfferBinding;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PhotographyOfferActivity extends AppCompatActivity implements VendorInterface {
    TextView booking_now;
    ImageView back;
    ActivityPhotographyOfferBinding binding;
    String offer_product_id = "", offer_service_id = "", addon_product_id = "", addon_service_id = "", name = "", package_id = "";

    FinalAddonsNewModel.FinalAddOnsData.ServiceId.Data__1 addondServiceData = null;
    FinalAddonsNewModel.FinalAddOnsData.ServiceOfferDetailsId.Data__3 offerServiceData = null;
    FinalAddonsNewModel.FinalAddOnsData.ProductId.Data__2 addondProductData = null;
    FinalAddonsNewModel.FinalAddOnsData.ProductOfferDetailsId.Data__4 offerProductData = null;
    private  List<VendorBidsModel.VendorBidData> vendorListData  = new ArrayList<>();
    private  List<VendorBidsModel.VendorBidData> filterdVendorListData  = new ArrayList<>();
    private  VendorBidsAdapter vendorBidsAdapter ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPhotographyOfferBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        Window window = getWindow();
        window.setStatusBarColor(getResources().getColor(R.color.white));
        binding.back.setOnClickListener(v -> finish());


        if (getIntent() != null) {

            if (getIntent().getStringExtra("addon_product_id") != null) {
                addon_product_id = getIntent().getStringExtra("addon_product_id");

                if (getIntent().getSerializableExtra("addon_product_data") != null)
                    addondProductData = (FinalAddonsNewModel.FinalAddOnsData.ProductId.Data__2) getIntent().getSerializableExtra("addon_product_data");
            } else addon_product_id = "";


            if (getIntent().getStringExtra("offer_product_id") != null) {
                offer_product_id = getIntent().getStringExtra("offer_product_id");
                 if(getIntent().getSerializableExtra("offer_product_data") != null)
                     offerProductData = (FinalAddonsNewModel.FinalAddOnsData.ProductOfferDetailsId.Data__4) getIntent().getSerializableExtra("offer_product_data");

            } else
                offer_product_id = "";

            if (getIntent().getStringExtra("addon_service_id") != null) {
                addon_service_id = getIntent().getStringExtra("addon_service_id");

                if (getIntent().getSerializableExtra("addon_service_data") != null)
                    addondServiceData = (FinalAddonsNewModel.FinalAddOnsData.ServiceId.Data__1) getIntent().getSerializableExtra("addon_service_data");
            } else
                addon_service_id = "";

            if (getIntent().getStringExtra("offer_service_id") != null) {
                offer_service_id = getIntent().getStringExtra("offer_service_id");

                if(getIntent().getSerializableExtra("offer_service_data") != null)
                offerServiceData = (FinalAddonsNewModel.FinalAddOnsData.ServiceOfferDetailsId.Data__3) getIntent().getSerializableExtra("offer_service_data");
            } else
                offer_service_id = "";


            name = getIntent().getStringExtra("name");
            package_id = getIntent().getStringExtra("p_id");

            Log.e("TAG", "onCreate() called with: addon_product_id = [" + addon_product_id + "]");
            Log.e("TAG", "onCreate() called with: offer_product_id = [" + offer_product_id + "]");
            Log.e("TAG", "onCreate() called with: addon_service_id = [" + addon_service_id + "]");
            Log.e("TAG", "onCreate() called with: offer_service_id = [" + offer_service_id + "]");

            binding.title.setText(name);

            getVendorBidsList();

            binding.bookingNow.setOnClickListener(v -> {
                if(addOnceModels.size() != 0)
                    Log.e("TAG", "onClick() called with: v = [" + addOnceModels.toString() + "]");

                finish();
            });


            binding.searchEdit.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    filterVendors(charSequence.toString());
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });

        }


    }


    private void getVendorBidsList() {

        ApiInterface apiInterface = RetrofitClient.getclient(PhotographyOfferActivity.this);
        apiInterface.getAcceptedVendors(
                package_id,
                addon_service_id,
                addon_product_id,
                offer_service_id,
                offer_product_id
        ).enqueue(new Callback<VendorBidsModel>() {
            @Override
            public void onResponse(@NonNull Call<VendorBidsModel> call, @NonNull Response<VendorBidsModel> response) {

                if (response.code() == 200)
                    if (response.body() != null)
                        if (response.body().getResult().equalsIgnoreCase("true")) {
                            vendorListData.clear();

                            if (response.body().getData().size() != 0 ) {
                                vendorListData.addAll(response.body().getData());
                                binding.vendorsRecycler.setLayoutManager(new GridLayoutManager(PhotographyOfferActivity.this, 2));
                                vendorBidsAdapter =  new VendorBidsAdapter(PhotographyOfferActivity.this,vendorListData,PhotographyOfferActivity.this);
                                binding.vendorsRecycler.setAdapter(vendorBidsAdapter);
                                vendorBidsAdapter.notifyDataSetChanged();
                            }

                            binding.progressBar.setVisibility(View.GONE);
                        } else {
                            binding.progressBar.setVisibility(View.GONE);
                            Toast.makeText(PhotographyOfferActivity.this, "No vendors Found...", Toast.LENGTH_SHORT).show();
                        }
            }

            @Override
            public void onFailure(@NonNull Call<VendorBidsModel> call, @NonNull Throwable t) {
                Log.e("TAG", "onFailure() called with: call = [" + call + "], t = [" + t + "]");
                binding.progressBar.setVisibility(View.GONE);
            }
        });


    }

    @Override
    public void getPrice(String price, String name, String vendor_id) {
        Log.e("TAG", "getPrice() called with: price = [" + price + "], name = [" + name + "], vendor_id = [" + vendor_id + "]");

        if (addondServiceData != null) {

            Log.e("TAG", "addondServiceData.getId()"+addondServiceData.getId());
            addOnceModels.add(new BookingAddOnceModel(addon_service_id
                    , addondServiceData.getName()
                    , price
                    , price
                    , "0"
                    , addondServiceData.getId()
                    ,vendor_id
                    ,name
            ));
            packageDetailsInterface.onAddonsAdd();

        }
        else if(addondProductData != null){

            addOnceModels.add(new BookingAddOnceModel(addon_product_id
                    , addondProductData.getProductName()
                    , price
                    , price
                    ,addondProductData.getId()
                    , "0"
                    ,vendor_id
                    ,name
            ));
            packageDetailsInterface.onAddonsAdd();

        }
        else if(offerServiceData != null){
            offersModel.add(new BookingOffersModel(offer_service_id
                    ,price
                    ,offerServiceData.getName()
                    ,"0"
                    ,offerServiceData.getId()
                    ,vendor_id
                    ,name

            ));
            packageDetailsInterface.onOfferAdd();
        }
        else if(offerProductData != null){
            offersModel.add(new BookingOffersModel(offer_product_id
                    ,price
                    ,offerProductData.getProductName()
                    ,offerProductData.getId()
                    ,"0"
                    ,vendor_id
                    ,name

            ));
            packageDetailsInterface.onOfferAdd();
        }

    }


    private  void filterVendors(String key){
        filterdVendorListData.clear();

        if(key.equalsIgnoreCase(""))
            filterdVendorListData.addAll(vendorListData);
        else{

            for (int i = 0; i < vendorListData.size(); i++) {
                if(vendorListData.get(i).getUserName().toLowerCase().contains(key.toLowerCase()))
                    filterdVendorListData.add(vendorListData.get(i));
              }

        }

        binding.vendorsRecycler.setLayoutManager(new GridLayoutManager(PhotographyOfferActivity.this, 2));
        vendorBidsAdapter =  new VendorBidsAdapter(PhotographyOfferActivity.this,filterdVendorListData,PhotographyOfferActivity.this);

        binding.vendorsRecycler.setAdapter(vendorBidsAdapter);
        vendorBidsAdapter.notifyDataSetChanged();


    }

}