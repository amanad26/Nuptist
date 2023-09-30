package com.Nuptist.Chat;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.Nuptist.Chat.ChatWithUsers.ChatListUserModel;
import com.Nuptist.Chat.ChatWithUsers.ChatUsersListAdapter;
import com.Nuptist.RetrofitApis.ApiInterface;
import com.Nuptist.RetrofitApis.RetrofitClient;
import com.Nuptist.Session.Session;
import com.Nuptist.databinding.FragmentChatWithVendorsBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ChatWithVendorsFragment extends Fragment {

    FragmentChatWithVendorsBinding binding  ;
    Session session ;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentChatWithVendorsBinding.inflate(getLayoutInflater());

        session = new Session(getContext());

        getVendorsList();

        return  binding.getRoot();
    }

    private  void getVendorsList(){

        ApiInterface apiInterface = RetrofitClient.getclient(getContext());

        apiInterface.getVendorsList(session.getUserId()).enqueue(new Callback<ChatListUserModel>() {
            @Override
            public void onResponse(@NonNull Call<ChatListUserModel> call, @NonNull Response<ChatListUserModel> response) {

                if(response.code() == 200)
                    if(response.body() != null)
                        if(response.body().getResult().equalsIgnoreCase("true")){
                            binding.vendorsRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
                            binding.vendorsRecycler.setAdapter(new ChatUsersListAdapter(getContext(),response.body().getData(), "user"));
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