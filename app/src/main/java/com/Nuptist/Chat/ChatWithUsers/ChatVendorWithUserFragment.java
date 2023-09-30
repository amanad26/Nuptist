package com.Nuptist.Chat.ChatWithUsers;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.Nuptist.R;
import com.Nuptist.RetrofitApis.ApiInterface;
import com.Nuptist.RetrofitApis.RetrofitClient;
import com.Nuptist.Session.Session;
import com.Nuptist.databinding.FragmentChatVendorWithUserBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatVendorWithUserFragment extends Fragment {

    FragmentChatVendorWithUserBinding binding ;
    Session session ;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentChatVendorWithUserBinding.inflate(getLayoutInflater());

        session = new Session(getContext());

        getVendorsList();

        return  binding.getRoot();


    }

    private  void getVendorsList(){

        ApiInterface apiInterface = RetrofitClient.getclient(getContext());

        apiInterface.getUsersList(session.getUserId_Vendor()).enqueue(new Callback<ChatListUserModel>() {
            @Override
            public void onResponse(@NonNull Call<ChatListUserModel> call, @NonNull Response<ChatListUserModel> response) {

                if(response.code() == 200)
                    if(response.body() != null)
                        if(response.body().getResult().equalsIgnoreCase("true")){

                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
                            linearLayoutManager.setReverseLayout(true);
                            linearLayoutManager.setStackFromEnd(true);

                            binding.vendorsRecycler.setLayoutManager(linearLayoutManager);
                            binding.vendorsRecycler.setAdapter(new ChatUsersListAdapter(getContext(),response.body().getData(),"vendor"));
                            binding.progress.setVisibility(View.GONE);
                        }else {
                            binding.progress.setVisibility(View.GONE);
                            Toast.makeText(getContext(), "No Data Found", Toast.LENGTH_SHORT).show();
                        }

            }

            @Override
            public void onFailure(@NonNull Call<ChatListUserModel> call, @NonNull Throwable t) {
                binding.progress.setVisibility(View.GONE);
                Toast.makeText(getContext(), "No Data Found", Toast.LENGTH_SHORT).show();
                Log.e("TAG", "onFailure() called with: call = [" + call + "], t = [" + t + "]");
            }
        });


    }
}