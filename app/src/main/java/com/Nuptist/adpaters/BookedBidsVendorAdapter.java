package com.Nuptist.adpaters;

import static android.content.ContentValues.TAG;
import static com.Nuptist.RetrofitApis.BaseUrls.USER_IMAGE_URL;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.Nuptist.Chat.ChatWithUsers.ChatWithVendorsActivity;
import com.Nuptist.MyBookings.MyBookingDetailsModel;
import com.Nuptist.R;
import com.Nuptist.ViewVendorProfileActivity;
import com.Nuptist.databinding.BookedVendorLayoutBinding;
import com.squareup.picasso.Picasso;

import java.util.List;

public class BookedBidsVendorAdapter extends RecyclerView.Adapter<BookedBidsVendorAdapter.ViewHolder>{

    Context context ;
    List<MyBookingDetailsModel.MyBookingdata.BidsAddonce> model ;
    List<MyBookingDetailsModel.MyBookingdata.BidsOffer> model2 ;

    public BookedBidsVendorAdapter(Context context, List<MyBookingDetailsModel.MyBookingdata.BidsAddonce> model, List<MyBookingDetailsModel.MyBookingdata.BidsOffer> model2) {
        this.context = context;
        this.model = model;
        this.model2 = model2;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.booked_vendor_layout,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        try{

            if(model.size() != 0 ){

               if( position < model.size() ){

               Log.e(TAG, "onBindViewHolder: if pos " + position);
               MyBookingDetailsModel.MyBookingdata.BidsAddonce data = model.get(position);
               holder.binding.notificationTitle.setText(data.getVenderName());
               holder.binding.notificationText.setText(data.getAddonceName());
               holder.binding.notificationTime.setText("Rs. "+data.getBidsPrice());
               Picasso.get().load(USER_IMAGE_URL+data.getVenderImage()).placeholder(R.drawable.user).into(holder.binding.notificationImage);

               holder.itemView.setOnClickListener(view -> context.startActivity(new Intent(context, ViewVendorProfileActivity.class)
                       .putExtra("vendor_id",model.get(holder.getAbsoluteAdapterPosition()).getVenderId())
                       .putExtra("type","1")
                       .putExtra("f_name",data.getVenderName())
                       .putExtra("f_image",USER_IMAGE_URL+data.getVenderImage())
               ));

//                   holder.itemView.setOnClickListener(new View.OnClickListener() {
//                       @Override
//                       public void onClick(View v) {
//                           context.startActivity(new Intent(context, ChatWithVendorsActivity.class)
//                                   .putExtra("f_id",data.getVenderId())
//                                   .putExtra("f_name",data.getVenderName())
//                                   .putExtra("f_image",USER_IMAGE_URL+data.getVenderImage())
//                           );
//                       }
//                   });

               }else {
                   if (model2.size() != 0 ) {
                        int pos =    position - model.size();
                        MyBookingDetailsModel.MyBookingdata.BidsOffer data = model2.get(pos);
                        Log.d(TAG, "onBindViewHolder() called with: pos = [" + pos + "], position = [" + position + "] , data "+data.toString());
                        holder.binding.notificationTitle.setText(data.getVenderName());
                        holder.binding.notificationText.setText(data.getOfferName());
                        holder.binding.notificationTime.setText("Rs. "+data.getBidsPrice());

                       Picasso.get().load(USER_IMAGE_URL+data.getVenderImage()).placeholder(R.drawable.loading).into(holder.binding.notificationImage);

//               holder.itemView.setOnClickListener(new View.OnClickListener() {
//                   @Override
//                   public void onClick(View v) {
//                       context.startActivity(new Intent(context, ChatWithVendorsActivity.class)
//                               .putExtra("f_id",data.getVenderId())
//                               .putExtra("f_name",data.getVenderName())
//                               .putExtra("f_image",USER_IMAGE_URL+data.getVenderImage())
//                       );
//                   }
//               });

                       holder.itemView.setOnClickListener(view -> context.startActivity(new Intent(context, ViewVendorProfileActivity.class)
                               .putExtra("vendor_id",model.get(pos).getVenderId())
                               .putExtra("type","1")
                               .putExtra("f_name",data.getVenderName())
                               .putExtra("f_image",USER_IMAGE_URL+data.getVenderImage())
                       ));
                   }

               }


           }else {

                   if(model2.size() != 0 ) {

                    ///   int pos =    position - model.size();
                       MyBookingDetailsModel.MyBookingdata.BidsOffer data = model2.get(position);
                       Log.d(TAG, "onBindViewHolder() called with: pos = [" + position + "], position = [" + position + "] , data "+data.toString());
                       holder.binding.notificationTitle.setText(data.getVenderName());
                       holder.binding.notificationText.setText(data.getOfferName());
                       holder.binding.notificationTime.setText("Rs. "+data.getBidsPrice());

                       Picasso.get().load(USER_IMAGE_URL+data.getVenderImage()).placeholder(R.drawable.loading).into(holder.binding.notificationImage);

//                holder.itemView.setOnClickListener(new View.OnClickListener() {
//                   @Override
//                   public void onClick(View v) {
//                       context.startActivity(new Intent(context, ChatWithVendorsActivity.class)
//                               .putExtra("f_id",data.getVenderId())
//                               .putExtra("f_name",data.getVenderName())
//                               .putExtra("f_image",USER_IMAGE_URL+data.getVenderImage())
//                       );
//                      }
//                  });

                       holder.itemView.setOnClickListener(view -> context.startActivity(new Intent(context, ViewVendorProfileActivity.class)
                               .putExtra("vendor_id",model2.get(position).getVenderId())
                               .putExtra("type","1")
                               .putExtra("f_name",data.getVenderName())
                               .putExtra("f_image",USER_IMAGE_URL+data.getVenderImage())
                       ));
                   }


               }



        }catch (Exception e){
            e.getLocalizedMessage();
        }


    }

    @Override
    public int getItemCount() {
        return model.size()+model2.size();
    }

    public  class  ViewHolder extends RecyclerView.ViewHolder{
        BookedVendorLayoutBinding binding ;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = BookedVendorLayoutBinding.bind(itemView);
        }
    }
}
