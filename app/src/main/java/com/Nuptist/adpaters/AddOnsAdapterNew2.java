package com.Nuptist.adpaters;

import static com.Nuptist.DetailsActivity.packageDetailsInterface;
import static com.Nuptist.RetrofitApis.BaseUrls.IMAGE_URL;
import static com.Nuptist.Utils.OfferAddonsToast;
import static com.Nuptist.VenueFragment.bookPackageInterface;
import static com.Nuptist.VenueFragment.selectedDate;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.Nuptist.CreateOfferServiceActivity;
import com.Nuptist.calender.AddOnceNew.FinalAddonsNewModel;
import com.Nuptist.Models.BookingModels.BookingAddOnceModel;
import com.Nuptist.PhotographyOfferActivity;
import com.Nuptist.R;
import com.Nuptist.databinding.AddOnceLayoutBinding;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class AddOnsAdapterNew2 extends RecyclerView.Adapter<AddOnsAdapterNew2.ViewHolder> {

    Context context;

    List<FinalAddonsNewModel.FinalAddOnsData.ProductId> model;
    List<FinalAddonsNewModel.FinalAddOnsData.ServiceId> model2;
    public static List<BookingAddOnceModel> addOnceModels = new ArrayList<>();
    String package_id;

    public static String vendor_price = "";
    String type = "";

    public AddOnsAdapterNew2(Context context, List<FinalAddonsNewModel.FinalAddOnsData.ProductId> model, List<FinalAddonsNewModel.FinalAddOnsData.ServiceId> model2, String pid) {
        this.context = context;
        this.model = model;
        this.model2 = model2;
        this.package_id = pid;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.add_once_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        try {
            if (position < model.size()) {
                FinalAddonsNewModel.FinalAddOnsData.ProductId.Data__2 data = model.get(position).getData();
                holder.binding.addOnceTitle.setText(data.getProductName());


                try {
                    if (data.getRecommendedPrice().equalsIgnoreCase("")) {
                        holder.binding.addOncePrice.setText("Rs. 0");
                    } else {
                        holder.binding.addOncePrice.setText("Rs. " + NumberFormat.getIntegerInstance().format(Integer.parseInt(data.getRecommendedPrice())));
                        //holder.binding.addOncePrice.setText("Rs. " + data.getRecommendedPrice());
                    }
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }


                try {
                    Picasso.get().load(IMAGE_URL + data.getImage()).into(holder.binding.addOnceImage);
                } catch (Exception e) {
                    e.printStackTrace();
                }


                if (addOnceModels.size() != 0) {
                    for (int i = 0; i < addOnceModels.size(); i++) {
                        Log.e("TAG", "onBindViewHolder() service  id = [" + addOnceModels.get(i).getService_id() + "], data.getId() = [" + data.getId() + "]");
                        if (addOnceModels.get(i).getProduct_id().equalsIgnoreCase(data.getId())) {
                            holder.binding.addBtn.setVisibility(View.GONE);
                            holder.binding.cancleBtn.setVisibility(View.VISIBLE);
                        }

                    }

                }

                holder.binding.cancleBtn.setOnClickListener(view -> {
                    holder.binding.addBtn.setVisibility(View.VISIBLE);
                    holder.binding.cancleBtn.setVisibility(View.GONE);
                    for (int i = 0; i < addOnceModels.size(); i++) {
                        if (addOnceModels.get(i).getProduct_id().equalsIgnoreCase(data.getId())) {
                            addOnceModels.remove(i);
                            packageDetailsInterface.onAddonsRemove();
                        }
                    }
                    data.setIsselected(false);
                    Log.e("TAG", "onClick() called with: Remove Add Once = [" + addOnceModels.toString() + "]");
                });



                holder.binding.addBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        if (!selectedDate.equalsIgnoreCase("")) {
                            if (data.getCount().equalsIgnoreCase("0")) {

                                holder.binding.addBtn.setVisibility(View.GONE);
                                holder.binding.cancleBtn.setVisibility(View.VISIBLE);

                                if (!data.isIsselected()) {
                                    addOnceModels.add(new BookingAddOnceModel(data.getId()
                                            , data.getProductName()
                                            , data.getRecommendedPrice()
                                            , data.getRecommendedPrice()
                                            , data.getId()
                                            , "0"
                                            , "0"
                                            , ""
                                    ));
                                    data.setIsselected(true);
                                    packageDetailsInterface.onAddonsAdd();
                                }


                                Log.e("TAG", "onClick() called with: Remove Add Once = [" + addOnceModels.toString() + "]");
                            } else {
                                context.startActivity(new Intent(context, PhotographyOfferActivity.class)
                                        .putExtra("addon_product_id", data.getId())
                                        .putExtra("name", data.getProductName())
                                        .putExtra("addon_product_data", (Serializable) data)
                                        .putExtra("p_id", package_id)
                                );
                            }



                        } else {
                             showErrorBox();
                            // Toast.makeText(context, OfferAddonsToast, Toast.LENGTH_SHORT).show();
                        }


                    }
                });


                holder.binding.cardview.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        type = "product";

                        context.startActivity(new Intent(context, CreateOfferServiceActivity.class)
                                .putExtra("type","1")
                                .putExtra("id",data.getId())
                        );
//                        if (!selectedDate.equalsIgnoreCase("")) {
//                            if (data.getCount().equalsIgnoreCase("0")) {
//
//                                holder.binding.addBtn.setVisibility(View.GONE);
//                                holder.binding.cancleBtn.setVisibility(View.VISIBLE);
//
//                                if (!data.isIsselected()) {
//                                    addOnceModels.add(new BookingAddOnceModel(data.getId()
//                                            , data.getProductName()
//                                            , data.getRecommendedPrice()
//                                            , data.getRecommendedPrice()
//                                            , data.getId()
//                                            , "0"
//                                            , "0"
//                                            , ""
//                                    ));
//                                    data.setIsselected(true);
//                                }
//
//
//                                Log.e("TAG", "onClick() called with: Remove Add Once = [" + addOnceModels.toString() + "]");
//                            } else {
//                                context.startActivity(new Intent(context, PhotographyOfferActivity.class)
//                                        .putExtra("addon_product_id", data.getId())
//                                        .putExtra("name", data.getProductName())
//                                        .putExtra("addon_product_data", (Serializable) data)
//                                        .putExtra("p_id", package_id)
//                                );
//                            }
//                        } else {
//                            // Toast.makeText(context, OfferAddonsToast, Toast.LENGTH_SHORT).show();
//                        }


                    }
                });


                holder.binding.addViewDetails.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        context.startActivity(new Intent(context, CreateOfferServiceActivity.class)
                                .putExtra("type","1")
                                .putExtra("id",data.getId())
                        );
                    }
                });


                holder.binding.addOnceTitle.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        context.startActivity(new Intent(context, CreateOfferServiceActivity.class)
                                .putExtra("type","1")
                                .putExtra("id",data.getId())
                        );
                    }
                });

            } else {
                int pos = position - model.size();

                FinalAddonsNewModel.FinalAddOnsData.ServiceId.Data__1 data = model2.get(pos).getData();
                holder.binding.addOnceTitle.setText(data.getName());


                try {

                    if (data.getRecommendedPrice().equalsIgnoreCase("")) {
                        holder.binding.addOncePrice.setText("Rs. 0");
                    } else {
                        holder.binding.addOncePrice.setText("Rs. " + NumberFormat.getIntegerInstance().format(Integer.parseInt(data.getRecommendedPrice())));
                    //    holder.binding.addOncePrice.setText("Rs. " + data.getRecommendedPrice());
                    }
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }


                if (addOnceModels.size() != 0) {

                    for (int i = 0; i < addOnceModels.size(); i++) {
                        Log.e("TAG", "onBindViewHolder() service  id = [" + addOnceModels.get(i).getService_id() + "], data.getId() = [" + data.getId() + "]");
                        if (addOnceModels.get(i).getService_id().equalsIgnoreCase(data.getId())) {
                            holder.binding.addBtn.setVisibility(View.GONE);
                            holder.binding.cancleBtn.setVisibility(View.VISIBLE);
                        }

                    }

                }


                try {
                    Picasso.get().load(IMAGE_URL + data.getImage()).into(holder.binding.addOnceImage);
                } catch (Exception e) {
                    e.printStackTrace();
                }


                holder.binding.cancleBtn.setOnClickListener(view -> {
                    holder.binding.addBtn.setVisibility(View.VISIBLE);
                    holder.binding.cancleBtn.setVisibility(View.GONE);
                    for (int i = 0; i < addOnceModels.size(); i++) {
                        if (addOnceModels.get(i).getService_id().equalsIgnoreCase(data.getId())) {
                            addOnceModels.remove(i);
                          packageDetailsInterface.onAddonsRemove();
                        }
                    }
                    data.setIsselected(false);
                    Log.e("TAG", "onClick() called with: Remove Add Once = [" + addOnceModels.toString() + "]");
                });



                holder.binding.addBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        if (!selectedDate.equalsIgnoreCase("")) {
                            if (data.getCount().equalsIgnoreCase("0")) {
                                holder.binding.addBtn.setVisibility(View.GONE);
                                holder.binding.cancleBtn.setVisibility(View.VISIBLE);


                                if (!data.isIsselected()) {
                                    addOnceModels.add(new BookingAddOnceModel(data.getId()
                                            , data.getName()
                                            , data.getRecommendedPrice()
                                            , data.getRecommendedPrice()
                                            , "0"
                                            , data.getId()
                                            , "0"
                                            , ""
                                    ));
                                    packageDetailsInterface.onAddonsAdd();
                                    data.setIsselected(true);
                                }

                                Log.e("TAG", "onClick() called with: Remove Add Once = [" + addOnceModels.toString() + "]");

                            } else {
                                type = "service";
                                context.startActivity(new Intent(context, PhotographyOfferActivity.class)
                                        .putExtra("addon_service_id", data.getId())
                                        .putExtra("addon_service_data", (Serializable) data)
                                        .putExtra("name", data.getName())
                                        .putExtra("p_id", package_id)
                                );
                            }
                        } else {
                            showErrorBox();
                            // Toast.makeText(context, OfferAddonsToast, Toast.LENGTH_SHORT).show();
                        }


                    }
                });


                holder.binding.cardview.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

