package com.project.RidEasyDriver;

import android.view.View;

public interface LoginValidation {
    boolean validateEmail();
    boolean validatePassword();
    void loginUser(View view);

}
