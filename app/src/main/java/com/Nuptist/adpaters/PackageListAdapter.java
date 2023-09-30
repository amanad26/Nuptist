package com.Nuptist.adpaters;


import static android.content.ContentValues.TAG;
import static com.Nuptist.RetrofitApis.BaseUrls.IMAGE_URL;
import static com.Nuptist.RetrofitApis.BaseUrls.PACKAGE_IMAGE_URL;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.Nuptist.DetailsActivity;
import com.Nuptist.HomeActivity;
import com.Nuptist.Models.GetPackageModel;
import com.Nuptist.ProfileActivity;
import com.Nuptist.R;
import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PackageListAdapter extends RecyclerView.Adapter<PackageListAdapter.ViewHolder> {

    Context context ;
    List<GetPackageModel.PackageData> model ;
    String path ;
    int count ;

    public PackageListAdapter(Context context, List<GetPackageModel.PackageData> model, String path , int count) {
        this.context = context;
        this.model = model;
        this.path = path;
        this.count = count ;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.package_layout,parent,false);
        return  new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {


        try {
            if(model.get(position).getCity() == null){
                holder.address.setText(model.get(position).getAddress());
            }else {
                holder.address.setText(model.get(position).getCity());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        holder.name.setText(model.get(position).getPackageName());

            Picasso.get().load(IMAGE_URL+model.get(position).getImage()).placeholder(R.drawable.loading).into(holder.image);

            holder.tochech.setOnClickListener(view -> {
                context.startActivity(new Intent(context, DetailsActivity.class)
                        .putExtra("p_id",model.get(holder.getAdapterPosition()).getId())
                        .putExtra("image",IMAGE_URL+model.get(position).getImage()));
            });


    }

    public static String capitalizeSentence(String text) {
        return text.substring(0, 1).toUpperCase() + text.substring(1);
    }

    @Override
    public int getItemCount() {
      if(model.size() > 10){
          return count;
      }else {
          return  model.size();
      }
    }

    public  class  ViewHolder extends RecyclerView.ViewHolder{
        TextView name , address ;
        ImageView image ;
        LinearLayout tochech ;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.package_name);
            address = itemView.findViewById(R.id.package_address);
            image = itemView.findViewById(R.id.package_image);
            tochech = itemView.findViewById(R.id.tochech);
        }
    }
}
