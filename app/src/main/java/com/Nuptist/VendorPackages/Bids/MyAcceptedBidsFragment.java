package com.Nuptist.VendorPackages.Bids;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.Nuptist.Models.MyBidsModel;
import com.Nuptist.R;
import com.Nuptist.RetrofitApis.ApiInterface;
import com.Nuptist.RetrofitApis.RetrofitClient;
import com.Nuptist.Session.Session;
import com.Nuptist.adpaters.MyBidAdapter;
import com.Nuptist.databinding.FragmentMyAcceptedBidsBinding;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MyAcceptedBidsFragment extends Fragment {


    Session session ;
    FragmentMyAcceptedBidsBinding binding ;
    List<MyBidsModel.BidData> myAcceptedBids = new ArrayList<>();

    public MyAcceptedBidsFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentMyAcceptedBidsBinding.inflate(getLayoutInflater());

        session = new Session(getContext());

         getMyBids();
        return  binding.getRoot();
    }


    private  void getMyBids(){

        ApiInterface apiInterface = RetrofitClient.getclient(getContext());

        apiInterface.getMyAllBids(session.getUserId_Vendor()).enqueue(new Callback<MyBidsModel>() {
            @Override
            public void onResponse(@NonNull Call<MyBidsModel> call, @NonNull Response<MyBidsModel> response) {

                try {
                    if(response.code() == 200)
                        if(response.body() != null )
                            if(response.body().getResult().equalsIgnoreCase("true")){
                                binding.animationView.setVisibility(View.GONE);
                                binding.myBidRecy.setVisibility(View.VISIBLE);

                                for (int i =0 ; i<response.body().getData().size(); i++){
                                    if (response.body().getData().get(i).getStatus().equalsIgnoreCase("1")){
                                        myAcceptedBids.add(response.body().getData().get(i));
                                    }
                                }

                              if(myAcceptedBids.size() != 0){
                                  binding.myBidRecy.setLayoutManager(new LinearLayoutManager(getContext()));
                                  binding.myBidRecy.setAdapter(new MyBidAdapter(getContext(),response.body().getData()));
                              }else {
                                  Toast.makeText(getContext(), "No Bids Package Found..", Toast.LENGTH_SHORT).show();
                                  binding.animationView.setVisibility(View.VISIBLE);
                              }

                            }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(@NonNull Call<MyBidsModel> call, @NonNull Throwable t) {
                Log.e("TAG", "onFailure() called with: call = [" + call + "], t = [" + t.getLocalizedMessage() + "]");
            }
        });

    }
}