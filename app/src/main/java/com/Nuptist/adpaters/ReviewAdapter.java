package com.Nuptist.adpaters;

import static com.Nuptist.RetrofitApis.BaseUrls.IMAGE_URL;
import static com.Nuptist.RetrofitApis.BaseUrls.USER_IMAGE_URL;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.Nuptist.DetailsActivity;
import com.Nuptist.Models.ReviewModel;
import com.Nuptist.R;
import com.Nuptist.databinding.ReviewLayoutBinding;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ViewHolder>{

    Context context ;
    List<ReviewModel.ReviewData> model ;

    public ReviewAdapter(Context context, List<ReviewModel.ReviewData> model) {
        this.context = context;
        this.model = model;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return  new ViewHolder(LayoutInflater.from(context).inflate(R.layout.review_layout, parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        try {
            if (!model.get(position).getUserName().equalsIgnoreCase("")) {
                String res = model.get(position).getUserName().substring(0, 1).toUpperCase() + model.get(position).getUserName().substring(1);
                holder.binding.name.setText(res);
            }

            holder.binding.reviewDescription.setText(model.get(position).getMessage());
            holder.binding.someId.setText(model.get(position).getRating());

            holder.binding.date.setText(formatDate(model.get(position).getDate()));

            Picasso.get().load(USER_IMAGE_URL+model.get(position).getUserImage()).into(holder.binding.profilePic);




        } catch (Exception e) {
            e.printStackTrace();
            Log.e("TAG", "onBindViewHolder() called with: holder = [" + holder + "], position = [" + e.getLocalizedMessage() + "]");
        }


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
        return Math.min(model.size(), 5);
    }

    public  class  ViewHolder extends RecyclerView.ViewHolder{
        ReviewLayoutBinding binding ;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = ReviewLayoutBinding.bind(itemView);
        }
    }
}
