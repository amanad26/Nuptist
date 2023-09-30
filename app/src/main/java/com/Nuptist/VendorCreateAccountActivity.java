package com.Nuptist;

import static com.Nuptist.RetrofitApis.BaseUrls.VENDOR_SIGNUP;
import static com.Nuptist.venue.SearchVenueActivity.selected_address;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.Nuptist.Models.ProgressDialog;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;

import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.Nuptist.RetrofitApis.BaseUrls;
import com.Nuptist.Session.Session;
import com.Nuptist.databinding.ActivityVendorCreateAccountBinding;
import com.Nuptist.venue.AddVenueActivity;
import com.Nuptist.venue.SearchVenueActivity;
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.ANRequest;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import me.rosuh.filepicker.config.FilePickerManager;

public class VendorCreateAccountActivity extends AppCompatActivity {
    private TextView request_to_baccount;
    private LinearLayout dissmiss, vendor_profile;
    private ImageView back;

    private ActivityVendorCreateAccountBinding binding;
    private File user_image = null, cover_image = null, id_proof_image = null;
    private int USER_IMAGE_CODE = 200;
    private int ID_PROOF_IMAGE_CODE = 300;
    private int COVER_IMAGE_CODE = 400;
    private Uri filepath;
    private String mobile;
    private Bitmap bitmap;
    private Session session;
    private boolean isChecked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityVendorCreateAccountBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        Window window = getWindow();
        window.setStatusBarColor(getResources().getColor(R.color.white));

        session = new Session(VendorCreateAccountActivity.this);

        back = findViewById(R.id.back);
        dissmiss = findViewById(R.id.dissmiss2);
        vendor_profile = findViewById(R.id.vendor_profile);
        request_to_baccount = findViewById(R.id.request_to_baccount);

        binding.fName.setText(session.getUserName());
        binding.mName.setText(session.getUserFirstName());
        binding.lName.setText(session.getUserLastName());
        binding.phoneNumber.setText(session.getMobile());
        binding.serviceAddress.setText(session.getUserAddress());


        if(!session.getUserImage().equalsIgnoreCase(""))
            Picasso.get().load(session.getUserImage()).placeholder(R.drawable.ic_nature_svg).into(binding.userImg);


        binding.vendorTermsAndCondition.setOnCheckedChangeListener((compoundButton, b) -> isChecked = b);

        binding.serviceAddress.setOnClickListener(view -> startActivity(new Intent(VendorCreateAccountActivity.this, SearchVenueActivity.class)
                .putExtra("where", "create_profile")));

        binding.termsConditionTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(VendorCreateAccountActivity.this, TemsAndConditionActivity.class).putExtra("type","Vendor"));
            }
        });


        binding.requestToBaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (binding.fName.getText().toString().equalsIgnoreCase("")) {
                    binding.fName.setError("Enter First Name");
                    binding.fName.requestFocus();
                } else if (binding.lName.getText().toString().equalsIgnoreCase("")) {
                    binding.lName.setError("Enter Last Name");
                    binding.lName.requestFocus();
                } else if (binding.serviceAddress.getText().toString().equalsIgnoreCase("")) {
                    binding.serviceAddress.setError("Enter Service Address");
                    binding.serviceAddress.requestFocus();
                } else if (binding.phoneNumber.getText().toString().equalsIgnoreCase("")) {
                    binding.phoneNumber.setError("Enter Mobile Number");
                    binding.phoneNumber.requestFocus();
                } else if (binding.emailEdit.getText().toString().equalsIgnoreCase("")) {
                    binding.emailEdit.setError("Enter Email");
                    binding.emailEdit.requestFocus();
                } else if (!emailValidator(binding.emailEdit.getText().toString())) {
                    binding.emailEdit.setError("Invalid Email");
                    binding.emailEdit.requestFocus();
                } else if (binding.serviceAvailable.getText().toString().equalsIgnoreCase("")) {
                    binding.serviceAvailable.setError("Enter Service Name");
                    binding.serviceAvailable.requestFocus();
                }

