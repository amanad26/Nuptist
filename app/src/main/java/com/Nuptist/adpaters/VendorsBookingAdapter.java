package com.Nuptist.adpaters;

import static com.Nuptist.FormatDate.formatDate;
import static com.Nuptist.RetrofitApis.BaseUrls.IMAGE_URL;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.Nuptist.BarDetailsActivity;
import com.Nuptist.Models.MyBookingModel;
import com.Nuptist.Models.PackageDetailsModel;
import com.Nuptist.R;
import com.Nuptist.RetrofitApis.ApiInterface;
import com.Nuptist.RetrofitApis.RetrofitClient;
import com.Nuptist.Session.Session;
import com.Nuptist.databinding.MyBookingLayoutBinding;
import com.airbnb.lottie.animation.content.Content;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VendorsBookingAdapter extends RecyclerView.Adapter<VendorsBookingAdapter.ViewHolder>{

    Context context ;
    List<MyBookingModel.MyBookingData> model ;
    Session session ;

    public VendorsBookingAdapter(Context context, List<MyBookingModel.MyBookingData> model) {
        this.context = context;
        this.model = model;
        session = new Session(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.my_booking_layout,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        try {
            if(model.get(position).getPackage_name() != null)
                holder.binding.packageName.setText(model.get(position).getPackage_name());

            holder.binding.date.setText(formatDate(model.get(position).getStartDate()) + " to "+formatDate(model.get(position).getEndDate()));
            holder.binding.totalPrice.setText("Rs. "+model.get(position).getTotalAmount());

            if(model.get(position).getBookingStatus().equalsIgnoreCase("pending")){
                holder.binding.status.setText(model.get(position).getBookingStatus());
            }else if (model.get(position).getBookingStatus().equalsIgnoreCase("cancelled")){
                holder.binding.status.setTextColor(context.getResources().getColor(R.color.red));
                holder.binding.status.setText(model.get(position).getBookingStatus());
            }else {
                holder.binding.status.setTextColor(context.getResources().getColor(R.color.blackk));
                holder.binding.status.setText(model.get(position).getBookingStatus());
            }

            holder.itemView.setOnClickListener(v -> {
                Intent intent = new Intent(context, BarDetailsActivity.class);
                intent.putExtra("booking_id",model.get(holder.getAbsoluteAdapterPosition()).getId());
                intent.putExtra("type","vendor");
                context.startActivity(intent);
            });



            getPackageDetials(model.get(position).getPackageId(), holder);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    private void getPackageDetials(String packageId, ViewHolder holder) {
//


        ApiInterface apiInterface = RetrofitClient.getclient(context);

        apiInterface.getPackagesDetails(packageId, session.getUserId()).enqueue(new Callback<PackageDetailsModel>() {
            @Override
            public void onResponse(@NonNull Call<PackageDetailsModel> call, @NonNull Response<PackageDetailsModel> response) {

                try {

                    if (response.body() != null) {
                        if (response.body().getResult().equalsIgnoreCase("true")) {

                            try {

                                Picasso.get().load(IMAGE_URL+response.body().getData().getPackages().getImage()).placeholder(R.drawable.ic_nature_svg).into(holder.binding.packageId);
                            }catch (Exception e){
                                e.getLocalizedMessage();
                                Log.e("TAG", "Start Date Error  = [" + e.getLocalizedMessage() + "]");
                            }

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


    @Override
    public int getItemCount() {
        return model.size();
    }

    public  class  ViewHolder extends RecyclerView.ViewHolder{
        MyBookingLayoutBinding binding ;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = MyBookingLayoutBinding.bind(itemView);
        }
    }
}
