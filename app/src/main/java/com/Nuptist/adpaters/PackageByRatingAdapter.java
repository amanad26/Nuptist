package com.Nuptist.adpaters;

import static com.Nuptist.RetrofitApis.BaseUrls.IMAGE_URL;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.Nuptist.DetailsActivity;
import com.Nuptist.Models.PackageByRatingModel;
import com.Nuptist.R;
import com.Nuptist.databinding.PolularPackagesLayoutBinding;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PackageByRatingAdapter extends RecyclerView.Adapter<PackageByRatingAdapter.ViewHolder> {

    Context context;
    List<PackageByRatingModel.PackageRatingData> models;
    String imageUrl = "";

    public PackageByRatingAdapter(Context context, List<PackageByRatingModel.PackageRatingData> models, String imageUrl) {
        this.context = context;
        this.models = models;
        this.imageUrl = imageUrl;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.polular_packages_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        try {
            holder.binding.packagePrice.setText(models.get(position).getPrice());
            holder.binding.packageTitle.setText(models.get(position).getPackage_name());


            try {
                if (models.get(position).getAverage_rating().equalsIgnoreCase("") || models.get(position).getAverage_rating() == null)
                    holder.binding.someId.setText("0");
                else {
                    float rating = Float.parseFloat(models.get(position).getAverage_rating());
                    rating = Math.round(rating);
                    holder.binding.someId.setText(String.valueOf(rating));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }


            Picasso.get().load(imageUrl + models.get(position).getImage()).into(holder.binding.imagePackage);


            holder.binding.tochIt.setOnClickListener(view -> {
                Log.e("TAG", "onBindViewHolder() called with: Package id  = [" + models.get(position).getPackageId() + "], position = [" + position + "]");
                context.startActivity(new Intent(context, DetailsActivity.class)
                        .putExtra("p_id",models.get(position).getPackageId())
                        .putExtra("image",IMAGE_URL+models.get(position).getImage()));
            });

        } catch (Exception e) {
            Log.e("TAG", "onBindViewHolder() called with: holder = [" + holder + "], error = [" + e.getLocalizedMessage() + "]");
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return Math.min(models.size(), 15);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        PolularPackagesLayoutBinding binding;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = PolularPackagesLayoutBinding.bind(itemView);
        }
    }

}
