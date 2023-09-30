package com.Nuptist.adpaters;

import static com.Nuptist.RetrofitApis.BaseUrls.IMAGE_URL;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.Nuptist.DetailsActivity;
import com.Nuptist.DetailsActivityAdapter;
import com.Nuptist.Models.MyBidsModel;
import com.Nuptist.Models.PackageDetailsModel;
import com.Nuptist.Models.ProgressDialog;
import com.Nuptist.R;
import com.Nuptist.RetrofitApis.ApiInterface;
import com.Nuptist.RetrofitApis.RetrofitClient;
import com.Nuptist.Session.Session;
import com.Nuptist.databinding.MyBidsLayoutBinding;
import com.google.android.material.tabs.TabLayoutMediator;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyBidAdapter extends RecyclerView.Adapter<MyBidAdapter.ViewHolder>{

    Context context ;
    List<MyBidsModel.BidData> model;
    Session session ;

    public MyBidAdapter(Context context, List<MyBidsModel.BidData> model) {
        this.context = context;
        this.model = model;
        session = new Session(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return  new ViewHolder(LayoutInflater.from(context).inflate(R.layout.my_bids_layout,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.binding.maxPrice.setText("Rs. "+model.get(position).getMaximumPrice());
        holder.binding.minPrice.setText(model.get(position).getMinimumPrice());

//        holder.binding.minPrice.setText(NumberFormat.getIntegerInstance().format(Integer.parseInt(model.get(position).getMinimumPrice())));
//        holder.binding.maxPrice.setText(NumberFormat.getIntegerInstance().format(Integer.parseInt(model.get(position).getMaximumPrice())));

        getPackageDetials(holder, model.get(position).getPackageId());

    }


    private void getPackageDetials(ViewHolder holder, String packageId) {

        ApiInterface apiInterface = RetrofitClient.getclient(context);

        apiInterface.getPackagesDetails(packageId, session.getUserId()).enqueue(new Callback<PackageDetailsModel>() {
            @Override
            public void onResponse(@NonNull Call<PackageDetailsModel> call, @NonNull Response<PackageDetailsModel> response) {


                try {

                    if (response.body() != null) {
                        if (response.body().getResult().equalsIgnoreCase("true")) {

                            PackageDetailsModel.PacjkageDetailsData.Packages data = response.body().getData().getPackages();

                            String res = data.getCity().substring(0, 1).toUpperCase() + data.getCity().substring(1);

                            holder.binding.packageName.setText(data.getPackageName());
                            try {
                                Picasso.get().load(IMAGE_URL+response.body().getData().getPackages().getImage()).into(holder.binding.pcakgeImage);
                                if (data.getPrice().equalsIgnoreCase("")) {
                                    holder.binding.minPrice.setText("Rs.0");
                                } else {
//                                    holder.binding.minPrice.setText("Rs. "+data.getPrice());
                                    holder.binding.minPrice.setText("Rs. "+NumberFormat.getIntegerInstance().format(Integer.parseInt(data.getPrice())));
                                }
                                Log.e("TAG", "Start Date  = [" + data.getStartDate() + "]");
                            }catch (Exception e){
                                e.getLocalizedMessage();
                                Log.e("TAG", "Start Date Error  = [" + e.getLocalizedMessage() + "]");
                            }

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

    public  class  ViewHolder extends RecyclerView.ViewHolder{
        MyBidsLayoutBinding binding ;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = MyBidsLayoutBinding.bind(itemView);
        }
    }
}
