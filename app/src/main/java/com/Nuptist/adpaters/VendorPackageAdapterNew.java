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

import com.Nuptist.Models.GetPackageModel;
import com.Nuptist.R;
import com.Nuptist.VendorPackages.VendorPackagedetailsActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

public class VendorPackageAdapterNew extends RecyclerView.Adapter<VendorPackageAdapterNew.ViewHolder>{
    Context context ;
    List<GetPackageModel.PackageData> model ;
    String path ;


    public VendorPackageAdapterNew(Context context, List<GetPackageModel.PackageData> model, String path) {
        this.context = context;
        this.model = model;
        this.path = path;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       return  new ViewHolder(LayoutInflater.from(context).inflate(R.layout.package_layout,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        try {

            holder.name.setText(model.get(position).getPackageName());

            Picasso.get().load(IMAGE_URL+model.get(position).getImage()).placeholder(R.drawable.decoration).into(holder.image);


            holder.itemView.setOnClickListener(view -> context.startActivity(new Intent(context, VendorPackagedetailsActivity.class)
                    .putExtra("p_id",model.get(holder.getAdapterPosition()).getId())
                    .putExtra("image",IMAGE_URL+model.get(holder.getAdapterPosition()).getImage())
                   ));

            holder.address.setText(model.get(position).getCity());
        } catch (Exception e) {
            Log.e("TAG", "onBindViewHolder() called with: holder = [" + holder + "], position = [" + position + "] exception e "+e.getLocalizedMessage());
            e.printStackTrace();
        }

    }

    public static String capitalizeSentence(String text) {
         return text.substring(0, 1).toUpperCase() + text.substring(1);
    }

    @Override
    public int getItemCount() {
        return model.size();
    }


    public  class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, address;
        ImageView image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.package_name);
            address = itemView.findViewById(R.id.package_address);
            image = itemView.findViewById(R.id.package_image);
        }

    }

}
