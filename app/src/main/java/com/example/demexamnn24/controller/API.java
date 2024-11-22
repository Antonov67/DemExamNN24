package com.example.demexamnn24.controller;


import com.example.demexamnn24.data.ChangePasswordToken;
import com.example.demexamnn24.data.Email;
import com.example.demexamnn24.data.ResponseUser;
import com.example.demexamnn24.data.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface API {

    //регистрация юзера
    @POST("signup")
    Call<ResponseUser> signUpByEmailAndPswrd(@Header("apikey") String apikey, @Body User user);

    //авторизация юзера
    @POST("token")
    Call<ResponseUser> login(@Query("grant_type") String grant_type, @Header("apikey") String apikey, @Body User user);

    //отправка кода на почту для восстановления пароля
    @POST("recover")
    Call<Void> sendCode(@Header("apikey") String apikey, @Body Email email);

    @POST("verify")
    Call<ResponseUser> verifyCode(@Header("apikey") String apikey, @Body ChangePasswordToken changePasswordToken);

}
