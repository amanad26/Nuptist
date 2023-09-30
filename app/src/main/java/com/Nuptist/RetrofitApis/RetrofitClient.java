package com.Nuptist.RetrofitApis;

import android.app.Dialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.Nuptist.R;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {




    public  static Retrofit RETRFIT = null;


    public  static ApiInterface getclient(Context context){



        try {

            ConnectivityManager connectivityManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
            if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                    connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
                //we are connected to a network

            }
            else{
                final Dialog dialog = new Dialog(context);

                // setting content view to dialog
                dialog.setContentView(R.layout.no_internet_layout);
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                dialog.getWindow().getAttributes().windowAnimations = R.style.animation;
                dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                dialog.findViewById(R.id.parentRelative).setOnClickListener(View->dialog.dismiss());
                ImageView close ;
                dialog.show();

                Toast.makeText(context, "Please Check Your Internet Connection", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Log.e("TAG", "getclient() called with: context Error  = [" + e.getLocalizedMessage() + "]");
            e.printStackTrace();
        }





        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder client = new OkHttpClient.Builder()
                .connectTimeout(2, TimeUnit.MINUTES)
                .readTimeout(2, TimeUnit.MINUTES);

        ///    if (BaseUrl.Development.equals(Constants.Key.Debug)) {
        client.addInterceptor(interceptor);
        //    }

        if(RETRFIT == null) {
            RETRFIT = new Retrofit.Builder()
                    .baseUrl(BaseUrls.URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client.build())
                    .build();
        }


        ApiInterface apiInterface ;
        apiInterface = RETRFIT.create(ApiInterface.class);
        return  apiInterface;

    }





}
