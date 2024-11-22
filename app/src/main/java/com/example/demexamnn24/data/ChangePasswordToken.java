package com.example.demexamnn24.data;

public class ChangePasswordToken {

    String type;
    String email;
    String token;

    public ChangePasswordToken(String type, String email, String token) {
        this.type = type;
        this.email = email;
        this.token = token;
    }
}
