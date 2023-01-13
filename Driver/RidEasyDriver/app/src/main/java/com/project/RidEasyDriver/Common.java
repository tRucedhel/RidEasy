package com.project.RidEasyDriver;

import com.project.RidEasyDriver.Model.storingdata;

public class Common {
    public static storingdata currentUser;

    public static String buildWelcomeMessage() {
        if(Common.currentUser!=null){
            return new StringBuilder("Welcome ").append(currentUser.getFullname()).toString();
        }else{
            return "";
        }
    }
}
