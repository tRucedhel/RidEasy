package com.project.RidEasyCustomer;

import com.google.android.gms.maps.model.Marker;
import com.project.RidEasyCustomer.Model.DriverGeoModel;
import com.project.RidEasyCustomer.Model.storingdata;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class Common {
    public static storingdata currentUser;

    public static Set<DriverGeoModel> driversFound = new HashSet<DriverGeoModel>();
    public static HashMap<String, Marker> markerlist = new HashMap<>();

    public static String buildWelcomeMessage() {
        if(Common.currentUser!=null){
            return new StringBuilder("Welcome ").append(currentUser.getFullname()).toString();
        }else{
            return "";
        }
    }

    public static String buildName(String firstname, String lastname) {
        return new StringBuilder(firstname).append(" ").append(lastname).toString();
    }
}
