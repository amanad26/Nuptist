package com.Nuptist.VendorPackages;

import static com.Nuptist.RetrofitApis.BaseUrls.IMAGE_URL;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.Nuptist.CreateOfferServiceActivity;
import com.Nuptist.TemsAndConditionActivity;
import com.Nuptist.calender.AddOnceNew.FinalAddonsNewModel;
import com.Nuptist.Models.BookingModels.BookingAddOnceModel;
import com.Nuptist.R;
import com.Nuptist.RetrofitApis.BidInterface;
import com.deishelon.roundedbottomsheet.RoundedBottomSheetDialog;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import soup.neumorphism.NeumorphCardView;

public class AddOnceAdapterVendor extends RecyclerView.Adapter<AddOnceAdapterVendor.ViewHolder>{

    BidInterface bidInterface ;
    Context context ;
    boolean isChecked = false ;
  ////  List<AddOncesModelNew.AddOnceData.AllAddOnces.PackageDataInAddonce> model ;
    public static  List<BookingAddOnceModel> addOnceModels = new ArrayList<>();

    List<FinalAddonsNewModel.FinalAddOnsData.ProductId> model ;
    List<FinalAddonsNewModel.FinalAddOnsData.ServiceId> model2 ;

    public AddOnceAdapterVendor(BidInterface bidInterface, Context context, List<FinalAddonsNewModel.FinalAddOnsData.ProductId> model, List<FinalAddonsNewModel.FinalAddOnsData.ServiceId> model2) {
        this.bidInterface = bidInterface;
        this.context = context;
        this.model = model;
        this.model2 = model2;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       return  new ViewHolder(LayoutInflater.from(context).inflate(R.layout.add_once_new_layout,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        try {
            if (position < model.size()) {

                if (model.size() != 0) {
                    FinalAddonsNewModel.FinalAddOnsData.ProductId.Data__2 data = model.get(position).getData();
                    holder.title.setText(data.getProductName());
                   // holder.price.setText("Rs. "+data.getRecommendedPrice());
                    holder.price.setText("Rs. " + NumberFormat.getIntegerInstance().format(Integer.parseInt(data.getRecommendedPrice())));

                    ////holder.price.setText("Rs. "+data.getBid_min()+" - "+data.getBid_max());

                    holder.bid_count.setText(data.getCount());
                    holder.bid_noe.setOnClickListener(view -> showDialog2(data.getId(), holder.getAbsoluteAdapterPosition()));

                    holder.itemView.setOnClickListener(new View.OnClickListener() {
                     @Override
                     public void onClick(View view) {
                         context.startActivity(new Intent(context, CreateOfferServiceActivity.class)
                                 .putExtra("type","1")
                                 .putExtra("id",data.getId())
                         );
                     }
                 });

                    try {
                        if(data.getVender_status().equalsIgnoreCase("1")){
                            holder.bid_noe_inactive.setVisibility(View.VISIBLE);
                            holder.bid_noe.setVisibility(View.GONE);
                        }else {
                            holder.bid_noe_inactive.setVisibility(View.GONE);
                            holder.bid_noe.setVisibility(View.VISIBLE);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    try {
                        Picasso.get().load(IMAGE_URL+data.getImage()).into(holder.image);
                        Log.e("TAG", "onBindViewHolder() called with: Image Url = "+ IMAGE_URL+"  Image Path "+data.getImage());
                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.e("TAG", "onBindViewHolder() called with: Image error "+e.getLocalizedMessage());

                    }

                }

            } else {


                if (model2.size() != 0) {
                    int pos = position - model.size();

                    FinalAddonsNewModel.FinalAddOnsData.ServiceId.Data__1  data = model2.get(pos).getData();
                    holder.title.setText(data.getName());
                   /// holder.price.setText("Rs. "+data.getBid_max()+" - "+data.getBid_min());
                  //  holder.price.setText("Rs. "+data.getRecommendedPrice());
                    holder.price.setText("Rs. " + NumberFormat.getIntegerInstance().format(Integer.parseInt(data.getRecommendedPrice())));

                    holder.bid_count.setText(data.getCount());

                    holder.bid_noe.setOnClickListener(view -> showDialog(data.getId(),pos));

                    try {
                        if(data.getVender_status().equalsIgnoreCase("1")){
                            holder.bid_noe_inactive.setVisibility(View.VISIBLE);
                            holder.bid_noe.setVisibility(View.GONE);
                        }else {
                            holder.bid_noe_inactive.setVisibility(View.GONE);
                            holder.bid_noe.setVisibility(View.VISIBLE);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    holder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            context.startActivity(new Intent(context, CreateOfferServiceActivity.class)
                                    .putExtra("type","0")
                                    .putExtra("id",data.getId())
                            );
                        }
                    });

                    try {
                        Picasso.get().load(IMAGE_URL+data.getImage()).into(holder.image);
                        Log.e("TAG", "onBindViewHolder() called with: Image Url = "+ IMAGE_URL+"  Image Path "+data.getImage());
                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.e("TAG", "onBindViewHolder() called with: Image error "+e.getLocalizedMessage());

                    }


                }


            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    private void showDialog(String id, int holder) {

        RoundedBottomSheetDialog mBottomSheetDialog = new RoundedBottomSheetDialog(context);
        View sheetView = mBottomSheetDialog.getLayoutInflater().inflate(R.layout.add_bid_layout, null);

        mBottomSheetDialog.setContentView(sheetView);

        TextView update_dob = mBottomSheetDialog.findViewById(R.id.update_dob);
        EditText maximum_amoint = mBottomSheetDialog.findViewById(R.id.maximum_amoint);
        EditText minimum_amount = mBottomSheetDialog.findViewById(R.id.minimum_amount);
        NeumorphCardView add_bid_btn = mBottomSheetDialog.findViewById(R.id.add_bid_btn);
        LinearLayout parent_layout = mBottomSheetDialog.findViewById(R.id.parent_layout);
        CheckBox terms_and_condition = mBottomSheetDialog.findViewById(R.id.terms_and_condition);
        TextView terms_condition_tv = mBottomSheetDialog.findViewById(R.id.terms_condition_tv);


        terms_condition_tv.setOnClickListener(view -> {
            context.startActivity(new Intent(context, TemsAndConditionActivity.class).putExtra("type","Groom"));

        });


        terms_and_condition.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                isChecked = b ;
            }
        });


        parent_layout.setOnClickListener(view -> mBottomSheetDialog.dismiss());

        update_dob.setOnClickListener(view -> {
            selectDate(update_dob);
        });

        add_bid_btn.setOnClickListener(view -> {
            if(maximum_amoint.getText().toString().equalsIgnoreCase(""))
              maximum_amoint.setError("Enter Amount");
            else if(!isChecked)
                Toast.makeText(context, "Accept Terms And Conditions..!", Toast.LENGTH_SHORT).show();
           else {

                bidInterface.addBid(minimum_amount.getText().toString(),maximum_amoint.getText().toString(),"", id,id);
                notifyItemRemoved(holder);
                notifyItemRangeChanged(0,model2.size()-1);
                model2.remove(holder);
                mBottomSheetDialog.dismiss();
            }

        });

//      mBottomSheetDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.ANSPARENT));
        mBottomSheetDialog.setContentView(sheetView);
        mBottomSheetDialog.show();

    }

    private void showDialog2(String id, int holder) {

        RoundedBottomSheetDialog mBottomSheetDialog = new RoundedBottomSheetDialog(context);
        View sheetView = mBottomSheetDialog.getLayoutInflater().inflate(R.layout.add_bid_layout, null);

        mBottomSheetDialog.setContentView(sheetView);

        TextView update_dob = mBottomSheetDialog.findViewById(R.id.update_dob);
        EditText maximum_amoint = mBottomSheetDialog.findViewById(R.id.maximum_amoint);
        EditText minimum_amount = mBottomSheetDialog.findViewById(R.id.minimum_amount);
        NeumorphCardView add_bid_btn = mBottomSheetDialog.findViewById(R.id.add_bid_btn);
        CheckBox terms_and_condition = mBottomSheetDialog.findViewById(R.id.terms_and_condition);
        LinearLayout parent_layout = mBottomSheetDialog.findViewById(R.id.parent_layout);

        TextView terms_condition_tv = mBottomSheetDialog.findViewById(R.id.terms_condition_tv);


        terms_condition_tv.setOnClickListener(view -> {
            context.startActivity(new Intent(context, TemsAndConditionActivity.class).putExtra("type","Groom"));

        });


        terms_and_condition.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                isChecked = b ;
            }
        });

        parent_layout.setOnClickListener(view -> mBottomSheetDialog.dismiss());

        update_dob.setOnClickListener(view -> {
            selectDate(update_dob);
        });

        add_bid_btn.setOnClickListener(view -> {
            if(maximum_amoint.getText().toString().equalsIgnoreCase(""))
                maximum_amoint.setError("Enter Amount");
            else if(!isChecked)
                Toast.makeText(context, "Accept Terms And Conditions..!", Toast.LENGTH_SHORT).show();
            else  {
                bidInterface.addBid2(minimum_amount.getText().toString(),maximum_amoint.getText().toString(),"", id,id);
                notifyItemRemoved(holder);
                notifyItemRangeChanged(0,model.size()-1);
                model.remove(holder);
                isChecked = false ;
                mBottomSheetDialog.dismiss();
            }


        });

//      mBottomSheetDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.ANSPARENT));
        mBottomSheetDialog.setContentView(sheetView);
        mBottomSheetDialog.show();

    }


