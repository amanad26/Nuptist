package com.Nuptist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;

import com.Nuptist.VendorUi.VendorHomeActivity;

public class VendorSuccessActivity extends AppCompatActivity {
    private static final int SPLASH_SCREEN_TIME_OUT = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendor_sucess);

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        Window window = getWindow();

        window.setStatusBarColor(getResources().getColor(R.color.colorPrimary));

        new Handler().postDelayed(() -> {
            Intent i = new Intent(VendorSuccessActivity.this, VendorHomeActivity.class);
            startActivity(i);
            overridePendingTransition(R.anim.enter, R.anim.exit);
            finish();
        }, SPLASH_SCREEN_TIME_OUT);
    }
}