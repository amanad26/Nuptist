package com.Nuptist.VendorPackages.Bids.OffersBid;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.Nuptist.Models.MyOffersBidModel;
import com.Nuptist.RetrofitApis.ApiInterface;
import com.Nuptist.RetrofitApis.RetrofitClient;
import com.Nuptist.Session.Session;
import com.Nuptist.databinding.FragmentOffersAllBidsBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class OffersAllBidsFragment extends Fragment {

    FragmentOffersAllBidsBinding binding ;
    Session session ;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentOffersAllBidsBinding.inflate(getLayoutInflater());
        if (getContext() != null) {
            session = new Session(getContext());
        }


        getMyAllOffersBids();

        return  binding.getRoot();
    }

    private  void getMyAllOffersBids(){
        ApiInterface apiInterface = RetrofitClient.getclient(getContext());

        apiInterface.getMyOfferAllBids(session.getUserId_Vendor()).enqueue(new Callback<MyOffersBidModel>() {
            @Override
            public void onResponse(@NonNull Call<MyOffersBidModel> call, @NonNull Response<MyOffersBidModel> response) {

                try {
                    if(response.code() == 200)
                        if(response.body() != null )
                            if(response.body().getResult().equalsIgnoreCase("true")){
                                binding.animationView.setVisibility(View.GONE);
                                binding.myBidRecy.setVisibility(View.VISIBLE);

                                binding.myBidRecy.setLayoutManager(new LinearLayoutManager(getContext()));
                                binding.myBidRecy.setAdapter(new MyOfferBidAdapter(getContext(),response.body().getData()));

                            }
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onFailure(@NonNull Call<MyOffersBidModel> call, @NonNull Throwable t) {
                Log.e("TAG", "onFailure() called with: call = [" + call + "], t = [" + t.getLocalizedMessage() + "]");
            }
        });

    }
}