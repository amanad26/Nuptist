package com.Nuptist.VendorPackages;

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

import com.Nuptist.DetailsActivity;
import com.Nuptist.Models.PackageDetailsModel;
import com.Nuptist.Models.ReviewModel;
import com.Nuptist.Models.SearchPackageModel;
import com.Nuptist.R;
import com.Nuptist.RecySearchresultAdapter;
import com.Nuptist.RetrofitApis.ApiInterface;
import com.Nuptist.RetrofitApis.RetrofitClient;
import com.Nuptist.Session.Session;
import com.Nuptist.databinding.RecylerItemBinding;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VendorSearchPackageAdapter extends RecyclerView.Adapter<VendorSearchPackageAdapter.ViewHolder>{


    Context context;
    List<SearchPackageModel.SearchData> model ;
    Session session ;

    public VendorSearchPackageAdapter(Context context, List<SearchPackageModel.SearchData> model) {
        this.context = context;
        this.model = model;
        session = new Session(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(RecylerItemBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        try {
            holder.binding.addressPackage.setText(model.get(position).getCity());
        } catch (Exception e) {
            e.printStackTrace();
        }
        holder.binding.ratingPackage.setVisibility(View.GONE);
        holder.binding.ratingCard.setVisibility(View.GONE);
        holder.binding.namePackage.setText(model.get(position).getPackageName());
        holder.binding.maxGuestPackage.setText(model.get(position).getGuestCapycityMix()+" People");

        holder.binding.pricePackage.setText("Rs. "+model.get(position).getPrice());
        getPackageDetials(model.get(position).getPackageId(), holder);

        holder.binding.details.setOnClickListener(view -> {
            Intent intent = new Intent(context, VendorPackagedetailsActivity.class)
                    .putExtra("p_id",model.get(holder.getAdapterPosition()).getPackageId());
            context.startActivity(intent);
        });

    }

    private void getPackageDetials(String packageId, ViewHolder holder) {
        ApiInterface apiInterface = RetrofitClient.getclient(context);

        apiInterface.getPackagesDetails(packageId, session.getUserId()).enqueue(new Callback<PackageDetailsModel>() {
            @Override
            public void onResponse(@NonNull Call<PackageDetailsModel> call, @NonNull Response<PackageDetailsModel> response) {

                try {

                    if (response.body() != null) {
                        if (response.body().getResult().equalsIgnoreCase("true")) {
                            Picasso.get().load(IMAGE_URL+response.body().getData().getPackages().getImage()).placeholder(R.drawable.loading).into(holder.binding.imagePackage);
                        }
                    }

                } catch (Exception e) {
                    Log.e("Date--------->", "onResponse() called with: Start Date = [" + call + "], response = [" + e.getLocalizedMessage() + "]");
                    e.getLocalizedMessage();
                }

            }

            @Override
            public void onFailure(@NonNull Call<PackageDetailsModel> call, @NonNull Throwable t) {
                Log.e("TAG", "onFailure() called with: call = [" + call + "], t = [" + t.getLocalizedMessage() + "]");
            }
        });


    }


    @Override
    public int getItemCount() {
        return model.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        RecylerItemBinding binding;

        public ViewHolder(@NonNull RecylerItemBinding binding) {
            super(binding.getRoot());
            this.binding=binding;


        }
    }

}
