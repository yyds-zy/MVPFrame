package com.free.fileupload.presenter;

import com.free.fileupload.contract.LoginModel;
import com.free.fileupload.contract.LoginPresent;
import com.free.fileupload.contract.LoginView;

public class LoginPresentImpl implements LoginPresent, LoginModel.OnLoginListener {

    private LoginModel loginModel;
    private LoginView loginView;

    public LoginPresentImpl(LoginModel loginModel,LoginView loginView){
        this.loginModel = loginModel;
        this.loginView = loginView;
    }

    @Override
    public void onLoginSuccess() {
        loginView.hideProgress();
        loginView.loginSuccess();
    }

    @Override
    public void onLoginFail() {
        loginView.hideProgress();
        loginView.loginFail();
    }

    @Override
    public void doLogin(String username, String password) {
        loginView.showProgress();
        loginModel.login(username,password,this);
    }
}
