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
import com.google.firebase.database.DatabaseReference;

public class DriverLoginActivity extends AppCompatActivity implements LoginValidation {

    private Button DriverSignupButton;
    private Button DriverLoginButton;

    private FirebaseAuth mAuth;
    private DatabaseReference mDataDriver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_login);

        //inisilaisasi buttons
        DriverSignupButton = findViewById(R.id.driver_signup_btn);
        DriverLoginButton = findViewById(R.id.driver_login_btn);

        //inisialisasi button dari login ke signup
        DriverSignupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent SignupDriverIntent = new Intent(DriverLoginActivity.this, DriverSignUpActivity.class);
                startActivity(SignupDriverIntent);
            }
        });

        DriverLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginUser(view);
            }
        });

    }

    @Override
    public boolean validateEmail() {
        EditText DrivEmailLog = findViewById(R.id.driver_email_login);
        String DrivEmail = DrivEmailLog.getText().toString();

        if (DrivEmail.isEmpty()) {
            DrivEmailLog.setError("Field cannot be empty");
            DrivEmailLog.requestFocus();
            return false;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(DrivEmail).matches()) {
            DrivEmailLog.setError("Please enter valid email address");
            DrivEmailLog.requestFocus();
            return false;
        } else {
            DrivEmailLog.setError(null);
            return true;
        }
    }

    public boolean validatePassword() {
        EditText DrivPassLog = findViewById(R.id.driver_pass);
        String DrivPass = DrivPassLog.getText().toString();

        if (DrivPass.isEmpty()) {
            DrivPassLog.setError("Field cannot be empty");
            DrivPassLog.requestFocus();
            return false;
        } else {
            DrivPassLog.setError(null);
            return true;
        }
    }

    @Override
    public void loginUser(View view) {
        if (!validateEmail() | !validateEmail()) {
            return;
        } else {
            isDriver();
        }
    }

    private void isDriver() {
        EditText DrivEmailLog = findViewById(R.id.driver_email_login);
        String DrivEmail = DrivEmailLog.getText().toString().trim();

        EditText DrivPassLog = findViewById(R.id.driver_pass);
        String DrivPass = DrivPassLog.getText().toString().trim();

        mAuth = FirebaseAuth.getInstance();

        mAuth.signInWithEmailAndPassword(DrivEmail, DrivPass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()){

                    Intent intent = new Intent(DriverLoginActivity.this, DriverHomeActivity.class);
                    startActivity(intent);
                    DrivEmailLog.getText().clear();
                    DrivPassLog.getText().clear();

                } else {
                    Toast.makeText(DriverLoginActivity.this, "Failed to login! Try again!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}