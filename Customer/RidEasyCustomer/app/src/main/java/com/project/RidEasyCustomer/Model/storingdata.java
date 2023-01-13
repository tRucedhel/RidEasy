package com.project.RidEasyCustomer.Model;

public class storingdata {
   private String Firstname, Lastname, Fullname, Email, Pass, CoPass, avatar;

    public storingdata(String firstname, String lastname, String fullname, String email, String pass, String coPass) {
        this.Firstname = firstname;
        this.Lastname = lastname;
        this.Fullname = fullname;
        this.Email = email;
        this.Pass = pass;
        this.CoPass = coPass;
    }

    public String getFirstname() {
        return Firstname;
    }

    public void setFirstname(String firstname) {
        Firstname = firstname;
    }

    public String getLastname() {
        return Lastname;
    }

    public void setLastname(String lastname) {
        Lastname = lastname;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPass() {
        return Pass;
    }

    public void setPass(String pass) {
        Pass = pass;
    }

    public String getCoPass() {
        return CoPass;
    }

    public void setCoPass(String coPass) {
        CoPass = coPass;
    }

    public String getFullname(){
        return Fullname;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
