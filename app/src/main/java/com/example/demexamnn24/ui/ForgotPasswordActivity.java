package com.example.demexamnn24.ui;

import static com.example.demexamnn24.utils.Utils.APIKEY;
import static com.example.demexamnn24.utils.Utils.BASE_URL;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.demexamnn24.R;
import com.example.demexamnn24.controller.API;
import com.example.demexamnn24.data.Email;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ForgotPasswordActivity extends AppCompatActivity {

    TextInputLayout emailField;
    MaterialButton button;
    Retrofit retrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        emailField = findViewById(R.id.forgotEmailAddress);
        button = findViewById(R.id.button_forgot);

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        API api = retrofit.create(API.class);

        //Создадим объект Email для отправки на почту кода восстановления
        Email email = new Email(String.valueOf(emailField.getEditText().getText()));

        //по нажатию на кнопку отправипм код
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Call<Void> call = api.sendCode(APIKEY, email);
                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        Toast.makeText(ForgotPasswordActivity.this, "Send OK", Toast.LENGTH_SHORT).show();
                        //Если почта корректна, то переходим на OTP Verification
                        if (isEmailValid(String.valueOf(emailField.getEditText().getText()))){
                            Intent intent = new Intent(ForgotPasswordActivity.this, OTPVerificationActivity.class);
                            intent.putExtra("email", emailField.getEditText().getText().toString());
                            startActivity(intent);
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Toast.makeText(ForgotPasswordActivity.this, t.getMessage().toString(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });


    }

    public void signin2(View view) {
        startActivity(new Intent(ForgotPasswordActivity.this, LogInActivity.class));
    }
    boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}