package com.Nuptist.Chat.ChatWithUsers;

import static com.Nuptist.Chat.SendNotification.sendNotification;
import static com.Nuptist.RetrofitApis.BaseUrls.IMAGE_URL;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;

import com.Nuptist.Chat.ChatAdapter;
import com.Nuptist.Chat.ChatAdapterNew;
import com.Nuptist.Chat.ChatModelNew;
import com.Nuptist.Chat.MessageModel;
import com.Nuptist.Chat.SendNotification;
import com.Nuptist.Models.AccountModel;
import com.Nuptist.Models.LogOutModel;
import com.Nuptist.Models.ProgressDialog;
import com.Nuptist.Models.VendorProfileModel;
import com.Nuptist.ProfileActivity;
import com.Nuptist.R;
import com.Nuptist.RetrofitApis.ApiInterface;
import com.Nuptist.RetrofitApis.RetrofitClient;
import com.Nuptist.Session.Session;
import com.Nuptist.databinding.ActivityChatWithUsersBinding;
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

public class ChatWithUsersActivity extends AppCompatActivity {

    String senderRoom;
    String reciverRoom;
    String senderId, reciverid = "1";

    String date, time;
    Session session;
    FirebaseDatabase database;
    ChatAdapterForVendor chatAdapter;
    DatabaseReference reference;
    ChatAdapterForVendor adapterNew;

    String message_id;
    String reciverFcmId = "" , receiver_name = "", receiver_image = "";
    ArrayList<MessageModel> messageModelsList = new ArrayList<>();
    ArrayList<ChatModelNew> chatModel = new ArrayList<>();

    ActivityChatWithUsersBinding binding;
    ChatWithUsersActivity activity;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChatWithUsersBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

            activity = this;

            session = new Session(activity);

        if(getIntent() != null){
            reciverid = getIntent().getStringExtra("f_id");
            receiver_name = getIntent().getStringExtra("f_name");
            receiver_image = getIntent().getStringExtra("f_image");
            reciverFcmId = getIntent().getStringExtra("f_fcm");


            binding.reciverName.setText(receiver_name);
            Picasso.get().load(receiver_name).placeholder(R.drawable.nuptistnew).into(binding.reciverImage);
            Log.e("TAG", "onCreate() called with: fcm  = [" + reciverFcmId + "]");
             get_profile();
        }


        senderId = session.getUserId_Vendor();

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

        ///chatAdapter = new ChatAdapterForVendor(activity, messageModelsList);
        Log.e("TAG", "onCreate() called with: savedInstanceState = [" + date + "]");
        Log.e("TAG", "onCreate() called with: Time = [" + formatter2.format(d.getTime()) + "]");

        binding.sendMessage.setOnClickListener(view -> {
            if (!binding.message.getText().toString().equalsIgnoreCase(""))
                sendMessage();
        });

        adapterNew = new ChatAdapterForVendor(activity, chatModel);
        LinearLayoutManager linearLayout = new LinearLayoutManager(activity);
        linearLayout.setOrientation(RecyclerView.VERTICAL);
        linearLayout.setStackFromEnd(true);
        binding.chatRecler.setLayoutManager(linearLayout);
        binding.chatRecler.setAdapter(adapterNew);
        binding.back.setOnClickListener(v -> onBackPressed());
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

    private void sendSmsToAdmin(String message) {

        ApiInterface apiInterface = RetrofitClient.getclient(activity);

        apiInterface.sendSmsToUserVendor(
                reciverid,
                senderId,
                message
        ).enqueue(new Callback<LogOutModel>() {
            @Override
            public void onResponse(@NonNull Call<LogOutModel> call, @NonNull Response<LogOutModel> response) {

                try {
                    if (response.code() == 200)
                        if (response.body() != null) {
                            if (response.body().getResult().equalsIgnoreCase("true")) {
                                Log.e("TAG", "onResponse() called with: call = [" + call + "], response = [" + response.body().getMsg() + "]");

                                Log.e("TAG", "onResponse() reciverFcmId = [" + reciverFcmId +"]");
                                if(!reciverFcmId.equalsIgnoreCase("")){
                                    String body = "("+receiver_name+") "+message;
                                    Map<String, String> notiBody = new HashMap<>();
                                    Map<String, String> data = new HashMap<>();
                                    notiBody.put("title", "Nuptist");
                                    notiBody.put("body", "("+session.getUserVendorName()+") "+message);

                                    SendNotification.sendNotification(reciverFcmId,notiBody,data);

                                }else {
                                    Log.e("TAG", "onResponse() reciverFcmId else  = [" + reciverFcmId +"]");
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

    private void sendMessage() {
        message_id = reference.push().getKey();
        Log.e("TAG", "sendMessage() called Message key" + message_id);

        ChatModelNew modelNew = new ChatModelNew("Help", "", binding.message.getText().toString(), "Help", reciverid, "", senderId, "", time, "", "text");

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
                            .setValue(modelNew)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    sendSmsToAdmin(message);
                                    binding.chatRecler.setAdapter(adapterNew);
                                }
                            });
                }).addOnFailureListener(e -> Log.e("TAG", "onFailure() called with: e = [" + e.getLocalizedMessage() + "]"));


    }

    private  void get_profile(){

        ProgressDialog pd = new ProgressDialog(activity);
        pd.show();

        ApiInterface apiInterface = RetrofitClient.getclient(activity);

        apiInterface.get_profile(reciverid).enqueue(new Callback<AccountModel>() {
            @Override
            public void onResponse(@NonNull Call<AccountModel> call,@NonNull Response<AccountModel> response) {
                pd.dismiss();
                try {

                    if (response.body() != null && response.body().getResult().equalsIgnoreCase("true")) {
                       reciverFcmId = response.body().getData().getFcmId() ;
                    }

                }catch (Exception e){
                    pd.dismiss();
                    e.getLocalizedMessage();
                }

            }

            @Override
            public void onFailure(@NonNull Call<AccountModel> call,@NonNull Throwable t) {
                pd.dismiss();
                Log.e("TAG", "onFailure() called with: call = [" + call + "], t = [" + t + "]");
            }
        });



    }

}