//                        if (!selectedDate.equalsIgnoreCase("")) {
//                            if (data.getCount().equalsIgnoreCase("0")) {
//                                holder.binding.addBtn.setVisibility(View.GONE);
//                                holder.binding.cancleBtn.setVisibility(View.VISIBLE);
//
//
//                                if (!data.isIsselected()) {
//                                    addOnceModels.add(new BookingAddOnceModel(data.getId()
//                                            , data.getName()
//                                            , data.getRecommendedPrice()
//                                            , data.getRecommendedPrice()
//                                            , "0"
//                                            , data.getId()
//                                            , "0"
//                                            , ""
//                                    ));
//                                    data.setIsselected(true);
//                                }
//
//                                Log.e("TAG", "onClick() called with: Remove Add Once = [" + addOnceModels.toString() + "]");
//
//                            } else {
//                                type = "service";
//                                context.startActivity(new Intent(context, PhotographyOfferActivity.class)
//                                        .putExtra("addon_service_id", data.getId())
//                                        .putExtra("addon_service_data", (Serializable) data)
//                                        .putExtra("name", data.getName())
//                                        .putExtra("p_id", package_id)
//                                );
//                            }
//                        } else {
//                            // Toast.makeText(context, OfferAddonsToast, Toast.LENGTH_SHORT).show();
//                        }
                        context.startActivity(new Intent(context, CreateOfferServiceActivity.class)
                                .putExtra("type", "0")
                                .putExtra("id", data.getId())
                        );

                    }
                });

                holder.binding.addViewDetails.setOnClickListener(view -> {
                    context.startActivity(new Intent(context, CreateOfferServiceActivity.class)
                            .putExtra("type", "0")
                            .putExtra("id", data.getId())
                    );
                });

                holder.binding.addOnceTitle.setOnClickListener(view ->
                        context.startActivity(new Intent(context, CreateOfferServiceActivity.class)
                                .putExtra("type", "0")
                                .putExtra("id", data.getId())

                ));

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private  void showErrorBox() {
        final Dialog dialog = new Dialog(context);

        dialog.setContentView(R.layout.select_date_layout);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(false);
        dialog.getWindow().getAttributes().windowAnimations = R.style.animation;

        LinearLayout close = dialog.findViewById(R.id.dismiss);
        TextView viewPrivacyPolicy = dialog.findViewById(R.id.continue_anyway);

        close.setOnClickListener(view -> dialog.dismiss());

        viewPrivacyPolicy.setOnClickListener(v -> {
            bookPackageInterface.onDateChange();
            dialog.dismiss();

        });

        dialog.show();



    }


    @Override
    public int getItemCount() {
        return model.size() + model2.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        AddOnceLayoutBinding binding;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = AddOnceLayoutBinding.bind(itemView);
        }
    }
}
