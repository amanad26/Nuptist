package com.Nuptist;

import static com.Nuptist.RetrofitApis.BaseUrls.IMAGE_URL;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.Nuptist.Models.PackageDetailsModel;
import com.Nuptist.Models.ReviewModel;
import com.Nuptist.Models.SearchPackageModel;
import com.Nuptist.RetrofitApis.ApiInterface;
import com.Nuptist.RetrofitApis.RetrofitClient;
import com.Nuptist.Session.Session;
import com.Nuptist.databinding.RecylerItemBinding;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecySearchresultAdapter extends RecyclerView.Adapter<RecySearchresultAdapter.ViewHolder>{

    Context context;
    List<SearchPackageModel.SearchData> model ;
    Session session ;


    public RecySearchresultAdapter(Context context, List<SearchPackageModel.SearchData> model) {
        this.context = context;
        this.model = model;
        session = new Session(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(RecylerItemBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {


        try {
            holder.binding.addressPackage.setText(model.get(position).getCity());
        } catch (Exception e) {
            e.printStackTrace();
        }

        holder.binding.namePackage.setText(model.get(position).getPackageName());
        holder.binding.maxGuestPackage.setText(model.get(position).getGuestCapycityMix()+" People");

        holder.binding.pricePackage.setText("Rs. "+model.get(position).getPrice());
        getPackageDetials(model.get(position).getPackageId(), holder);

        try {
            getReviewData(model.get(position).getPackageId(), holder);
        } catch (Exception e) {
            e.printStackTrace();
        }

        holder.binding.details.setOnClickListener(view -> {
            Intent intent = new Intent(context, DetailsActivity.class)
             .putExtra("p_id",model.get(holder.getAdapterPosition()).getPackageId());
            context.startActivity(intent);
        });


    }

    @Override
    public int getItemCount() {
        return model.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        RecylerItemBinding binding;

        public ViewHolder(@NonNull RecylerItemBinding binding) {
            super(binding.getRoot());
            this.binding=binding;


        }
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
                                holder.binding.ratingPackage.setText(String.valueOf(review/response.body().getData().size()));
                            }else {
                                holder.binding.ratingPackage.setText(String.valueOf(0));
                            }



                        }

            }

            @Override
            public void onFailure(@NonNull Call<ReviewModel> call, @NonNull Throwable t) {

            }
        });

    }

    private void getPackageDetials(String packageId, ViewHolder holder) {


        ApiInterface apiInterface = RetrofitClient.getclient(context);

        apiInterface.getPackagesDetails(packageId, session.getUserId()).enqueue(new Callback<PackageDetailsModel>() {
            @Override
            public void onResponse(@NonNull Call<PackageDetailsModel> call, @NonNull Response<PackageDetailsModel> response) {

                try {

                    if (response.body() != null) {
                        if (response.body().getResult().equalsIgnoreCase("true")) {
                            Picasso.get().load(IMAGE_URL+response.body().getData().getPackages().getImage()).placeholder(R.drawable.loading).into(holder.binding.imagePackage);
                        }
                    }

                } catch (Exception e) {
                    Log.e("Date--------->", "onResponse() called with: Start Date = [" + call + "], response = [" + e.getLocalizedMessage() + "]");
                    e.getLocalizedMessage();
                }

            }

            @Override
            public void onFailure(@NonNull Call<PackageDetailsModel> call, @NonNull Throwable t) {
                Log.e("TAG", "onFailure() called with: call = [" + call + "], t = [" + t.getLocalizedMessage() + "]");
            }
        });


    }
}
