package com.Nuptist.adpaters;

import static com.Nuptist.RetrofitApis.BaseUrls.USER_IMAGE_URL;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.Nuptist.Models.VendorBidsModel;
import com.Nuptist.Models.VendorInterface;
import com.Nuptist.R;
import com.Nuptist.Session.Session;
import com.Nuptist.ViewVendorProfileActivity;
import com.Nuptist.databinding.VendorBidsListLayoutBinding;
import com.squareup.picasso.Picasso;

import java.util.List;

public class VendorBidsAdapter extends RecyclerView.Adapter<VendorBidsAdapter.ViewHolder>{

    boolean islected = false;

    Context context ;
    int pos = -1 ;
    Session session ;
    List<VendorBidsModel.VendorBidData> model;
    VendorInterface vendorInterface ;
    String vendor_name = "" , vendor_id  = "", vendor_price = "" ;

    public VendorBidsAdapter(Context context, List<VendorBidsModel.VendorBidData> model, VendorInterface vendorInterface ) {
        this.context = context;
        this.model = model;
        this.vendorInterface = vendorInterface ;
        session = new Session(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return  new ViewHolder(LayoutInflater.from(context).inflate(R.layout.vendor_bids_list_layout,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        if (!session.getUserId_Vendor().equalsIgnoreCase("")) {

            if (model.size() != 0 ) {
                if (!session.getUserId_Vendor().equalsIgnoreCase(model.get(position).getVender_id())) {
                    holder.binding.userName.setText(model.get(position).getUserName());
                    holder.binding.userBidPrice.setText(model.get(position).getBidsPrice());

                    Picasso.get().load(USER_IMAGE_URL+model.get(position).getUserImage()).into(holder.binding.userImage);
                    Picasso.get().load(USER_IMAGE_URL+model.get(position).getCoverImage()).into(holder.binding.userCoverImage);

                    holder.binding.viewProfileBtnNew.setOnClickListener(view -> {
                        context.startActivity(new Intent(context, ViewVendorProfileActivity.class)
                                .putExtra("vendor_id", model.get(holder.getAbsoluteAdapterPosition()).getVender_id())
                                .putExtra("type", "0")
                        );

                        Log.e("TAG", "onBindViewHolder() called with: holder = [" + holder + "], position = [" + position + "]");
                    });

                    holder.binding.cardview.setOnClickListener(v -> {
                        if(pos == -1){
                            holder.binding.selectedImage.setVisibility(View.VISIBLE);
                            islected = true ;
                            pos = holder.getAbsoluteAdapterPosition() ;
                            vendor_name = model.get(holder.getAbsoluteAdapterPosition()).getUserName();
                            vendor_price = model.get(holder.getAbsoluteAdapterPosition()).getBidsPrice();
                            vendor_id = model.get(holder.getAbsoluteAdapterPosition()).getBidsId();
                            vendorInterface.getPrice(vendor_price,vendor_name,vendor_id);
                        }else {

                            if(pos == holder.getAbsoluteAdapterPosition()){
                                holder.binding.selectedImage.setVisibility(View.GONE);
                                islected = false ;
                                pos =  -1 ;
                                vendor_id = "";
                                vendor_name = "";
                                vendor_price = "";
                            }
                        }
                    });

                } else {
                    try {
                        holder.itemView.setVisibility(View.GONE);
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(0, model.size()-1);
                        model.remove(position);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }


        }else {

            if (model.size() != 0 ) {
                holder.binding.userName.setText(model.get(position).getUserName());
                holder.binding.userBidPrice.setText(model.get(position).getBidsPrice());

                Picasso.get().load(USER_IMAGE_URL+model.get(position).getUserImage()).into(holder.binding.userImage);
                Picasso.get().load(USER_IMAGE_URL+model.get(position).getCoverImage()).into(holder.binding.userCoverImage);

                holder.binding.viewProfileBtnNew.setOnClickListener(view -> {
                    context.startActivity(new Intent(context, ViewVendorProfileActivity.class)
                            .putExtra("vendor_id", model.get(holder.getAbsoluteAdapterPosition()).getVender_id())
                            .putExtra("type", "0")
                    );

                    Log.e("TAG", "onBindViewHolder() called with: holder = [" + holder + "], position = [" + position + "]");
                });


                holder.binding.cardview.setOnClickListener(v -> {
                    if(pos == -1){
                        holder.binding.selectedImage.setVisibility(View.VISIBLE);
                        islected = true ;
                        pos = holder.getAbsoluteAdapterPosition() ;
                        vendor_name = model.get(holder.getAbsoluteAdapterPosition()).getUserName();
                        vendor_price = model.get(holder.getAbsoluteAdapterPosition()).getBidsPrice();
                        vendor_id = model.get(holder.getAbsoluteAdapterPosition()).getBidsId();
                        vendorInterface.getPrice(vendor_price,vendor_name,vendor_id);
                    }else {

                        if(pos == holder.getAbsoluteAdapterPosition()){
                            holder.binding.selectedImage.setVisibility(View.GONE);
                            islected = false ;
                            pos =  -1 ;
                            vendor_id = "";
                            vendor_name = "";
                            vendor_price = "";
                        }
                    }
                });
            }


        }

    }

    @Override
    public int getItemCount() {
        return model.size();
    }

    public  class  ViewHolder extends RecyclerView.ViewHolder{

        VendorBidsListLayoutBinding binding ;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = VendorBidsListLayoutBinding.bind(itemView);
        }
    }
}
