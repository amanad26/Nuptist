package com.Nuptist.adpaters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.Nuptist.R;
import com.Nuptist.databinding.VenueImagesLayoutBinding;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class VenueImagesAdapter extends RecyclerView.Adapter<VenueImagesAdapter.ViewHolder>{

    Context context;
    ArrayList<String> model ;

    public VenueImagesAdapter(Context context, ArrayList<String> model) {
        this.context = context;
        this.model = model;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return  new ViewHolder(LayoutInflater.from(context).inflate(R.layout.venue_images_layout,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

      Picasso.get().load(model.get(position)).placeholder(R.drawable.ic_nature_svg).into(holder.binding.imageId);

    }

    @Override
    public int getItemCount() {
        return model.size();
    }

    public  class  ViewHolder extends RecyclerView.ViewHolder{

        VenueImagesLayoutBinding binding ;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = VenueImagesLayoutBinding.bind(itemView);
        }
    }
}
