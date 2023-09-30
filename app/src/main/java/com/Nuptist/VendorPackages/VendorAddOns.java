package com.Nuptist.VendorPackages;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.Nuptist.calender.AddOnceNew.FilterdFinalAddonsModel;
import com.Nuptist.R;
import com.Nuptist.RetrofitApis.BidInterface;
import com.Nuptist.databinding.AddOnceLayoutOneBinding;

import java.util.List;

public class VendorAddOns extends RecyclerView.Adapter<VendorAddOns.ViewHolder>{

    Context context ;
//    List<FinalAdOns> model ;
    BidInterface bidInterface ;


    List<FilterdFinalAddonsModel> model ;


    public VendorAddOns(Context context, BidInterface bidInterface, List<FilterdFinalAddonsModel> model) {
        this.context = context;
        this.bidInterface = bidInterface;
        this.model = model;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.add_once_layout_one,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.binding.addonsCategroryName.setText(model.get(position).getTitle());

        if (position == 0){
            holder.binding.image.setImageResource(R.drawable.ic_arrow_up);
            model.get(holder.getAbsoluteAdapterPosition()).setOpened(true);
            holder.binding.addOnceRecycler.setLayoutManager(new LinearLayoutManager(context));
            holder.binding.addOnceRecycler.setAdapter(new AddOnceAdapterVendor(bidInterface, context, model.get(holder.getAdapterPosition()).getProductIds(), model.get(holder.getAdapterPosition()).getServiceIdList()));
            holder.binding.addOnceRecycler.setVisibility(View.VISIBLE);
        }

        holder.itemView.setOnClickListener(v -> {
            if(model.get(holder.getAbsoluteAdapterPosition()).isOpened()){
                holder.binding.image.setImageResource(R.drawable.ic_arrow_down);
                model.get(holder.getAbsoluteAdapterPosition()).setOpened(false);
                holder.binding.addOnceRecycler.setVisibility(View.GONE);
            }else {
                holder.binding.image.setImageResource(R.drawable.ic_arrow_up);
                model.get(holder.getAbsoluteAdapterPosition()).setOpened(true);
                holder.binding.addOnceRecycler.setLayoutManager(new LinearLayoutManager(context));
                holder.binding.addOnceRecycler.setAdapter(new AddOnceAdapterVendor(bidInterface, context, model.get(holder.getAdapterPosition()).getProductIds(), model.get(holder.getAdapterPosition()).getServiceIdList()));
                holder.binding.addOnceRecycler.setVisibility(View.VISIBLE);
            }


        });

    }

    @Override
    public int getItemCount() {
        return model.size();
    }


    public  class  ViewHolder extends RecyclerView.ViewHolder{
        AddOnceLayoutOneBinding binding ;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = AddOnceLayoutOneBinding.bind(itemView);
        }
    }

}
