package com.example.demexamnn24.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Email {

    @SerializedName("email")
    @Expose
    private String email;

    public Email(String email) {
        this.email = email;
    }
}
