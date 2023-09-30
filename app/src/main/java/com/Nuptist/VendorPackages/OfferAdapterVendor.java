package com.Nuptist.VendorPackages;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.Nuptist.Models.PackageDetailsModel;
import com.Nuptist.R;
import com.Nuptist.databinding.AddOnceNewLayoutBinding;

import java.util.List;

public class OfferAdapterVendor extends RecyclerView.Adapter<OfferAdapterVendor.ViewHolder>{


    Context context ;
    List<PackageDetailsModel.PacjkageDetailsData.Offer> model ;
    String url ;


    public OfferAdapterVendor(Context context, List<PackageDetailsModel.PacjkageDetailsData.Offer> model, String url) {
        this.context = context;
        this.model = model;
        this.url = url;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.offer_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.binding.addOnceTitle.setText(model.get(position).getHinduWeddingSpecial());

    }

    @Override
    public int getItemCount() {
        return model.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        AddOnceNewLayoutBinding binding ;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = AddOnceNewLayoutBinding.bind(itemView);
        }
    }
}
