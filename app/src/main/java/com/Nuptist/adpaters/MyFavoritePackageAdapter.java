package com.Nuptist.adpaters;

import static com.Nuptist.RetrofitApis.BaseUrls.IMAGE_URL;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.Nuptist.DetailsActivity;
import com.Nuptist.Models.MyFavoritePackageModel;
import com.Nuptist.Models.PackageDetailsModel;
import com.Nuptist.Models.PackageViewModel;
import com.Nuptist.Models.ReviewModel;
import com.Nuptist.R;
import com.Nuptist.RetrofitApis.ApiInterface;
import com.Nuptist.RetrofitApis.RetrofitClient;
import com.Nuptist.Session.Session;
import com.skydoves.elasticviews.ElasticCardView;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyFavoritePackageAdapter extends RecyclerView.Adapter<MyFavoritePackageAdapter.ViewHolder>{

    Context context ;
    List<MyFavoritePackageModel.FavoritePackageData> model ;
    String url ;
    boolean isliked = true ;
    Session session ;

    public MyFavoritePackageAdapter(Context context, List<MyFavoritePackageModel.FavoritePackageData> model, String url) {
        this.context = context;
        this.model = model;
        this.url = url;
        session = new Session(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.my_favorite_package_list_layout,parent,false);
        return  new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

       // holder.address.setText(model.get(position).getFavPackageAddress());

        try {

            holder.total_user.setText(model.get(position).getFavVenueCapacity()+" People");
            Picasso.get().load(IMAGE_URL+model.get(position).getFavPackageImage()).placeholder(R.drawable.ic_pic1).into(holder.image);
            holder.package_name.setText(model.get(position).getFavPackageName());



            getPackageDetials(model.get(position).getPackageId(), holder);
            holder.date.setText(formatDate(model.get(position).getFavPackageStartDate()));
            getReviewData(model.get(position).getPackageId(), holder);
            String address = model.get(position).getFavPackageAddress().substring(0, 1).toUpperCase() + model.get(position).getFavPackageAddress().substring(1);
            holder.address.setText(address);

            holder.to_details.setOnClickListener(view -> {
                context.startActivity(new Intent(context, DetailsActivity.class)
                        .putExtra("p_id",model.get(holder.getAdapterPosition()).getPackageId())
                        .putExtra("image",IMAGE_URL+model.get(position).getFavPackageImage()));
            });


        } catch (Exception e) {
            e.printStackTrace();
        }



//        holder.itemView.setOnClickListener(view -> {
//            context.startActivity(new Intent(context, DetailsActivity.class)
//                    .putExtra("p_id",model.get(holder.getAdapterPosition()).getId())
//                    .putExtra("image",IMAGE_URL+model.get(position).getFavPackageImage()));
//        });

        holder.unfavourite_package.setOnClickListener(v -> {
            likePackage(holder.getAbsoluteAdapterPosition() , model.get(holder.getAbsoluteAdapterPosition()).getPackageId());
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
                           String packagePrice = response.body().getData().getPackages().getPrice();
                           holder.packge_price.setText("Rs. "+packagePrice);
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



    private void likePackage(int absoluteAdapterPosition, String packageId) {


        ApiInterface apiInterface = RetrofitClient.getclient(context);
        String userid;
        if (session.getType().equalsIgnoreCase("Vendor")) {
            userid = session.getUserId_Vendor();
        } else {
            userid = session.getUserId();
        }

        apiInterface.favoritePackage(userid, packageId).enqueue(new Callback<PackageViewModel>() {
            @Override
            public void onResponse(@NonNull Call<PackageViewModel> call, @NonNull Response<PackageViewModel> response) {

                try {
                    if (response.body() != null && response.body().getResult().equalsIgnoreCase("true")) {
                        if (response.body().getMsg().equalsIgnoreCase("Unfavorite")) {
                            notifyItemRemoved(absoluteAdapterPosition);
                            notifyItemRangeChanged(0,model.size()-1);
                            model.remove(absoluteAdapterPosition);
                        }

                    }

                } catch (Exception e) {
                    e.getLocalizedMessage();
                }

            }

            @Override
            public void onFailure(@NonNull Call<PackageViewModel> call, @NonNull Throwable t) {
                Log.e("TAG", "onFailure() called with: call = [" + call + "], t = [" + t + "]");
            }
        });

    }


    public String formatDate(String s) {
        //  s = "2022-10-21 08:08:18";

        String[] dateTime = s.split(" ");

        String dateS = dateTime[0];
//        String timeS = dateTime[1];
//
//        timeS = timeS.substring(0, 5);

        String[] datess = dateS.split("-");

        String year = datess[0];
        String month = datess[1];
        String day = datess[2];

        return day + " " + getMonthName(Integer.parseInt(month)) + " " + year + " ";
    }

    public String getMonthName(int month) {

        String monthString;
        switch (month) {
            case 1:
                monthString = "January";
                break;
            case 2:
                monthString = "February";
                break;
            case 3:
                monthString = "March";
                break;
            case 4:
                monthString = "April";
                break;
            case 5:
                monthString = "May";
                break;
            case 6:
                monthString = "June";
                break;
            case 7:
                monthString = "July";
                break;
            case 8:
                monthString = "August";
                break;
            case 9:
                monthString = "September";
                break;
            case 10:
                monthString = "October";
                break;
            case 11:
                monthString = "November";
                break;
            case 12:
                monthString = "December";
                break;
            default:
                monthString = "Invalid month";
                break;
        }
        System.out.println(monthString);

        return monthString;
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

                            for(int i =0 ; i<response.body().getData().size(); i++){
                                float re = Float.parseFloat(response.body().getData().get(i).getRating());
                                review = review+re;
                            }

                            if (response.body().getData().size() >= 2) {
                                review = review/5;
                                holder.some_id.setText(String.valueOf(review));
                                Log.e("TAG", "Total avg rating is "+review);
                            }else {
                                holder.some_id.setText(String.valueOf(4.6));
                            }

                        }

            }

            @Override
            public void onFailure(@NonNull Call<ReviewModel> call, @NonNull Throwable t) {

            }
        });

    }

    @Override
    public int getItemCount() {
        return model.size();
    }

    public class  ViewHolder extends RecyclerView.ViewHolder{

        TextView address , date , total_user,package_name ,packge_price ,some_id;
        ImageView image ,unfavourite_package;
        ElasticCardView to_details ;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            address = itemView.findViewById(R.id.fav_package_address);
            date = itemView.findViewById(R.id.fav_package_date);
            total_user = itemView.findViewById(R.id.fav_user_count);
            image = itemView.findViewById(R.id.fav_package_image);
            package_name = itemView.findViewById(R.id.package_name);
            unfavourite_package = itemView.findViewById(R.id.unfavourite_package);
            packge_price = itemView.findViewById(R.id.packge_price);
            to_details = itemView.findViewById(R.id.to_details);
            some_id = itemView.findViewById(R.id.some_id);
        }
    }
}
