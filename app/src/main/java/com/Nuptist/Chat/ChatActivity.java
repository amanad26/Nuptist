package com.Nuptist.Chat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;


import com.Nuptist.HomeActivity;
import com.Nuptist.Models.LogOutModel;
import com.Nuptist.ProfileActivity;
import com.Nuptist.RetrofitApis.ApiInterface;
import com.Nuptist.RetrofitApis.RetrofitClient;
import com.Nuptist.Session.Session;
import com.Nuptist.VendorProfileActivity;
import com.Nuptist.databinding.ActivityChatBinding;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatActivity extends AppCompatActivity {

    ActivityChatBinding binding;

     String senderRoom;
     String reciverRoom;
     String senderId, reciverid = "1";

     String date, time;
     Session session;
     FirebaseDatabase database;
     ChatAdapter chatAdapter;
     DatabaseReference reference;
     ChatAdapterNew adapterNew;

     String message_id;
     ArrayList<MessageModel> messageModelsList = new ArrayList<>();
     ArrayList<ChatModelNew> chatModel = new ArrayList<>();
     String items[] = {"Users","Admin"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChatBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        session = new Session(ChatActivity.this);

        senderId = session.getUserId();

        database = FirebaseDatabase.getInstance();

        reference = database.getReference().child("chat");

        senderRoom = senderId + reciverid;
        reciverRoom = reciverid + senderId;

        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter2 = new SimpleDateFormat("HH:mm");
        Date d = new Date();
        time = formatter2.format(d.getTime());

        Locale locale = new Locale("fr", "FR");
        DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.DEFAULT, locale);
        date = dateFormat.format(d);

       // getAllMessages();

        chatAdapter = new ChatAdapter(this, messageModelsList);
        Log.e("TAG", "onCreate() called with: savedInstanceState = [" + date + "]");
        Log.e("TAG", "onCreate() called with: Time = [" + formatter2.format(d.getTime()) + "]");

        binding.sendMessage.setOnClickListener(view -> {
            if (!binding.message.getText().toString().equalsIgnoreCase("")){

               /// sendMessage();
            }
        });



        String type = "";
        if (session.getUserType().equalsIgnoreCase("Vendor")) {

            if(session.getIsPersonal()){
                type = "user";
            }else {
                type = "vendor";
            }

        } else {
            type = "user";
        }


        ChatPageAdapter adapter = new ChatPageAdapter(ChatActivity.this, type);
        binding.viewPager.setAdapter(adapter);

        new TabLayoutMediator(binding.tabLayout, binding.viewPager, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                tab.setText(items[position]);
            }
        }).attach();


//        adapterNew = new ChatAdapterNew(ChatActivity.this, chatModel);
//        LinearLayoutManager linearLayout = new LinearLayoutManager(ChatActivity.this);
//        linearLayout.setOrientation(RecyclerView.VERTICAL);
//        linearLayout.setStackFromEnd(true);
//        binding.chatRecler.setLayoutManager(linearLayout);
//        binding.chatRecler.setAdapter(adapterNew);
//        binding.back.setOnClickListener(v -> onBackPressed());

    }

//    private void getAllMessages() {
//
//        database.getReference().child("chat").child(reciverRoom)
//                .addValueEventListener(new ValueEventListener() {
//                    @SuppressLint("NotifyDataSetChanged")
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//                        chatModel.clear();
//                        for (DataSnapshot snapshot1 : snapshot.getChildren()) {
//                            ChatModelNew messageModel = snapshot1.getValue(ChatModelNew.class);
//                            chatModel.add(messageModel);
//                        }
//                         adapterNew.notifyDataSetChanged();
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//
//                    }
//                });
//    }
//
//    private void sendSmsToAdmin(String message) {
//
//        ApiInterface apiInterface = RetrofitClient.getclient(ChatActivity.this);
//
//        apiInterface.sendSmsToAdmin(
//                session.getUserId(),
//                message
//        ).enqueue(new Callback<LogOutModel>() {
//            @Override
//            public void onResponse(@NonNull Call<LogOutModel> call, @NonNull Response<LogOutModel> response) {
//
//                try {
//                    if (response.code() == 200)
//                        if (response.body() != null) {
//                            if (response.body().getResult().equalsIgnoreCase("true")) {
//                                Log.e("TAG", "onResponse() called with: call = [" + call + "], response = [" + response.body().getMsg() + "]");
//
//                            } else {
//                                Log.e("TAG", "onResponse() called with: call = [" + call + "], response = [" + response.body().getMsg() + "]");
//                            }
//                        }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//
//            }
//
//            @Override
//            public void onFailure(@NonNull Call<LogOutModel> call, @NonNull Throwable t) {
//                Log.e("TAG", "onFailure() called with: call = [" + call + "], t = [" + t + "]");
//            }
//        });
//
//    }
//
//    private void sendMessage() {
//        message_id = reference.push().getKey();
//        Log.e("TAG", "sendMessage() called Message key" + message_id);
//
//        ChatModelNew modelNew = new ChatModelNew("Help", "", binding.message.getText().toString(), "Help", "1", "", session.getUserId(), "", time, "", "");
//
//        String message = binding.message.getText().toString();
//        binding.message.setText("");
//        reference
//                .child(reciverRoom)
//                .push()
//                .setValue(modelNew)
//                .addOnSuccessListener(unused -> {
//                    sendSmsToAdmin(message);
//                    binding.chatRecler.setAdapter(adapterNew);
//                }).addOnFailureListener(e -> Log.e("TAG", "onFailure() called with: e = [" + e.getLocalizedMessage() + "]"));
//
//
//    }

}