package com.Nuptist;

import static com.Nuptist.RetrofitApis.BaseUrls.VENDOR_SIGNUP;
import static com.Nuptist.RetrofitApis.BaseUrls.VENDOR_UPDATE_PROFILE;
import static com.Nuptist.RetrofitApis.BaseUrls.update_image_user_vender;
import static com.Nuptist.venue.SearchVenueActivity.selected_address;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.Nuptist.Models.VendorProfileModel;
import com.Nuptist.RetrofitApis.ApiInterface;
import com.Nuptist.RetrofitApis.BaseUrls;
import com.Nuptist.RetrofitApis.RetrofitClient;
import com.Nuptist.Session.Session;
import com.Nuptist.databinding.ActivityUpdateVendorProfileBinding;
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
import java.util.List;

import me.rosuh.filepicker.config.FilePickerManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateVendorProfile extends AppCompatActivity {

    private static final int USER_IMAGE_CODE = 200;
    private static final int COVER_IMAGE_CODE = 300;
    Session session ;
    ActivityUpdateVendorProfileBinding binding ;
    private File user_image = null;
    private File cover_image = null;
    private File id_proof_image= null;
    Bitmap bitmap ;
    Uri  filepath ;

    ProgressDialog pd ;
    private int ID_PROOF_IMAGE_CODE = 400;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUpdateVendorProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        pd = new ProgressDialog(UpdateVendorProfile.this);
        pd.setCancelable(false);

        session = new Session(UpdateVendorProfile.this);


        get_profile();


        binding.back.setOnClickListener(v -> onBackPressed());

        binding.userImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
//                intent.setType("image/*");
//                startActivityForResult(intent,USER_IMAGE_CODE);

                ImagePicker.Companion.with(UpdateVendorProfile.this)
                .crop()
                .compress(200) //Final image size will be less than 1 MB(Optional)
                .start(USER_IMAGE_CODE);


//                FilePickerManager
//                        .from(UpdateVendorProfile.this)
//                        .maxSelectable(1)
//                        .forResult(USER_IMAGE_CODE);

            }
        });


        binding.serviceAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity( new Intent(UpdateVendorProfile.this, SearchVenueActivity.class)
                        .putExtra("where","vendor_profile"));
            }
        });

        binding.coverImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FilePickerManager
                        .from(UpdateVendorProfile.this)
                        .maxSelectable(1)
                        .forResult(COVER_IMAGE_CODE);
            }
        });

        binding.idProof.setOnClickListener(view -> {
//                FilePickerManager
//                        .from(UpdateVendorProfile.this)
//                        .maxSelectable(1)
//                        .forResult(ID_PROOF_IMAGE_CODE);

            ImagePicker.Companion.with(UpdateVendorProfile.this)
                    .crop()
                    .compress(200) //Final image size will be less than 1 MB(Optional)
                    .start(ID_PROOF_IMAGE_CODE);

        });

        binding.update.setOnClickListener(view -> {

            pd.show();

            Log.e("TAG", "onClick() called with: user_id = [" + session.getUserId_Vendor() + "]");
            Log.e("TAG", "onClick() called with: name = [" + binding.fName.getText().toString() + "]");
            Log.e("TAG", "onClick() called with: mname = [" +  binding.mName.getText().toString() + "]");
            Log.e("TAG", "onClick() called with: lname = [" + binding.lName.getText().toString() + "]");
            Log.e("TAG", "onClick() called with: email = [" + binding.emailEdit.getText().toString() + "]");
            Log.e("TAG", "onClick() called with: mobile = [" + binding.phoneNumber.getText().toString() + "]");
            Log.e("TAG", "onClick() called with: service Address = [" + binding.serviceAddress.getText().toString() + "]");
            Log.e("TAG", "onClick() called with: service Available = [" +binding.serviceAvailable.getText().toString() + "]");
           ///// if(user_image != null)    Log.e("TAG", "onClick() called with: image = [" + user_image.getName()+ "]");
            if(id_proof_image != null) Log.e("TAG", "onClick() called with: id proof  = [" + id_proof_image.getName() + "]");
            if(cover_image != null)  Log.e("TAG", "onClick() called with: cover  = [" + cover_image + "]");

            ANRequest.MultiPartBuilder anAdd = AndroidNetworking.upload((BaseUrls.URL+VENDOR_UPDATE_PROFILE));
            anAdd.addMultipartParameter("user_id", session.getUserId_Vendor());
            anAdd.addMultipartParameter("name", binding.fName.getText().toString());
            anAdd.addMultipartParameter("mname", binding.mName.getText().toString());
            anAdd.addMultipartParameter("lname", binding.lName.getText().toString());
            anAdd.addMultipartParameter("email", binding.emailEdit.getText().toString());
            anAdd.addMultipartParameter("mobile", binding.phoneNumber.getText().toString());
            anAdd.addMultipartParameter("services_address", binding.serviceAddress.getText().toString());
            anAdd.addMultipartParameter("service_abailble", binding.serviceAvailable.getText().toString());
            ////// if(user_image != null)    anAdd.addMultipartFile("image", user_image);
            if(cover_image != null)   anAdd.addMultipartFile("cover_image", cover_image);
            if(id_proof_image != null)  anAdd.addMultipartFile("id_proof", id_proof_image);

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

                                if(result.equalsIgnoreCase("true")){
                                    Toast.makeText(UpdateVendorProfile.this, "Profile Details Updated..", Toast.LENGTH_SHORT).show();
                                    finish();
                                }

                            } catch (JSONException e) {
                                pd.dismiss();
                                Toast.makeText(UpdateVendorProfile.this, e.toString(), Toast.LENGTH_SHORT).show();
                                e.printStackTrace();
                            }

                        }

                        @Override
                        public void onError(ANError anError) {
                            pd.dismiss();
                            Log.e("TAG", "onError: "+anError.getLocalizedMessage());

                        }
                    });
              });


    }

    @Override
    protected void onResume() {
        super.onResume();
        if(!selected_address.equalsIgnoreCase("")){
            binding.serviceAddress.setText(selected_address);
            selected_address  = "";
        }
    }

    private  void get_profile(){

        ApiInterface apiInterface = RetrofitClient.getclient(UpdateVendorProfile.this);

        apiInterface.get_vendor_profile(session.getUserId_Vendor()).enqueue(new Callback<VendorProfileModel>() {
            @Override


            public void onResponse(Call<VendorProfileModel> call, Response<VendorProfileModel> response) {


                try {

                    if(response.body().getResult().equalsIgnoreCase("true")){

                        binding.fName.setText(response.body().getData().getName());
                        binding.mName.setText(response.body().getData().getMname());
                        binding.lName.setText(response.body().getData().getLname());
                        binding.emailEdit.setText(response.body().getData().getEmail());
                        binding.serviceAddress.setText(response.body().getData().getServicesAddress());
                        binding.serviceAvailable.setText(response.body().getData().getServiceAbailble());
                        binding.phoneNumber.setText(response.body().getData().getMobile());

                        Picasso.get().load(response.body().getPath()+response.body().getData().getImage()).placeholder(R.drawable.ic_nature_svg).into(binding.userImage);
                        Picasso.get().load(response.body().getPath()+response.body().getData().getIdProof()).placeholder(R.drawable.ic_nature_svg).into(binding.idProof);

                        //  Picasso.get().load(response.body().getPath()+response.body().getData().getC()).placeholder(R.drawable.ic_nature_svg).into(binding.idProof);

                        Log.e("TAG", "onResponse() called with: Image = [" + call + "], response = [" + response.body().getPath()+response.body().getData().getImage() + "]");
                    }

                }catch (Exception e){
                    Log.e("TAG", "onResponse() called with: Image Error = [" + e.getLocalizedMessage() + "]");
                    e.getLocalizedMessage();
                }

            }

            @Override
            public void onFailure(Call<VendorProfileModel> call, Throwable t) {
                Log.e("TAG", "onFailure() called with: call = [" + call + "], t = [" + t + "]");
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data != null)
            filepath = data.getData();


        if(requestCode == USER_IMAGE_CODE && resultCode == RESULT_OK){

//            List<String> image = FilePickerManager.obtainData();
//            user_image = new File(image.get(0));
//            Log.e("TAG", "onActivityResult() called with: requestCode = [" + requestCode + "], resultCode = [" + resultCode + "], data = [" + user_image + "]");
//
//            binding.userImage.setImageBitmap(BitmapFactory.decodeFile(user_image.getPath()));

            bitmap = setBitmap(binding.userImage, binding.userImage);
            user_image = bitmapToFile(UpdateVendorProfile.this, bitmap);
            binding.userImage.setImageBitmap(bitmap);
            updateImage();
        }else if(requestCode == ID_PROOF_IMAGE_CODE && resultCode == RESULT_OK){
//            List<String> image = FilePickerManager.obtainData();
//            id_proof_image = new File(image.get(0));
//            Log.e("TAG", "onActivityResult() called with: requestCode = [" + requestCode + "], resultCode = [" + resultCode + "], data = [" + id_proof_image + "]");
//            binding.idProof.setImageBitmap(BitmapFactory.decodeFile(id_proof_image.getPath()));

            bitmap = setBitmap(binding.idProof, binding.idProof);
            id_proof_image = bitmapToFile(UpdateVendorProfile.this, bitmap);
            binding.idProof.setImageBitmap(bitmap);

        }

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


    private  void updateImage(){
        ANRequest.MultiPartBuilder anAdd = AndroidNetworking.upload((BaseUrls.URL + update_image_user_vender));
        anAdd.addMultipartParameter("user_id", session.getUserId());
        if (user_image != null) anAdd.addMultipartFile("image", user_image);

        anAdd.setPriority(Priority.HIGH);
        anAdd.build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        try {
                            Log.d("---rrrProfile", "save_postsave_post" + jsonObject.toString());
                            // JSONObject obj = new JSONObject(response);
                            String result = jsonObject.getString("result");



                        } catch (JSONException e) {
                        }

                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.e("TAG", "onError: " + anError);
                    }
                });
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


}
