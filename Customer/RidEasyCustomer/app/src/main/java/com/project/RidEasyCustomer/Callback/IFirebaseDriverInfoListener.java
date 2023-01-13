package com.project.RidEasyCustomer.Callback;

import com.project.RidEasyCustomer.Model.DriverGeoModel;

public interface IFirebaseDriverInfoListener {
    void onDriverInfoLoadSuccess(DriverGeoModel driverGeoModel);
}
