package com.Nuptist.VendorPackages;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.Nuptist.calender.AddOnceNew.FilterdFinalAddonsModel;
import com.Nuptist.calender.AddOnceNew.FinalAddonsNewModel;
import com.Nuptist.Models.PackageDetailsModel;
import com.Nuptist.RetrofitApis.ApiInterface;
import com.Nuptist.RetrofitApis.BidInterface;
import com.Nuptist.RetrofitApis.RetrofitClient;
import com.Nuptist.Session.Session;
import com.Nuptist.databinding.FragmentVendorAddonceBinding;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class VendorAddonceFragment extends Fragment {

    FragmentVendorAddonceBinding binding ;
    PackageDetailsModel data;
    Session session ;
    BidInterface bidInterface ;
    public VendorAddonceFragment(PackageDetailsModel data, BidInterface bidInterface) {
        this.data = data;
        this.bidInterface = bidInterface ;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding   = FragmentVendorAddonceBinding.inflate(inflater, container, false);

        session = new Session(getContext());
//        binding.addOnceRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
//        binding.addOnceRecycler.setAdapter(new AddOnceAdapterVendor(getContext(),data.getData().getAddOnce(),bidInterface));
//

      ////  getPackageAddOnce();

        getPackageFinalAddOnce();
        return  binding.getRoot();

    }

/*
    public void getPackageAddOnce() {


        ApiInterface apiInterface = RetrofitClient.getclient(getContext());

        apiInterface.getAllAddOnce(data.getData().getPackages().getId()).enqueue(new Callback<AddOncesModelNew>() {
            //        apiInterface.getAllAddOnce(data.getData().getPackages().getId()).enqueue(new Callback<AddOncesModelNew>() {
            @Override
            public void onResponse(@NonNull Call<AddOncesModelNew> call, @NonNull Response<AddOncesModelNew> response) {

                try {
                    if (response.code() == 200)
                        if (response.body() != null)
                            if (response.body().getResult().equalsIgnoreCase("true")) {
                                List<AddOncesModelNew.AddOnceData.AllAddOnces> allAddOns = response.body().getData().getAddOnce();
                                ;

                                List<FinalAdOns> finalList = new ArrayList<>();
                                for (int i = 0; i < allAddOns.size(); i++) {
                                    AddOncesModelNew.AddOnceData.AllAddOnces.PackageDataInAddonce currentData = allAddOns.get(i).getData();

                                    String currentAddOnsId = currentData.getAddonceId();

                                    if (finalList.size() != 0) {
                                        boolean flag = false;
                                        for (int k = 0; k < finalList.size(); k++) {
                                            FinalAdOns currentInner = finalList.get(k);

                                            if (currentInner.getAddOnsId().equalsIgnoreCase(currentAddOnsId)) {
                                                currentInner.getAdOnsList().add(currentData);
                                                flag = true;
                                            }
                                        }
                                        if (!flag) {
                                            FinalAdOns model = new FinalAdOns();

                                            model.setAddOnsId(currentAddOnsId);
                                            model.setName(allAddOns.get(i).getName());

                                            model.getAdOnsList().add(currentData);

                                            finalList.add(model);
                                        }

                                    } else {
                                        FinalAdOns model = new FinalAdOns();

                                        model.setAddOnsId(currentAddOnsId);
                                        model.setName(allAddOns.get(i).getName());

                                        model.getAdOnsList().add(currentData);

                                        finalList.add(model);
                                    }
                                }

                                for (int i = 0; i < finalList.size(); i++) {
                                    Log.e("TAG", "onResponse() called with: Name = [" + finalList.get(i).getName() + "]," +
                                            " AddOnsId = [" + finalList.get(i).getAddOnsId() + "], " +
                                            "List.size = [" + finalList.get(i).getAdOnsList().size() + "]");
                                }

                                VendorAddOns addOnceNewOneAdapter = new VendorAddOns(getContext(),finalList, bidInterface );

                                binding.addOnceRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
                                binding.addOnceRecycler.setAdapter(addOnceNewOneAdapter);


                            }
                } catch (
                        Exception e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onFailure(@NonNull Call<AddOncesModelNew> call, @NonNull Throwable t) {
                Log.d("TAG", "onFailure() called with: call = [" + call + "], t = [" + t.getLocalizedMessage() + "]");
            }
        });


    }*/

    public void getPackageFinalAddOnce() {

        ApiInterface apiInterface = RetrofitClient.getclient(getContext());

        apiInterface.getFinalAllAddOnce(data.getData().getPackages().getId(), session.getUserId_Vendor()).enqueue(new Callback<FinalAddonsNewModel>() {
            //        apiInterface.getAllAddOnce(data.getData().getPackages().getId()).enqueue(new Callback<AddOncesModelNew>() {
            @Override
            public void onResponse(@NonNull Call<FinalAddonsNewModel> call, @NonNull Response<FinalAddonsNewModel> response) {

                try {
                    if (response.code() == 200)
                        if (response.body() != null)
                            if (response.body().getResult().equalsIgnoreCase("true")) {

                                List<FinalAddonsNewModel.FinalAddOnsData.ServiceId> serviceIdList = response.body().getData().getServiceId();
                                List<FinalAddonsNewModel.FinalAddOnsData.ProductId> productIds = response.body().getData().getProductId();

                                List<FilterdFinalAddonsModel> filterdFinalAddonsModels = new ArrayList<>();

                                if (serviceIdList != null) {
                                    for (int i = 0; i < serviceIdList.size(); i++) {
                                        String currentId = serviceIdList.get(i).getAddOnceDetailsId();
                                        String currentTitle = serviceIdList.get(i).getName();
                                        FinalAddonsNewModel.FinalAddOnsData.ServiceId currentItem = serviceIdList.get(i);
                                        if (filterdFinalAddonsModels.size() != 0) {
                                            boolean flag = true;
                                            for (int k = 0; k < filterdFinalAddonsModels.size(); k++) {
                                                if (filterdFinalAddonsModels.get(k).getId().equalsIgnoreCase(currentId)) {
                                                    flag = false;

                                                    filterdFinalAddonsModels.get(k).getServiceIdList().add(currentItem);
                                                    break;
                                                }

                                            }
                                            if (flag) {
                                                FilterdFinalAddonsModel model = new FilterdFinalAddonsModel(
                                                        currentTitle,
                                                        currentId
                                                );

                                                model.getServiceIdList().add(currentItem);

                                                filterdFinalAddonsModels.add(model);
                                            }
                                        } else {
                                            FilterdFinalAddonsModel model = new FilterdFinalAddonsModel(
                                                    currentTitle,
                                                    currentId
                                            );

                                            model.getServiceIdList().add(currentItem);

                                            filterdFinalAddonsModels.add(model);
                                        }
                                    }
                                }


                                if (productIds != null) {
                                    for (int i = 0; i < productIds.size(); i++) {
                                        String currentId = productIds.get(i).getAddOnceDetailsId();
                                        String currentTitle = productIds.get(i).getName();
                                        FinalAddonsNewModel.FinalAddOnsData.ProductId currentItem = productIds.get(i);
                                        if (filterdFinalAddonsModels.size() != 0) {
                                            boolean flag = true;
                                            for (int k = 0; k < filterdFinalAddonsModels.size(); k++) {
                                                if (filterdFinalAddonsModels.get(k).getId().equalsIgnoreCase(currentId)) {
                                                    flag = false;

                                                    filterdFinalAddonsModels.get(k).getProductIds().add(currentItem);
                                                    break;
                                                }

                                            }
                                            if (flag) {
                                                FilterdFinalAddonsModel model = new FilterdFinalAddonsModel(
                                                        currentTitle,
                                                        currentId
                                                );

                                                model.getProductIds().add(currentItem);

                                                filterdFinalAddonsModels.add(model);
                                            }
                                        } else {
                                            FilterdFinalAddonsModel model = new FilterdFinalAddonsModel(
                                                    currentTitle,
                                                    currentId
                                            );

                                            model.getProductIds().add(currentItem);

                                            filterdFinalAddonsModels.add(model);
                                        }
                                    }
                                }


                                if(filterdFinalAddonsModels.size() != 0){
                                    for (int i = 0 ; i<filterdFinalAddonsModels.size(); i++){
                                        Log.e("TAG", "onResponse()  Filterd Model List Postion "+i+" And data "+filterdFinalAddonsModels.toString());
                                    }


                                    VendorAddOns addOns  = new VendorAddOns(getContext(),bidInterface, filterdFinalAddonsModels);

                                    binding.addOnceRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
                                    binding.addOnceRecycler.setAdapter(addOns);

                                    binding.progress.setVisibility(View.GONE);
//                                    AddOnceNewOneAdapter addOnceNewOneAdapter = new AddOnceNewOneAdapter(getContext(), filterdFinalAddonsModels);
//                                    binding.addOnceRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
//                                    binding.addOnceRecycler.setAdapter(addOnceNewOneAdapter);
//
                                }


                            }
                } catch (Exception e) {
                    binding.progress.setVisibility(View.GONE);
                    e.printStackTrace();
                }


            }

            @Override
            public void onFailure(@NonNull Call<FinalAddonsNewModel> call, @NonNull Throwable t) {
                binding.progress.setVisibility(View.GONE);
                Log.d("TAG", "onFailure() called with: call = [" + call + "], t = [" + t.getLocalizedMessage() + "]");
            }
        });


    }

}