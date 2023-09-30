package com.Nuptist.adpaters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.Nuptist.calender.AddOnceNew.OfferFinalModel;
import com.Nuptist.R;
import com.Nuptist.databinding.AddOnceLayoutOneBinding;

import java.util.List;

public class OfferAdapter extends RecyclerView.Adapter<OfferAdapter.ViewHolder>{

    Context context ;
    List<OfferFinalModel> model ;
    String package_id  ;



    public OfferAdapter(Context context, List<OfferFinalModel> model, String id ) {
        this.context = context;
        this.model = model;
        this.package_id = id ;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
     return  new ViewHolder(LayoutInflater.from(context).inflate(R.layout.add_once_layout_one,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.binding.addonsCategroryName.setText(model.get(position).getTitle());


        if(position == 0 ){
            holder.binding.image.setImageResource(R.drawable.ic_arrow_up);
            model.get(holder.getAbsoluteAdapterPosition()).setOpened(true);
            holder.binding.addOnceRecycler.setLayoutManager(new LinearLayoutManager(context));
            holder.binding.addOnceRecycler.setAdapter(new OfferAdapter2(context,model.get(holder.getAbsoluteAdapterPosition()).getProductOfferDetailsIds(),model.get(holder.getAbsoluteAdapterPosition()).getServiceOfferDetailsIds(), package_id));
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
                holder.binding.addOnceRecycler.setAdapter(new OfferAdapter2(context,model.get(holder.getAbsoluteAdapterPosition()).getProductOfferDetailsIds(),model.get(holder.getAbsoluteAdapterPosition()).getServiceOfferDetailsIds(), package_id));
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
