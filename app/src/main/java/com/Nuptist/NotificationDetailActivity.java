package com.Nuptist;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.Nuptist.databinding.ActivityNotificationDetailBinding;
import com.squareup.picasso.Picasso;

public class NotificationDetailActivity extends AppCompatActivity {

    private ActivityNotificationDetailBinding binding;
    private NotificationDetailActivity activity;
    private String notificationImage = "";
    private String notificationDescription = "";
    private String notificationTitle = "";
    private String notificationTime= "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNotificationDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        activity = this;
        if (getIntent() != null) {
            notificationImage = getIntent().getStringExtra("n_image");
            notificationTitle = getIntent().getStringExtra("n_title");
            notificationDescription = getIntent().getStringExtra("n_description");
            notificationTime = getIntent().getStringExtra("n_time");
        }


        binding.descriptionTv.setText(notificationDescription);
        binding.titleTv.setText(notificationTitle);
        binding.headingTitle.setText(notificationTitle);
        binding.timeTv.setText(notificationTime);
        Picasso.get().load(notificationImage).into(binding.notificationImage);


        binding.back.setOnClickListener(view -> onBackPressed());

    }

}