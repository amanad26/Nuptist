package com.Nuptist.adpaters;

import static com.Nuptist.RetrofitApis.BaseUrls.IMAGE_URL;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.Nuptist.Models.NotificationModel;
import com.Nuptist.NotificationDetailActivity;
import com.Nuptist.R;
import com.Nuptist.databinding.NotificationLayoutBinding;
import com.squareup.picasso.Picasso;

import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder>{

    Context context ;
    List<NotificationModel.NotificationData> model ;
    String image ;

    public NotificationAdapter(Context context, List<NotificationModel.NotificationData> model, String path) {
        this.context = context;
        this.model = model;
        this.image = path;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return  new ViewHolder(LayoutInflater.from(context).inflate(R.layout.notification_layout,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.binding.notificationText.setText(model.get(position).getDescription());
        holder.binding.notificationTitle.setText(model.get(position).getTitle());

        Picasso.get().load(image+model.get(position).getFilepath()).placeholder(R.drawable.loading).into(holder.binding.notificationImage);

        try {
            holder.binding.notificationTime.setText(formatDate(model.get(position).getCreatedAt()));
        } catch (Exception e) {
            e.printStackTrace();
        }

        holder.itemView.setOnClickListener(view -> context.startActivity(new Intent(context, NotificationDetailActivity.class)
                .putExtra("n_title",model.get(position).getTitle())
                .putExtra("n_description",model.get(position).getDescription())
                .putExtra("n_image",image+model.get(position).getFilepath())
                .putExtra("n_time",formatDate(model.get(position).getCreatedAt()))
        ));

    }

    public String formatDate(String s) {
        //  s = "2022-10-21 08:08:18";

        String[] dateTime = s.split(" ");

        String dateS = dateTime[0];
        String timeS = dateTime[1];

        timeS = timeS.substring(0, 5);

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

    public  class ViewHolder extends RecyclerView.ViewHolder{
        NotificationLayoutBinding binding ;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = NotificationLayoutBinding.bind(itemView);
        }
    }
}
