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
import com.Nuptist.Models.RecentPackagesModel;
import com.Nuptist.R;
import com.Nuptist.Session.Session;
import com.Nuptist.VendorHomePackagesFragment;
import com.Nuptist.VendorPackages.VendorPackagedetailsActivity;
import com.Nuptist.mainscreens.HomeUserFragment;
import com.airbnb.lottie.animation.content.Content;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RecentPackageAdapter extends RecyclerView.Adapter<RecentPackageAdapter.ViewHolder>{

    Context context ;
    Session session ;
    List<RecentPackagesModel.RecentPackageData> models ;

    public RecentPackageAdapter(Context context, List<RecentPackagesModel.RecentPackageData> models) {
        this.context = context;
        this.models = models;
        session = new Session(context);
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
            if(models.get(position).getCity() == null){
                holder.address.setText(models.get(position).getAddress());
            }else {
                holder.address.setText(models.get(position).getCity());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        holder.name.setText(models.get(position).getPackageName());

        Picasso.get().load(IMAGE_URL+models.get(position).getImage()).placeholder(R.drawable.loading).into(holder.image);

        holder.tochech.setOnClickListener(view -> {

            if (session.getUserType().equalsIgnoreCase("Vendor")) {
                if (session.getIsPersonal()) {
                    context.startActivity(new Intent(context, DetailsActivity.class)
                            .putExtra("p_id",models.get(holder.getAdapterPosition()).getPackageId())
                            .putExtra("image",IMAGE_URL+models.get(position).getImage()));

                } else {
                    context.startActivity(new Intent(context, VendorPackagedetailsActivity.class)
                            .putExtra("p_id",models.get(holder.getAdapterPosition()).getPackageId())
                            .putExtra("image",IMAGE_URL+models.get(position).getImage()));

                }

            } else {
                context.startActivity(new Intent(context, DetailsActivity.class)
                        .putExtra("p_id",models.get(holder.getAdapterPosition()).getPackageId())
                        .putExtra("image",IMAGE_URL+models.get(position).getImage()));

            }




        });

    }

    @Override
    public int getItemCount() {
        return models.size();
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
