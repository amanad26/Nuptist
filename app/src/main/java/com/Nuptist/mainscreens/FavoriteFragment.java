package com.Nuptist.mainscreens;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.Nuptist.DetailsActivity;
import com.Nuptist.Models.MyFavoritePackageModel;
import com.Nuptist.Models.ProgressDialog;
import com.Nuptist.R;
import com.Nuptist.RetrofitApis.ApiInterface;
import com.Nuptist.RetrofitApis.RetrofitClient;
import com.Nuptist.Session.Session;
import com.Nuptist.adpaters.MyFavoritePackageAdapter;
import com.skydoves.elasticviews.ElasticCardView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FavoriteFragment extends Fragment {

    View root;

    ElasticCardView to_details;
    RecyclerView my_fav_recycler ;
    Session session ;
    LinearLayout no_data__linear ;
    Activity activity ;


    public  FavoriteFragment(){

    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.fragment_favorite, container, false);

        activity = requireActivity() ;

        try {
            session = new Session(activity);
            getFavoriteList();
        } catch (Exception e) {
            e.printStackTrace();
        }

        to_details = root.findViewById(R.id.to_details);
        no_data__linear = root.findViewById(R.id.no_data__linear);
        my_fav_recycler = root.findViewById(R.id.my_fav_recycler);

        to_details.setOnClickListener(view -> {
            Intent intent = new Intent(activity, DetailsActivity.class);
            startActivity(intent);
        });


        return root;

    }

    private  void getFavoriteList(){
        ProgressDialog pd ;
        pd = new ProgressDialog(activity);
        pd.show();

        ApiInterface apiInterface = RetrofitClient.getclient(activity);

        String userid  ;
        if(session.getType().equalsIgnoreCase("Vendor")){
            userid =  session.getUserId_Vendor();
        }else {
            userid = session.getUserId();
        }


        apiInterface.favoriteList(session.getUserId()).enqueue(new Callback<MyFavoritePackageModel>() {
            @Override
            public void onResponse(@NonNull Call<MyFavoritePackageModel> call, @NonNull Response<MyFavoritePackageModel> response) {

                pd.dismiss();
                try {

                    if(response.code() == 200)
                    if (response.body() != null) {
                        if(response.body().getResult().equalsIgnoreCase("true")){


                           if(response.body().getData().size() != 0 ){
                              my_fav_recycler.setLayoutManager(new LinearLayoutManager(activity));
                              my_fav_recycler.setAdapter(new MyFavoritePackageAdapter(activity,response.body().getData(),response.body().getPath()));
                           }else {
                               no_data__linear.setVisibility(View.VISIBLE);
                               Toast.makeText(activity, "Data Packages Found", Toast.LENGTH_SHORT).show();
                           }

                            Log.e("TAG", "onResponse() called with: call = [" + call + "], response = [" + response + "]");
                        }else {
                            no_data__linear.setVisibility(View.VISIBLE);
                            Toast.makeText(activity, "Data Packages Found", Toast.LENGTH_SHORT).show();
                        }
                    }

                }catch (Exception e){
                    pd.dismiss();
                    e.getLocalizedMessage();
                }

            }

            @Override
            public void onFailure(@NonNull Call<MyFavoritePackageModel> call, @NonNull Throwable t) {
                pd.dismiss();
                no_data__linear.setVisibility(View.VISIBLE);
                Log.e("TAG", "onFailure() called with: call = [" + call + "], t = [" + t.getLocalizedMessage() + "]");
            }
        });


    }

}
