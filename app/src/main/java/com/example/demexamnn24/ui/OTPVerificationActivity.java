package com.example.demexamnn24.ui;

import static com.example.demexamnn24.utils.Utils.APIKEY;
import static com.example.demexamnn24.utils.Utils.BASE_URL;
import static com.example.demexamnn24.utils.Utils.TOKEN;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.chaos.view.PinView;
import com.example.demexamnn24.R;
import com.example.demexamnn24.controller.API;
import com.example.demexamnn24.data.ChangePasswordToken;
import com.example.demexamnn24.data.ResponseUser;
import com.google.android.material.button.MaterialButton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class OTPVerificationActivity extends AppCompatActivity {

    TextView resendText;
    boolean runningTimer;
    MaterialButton newPassButton;
    CountDownTimer resetCountDownTimer;
    PinView pinView;
    Retrofit retrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otpverification);

        newPassButton = findViewById(R.id.SetNewPass);
        resendText = findViewById(R.id.resendAfter);
        pinView = findViewById(R.id.pinView);

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        API api = retrofit.create(API.class);



        resetCountDownTimer = new CountDownTimer(5000, 1000){
            @Override
            public void onTick(long l){
                resendText.setText("resend after 0:" + (l/1000 + 1));
            }

            @Override
            public void onFinish(){
                resendText.setText("resend");
                resendText.setTextColor(Color.parseColor("#006CEC"));
                runningTimer = false;
                resendText.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ResetTimer();
                    }
                });
            }
        }.start();

        newPassButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = getIntent().getStringExtra("email");
                if (pinView.getText().toString().length() == 6){
                    ChangePasswordToken changePasswordToken =
                            new ChangePasswordToken(
                                    "email",
                                    email,
                                    pinView.getText().toString());
                    Call<ResponseUser> call = api.verifyCode(APIKEY, changePasswordToken);
                    call.enqueue(new Callback<ResponseUser>() {
                        @Override
                        public void onResponse(Call<ResponseUser> call, Response<ResponseUser> response) {
                            if (response.isSuccessful()){
                                if (response.body() != null){
                                    TOKEN = response.body().accessToken;
                                    startActivity(new Intent(OTPVerificationActivity.this, NewPasswordActivity.class));
                                }
                            } else {
                                Toast.makeText(OTPVerificationActivity.this, response.code() + "", Toast.LENGTH_SHORT).show();
                            }

                        }

                        @Override
                        public void onFailure(Call<ResponseUser> call, Throwable throwable) {

                        }
                    });
                } else {
                    Toast.makeText(OTPVerificationActivity.this, "введите код", Toast.LENGTH_SHORT).show();
                }
               // Intent intent = new Intent(OTPVerificationActivity.this, NewPasswordActivity.class);
                //startActivity(intent);
            }
        });



    }

    private void ResetTimer(){
        if(runningTimer == false){
            runningTimer = true;
            resendText.setTextColor(Color.parseColor("#A7A7A7"));
            resetCountDownTimer.start();
        }
    }
}