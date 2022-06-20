package com.free.fileupload.model;

import android.os.Handler;

import com.free.fileupload.contract.LoginModel;

public class LoginModelImpl implements LoginModel {
    @Override
    public void login(String username, String password, OnLoginListener listener) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (username.equals("1") && password.equals("1")) {
                    listener.onLoginSuccess();
                } else {
                    listener.onLoginFail();
                }
            }
        }, 3000);
    }
}
