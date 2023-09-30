package com.Nuptist.Chat.ChatWithUsers;

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

import com.Nuptist.R;
import com.Nuptist.databinding.ChatListLayoutBinding;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ChatUsersListAdapter extends RecyclerView.Adapter<ChatUsersListAdapter.ViewHolder>{


    Context context ;
    List<ChatListUserModel.ChatListUserData> model ;
    String type = "";


    public ChatUsersListAdapter(Context context, List<ChatListUserModel.ChatListUserData> model, String type) {
        this.context = context;
        this.model = model;
        this.type = type ;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return  new ViewHolder(LayoutInflater.from(context).inflate(R.layout.chat_list_layout,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {


        if(type.equalsIgnoreCase("user")) {
            holder.binding.lastMsg.setText(model.get(position).getMessage());
            holder.binding.messagTime.setText(formatDate(model.get(position).getUpdatedDate()));
            holder.binding.userName.setText(model.get(position).getVenderName());
            Picasso.get().load(USER_IMAGE_URL+model.get(holder.getAbsoluteAdapterPosition()).getVenderImage()).into(holder.binding.userPic);

            Log.e("TAG", "onBindViewHolder() called with: IMAGE VENDOR = [" + USER_IMAGE_URL+model.get(holder.getAbsoluteAdapterPosition()).getVenderImage() + "], position = [" + position + "]");

        }else {
            holder.binding.lastMsg.setText(model.get(position).getMessage());
            holder.binding.messagTime.setText(formatDate(model.get(position).getUpdatedDate()));
            Picasso.get().load(USER_IMAGE_URL+model.get(holder.getAbsoluteAdapterPosition()).getUserImage()).into(holder.binding.userPic);
            holder.binding.userName.setText(model.get(position).getUserName());
            Log.e("TAG", "onBindViewHolder() called with: IMAGE USER = [" + USER_IMAGE_URL+model.get(holder.getAbsoluteAdapterPosition()).getUserImage() + "], position = [" + position + "]");

        }


        holder.itemView.setOnClickListener(v -> {
            Intent intent;
          if(type.equalsIgnoreCase("user")) {
              intent = new Intent(context, ChatWithVendorsActivity.class);
              intent.putExtra("f_id",model.get(holder.getAbsoluteAdapterPosition()).getVenderId());
              intent.putExtra("f_name",model.get(holder.getAbsoluteAdapterPosition()).getVenderName());
              intent.putExtra("f_image",USER_IMAGE_URL+model.get(holder.getAbsoluteAdapterPosition()).getVenderImage());

          }else{
              intent = new Intent(context, ChatWithUsersActivity.class);
              intent.putExtra("f_id",model.get(holder.getAbsoluteAdapterPosition()).getUserId());
              intent.putExtra("f_name",model.get(holder.getAbsoluteAdapterPosition()).getUserName());
              intent.putExtra("f_image",USER_IMAGE_URL+model.get(holder.getAbsoluteAdapterPosition()).getUserImage());
              intent.putExtra("f_fcm",model.get(holder.getAbsoluteAdapterPosition()).getFcmId());
            }

           context.startActivity(intent);

        });

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

        return day + " " + getMonthName(Integer.parseInt(month)) + " " + year + " "+timeS;
    }

    public String getMonthName(int month) {

        String monthString;
        switch (month) {
            case 1:
                monthString = "Jan";
                break;
            case 2:
                monthString = "Feb";
                break;
            case 3:
                monthString = "Mar";
                break;
            case 4:
                monthString = "Apr";
                break;
            case 5:
                monthString = "May";
                break;
            case 6:
                monthString = "Jun";
                break;
            case 7:
                monthString = "July";
                break;
            case 8:
                monthString = "Aug";
                break;
            case 9:
                monthString = "Sep";
                break;
            case 10:
                monthString = "Oct";
                break;
            case 11:
                monthString = "Nov";
                break;
            case 12:
                monthString = "Dec";
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
        ChatListLayoutBinding binding ;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = ChatListLayoutBinding.bind(itemView);
        }
    }
}
