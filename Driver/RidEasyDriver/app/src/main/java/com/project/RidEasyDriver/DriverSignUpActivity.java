package com.project.RidEasyDriver;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.project.RidEasyDriver.Model.storingdata;

public class DriverSignUpActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;

    EditText DrivFirstnameReg,DrivLastnameReg,DrivEmailReg,DrivPassReg,DrivCopassReg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_sign_up);

        mAuth = FirebaseAuth.getInstance();

        Button DriverSigninButton = findViewById(R.id.driver_signin_btnreg);
        Button DriverSignupButton = findViewById(R.id.driver_signup_btnreg);
        DrivFirstnameReg = findViewById(R.id.driver_firstname_reg);
        DrivLastnameReg = findViewById(R.id.driver_lastname_reg);
        DrivEmailReg = findViewById(R.id.driver_email);
        DrivPassReg = findViewById(R.id.driver_pass);
        DrivCopassReg = findViewById(R.id.driver_copass);




        DriverSignupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cekDriver();
            }
        });

        DriverSigninButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent SigninDriverIntent = new Intent(DriverSignUpActivity.this, DriverLoginActivity.class);
                startActivity(SigninDriverIntent);
            }
        });
    }

    private void cekDriver(){
        String DrivFirstname = DrivFirstnameReg.getText().toString();
        String DrivLastname = DrivLastnameReg.getText().toString();
        String DrivEmail = DrivEmailReg.getText().toString();
        String DrivPass = DrivPassReg.getText().toString();
        String DrivCopass = DrivCopassReg.getText().toString();
        final String DrivFullName = DrivFirstname+" "+DrivLastname;



        if (DrivFirstname.isEmpty()){
            DrivFirstnameReg.setError("First name is required");
            DrivFirstnameReg.requestFocus();
            return;
        }

        if (DrivLastname.isEmpty()){
            DrivLastnameReg.setError("Last name is required");
            DrivLastnameReg.requestFocus();
            return;
        }

        if (DrivEmail.isEmpty()){
            DrivEmailReg.setError("Email is required");
            DrivEmailReg.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(DrivEmail).matches()){
            DrivEmailReg.setError("Please provide valid email");
            DrivEmailReg.requestFocus();
            return;
        }

        if (DrivPass.isEmpty()){
            DrivPassReg.setError("Password cannot be empty");
            DrivPassReg.requestFocus();
            return;
        }

        if (DrivPass.length() < 6){
            DrivPassReg.setError("Password must at least contain 6 characters");
            DrivPassReg.requestFocus();
            return;
        }

        if (!DrivPass.equals(DrivCopass)){
            DrivCopassReg.setError("Password must be identical");
            DrivCopassReg.requestFocus();
            return;
        }

        mAuth.createUserWithEmailAndPassword(DrivEmail, DrivPass)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            storingdata driverData = new storingdata(DrivFirstname, DrivLastname, DrivFullName, DrivEmail, DrivPass, DrivCopass);
                            FirebaseDatabase.getInstance().getReference("InfoDriver")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(driverData).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful()){
                                                Toast.makeText(DriverSignUpActivity.this, "Driver successfully registered", Toast.LENGTH_LONG).show();
                                                Intent intent =new Intent(DriverSignUpActivity.this, DriverHomeActivity.class);
                                                startActivity(intent);
                                                finish();
                                            } else {
                                                Toast.makeText(DriverSignUpActivity.this, "Failed to register! Try again!", Toast.LENGTH_LONG).show();
                                            }
                                        }
                                    });
                        }
                    }
                });
    }
}