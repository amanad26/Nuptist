package com.Nuptist.MyBookings;

import static com.Nuptist.RetrofitApis.BaseUrls.IMAGE_URL;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.Nuptist.R;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.List;

public class MyBookingAddonsAdapter extends RecyclerView.Adapter<MyBookingAddonsAdapter.ViewHolder>{

    Context context ;
    List<MyBookingDetailsModel.MyBookingdata.ProductIdList> model ;
    List<MyBookingDetailsModel.MyBookingdata.ServiceIdList> model2 ;
    String url = IMAGE_URL ;

    public MyBookingAddonsAdapter(Context context, List<MyBookingDetailsModel.MyBookingdata.ProductIdList> model, List<MyBookingDetailsModel.MyBookingdata.ServiceIdList> model2) {
        this.context = context;
        this.model = model;
        this.model2 = model2;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.my_booking_offers,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {


        try {

            if( position < model.size()){

                MyBookingDetailsModel.MyBookingdata.ProductIdList data = model.get(position);

                holder.title.setText(data.getProductName());
//                holder.price.setText("Rs. "+data.getRecommendedPrice());
                holder.price.setText("Rs. "+ NumberFormat.getIntegerInstance().format(Integer.parseInt(data.getRecommendedPrice())));

                Picasso.get().load(url+data.getImage()).placeholder(R.drawable.ic_nature_svg).into(holder.imageView);

            }else {
                int pos = position - model.size();

                MyBookingDetailsModel.MyBookingdata.ServiceIdList data = model2.get(pos);

                holder.title.setText(data.getName());
                holder.price.setText("Rs. "+data.getRecommendedPrice());
                holder.price.setText("Rs. "+ NumberFormat.getIntegerInstance().format(Integer.parseInt(data.getRecommendedPrice())));
                Picasso.get().load(url+data.getImage()).placeholder(R.drawable.ic_nature_svg).into(holder.imageView);
            }



        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return model.size()+model2.size();
    }

    public  class  ViewHolder extends RecyclerView.ViewHolder{
       ImageView imageView;
       TextView title , price ;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.offter_title);
            price = itemView.findViewById(R.id.offer_price);
            imageView = itemView.findViewById(R.id.offer_image);
        }
    }
}
