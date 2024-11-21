package com.example.demexamnn24.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.chaos.view.PinView;
import com.example.demexamnn24.R;
import com.google.android.material.button.MaterialButton;

public class OTPVerificationActivity extends AppCompatActivity {

    TextView resendText;
    boolean runningTimer;
    MaterialButton NewPassButton;
    CountDownTimer resetCountDownTimer;
    PinView pinView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otpverification);

        NewPassButton = findViewById(R.id.SetNewPass);
        resendText = findViewById(R.id.resendAfter);
        pinView = findViewById(R.id.pinView);

        String code = pinView.getText().toString();

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

        NewPassButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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