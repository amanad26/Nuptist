package com.Nuptist;

import static com.Nuptist.RetrofitApis.BaseUrls.UPDATE_PROFILE_URL;
import static com.Nuptist.RetrofitApis.BaseUrls.VENDOR_UPDATE_PROFILE;
import static com.Nuptist.RetrofitApis.BaseUrls.update_image_user_vender;
import static com.Nuptist.venue.SearchVenueActivity.selected_address;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;

import com.Nuptist.Models.ProgressDialog;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.Nuptist.Models.AccountModel;
import com.Nuptist.Models.VendorProfileModel;
import com.Nuptist.RetrofitApis.ApiInterface;
import com.Nuptist.RetrofitApis.BaseUrls;
import com.Nuptist.RetrofitApis.RetrofitClient;
import com.Nuptist.Session.Session;
import com.Nuptist.databinding.ActivityProfileEditBinding;
import com.Nuptist.venue.AddVanueImagesActivity;
import com.Nuptist.venue.AddVenueActivity;
import com.Nuptist.venue.SearchVenueActivity;
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.ANRequest;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;

import me.rosuh.filepicker.config.FilePickerManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileEditActivity extends AppCompatActivity {

    private static final int USER_IMAGE = 200;
    ActivityProfileEditBinding binding;
    private File user_image = null;
    private ProgressDialog pd;
    Session session;
    private Bitmap bitmap;
    Uri filepath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProfileEditBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        session = new Session(ProfileEditActivity.this);
        pd = new ProgressDialog(ProfileEditActivity.this);


        binding.icBack.setOnClickListener(view -> finish());

        get_profile();

//        binding.userImage.setOnClickListener(v -> {
//            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
//            intent.setType("image/*");
//            startActivityForResult(intent,USER_IMAGE);
//        });

        binding.userImage.setOnClickListener(v ->

                        ImagePicker.Companion.with(ProfileEditActivity.this)
                                .crop()
                                .compress(200) //Final image size will be less than 1 MB(Optional)
                                .start(USER_IMAGE)

//                Intent intent = new Intent(Intent.ACTION_GET_CONTENT)
//                 intent.setType("image/*")
//              startActivityForResult(intent,code)
        );


        binding.updateSaveChanges.setOnClickListener(view -> {


            ProgressDialog pd = new ProgressDialog(ProfileEditActivity.this);
            pd.show();

            Log.e("TAG", "onClick() called with: view = [" + binding.updateDob.getText().toString() + "]");

            ANRequest.MultiPartBuilder anAdd = AndroidNetworking.upload((BaseUrls.URL + UPDATE_PROFILE_URL));
            anAdd.addMultipartParameter("user_id", session.getUserId());
            anAdd.addMultipartParameter("name", binding.updateUserName.getText().toString());
            anAdd.addMultipartParameter("address", binding.updateAddress.getText().toString());
            anAdd.addMultipartParameter("phone", binding.updateMobile.getText().toString());
            anAdd.addMultipartParameter("dob", binding.updateDob.getText().toString());
            anAdd.addMultipartParameter("profession_id", binding.updateProfession.getText().toString());
            // if (user_image != null) anAdd.addMultipartFile("image", user_image);
            //.setPriority(Priority.MEDIUM)
            anAdd.build()
                    .getAsJSONObject(new JSONObjectRequestListener() {
                        @Override
                        public void onResponse(JSONObject jsonObject) {
                            pd.dismiss();
                            try {
                                Log.d("---rrrProfile", "save_postsave_post" + jsonObject.toString());
                                // JSONObject obj = new JSONObject(response);
                                String result = jsonObject.getString("result");
                                pd.dismiss();

                                if (result.equalsIgnoreCase("true")) {
                                    Toast.makeText(ProfileEditActivity.this, "Profile Details Updated", Toast.LENGTH_SHORT).show();
                                    finish();
                                }

                            } catch (JSONException e) {
                                pd.dismiss();
                                Toast.makeText(ProfileEditActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                                e.printStackTrace();
                            }

                        }

                        @Override
                        public void onError(ANError anError) {
                            pd.dismiss();
                            Log.e("TAG", "onError: " + anError.getLocalizedMessage());

                        }
                    });

        });

        binding.updateDob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectDate(binding.updateDob);
            }
        });

        binding.updateAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ProfileEditActivity.this, SearchVenueActivity.class)
                        .putExtra("where", "user_profile"));
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!selected_address.equalsIgnoreCase("")) {
            binding.updateAddress.setText(selected_address);
            selected_address = "";
        }
    }

    /*  public String formatDate(String s) {
              //  s = "2022-10-21 08:08:18";

              String[] dateTime = s.split(" ");

              String dateS = dateTime[0];
              String timeS = dateTime[1];

              timeS = timeS.substring(0, 5);

              String[] datess = dateS.split("-");

              String year = datess[0];
              String month = datess[1];
              String day = datess[2];

              return day + " " + getMonthName(Integer.parseInt(month)) + " " + year + " " + timeS;
          }
      */
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

    private void selectDate(TextView DateBtn) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, (datePicker, year1, month1, day1) -> {
            String date = day1 + " /" + getMonthName((month1 + 1)) + "/ " +
                    "/" + year1;
            DateBtn.setText(date);
        }, year, month, day);
        datePickerDialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == USER_IMAGE && resultCode == RESULT_OK) {
//            filepath = data.getData();
//              bitmap = setBitmap();
//            user_image = bitmapToFile(VendorCreateAccountActivity.this, bitmap);
//            Log.e("TAG", "onActivityResult() called with: userImage  = [" + user_image + "], resultCode = [" + resultCode + "], data = [" + data + "]");
            if (data != null) {
                filepath = data.getData();
            }

            bitmap = setBitmap(binding.userImage, binding.userImage);
            user_image = bitmapToFile(ProfileEditActivity.this, bitmap);
            binding.userImage.setImageBitmap(bitmap);

            showdialogbox();
        }

    }

    private void get_profile() {

        ProgressDialog pd = new ProgressDialog(ProfileEditActivity.this);
        pd.show();


        ApiInterface apiInterface = RetrofitClient.getclient(ProfileEditActivity.this);

        apiInterface.get_profile(session.getUserId()).enqueue(new Callback<AccountModel>() {
            @Override

            public void onResponse(@NonNull Call<AccountModel> call, @NonNull Response<AccountModel> response) {

                pd.dismiss();

                try {
                    if (response.body() != null && response.body().getResult().equalsIgnoreCase("true")) {

                        binding.updateUserName.setText(response.body().getData().getName());
                        binding.userNameHeaading.setText(response.body().getData().getName());

                        if (response.body().getData().getEmail().length() <= 7) {
                        } else {
                            binding.updateEmail.setText(response.body().getData().getEmail());
                        }

                        binding.userEmailHeading.setText(response.body().getData().getEmail());
                        binding.updateProfession.setText(response.body().getData().getProfessionId());
                        if (!response.body().getData().getPhone().equalsIgnoreCase("")) {
                            binding.updateMobile.setText(response.body().getData().getPhone());
                           // binding.updateMobileCode.setText(response.body().getData().getCountry_code());
                        }
                        binding.updateDob.setText(response.body().getData().getDob());

                        if (response.body().getData().getAddress().equalsIgnoreCase(""))
                            binding.updateAddress.setHint("Address");
                        else
                            binding.updateAddress.setText(response.body().getData().getAddress());

                        Picasso.get().load(response.body().getPath() + response.body().getData().getImage()).placeholder(R.drawable.user).into(binding.userImage);
                        pd.dismiss();
                        Log.e("TAG", "onResponse() called with: Image = [" + call + "], response = [" + response.body().getPath() + response.body().getData().getImage() + "]");
                    }

                } catch (Exception e) {
                    pd.dismiss();
                    e.getLocalizedMessage();
                }

            }

            @Override
            public void onFailure(@NonNull Call<AccountModel> call, @NonNull Throwable t) {
                pd.dismiss();
                Log.e("TAG", "onFailure() called with: call = [" + call + "], t = [" + t + "]");
            }
        });


    }


    public Bitmap setBitmap(ImageView imageView, ImageView imageView2) {

        try {
            bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filepath);
            imageView.setImageBitmap(bitmap);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return bitmap;
    }

    public static File bitmapToFile(Context mContext, Bitmap bitmap) {
        try {
            String name = System.currentTimeMillis() + ".png";
            File file = new File(mContext.getCacheDir(), name);

            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 60, bos);
            byte[] bArr = bos.toByteArray();
            bos.flush();
            bos.close();

            FileOutputStream fos = new FileOutputStream(file);
            fos.write(bArr);
            fos.flush();
            fos.close();

            return file;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void showdialogbox() {


        final Dialog dialog = new Dialog(ProfileEditActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        dialog.setContentView(R.layout.upload_image_layout);

        ImageView image = (ImageView) dialog.findViewById(R.id.image);
        ImageView close = (ImageView) dialog.findViewById(R.id.cancel_image);
        TextView upload_btn = (TextView) dialog.findViewById(R.id.upload_btn);

        image.setImageBitmap(BitmapFactory.decodeFile(user_image.getPath()));

        close.setOnClickListener(view -> dialog.dismiss());


        upload_btn.setOnClickListener(view -> {

            ProgressDialog pd = new ProgressDialog(ProfileEditActivity.this);
            pd.show();

            ANRequest.MultiPartBuilder anAdd = AndroidNetworking.upload((BaseUrls.URL + update_image_user_vender));
            anAdd.addMultipartParameter("user_id", session.getUserId());
            if (user_image != null) anAdd.addMultipartFile("image", user_image);

            anAdd.setPriority(Priority.HIGH);
            anAdd.build()
                    .getAsJSONObject(new JSONObjectRequestListener() {
                        @Override
                        public void onResponse(JSONObject jsonObject) {
                            pd.dismiss();
                            try {
                                Log.d("---rrrProfile", "save_postsave_post" + jsonObject.toString());
                                // JSONObject obj = new JSONObject(response);
                                String result = jsonObject.getString("result");
                                pd.dismiss();

                                if (result.equalsIgnoreCase("true")) {
                                    Toast.makeText(ProfileEditActivity.this, "Updated", Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();
                                } else {
                                    dialog.dismiss();
                                    Toast.makeText(ProfileEditActivity.this, jsonObject.getString("msg"), Toast.LENGTH_SHORT).show();

                                }

                            } catch (JSONException e) {
                                pd.dismiss();
                                dialog.dismiss();
                                Toast.makeText(ProfileEditActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                                e.printStackTrace();
                            }

                        }

                        @Override
                        public void onError(ANError anError) {
                            dialog.dismiss();
                            pd.dismiss();
                            Log.e("TAG", "onError: " + anError);
                        }
                    });

        });

        dialog.show();

    }

}