    private void selectDate(TextView DateBtn) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(context, (datePicker, year1, month1, day1) -> {
            String date = day1 + " /" + getMonthName((month1 + 1)) + "/ " +
                    "/" + year1;
            DateBtn.setText(date);
        }, year, month, day);
        datePickerDialog.show();
    }

    public String getMonthName(int month) {

        String monthString;
        switch (month) {
            case 1:
                monthString = "Jan";
                break;
            case 2:
                monthString = "Feb";
                break;
            case 3:
                monthString = "Mar";
                break;
            case 4:
                monthString = "Apr";
                break;
            case 5:
                monthString = "May";
                break;
            case 6:
                monthString = "Jun";
                break;
            case 7:
                monthString = "Jul";
                break;
            case 8:
                monthString = "Aug";
                break;
            case 9:
                monthString = "Sep";
                break;
            case 10:
                monthString = "Oct";
                break;
            case 11:
                monthString = "Nov";
                break;
            case 12:
                monthString = "Dec";
                break;
            default:
                monthString = "Inv month";
                break;
        }
        System.out.println(monthString);

        return monthString;
    }

    @Override
    public int getItemCount() {
        return model.size()+model2.size();
    }


    public  class  ViewHolder extends RecyclerView.ViewHolder{

        TextView title , price ,bid_count ;
        ImageView image ;
        LinearLayout bid_noe ,bid_noe_inactive ;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.add_once_image);
            title = itemView.findViewById(R.id.add_once_title);
            price = itemView.findViewById(R.id.add_once_price);
            bid_noe = itemView.findViewById(R.id.bid_noe);
            bid_count = itemView.findViewById(R.id.bid_count);
            bid_noe_inactive = itemView.findViewById(R.id.bid_noe_inactive);
        }
    }

}
