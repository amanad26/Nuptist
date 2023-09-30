package com.Nuptist.adpaters;

import static com.Nuptist.RetrofitApis.BaseUrls.IMAGE_URL;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.Nuptist.DetailsActivity;
import com.Nuptist.Models.CountryPackageModel;
import com.Nuptist.Models.GetPackageModel;
import com.Nuptist.Models.ReviewModel;
import com.Nuptist.R;
import com.Nuptist.RetrofitApis.ApiInterface;
import com.Nuptist.RetrofitApis.RetrofitClient;
import com.skydoves.elasticviews.ElasticCardView;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PopularPackageAdapter extends RecyclerView.Adapter<PopularPackageAdapter.ViewHolder>{

    Context context ;
    List<CountryPackageModel.PackageCountryData> model ;
    String path ;
    int count ;

    public PopularPackageAdapter(Context context,  List<CountryPackageModel.PackageCountryData> model, String path) {
        this.context = context;
        this.model = model;
        this.path = path;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.polular_packages_layout,parent,false);
        return  new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {


        Log.e("TAG", "onBindViewHolder() called with: holder = [" + holder + "], list size  = [" + model.size() + "]");

        holder.name.setText(model.get(position).getPackageName());

        try {
            Picasso.get().load(IMAGE_URL+model.get(position).getImage()).into(holder.image);
            getReviewData(model.get(position).getId(), holder);
        } catch (Exception e) {
            e.printStackTrace();
        }


        holder.toch_it.setOnClickListener(view -> {
            context.startActivity(new Intent(context, DetailsActivity.class)
                    .putExtra("p_id",model.get(position).getPackageId())
                    .putExtra("image",path+model.get(position).getImage()));

            Log.e("TAG", "onBindViewHolder() called with: model.get(holder.getAdapterPosition()).getId() = [" + model.get(holder.getAdapterPosition()).getId() + "], position = [" + position + "]");
        });


    }

    @Override
    public int getItemCount() {
        return model.size();
    }


    private void getReviewData(String package_id, ViewHolder holder) {
        ApiInterface apiInterface = RetrofitClient.getclient(context);

        apiInterface.packageReview(package_id).enqueue(new Callback<ReviewModel>() {
            @Override
            public void onResponse(@NonNull Call<ReviewModel> call, @NonNull Response<ReviewModel> response) {

                if (response.code() == 200)
                    if (response.body() != null)
                        if (response.body().getResult().equalsIgnoreCase("true")) {
                            float review = 0 ;

                            int total_5 = 0 ;
                            int total_4 = 0 ;
                            int total_3 = 0 ;
                            int total_2 = 0 ;
                            int total_1 = 0 ;


                            for(int i =0 ; i<response.body().getData().size(); i++){
                                float re = Float.parseFloat(response.body().getData().get(i).getRating());

//                                if(re == 1) total_1 = total_1+1 ;
//                                else if(re ==2 ) total_2 = total_2 +1 ;
//                                else if(re ==3 ) total_3 = total_3 +1 ;
//                                else if(re ==4 ) total_4 = total_4 +1 ;
//                                else  total_5 = total_5+1  ;
//
                                 review = review+re ;
                            }

//                            Log.e("TAG", "onResponse() called with: Pos "+holder.getAdapterPosition());
//
//                            Log.e("TAG", "onResponse() called with: total_1"+total_1);
//                            Log.e("TAG", "onResponse() called with: total_2"+total_2);
//                            Log.e("TAG", "onResponse() called with: total_3"+total_3);
//                            Log.e("TAG", "onResponse() called with: total_4"+total_4);
//                            Log.e("TAG", "onResponse() called with: total_5"+total_5);
//
//                            float total_user_count = total_1+total_2+total_3+total_4+total_5 ;
//                            Log.e("TAG", "onResponse() called with: total rating "+total_user_count );

//                            if (total_user_count > 0) {
//                                float total =  (total_1*1 + total_2*2 + total_3*3 + total_4*4 +total_5*5)/total_user_count ;
//
//                                Log.e("TAG", "onResponse() called with: total rating "+total );
//                            }else {
//                                Log.e("TAG", "onResponse() called with: total rating 0" );
//                            }


                            if (response.body().getData().size() != 0 ) {
                                holder.some_id.setText(String.valueOf(review/response.body().getData().size()));
                            }else {
                                holder.some_id.setText(String.valueOf(0));
                            }



                        }

            }

            @Override
            public void onFailure(@NonNull Call<ReviewModel> call, @NonNull Throwable t) {

            }
        });

    }


    public  class  ViewHolder extends RecyclerView.ViewHolder{
        TextView name , some_id ;
        ImageView image ;
        ElasticCardView toch_it ;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.package_title);
            image = itemView.findViewById(R.id.image_package);
            toch_it = itemView.findViewById(R.id.toch_it);
            some_id = itemView.findViewById(R.id.some_id);
        }
    }
}
