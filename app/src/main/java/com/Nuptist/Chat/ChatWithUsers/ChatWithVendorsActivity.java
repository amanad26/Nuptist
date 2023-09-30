package com.Nuptist.Chat.ChatWithUsers;

import static com.Nuptist.Chat.SendNotification.sendNotification;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.Nuptist.Chat.ChatAdapter;
import com.Nuptist.Chat.ChatAdapterNew;
import com.Nuptist.Chat.ChatModelNew;
import com.Nuptist.Chat.MessageModel;
import com.Nuptist.Chat.SendNotification;
import com.Nuptist.Models.LogOutModel;
import com.Nuptist.Models.ProgressDialog;
import com.Nuptist.Models.VendorFcmModel;
import com.Nuptist.Models.VendorProfileModel;
import com.Nuptist.R;
import com.Nuptist.RetrofitApis.ApiInterface;
import com.Nuptist.RetrofitApis.RetrofitClient;
import com.Nuptist.Session.Session;
import com.Nuptist.databinding.ActivityChatWithVendorsBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatWithVendorsActivity extends AppCompatActivity {

    String senderRoom;
    String reciverRoom;
    String senderId, reciverid = "1", reciverImage= "", reciverName = "";

    String date, time;
    Session session;
    FirebaseDatabase database;
    ChatAdapter chatAdapter;
    DatabaseReference reference;
    ChatAdapterNew adapterNew;

    String message_id;
    ArrayList<MessageModel> messageModelsList = new ArrayList<>();
    ArrayList<ChatModelNew> chatModel = new ArrayList<>();

    ActivityChatWithVendorsBinding binding;
    ChatWithVendorsActivity activity;
    private String reciver_fcm = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChatWithVendorsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        activity = this;

        session = new Session(activity);

        if(getIntent() != null){
            reciverid = getIntent().getStringExtra("f_id");
            reciverName = getIntent().getStringExtra("f_name");
            reciverImage = getIntent().getStringExtra("f_image");


            binding.reciverName.setText(reciverName);

            if(!reciverImage.equalsIgnoreCase(""))
                Picasso.get().load(reciverImage).into(binding.reciverImage);


            getVendorFcm();

          }


        Log.e("TAG", "onCreate() called with: reciverid = [" + reciverid + "]");
        Log.e("TAG", "onCreate() called with: reciverName = [" + reciverName + "]");
        Log.e("TAG", "onCreate() called with: reciverImage = [" + reciverImage + "]");

        senderId = session.getUserId();

        database = FirebaseDatabase.getInstance();

        reference = database.getReference().child("chats");

        senderRoom = senderId + reciverid;
        reciverRoom = reciverid + senderId;

        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter2 = new SimpleDateFormat("HH:mm");
        Date d = new Date();
        time = formatter2.format(d.getTime());

        Locale locale = new Locale("fr", "FR");
        DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.DEFAULT, locale);
        date = dateFormat.format(d);

        getAllMessages();

       //  chatAdapter = new ChatAdapter(activity, messageModelsList);
        Log.e("TAG", "onCreate() called with: savedInstanceState = [" + date + "]");
        Log.e("TAG", "onCreate() called with: Time = [" + formatter2.format(d.getTime()) + "]");

        binding.sendMessage.setOnClickListener(view -> {
            if (!binding.message.getText().toString().equalsIgnoreCase(""))
                sendMessage();
        });

        adapterNew = new ChatAdapterNew(activity, chatModel);
        LinearLayoutManager linearLayout = new LinearLayoutManager(activity);
        linearLayout.setOrientation(RecyclerView.VERTICAL);
        linearLayout.setStackFromEnd(true);
        binding.chatRecler.setLayoutManager(linearLayout);
        binding.chatRecler.setAdapter(adapterNew);
        binding.back.setOnClickListener(v -> onBackPressed());
    }

    private void getVendorFcm() {

        ApiInterface apiInterface = RetrofitClient.getclient(ChatWithVendorsActivity.this);
        apiInterface.getVendorFcm(reciverid).enqueue(new Callback<VendorFcmModel>() {
            @Override
            public void onResponse(Call<VendorFcmModel> call, Response<VendorFcmModel> response) {

                if(response.code() == 200)
                if (response.body() != null && response.body().getResult().equalsIgnoreCase("true")) {
                    reciver_fcm = response.body().getData().getFcmId();
                }

            }

            @Override
            public void onFailure(Call<VendorFcmModel> call, Throwable t) {
                Log.e("TAG", "onFailure() called with: call = [" + call + "], t = [" + t + "]");
            }
        });

    }


    private void getAllMessages() {

        database.getReference().child("chats").child(reciverRoom)
                .addValueEventListener(new ValueEventListener() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        chatModel.clear();
                        for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                            ChatModelNew messageModel = snapshot1.getValue(ChatModelNew.class);
                            chatModel.add(messageModel);
                        }
                        adapterNew.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    private void sendSmsToVendor(String message) {

        ApiInterface apiInterface = RetrofitClient.getclient(activity);

        apiInterface.sendSmsToUserVendor(
                session.getUserId(),
                reciverid,
                message
        ).enqueue(new Callback<LogOutModel>() {
            @Override
            public void onResponse(@NonNull Call<LogOutModel> call, @NonNull Response<LogOutModel> response) {

                try {
                    if (response.code() == 200)
                        if (response.body() != null) {
                            if (response.body().getResult().equalsIgnoreCase("true")) {
                                Log.e("TAG", "onResponse() called with: call = [" + call + "], response = [" + response.body().getMsg() + "]");

                                if(!reciver_fcm.equalsIgnoreCase("")){
                                    Map<String, String> notiBody = new HashMap<>();
                                    Map<String, String> data = new HashMap<>();

                                    notiBody.put("title", "Nuptist");
                                    notiBody.put("body", "("+session.getUserName()+") "+message);

                                    sendNotification(reciver_fcm,notiBody,data);

                                }


                            } else {
                                Log.e("TAG", "onResponse() called with: call = [" + call + "], response = [" + response.body().getMsg() + "]");
                            }
                        }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(@NonNull Call<LogOutModel> call, @NonNull Throwable t) {
                Log.e("TAG", "onFailure() called with: call = [" + call + "], t = [" + t + "]");
            }
        });

    }

    private void sendMessage()
    {

        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter2 = new SimpleDateFormat("HH:mm");
        Date d = new Date();
        time = formatter2.format(d.getTime());

        message_id = reference.push().getKey();
        Log.e("TAG", "sendMessage() called Message key" + message_id);

        ChatModelNew modelNew = new ChatModelNew("Help", "", binding.message.getText().toString(), "Help", reciverid, "", session.getUserId(), "", time, "", "text");

        String message = binding.message.getText().toString();
        binding.message.setText("");
        reference
                .child(reciverRoom)
                .push()
                .setValue(modelNew)
                .addOnSuccessListener(unused -> {

                    reference
                            .child(senderRoom)
                            .push()
                            .setValue(modelNew).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    sendSmsToVendor(message);
                                    binding.chatRecler.setAdapter(adapterNew);
                                }
                            });

                }).addOnFailureListener(e -> Log.e("TAG", "onFailure() called with: e = [" + e.getLocalizedMessage() + "]"));


    }

}


/*
*
* fSXMEF4MR4OD1liBSPzOKm:APA91bF22qXdekQ9KJj6yhdtHuwE2gbKWl3z7bjE4l9QrUizzvDOKCVjOhIq8O32qo8yX29G9GPhfrWYdkhTISz2d4CMSw3vF_6HrNWuEXLJYmufaZ-IyhhBPtpkK9Zpgo8mVXAN8kw0
*
* */