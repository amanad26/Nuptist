 package com.Nuptist;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.Nuptist.Models.AccountModel;
import com.Nuptist.RetrofitApis.ApiInterface;
import com.Nuptist.RetrofitApis.RetrofitClient;
import com.Nuptist.Session.Session;
import com.Nuptist.databinding.ActivityCahngePasswordBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

 public class ChangePasswordActivity extends AppCompatActivity {

    ActivityCahngePasswordBinding binding ;
    Session session ;
    ProgressDialog pd ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding  = ActivityCahngePasswordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        session   = new Session(ChangePasswordActivity.this);
        pd = new ProgressDialog(ChangePasswordActivity.this);
        pd.setCancelable(false);

        
        binding.changePassBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(binding.editNewPassword.getText().toString().equalsIgnoreCase("")){
                    binding.editNewPassword.setError("Enter New Password ");
                }else if(binding.editOldPass.getText().toString().equalsIgnoreCase("")){
                    binding.editOldPass.setError("Enter Old Password");
                }
                else if(binding.editNewPasswordConfirm.getText().toString().equalsIgnoreCase("")){
                    binding.editNewPasswordConfirm.setError("Enter Password Again..");
                }
                else if(!binding.editNewPasswordConfirm.getText().toString().equalsIgnoreCase(binding.editNewPassword.getText().toString())){
                    binding.editNewPasswordConfirm.setError("Password  Do Not Match");
                }
                else {
                    pd.show();
                    changePassword();
                }
            }
        });
        
        
    }

     private void changePassword() {

         ApiInterface apiInterface  = RetrofitClient.getclient(ChangePasswordActivity.this);

         apiInterface.changePassword(
                 session.getUserId(),
                 binding.editOldPass.getText().toString(),
                 binding.editNewPasswordConfirm.getText().toString(),
                 binding.editNewPassword.getText().toString()
         ).enqueue(new Callback<AccountModel>() {
             @Override
             public void onResponse(Call<AccountModel> call, Response<AccountModel> response) {

                 pd.dismiss();
                 try {
                     if (response.code() == 200)
                         if (response.body() != null) {
                             if (response.body().getResult().equalsIgnoreCase("true")) {
                                 finish();
                                 Toast.makeText(ChangePasswordActivity.this, "Password Changed", Toast.LENGTH_SHORT).show();
                             } else {
                                 Toast.makeText(ChangePasswordActivity.this, response.body().getMsg(), Toast.LENGTH_SHORT).show();
                             }
                         } else
                             Log.e("TAG", "onResponse() called with: call =  response = [" + response + "]");
                     else
                         Log.e("TAG", "onResponse() called with: call =  response = [" + response.code() + "]");
                 } catch (Exception e) {
                     pd.dismiss();
                     Log.e("TAG", "onResponse() called with: call = [" + call + "], response = [" + e.getLocalizedMessage() + "]");
                     e.printStackTrace();
                 }



             }

             @Override
             public void onFailure(Call<AccountModel> call, Throwable t) {
                 pd.dismiss();
                 Log.e("TAG", "onFailure() called with: call = [" + call + "], t = [" + t.getLocalizedMessage() + "]");
             }
         });



     }
 }