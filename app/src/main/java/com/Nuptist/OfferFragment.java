package com.Nuptist;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.Nuptist.calender.AddOnceNew.FinalAddonsNewModel;
import com.Nuptist.calender.AddOnceNew.OfferFinalModel;
import com.Nuptist.Models.PackageDetailsModel;
import com.Nuptist.RetrofitApis.ApiInterface;
import com.Nuptist.RetrofitApis.RetrofitClient;
import com.Nuptist.Session.Session;
import com.Nuptist.adpaters.OfferAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class OfferFragment extends Fragment {
    View root;

    LinearLayout photography;
    PackageDetailsModel data;
    RecyclerView offter_recler ;
    ProgressBar progressBar ;
    Session session ;
    private OfferAdapter addOnceNewOneAdapter;


    public OfferFragment(PackageDetailsModel data) {
       this.data = data ;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_offer, container, false);
       // photography = root.findViewById(R.id.photography);
        offter_recler = root.findViewById(R.id.offter_recler);
        progressBar = root.findViewById(R.id.progress_bar_new);

        session = new Session(getContext());
//
//        photography.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(getContext(),PhotographyOfferActivity.class);
//                startActivity(intent);
//            }
//        });
//
//

          getPackageFinalAddOnce();

//        offter_recler.setLayoutManager(new LinearLayoutManager(getContext()));
//        offter_recler.setAdapter(new OfferAdapter(getContext(),data.getData().getOffer(),"path"));

        return root;
    }


    public void getPackageFinalAddOnce()
    {
        ApiInterface apiInterface = RetrofitClient.getclient(getContext());

        apiInterface.getFinalAllAddOnce(data.getData().getPackages().getId(),session.getUserId_Vendor()).enqueue(new Callback<FinalAddonsNewModel>() {
            //        apiInterface.getAllAddOnce(data.getData().getPackages().getId()).enqueue(new Callback<AddOncesModelNew>() {
            @Override
            public void onResponse(@NonNull Call<FinalAddonsNewModel> call, @NonNull Response<FinalAddonsNewModel> response) {

                try {
                    if (response.code() == 200)
                        if (response.body() != null)
                            if (response.body().getResult().equalsIgnoreCase("true")) {

                                List<FinalAddonsNewModel.FinalAddOnsData.ServiceOfferDetailsId> serviceIdList = response.body().getData().getServiceOfferDetailsId();
                                List<FinalAddonsNewModel.FinalAddOnsData.ProductOfferDetailsId> productIds = response.body().getData().getProductOfferDetailsId();

                                List<OfferFinalModel> filterdFinalofferModels = new ArrayList<>();


                                if (serviceIdList != null) {
                                    for (int i = 0; i < serviceIdList.size(); i++) {
                                        String currentId = serviceIdList.get(i).getOfferDetailsId();
                                        String currentTitle = serviceIdList.get(i).getName();
                                        FinalAddonsNewModel.FinalAddOnsData.ServiceOfferDetailsId currentItem = serviceIdList.get(i);
                                        if (filterdFinalofferModels.size() != 0) {
                                            boolean flag = true;
                                            for (int k = 0; k < filterdFinalofferModels.size(); k++) {
                                                if (filterdFinalofferModels.get(k).getId().equalsIgnoreCase(currentId)) {
                                                    flag = false;

                                                    filterdFinalofferModels.get(k).getServiceOfferDetailsIds().add(currentItem);
                                                    break;
                                                }

                                            }
                                            if (flag) {
                                                OfferFinalModel model = new OfferFinalModel(
                                                        currentTitle,
                                                        currentId
                                                );

                                                model.getServiceOfferDetailsIds().add(currentItem);

                                                filterdFinalofferModels.add(model);
                                            }
                                        } else {
                                            OfferFinalModel model = new OfferFinalModel(
                                                    currentTitle,
                                                    currentId
                                            );

                                            model.getServiceOfferDetailsIds().add(currentItem);

                                            filterdFinalofferModels.add(model);
                                        }
                                    }
                                }


                                if (productIds != null) {
                                    for (int i = 0; i < productIds.size(); i++) {
                                        String currentId = productIds.get(i).getOfferDetailsId();
                                        String currentTitle = productIds.get(i).getName();
                                        FinalAddonsNewModel.FinalAddOnsData.ProductOfferDetailsId currentItem = productIds.get(i);
                                        if (filterdFinalofferModels.size() != 0) {
                                            boolean flag = true;
                                            for (int k = 0; k < filterdFinalofferModels.size(); k++) {
                                                if (filterdFinalofferModels.get(k).getId().equalsIgnoreCase(currentId)) {
                                                    flag = false;

                                                    filterdFinalofferModels.get(k).getProductOfferDetailsIds().add(currentItem);
                                                    break;
                                                }

                                            }
                                            if (flag) {
                                                OfferFinalModel model = new OfferFinalModel(
                                                        currentTitle,
                                                        currentId
                                                );

                                                model.getProductOfferDetailsIds().add(currentItem);

                                                filterdFinalofferModels.add(model);
                                            }
                                        } else {
                                            OfferFinalModel model = new OfferFinalModel(
                                                    currentTitle,
                                                    currentId
                                            );

                                            model.getProductOfferDetailsIds().add(currentItem);

                                            filterdFinalofferModels.add(model);
                                        }
                                    }
                                }


                                if(filterdFinalofferModels.size() != 0){
                                    for (int i = 0 ; i<filterdFinalofferModels.size(); i++){
                                        Log.e("TAG", "onResponse()  Filterd Model List Postion "+i+" And data "+filterdFinalofferModels.toString());
                                    }
                                }

                                addOnceNewOneAdapter = new OfferAdapter(getContext(), filterdFinalofferModels,data.getData().getPackages().getId() );

                                offter_recler.setLayoutManager(new LinearLayoutManager(getContext()));
                                offter_recler.setAdapter(addOnceNewOneAdapter);
                                addOnceNewOneAdapter.notifyDataSetChanged();
                                progressBar.setVisibility(View.GONE);
                            }else {
                                progressBar.setVisibility(View.GONE);
                            }
                } catch (Exception e) {
                    progressBar.setVisibility(View.GONE);
                    e.printStackTrace();
                }


            }

            @Override
            public void onFailure(@NonNull Call<FinalAddonsNewModel> call, @NonNull Throwable t) {
                progressBar.setVisibility(View.GONE);
                Log.d("TAG", "onFailure() called with: call = [" + call + "], t = [" + t.getLocalizedMessage() + "]");
            }
        });



    }


    @Override
    public void onResume() {
        super.onResume();
        getPackageFinalAddOnce();
    }
}