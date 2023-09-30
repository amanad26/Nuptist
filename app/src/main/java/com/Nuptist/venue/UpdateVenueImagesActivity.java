package com.Nuptist.venue;

import static com.Nuptist.RetrofitApis.BaseUrls.ADD_AND_UPDATE_VENUE_IMAGES;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.Nuptist.Models.ProgressDialog;
import com.Nuptist.Models.VenueImagesModel;
import com.Nuptist.R;
import com.Nuptist.RetrofitApis.ApiInterface;
import com.Nuptist.RetrofitApis.BaseUrls;
import com.Nuptist.RetrofitApis.RetrofitClient;
import com.Nuptist.Session.Session;
import com.Nuptist.VendorProfileActivity;
import com.Nuptist.databinding.ActivityUpdateVenueImagesBinding;
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
import java.io.FileOutputStream;
import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateVenueImagesActivity extends AppCompatActivity {

    private Session session;

    private ActivityUpdateVenueImagesBinding binding;
    private File gallery_1 = null, gallery_2 = null, gallery_3 = null, gallery_4 = null, gallery_5 = null, gallery_6 = null, gallery_7 = null;
    private Uri filepath = null;
    private Bitmap bitmap;
    private String venue_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUpdateVenueImagesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        session = new Session(UpdateVenueImagesActivity.this);

        getVenueImages();


        binding.back.setOnClickListener(v -> {
            finish();
        });

        venue_id = getIntent().getStringExtra("venue_id");

        binding.updateLinear.setOnClickListener(view -> addImages());


    }


    public void select_image(int code) {
        ImagePicker.Companion.with(UpdateVenueImagesActivity.this)
                .crop()
                .compress(200) //Final image size will be less than 1 MB(Optional)
                .start(code);


//        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
//        intent.setType("image/*");
//        startActivityForResult(intent,code);

    }


    private void getVenueImages() {

        ApiInterface apiInterface = RetrofitClient.getclient(UpdateVenueImagesActivity.this);


        apiInterface.getVenueImages(session.getUserId_Vendor()).enqueue(new Callback<VenueImagesModel>() {
            @Override
            public void onResponse(@NonNull Call<VenueImagesModel> call, @NonNull Response<VenueImagesModel> response) {

                try {
                    if (response.code() == 200)
                        if (response.body() != null)
                            if (response.body().getResult().equalsIgnoreCase("true")) {
                                String path = response.body().getPath();

                                Picasso.get().load(path + response.body().getData().get(0).getGallaryImage()).placeholder(R.drawable.ic_nature_svg).into(binding.galeery1);
                                Picasso.get().load(path + response.body().getData().get(0).getGallaryImage2()).placeholder(R.drawable.ic_nature_svg).into(binding.gallery2);
                                Picasso.get().load(path + response.body().getData().get(0).getGallaryImage3()).placeholder(R.drawable.ic_nature_svg).into(binding.gallery3);
                                Picasso.get().load(path + response.body().getData().get(0).getGallaryImage4()).placeholder(R.drawable.ic_nature_svg).into(binding.gallery4);
                                Picasso.get().load(path + response.body().getData().get(0).getGallaryImage5()).placeholder(R.drawable.ic_nature_svg).into(binding.gallery5);
                                Picasso.get().load(path + response.body().getData().get(0).getGallaryImage6()).placeholder(R.drawable.ic_nature_svg).into(binding.gallery6);
                                Picasso.get().load(path + response.body().getData().get(0).getGallaryImage7()).placeholder(R.drawable.ic_nature_svg).into(binding.gallery7);

                            }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(@NonNull Call<VenueImagesModel> call, @NonNull Throwable t) {
                Log.e("TAG", "onFailure() called with: call = [" + call + "], t = [" + t.getLocalizedMessage() + "]");
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            assert data != null;
            filepath = data.getData();

            switch (requestCode) {

                case 1:
                    bitmap = setBitmap(binding.galeery1);
                    gallery_1 = bitmapToFile(UpdateVenueImagesActivity.this, bitmap);
                    break;

                case 2:
                    bitmap = setBitmap(binding.gallery2);
                    gallery_2 = bitmapToFile(UpdateVenueImagesActivity.this, bitmap);
                    break;

                case 3:
                    bitmap = setBitmap(binding.gallery3);
                    gallery_3 = bitmapToFile(UpdateVenueImagesActivity.this, bitmap);
                    break;

                case 4:
                    bitmap = setBitmap(binding.gallery4);
                    gallery_4 = bitmapToFile(UpdateVenueImagesActivity.this, bitmap);
                    break;

                case 5:
                    bitmap = setBitmap(binding.gallery5);
                    gallery_5 = bitmapToFile(UpdateVenueImagesActivity.this, bitmap);
                    break;

                case 6:
                    bitmap = setBitmap(binding.gallery6);
                    gallery_6 = bitmapToFile(UpdateVenueImagesActivity.this, bitmap);
                    break;

                case 7:
                    bitmap = setBitmap(binding.gallery7);
                    gallery_7 = bitmapToFile(UpdateVenueImagesActivity.this, bitmap);
                    break;

            }


        }


    }


    public Bitmap setBitmap(ImageView imageView) {

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
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void addImages() {

        ProgressDialog pd = new ProgressDialog(UpdateVenueImagesActivity.this);
        pd.show();

        Log.e("TAG", "addImages() called Gallery 1 " + gallery_1.getName());
        Log.e("TAG", "addImages() called Gallery 2 " + gallery_2.getName());
        Log.e("TAG", "addImages() called Gallery 3 " + gallery_3.getName());
        Log.e("TAG", "addImages() called Gallery 4 " + gallery_4.getName());
        Log.e("TAG", "addImages() called Gallery 5 " + gallery_5.getName());
        Log.e("TAG", "addImages() called Gallery 6 " + gallery_6.getName());
        Log.e("TAG", "addImages() called Gallery 7 " + gallery_7.getName());


        ANRequest.MultiPartBuilder anAdd = AndroidNetworking.upload((BaseUrls.URL + ADD_AND_UPDATE_VENUE_IMAGES));
        anAdd.addMultipartParameter("venue_id", venue_id);
        if (gallery_1 != null) anAdd.addMultipartFile("gallary_image", gallery_1);
        if (gallery_2 != null) anAdd.addMultipartFile("gallary_image2", gallery_2);
        if (gallery_3 != null) anAdd.addMultipartFile("gallary_image3", gallery_3);
        if (gallery_4 != null) anAdd.addMultipartFile("gallary_image4", gallery_4);
        if (gallery_5 != null) anAdd.addMultipartFile("gallary_image5", gallery_5);
        if (gallery_6 != null) anAdd.addMultipartFile("gallary_image6", gallery_6);
        if (gallery_7 != null) anAdd.addMultipartFile("gallary_image7", gallery_7);


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
                                Toast.makeText(UpdateVenueImagesActivity.this, "Venue  Images Added ", Toast.LENGTH_SHORT).show();
                                session.setGalleryImage("Added");
                                finish();
                            } else {
                                Toast.makeText(UpdateVenueImagesActivity.this, jsonObject.getString("msg"), Toast.LENGTH_SHORT).show();

                            }

                        } catch (JSONException e) {
                            pd.dismiss();
                            Toast.makeText(UpdateVenueImagesActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onError(ANError anError) {
                        pd.dismiss();
                        Log.e("TAG", "onError: " + anError);

                    }
                });


    }

}