package com.free.fileupload.contract;

public interface LoginModel {
    interface OnLoginListener {
        void onLoginSuccess();

        void onLoginFail();
    }

    void login(String username, String password, OnLoginListener listener);
}