//                else if (user_image == null) {
//                    Toast.makeText(VendorCreateAccountActivity.this, "Select User  Image", Toast.LENGTH_SHORT).show();
//                }
//                else if(cover_image == null){
//                    Toast.makeText(VendorCreateAccountActivity.this, "Select Cover Image", Toast.LENGTH_SHORT).show();
//                }else if(id_proof_image == null){
//                    Toast.makeText(VendorCreateAccountActivity.this, "Select ID Proof Image", Toast.LENGTH_SHORT).show();
//                }
                else {

                    mobile = binding.phoneNumber.getText().toString();
                    Log.e("TAG", "onClick() called with: Mobile is  = [" + mobile + "]");

                    if (isChecked)
                        addDetailsIntoDatabse();
                    else
                        Toast.makeText(VendorCreateAccountActivity.this, "Please Accept Terms and Condition", Toast.LENGTH_SHORT).show();

                }

                /// showdialog();
            }
        });

        binding.brawoseUserImage.setOnClickListener(view -> {


//            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
//            intent.setType("image/*");
//            startActivityForResult(intent,USER_IMAGE_CODE);

            ImagePicker.Companion.with(VendorCreateAccountActivity.this)
                    .crop()
                    .compress(200) //Final image size will be less than 1 MB(Optional)
                    .start(USER_IMAGE_CODE);

        });


        binding.browseCoverImage.setOnClickListener(view -> {

            ImagePicker.Companion.with(VendorCreateAccountActivity.this)
                    .crop()
                    .compress(200) //Final image size will be less than 1 MB(Optional)
                    .start(COVER_IMAGE_CODE);

//            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
//            intent.setType("image/*");
//            startActivityForResult(intent,COVER_IMAGE_CODE);


        });

        binding.browseImageIdProof.setOnClickListener(view -> {

            ImagePicker.Companion.with(VendorCreateAccountActivity.this)
                    .crop()
                    .compress(200) //Final image size will be less than 1 MB(Optional)
                    .start(ID_PROOF_IMAGE_CODE);

//            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
//            intent.setType("image/*");
//            startActivityForResult(intent,ID_PROOF_IMAGE_CODE);

        });

        binding.back.setOnClickListener(view -> onBackPressed());
    }


    public boolean emailValidator(String email) {
        Pattern pattern;
        Matcher matcher;
        final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email);
        return matcher.matches();
    }

    private void addDetailsIntoDatabse() {

        Log.e("TAG", "addDetailsIntoDatabse() called with user_id " + session.getUserId());

        ProgressDialog pd = new ProgressDialog(VendorCreateAccountActivity.this);
        pd.show();
        pd.setCancelable(false);

        ANRequest.MultiPartBuilder anAdd = AndroidNetworking.upload((BaseUrls.URL + VENDOR_SIGNUP));
        anAdd.addMultipartParameter("name", binding.fName.getText().toString());
        if (!binding.mName.getText().toString().equalsIgnoreCase(""))
            anAdd.addMultipartParameter("mname", binding.mName.getText().toString());
        else anAdd.addMultipartParameter("mname", "m");
        anAdd.addMultipartParameter("lname", binding.lName.getText().toString());
        anAdd.addMultipartParameter("email", binding.emailEdit.getText().toString());
        anAdd.addMultipartParameter("mobile", mobile);
        anAdd.addMultipartParameter("services_address", binding.serviceAddress.getText().toString());
        anAdd.addMultipartParameter("service_abailble", binding.serviceAvailable.getText().toString());
        //// if (user_image != null) anAdd.addMultipartFile("image", user_image);
        if (cover_image != null) anAdd.addMultipartFile("cover_image", cover_image);
        if (id_proof_image != null) anAdd.addMultipartFile("id_proof", id_proof_image);
        anAdd.addMultipartParameter("user_id", session.getUserId());
        anAdd.addMultipartParameter("country_code", binding.numberCountyCode.getTextView_selectedCountry().getText().toString());

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
                            String msg = jsonObject.getString("msg");
                            pd.dismiss();

                            if (result.equalsIgnoreCase("true")) {
                                String user_id = jsonObject.getString("user_id");

                                Session session = new Session(VendorCreateAccountActivity.this);
                                session.setUser_id_vendor(user_id);
                                session.setUserType("Vendor");

                                Toast.makeText(VendorCreateAccountActivity.this, "Account Created", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(VendorCreateAccountActivity.this, HomeActivity.class));
                                finish();
                            } else {
                                pd.dismiss();
                                Toast.makeText(VendorCreateAccountActivity.this, msg, Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            pd.dismiss();
                            Toast.makeText(VendorCreateAccountActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
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

    @Override
    protected void onResume() {
        super.onResume();
        if (!selected_address.equalsIgnoreCase("")) {
            binding.serviceAddress.setText(selected_address);
            selected_address = "";
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data != null) {
            filepath = data.getData();
        }

        if (requestCode == USER_IMAGE_CODE && resultCode == RESULT_OK) {

            bitmap = setBitmap(binding.userImg, binding.userImg);
            user_image = bitmapToFile(VendorCreateAccountActivity.this, bitmap);

            binding.userImg.setImageBitmap(bitmap);
            if (user_image != null) binding.brawoseUserImage.setText(user_image.getName());


        } else if (requestCode == ID_PROOF_IMAGE_CODE && resultCode == RESULT_OK) {

            bitmap = setBitmap(binding.idCardImg, binding.idCardImg);
            id_proof_image = bitmapToFile(VendorCreateAccountActivity.this, bitmap);

            binding.idCardImg.setImageBitmap(bitmap);
            if (id_proof_image != null)
                binding.browseImageIdProof.setText(id_proof_image.getName());

        } else if (requestCode == COVER_IMAGE_CODE && resultCode == RESULT_OK) {

            bitmap = setBitmap(binding.coverImg, binding.idCardImg);
            cover_image = bitmapToFile(VendorCreateAccountActivity.this, bitmap);

            binding.coverImg.setImageBitmap(bitmap);
            if (cover_image != null) binding.browseCoverImage.setText(cover_image.getName());

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