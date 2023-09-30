package com.Nuptist.Chat;

import android.util.Log;

import androidx.annotation.NonNull;

import com.Nuptist.RetrofitApis.ApiInterface;
import com.Nuptist.firebase.firebase.NotiModel;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SendNotification {
//
//    public static  void sendNotification(String fcm_id , Map<String, Object> body) {
//      //  String fcmId = "fVn-JHesRR-gLeikv2r-KF:APA91bGETKv7oPT6BWRKhKw9W2tT2t5MI5GRFKtO8oRHdjZ4aiLVn0xQHjkB95bwhOvoExadaNMI-a7pgmLjhAERo8S98NtEh34Fo6E8EVZ23-8HpI1Qud_urWFFrCP1eHKqTHfSeCHS";
//        String bodyString = "This the body of notification";
//
//        Map<String, Object> map = new HashMap<>();
//
////        Map<String, String> notification = new HashMap<>();
////        notification.put("body", body);
////        notification.put("title", "Nuptist");
//
//        map.put("to", fcm_id);
//        map.put("notification", body);
//
//
//        Gson gson = new GsonBuilder()
//                .setLenient()
//                .create();
//
//
//
//        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
//        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
//        OkHttpClient.Builder client = new OkHttpClient.Builder()
//                .connectTimeout(2, TimeUnit.MINUTES)
//                .readTimeout(2, TimeUnit.MINUTES);
//
//        ///    if (BaseUrl.Development.equals(Constants.Key.Debug)) {
//        client.addInterceptor(interceptor);
//        //    }
//
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl("https://fcm.googleapis.com/fcm/")
//                .client(client.build())
//                .addConverterFactory(GsonConverterFactory.create(gson))
//                .build();
//
//
//        ApiInterface apiService = retrofit.create(ApiInterface.class);
//
//        apiService.sendNotification(
//                "key=AAAAixOGo7s:APA91bFDhYqHSB6KqyHIuQwRB6M7bb_B10v4g6N_5woDmapH74--J6nK6YGAobZB0W-73vzqBmCVWGBLrNBRtWMis5yRNtsrlg7RzGQkHhsFQmJea1aP4Rxs-NOvXmetMJ3u2iefVIh2",
//                "application/json",
//                map).enqueue(new Callback<NotiModel>() {
//            @Override
//            public void onResponse(Call<NotiModel> call, Response<NotiModel> response) {
//                Log.e("TAG", "onResponse() called with: call = [" + call + "], response = [" + response + "]");
//                Log.e("TAG", "Message Send  = [" + call + "], response = [" + response + "]");
//            }
//
//            @Override
//            public void onFailure(Call<NotiModel> call, Throwable t) {
//                Log.e("TAG", "onFailure() Message Not Send  = [" + call + "], t = [" + t.getLocalizedMessage() + "]");
//            }
//        });
//    }
//

    public static void sendNotification(String fcmId, Map<String, String> notification, Map<String, String> data) {
        Map<String, Object> map = new HashMap<>();

        map.put("to", fcmId);
        map.put("notification", notification);
        map.put("data", data);

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();

        interceptor.level(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                .connectTimeout(360, TimeUnit.MINUTES)
                .readTimeout(360, TimeUnit.MINUTES)
                .writeTimeout(360, TimeUnit.MINUTES)
                .addInterceptor(interceptor)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://fcm.googleapis.com/fcm/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(okHttpClient)
                .build();


        ApiInterface apiService = retrofit.create(ApiInterface.class);

        apiService.sendNotification(
                "key=AAAAixOGo7s:APA91bFDhYqHSB6KqyHIuQwRB6M7bb_B10v4g6N_5woDmapH74--J6nK6YGAobZB0W-73vzqBmCVWGBLrNBRtWMis5yRNtsrlg7RzGQkHhsFQmJea1aP4Rxs-NOvXmetMJ3u2iefVIh2",
                "application/json",
                map).enqueue(new Callback<NotiModel>() {
            @Override
            public void onResponse(@NonNull Call<NotiModel> call, @NonNull Response<NotiModel> response) {
                Log.e("TAG", "onResponse() called with: call = [" + call + "], response = [" + response + "]");
            }

            @Override
            public void onFailure(@NonNull Call<NotiModel> call, @NonNull Throwable t) {
                Log.e("TAG", "onFailure() called with: call = [" + call + "], t = [" + t + "]");
            }
        });
    }
}
