package com.Nuptist.venue;

import static com.Nuptist.RetrofitApis.BaseUrls.ADD_AND_UPDATE_VENUE_IMAGES;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.Nuptist.Models.ProgressDialog;
import com.Nuptist.Models.VenuModel;
import com.Nuptist.R;
import com.Nuptist.RetrofitApis.ApiInterface;
import com.Nuptist.RetrofitApis.BaseUrls;
import com.Nuptist.RetrofitApis.RetrofitClient;
import com.Nuptist.Session.Session;
import com.Nuptist.VendorProfileActivity;
import com.Nuptist.databinding.ActivityAddVanueImagesBinding;
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.ANRequest;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.github.dhaval2404.imagepicker.ImagePicker;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddVanueImagesActivity extends AppCompatActivity {


    private File gallery_1 = null, gallery_2 = null, gallery_3 = null, gallery_4 = null, gallery_5 = null, gallery_6 = null, gallery_7 = null;
    private Uri filepath;
    private Bitmap bitmap;
    private Session session;

    private ActivityAddVanueImagesBinding binding;
    private String venue_id = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddVanueImagesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        session = new Session(AddVanueImagesActivity.this);

        getvenueDetails();
        binding.selectFrontImage.setOnClickListener(View -> select_image(1));
        binding.selectLeftImage.setOnClickListener(View -> select_image(2));
        binding.selectRightImage.setOnClickListener(View -> select_image(3));
        binding.selectBackImage.setOnClickListener(View -> select_image(4));
        binding.selectDickyImage.setOnClickListener(View -> select_image(5));
        binding.selectLeftQuarterImage.setOnClickListener(View -> select_image(6));
        binding.selectRightQuarterImage.setOnClickListener(View -> select_image(7));


        binding.back.setOnClickListener(v -> {
            finish();
        });

        binding.addImages.setOnClickListener(view -> addImages());

        binding.cancleFrontImage.setOnClickListener(view -> {

            if (gallery_1 != null) {
                gallery_1 = null;
                binding.selectFrontImage.setImageResource(R.drawable.ic_nature_svg);
                binding.cancleFrontImage.setVisibility(View.INVISIBLE);
            }
        });
        binding.cancleLeftImage.setOnClickListener(view -> {
            if (gallery_2 != null) {
                gallery_2 = null;
                binding.selectLeftImage.setImageResource(R.drawable.ic_nature_svg);
                binding.cancleLeftImage.setVisibility(View.INVISIBLE);
            }
        });


        binding.cancleRightImage.setOnClickListener(view -> {

            if (gallery_3 != null) {
                gallery_3 = null;
                binding.selectRightImage.setImageResource(R.drawable.ic_nature_svg);
                binding.cancleRightImage.setVisibility(View.INVISIBLE);
            }


        });
        binding.cancleBackImage.setOnClickListener(view -> {

            if (gallery_4 != null) {
                gallery_4 = null;
                binding.selectBackImage.setImageResource(R.drawable.ic_nature_svg);
                binding.cancleBackImage.setVisibility(View.INVISIBLE);
            }


        });
        binding.cancleDickyImage.setOnClickListener(view -> {

            if (gallery_5 != null) {
                gallery_5 = null;
                binding.selectDickyImage.setImageResource(R.drawable.ic_nature_svg);
                binding.cancleDickyImage.setVisibility(View.INVISIBLE);
            }


        });
        binding.cancleLeftQuarterImage.setOnClickListener(view -> {

            if (gallery_6 != null) {
                gallery_6 = null;
                binding.selectLeftQuarterImage.setImageResource(R.drawable.ic_nature_svg);
                binding.cancleLeftQuarterImage.setVisibility(View.INVISIBLE);
            }


        });
        binding.cancleRightQuarterImage.setOnClickListener(view -> {

            if (gallery_7 != null) {
                gallery_7 = null;
                binding.selectRightQuarterImage.setImageResource(R.drawable.ic_nature_svg);
                binding.cancleRightQuarterImage.setVisibility(View.INVISIBLE);
            }


        });

    }

    private void addImages() {

        if (gallery_1 == null) {
            Toast.makeText(this, "Select Gallery 1 Images ", Toast.LENGTH_SHORT).show();
        } else if (gallery_2 == null) {
            Toast.makeText(this, "Select Gallery 2 Images ", Toast.LENGTH_SHORT).show();
        }
//        else if (gallery_3 == null) {
//            Toast.makeText(this, "Select Gallery 3 Images ", Toast.LENGTH_SHORT).show();
//
//        } else if (gallery_4 == null) {
//            Toast.makeText(this, "Select Gallery 4 Images ", Toast.LENGTH_SHORT).show();
//
//        } else if (gallery_5 == null) {
//            Toast.makeText(this, "Select Gallery 5 Images ", Toast.LENGTH_SHORT).show();
//
//        } else if (gallery_6 == null) {
//            Toast.makeText(this, "Select Gallery 6 Images ", Toast.LENGTH_SHORT).show();
//
//        } else if (gallery_7 == null) {
//            Toast.makeText(this, "Select Gallery 7 Images ", Toast.LENGTH_SHORT).show();
//        }
        else {
            ProgressDialog pd = new ProgressDialog(AddVanueImagesActivity.this);
            pd.show();

//            Log.e("TAG", "addImages() called Gallery 1 " + gallery_1.getName());
//            Log.e("TAG", "addImages() called Gallery 2 " + gallery_2.getName());
//            Log.e("TAG", "addImages() called Gallery 3 " + gallery_3.getName());
//            Log.e("TAG", "addImages() called Gallery 4 " + gallery_4.getName());
//            Log.e("TAG", "addImages() called Gallery 5 " + gallery_5.getName());
//            Log.e("TAG", "addImages() called Gallery 6 " + gallery_6.getName());
//            Log.e("TAG", "addImages() called Gallery 7 " + gallery_7.getName());


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
                                    Toast.makeText(AddVanueImagesActivity.this, "Venue  Images Added ", Toast.LENGTH_SHORT).show();
                                    session.setGalleryImage("Added");
                                    startActivity(new Intent(AddVanueImagesActivity.this, VendorProfileActivity.class)
                                            .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                                            .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                                    );
                                    finish();
                                } else {
                                    Toast.makeText(AddVanueImagesActivity.this, jsonObject.getString("msg"), Toast.LENGTH_SHORT).show();

                                }

                            } catch (JSONException e) {
                                pd.dismiss();
                                Toast.makeText(AddVanueImagesActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
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


    public void select_image(int code) {
        ImagePicker.Companion.with(AddVanueImagesActivity.this)
                .crop()
                .compress(200) //Final image size will be less than 1 MB(Optional)
                .start(code);


//        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
//        intent.setType("image/*");
//        startActivityForResult(intent, code);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            assert data != null;
            filepath = data.getData();

            switch (requestCode) {

                case 1:
                    bitmap = setBitmap(binding.selectFrontImage, binding.cancleFrontImage);
                    gallery_1 = bitmapToFile(AddVanueImagesActivity.this, bitmap);
                    break;

                case 2:
                    bitmap = setBitmap(binding.selectLeftImage, binding.cancleLeftImage);
                    gallery_2 = bitmapToFile(AddVanueImagesActivity.this, bitmap);
                    break;

                case 3:
                    bitmap = setBitmap(binding.selectRightImage, binding.cancleRightImage);
                    gallery_3 = bitmapToFile(AddVanueImagesActivity.this, bitmap);
                    break;

                case 4:
                    bitmap = setBitmap(binding.selectBackImage, binding.cancleBackImage);
                    gallery_4 = bitmapToFile(AddVanueImagesActivity.this, bitmap);
                    break;

                case 5:
                    bitmap = setBitmap(binding.selectDickyImage, binding.cancleDickyImage);
                    gallery_5 = bitmapToFile(AddVanueImagesActivity.this, bitmap);
                    break;

                case 6:
                    bitmap = setBitmap(binding.selectLeftQuarterImage, binding.cancleLeftQuarterImage);
                    gallery_6 = bitmapToFile(AddVanueImagesActivity.this, bitmap);
                    break;

                case 7:
                    bitmap = setBitmap(binding.selectRightQuarterImage, binding.cancleRightQuarterImage);
                    gallery_7 = bitmapToFile(AddVanueImagesActivity.this, bitmap);
                    break;

            }


        }


    }


    public Bitmap setBitmap(ImageView imageView, ImageView imageView2) {

        try {
            bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filepath);
            imageView.setImageBitmap(bitmap);
            imageView2.setVisibility(View.VISIBLE);

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

    public void getvenueDetails() {

        ApiInterface apiInterface = RetrofitClient.getclient(AddVanueImagesActivity.this);

        apiInterface.get_venue(session.getUserId_Vendor()).enqueue(new Callback<VenuModel>() {
            @Override
            public void onResponse(Call<VenuModel> call, Response<VenuModel> response) {

                try {

                    if (response.body().getResult().equalsIgnoreCase("true")) {
                        venue_id = response.body().getData().get(0).getId();
                    }

                } catch (Exception e) {
                    e.getLocalizedMessage();
                }

            }

            @Override
            public void onFailure(Call<VenuModel> call, Throwable t) {
                Log.e("TAG", "onFailure() called with: call = [" + call + "], t = [" + t.getLocalizedMessage() + "]");
            }
        });


    }

}