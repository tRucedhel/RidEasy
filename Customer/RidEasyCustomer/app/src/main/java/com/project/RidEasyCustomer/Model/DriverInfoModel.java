package com.project.RidEasyCustomer.Model;

import com.google.firebase.database.FirebaseDatabase;

public class DriverInfoModel {
    private String firstName, lastName, email, pass, copass, avatar;

    public DriverInfoModel() {
    }

    public String getFirstName() {
        firstName = FirebaseDatabase.getInstance().getReference().child("InfoDriver").child("firstname").toString();
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        lastName = FirebaseDatabase.getInstance().getReference().child("InfoDriver").child("lastname").orderByChild("lastname").toString();
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getCopass() {
        return copass;
    }

    public void setCopass(String copass) {
        this.copass = copass;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
