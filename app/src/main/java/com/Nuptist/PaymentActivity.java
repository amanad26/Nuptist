package com.Nuptist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.Nuptist.Models.LogOutModel;
import com.Nuptist.Models.ProgressDialog;
import com.Nuptist.RetrofitApis.ApiInterface;
import com.Nuptist.RetrofitApis.RetrofitClient;
import com.Nuptist.Session.Session;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PaymentActivity extends AppCompatActivity implements PaymentResultListener {


    String price , package_name , package_id , startDate , endDate, offerId , addonceId;
    Session session ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);


        session = new Session(PaymentActivity.this);

        price = getIntent().getStringExtra("price");
        addonceId = getIntent().getStringExtra("addOnce");
        offerId = getIntent().getStringExtra("offer");
        package_id = getIntent().getStringExtra("packageId");
        package_name = getIntent().getStringExtra("packageName");
        startDate = getIntent().getStringExtra("startDate");
        endDate = getIntent().getStringExtra("endDate");

        Log.e("TAG", "onCreate() called with: price = [" + price + "]");
        Log.e("TAG", "onCreate() called with: addonceId = [" + addonceId + "]");
        Log.e("TAG", "onCreate() called with: offerId = [" + offerId + "]");
        Log.e("TAG", "onCreate() called with: package_id = [" + package_id + "]");
        Log.e("TAG", "onCreate() called with: package_name = [" + package_name + "]");
        Log.e("TAG", "onCreate() called with: startDate = [" + startDate + "]");
        Log.e("TAG", "onCreate() called with: endDate = [" + endDate + "]");

        ProgressDialog progressDialog = new ProgressDialog(PaymentActivity.this);
        progressDialog.show();


        makePayment();



    }


    private void makePayment() {

        int amount = Math.round(Float.parseFloat(price) * 100);

        // initialize Razorpay account.
        Checkout checkout = new Checkout();

        // set your id as below
        checkout.setKeyID("rzp_test_W7bnxjP4xqCYzS");

        // set image
        checkout.setImage(R.drawable.nuptistnew_old);

        // initialize json object
        JSONObject object = new JSONObject();
        try {
            // to put name

            object.put("name", "Nuptist");

            // put description
            object.put("description", "Package Booking");

            // to set theme color
//            object.put("theme.color", "");

            // put the currency
            object.put("currency", "INR");

            // put amount
            object.put("amount", amount);

            // put mobile number
            object.put("prefill.contact", "000000000");

            // put email
            object.put("prefill.email", "nuptist@gmail.com");


            // open razorpay to checkout activity
            checkout.open(PaymentActivity.this, object);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onPaymentSuccess(String s) {
        Toast.makeText(this, "Payment Success", Toast.LENGTH_SHORT).show();
        Log.e("TAG", "onPaymentSuccess() called with: s = [" + s + "]");
        paymentDone();
    }

    @Override
    public void onPaymentError(int i, String s) {
        Log.e("TAG", "onPaymentError() called with: i = [" + i + "], s = [" + s + "]");
    }


    private  void paymentDone(){

        ProgressDialog pd = new ProgressDialog(PaymentActivity.this);

        Random random = new Random();
        int transectionId = random.nextInt(1000000);


        ApiInterface apiInterface = RetrofitClient.getclient(PaymentActivity.this);

        apiInterface.addBookingDates(
                package_id,
                session.getUserId(),
                "1",
                String.valueOf(price),
                startDate,
                endDate,
                String.valueOf(transectionId),
                "UPI",
                offerId,
                addonceId,
                package_name,
                "0,0" ,
                "0,0",
                "0,0",
                "0,0",
                "0,0",
                "0,0",
                ""
        ).enqueue(new Callback<LogOutModel>() {
            @Override
            public void onResponse(@NonNull Call<LogOutModel> call, @NonNull Response<LogOutModel> response) {

                pd.dismiss();
                try {
                    if (response.code() == 200 && response.body() != null)
                        if (response.body().getResult().equalsIgnoreCase("true")) {
                            pd.dismiss();
                            Toast.makeText(PaymentActivity.this, "Booking Successful..", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(PaymentActivity.this,HomeActivity.class));
                        } else {
                            pd.dismiss();
                            Toast.makeText(PaymentActivity.this, "Booking Failed..", Toast.LENGTH_SHORT).show();
                        }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(@NonNull Call<LogOutModel> call, @NonNull Throwable t) {
                pd.dismiss();
                Log.e("TAG", "onFailure() called with: call = [" + call + "], t = [" + t.getLocalizedMessage() + "]");
            }
        });

    }


}