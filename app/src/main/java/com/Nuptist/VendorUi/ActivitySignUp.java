package com.Nuptist.VendorUi;

import static com.airbnb.lottie.L.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.Nuptist.HomeActivity;
import com.Nuptist.R;

import ss.anoop.awesometextinputlayout.AwesomeTextInputLayout;

public class ActivitySignUp extends AppCompatActivity {

    EditText pharmacy_name, mobile_, email_, pincode_, otp_, referral_code;

    AwesomeTextInputLayout awsome_ph_name, awsome_mobile_, awsome_email_, awsome_pincode_, awsome_referal_code_, awsome_ttp;

    TextView sing_up_btn;

    String ph_name = "", mobile = "", email = "", otp = "", pincode = "", rcode = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_activity);

        pharmacy_name = findViewById(R.id.pharmacy_name);
        sing_up_btn = findViewById(R.id.sing_up_btn);
        referral_code = findViewById(R.id.referral_code);
        mobile_ = findViewById(R.id.mobile_);
        email_ = findViewById(R.id.email_);
        pincode_ = findViewById(R.id.pincode_);
        otp_ = findViewById(R.id.otp_);
        awsome_ph_name = findViewById(R.id.awsome_ph_name);
        awsome_mobile_ = findViewById(R.id.awsome_mobile_);
        awsome_email_ = findViewById(R.id.awsome_email_);
        awsome_pincode_ = findViewById(R.id.awsome_pincode_);
        awsome_referal_code_ = findViewById(R.id.awsome_referal_code_);
        awsome_ttp = findViewById(R.id.awsome_ttp);


        /*awsome_mobile_.setVisibility(View.GONE);
        awsome_email_.setVisibility(View.GONE);
        awsome_pincode_.setVisibility(View.GONE);
        awsome_referal_code_.setVisibility(View.GONE);
        awsome_ttp.setVisibility(View.GONE);
*/

        awsome_mobile_.setVisibility(View.VISIBLE);
        awsome_email_.setVisibility(View.VISIBLE);
        awsome_pincode_.setVisibility(View.VISIBLE);
        awsome_referal_code_.setVisibility(View.VISIBLE);
        awsome_ttp.setVisibility(View.VISIBLE);
        referral_code.setVisibility(View.VISIBLE);
        mobile_.setVisibility(View.VISIBLE);
        email_.setVisibility(View.VISIBLE);
        pincode_.setVisibility(View.VISIBLE);
        otp_.setVisibility(View.VISIBLE);
        ph_name = pharmacy_name.getText().toString();

        mobile = mobile_.getText().toString();
        email = email_.getText().toString();
        otp = otp_.getText().toString();
        pincode = pincode_.getText().toString();
        rcode = referral_code.getText().toString();

        sing_up_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    Log.e(TAG, "Intent: ");

                    Intent intent  = new Intent(ActivitySignUp.this,HomeActivity.class);
                    startActivity(intent);
                    finish();

            }
        });



    }
}







