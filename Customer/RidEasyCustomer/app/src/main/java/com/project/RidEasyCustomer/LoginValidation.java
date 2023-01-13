package com.project.RidEasyCustomer;

import android.view.View;

public interface LoginValidation {
    boolean validateEmail();
    boolean validatePassword();
    void loginUser(View view);

}
