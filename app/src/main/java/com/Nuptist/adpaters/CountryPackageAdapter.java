package com.Nuptist.adpaters;

import static com.Nuptist.RetrofitApis.BaseUrls.IMAGE_URL;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.Nuptist.DetailsActivity;
import com.Nuptist.Models.CountryPackageModel;
import com.Nuptist.Models.GetPackageModel;
import com.Nuptist.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CountryPackageAdapter extends RecyclerView.Adapter<CountryPackageAdapter.ViewHolder>{
    Context context ;
    List<CountryPackageModel.PackageCountryData> model ;
    String path ;
    int count ;

    public CountryPackageAdapter(Context context, List<CountryPackageModel.PackageCountryData> model, int count) {
        this.context = context;
        this.model = model;
        this.count = count;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.package_layout,parent,false));
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
                    .putExtra("p_id",model.get(holder.getAdapterPosition()).getPackageId())
                    .putExtra("image",IMAGE_URL+model.get(position).getImage()));
        });

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
