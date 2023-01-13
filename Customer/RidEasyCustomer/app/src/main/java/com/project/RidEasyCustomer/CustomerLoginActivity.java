package com.project.RidEasyCustomer;

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

public class CustomerLoginActivity extends AppCompatActivity implements LoginValidation {

    private Button CustomerSignupButton;
    private Button CustomerSigninButton;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_login);

        CustomerSignupButton = findViewById(R.id.customer_signup_btn);
        CustomerSigninButton = findViewById(R.id.customer_login_btn);

        CustomerSignupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CustomerLoginActivity.this, CustomerSignUpActivity.class);
                startActivity(intent);
            }
        });


        CustomerSigninButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginUser(view);
            }
        });
    }

    public boolean validateEmail() {
        EditText CustEmailLog = findViewById(R.id.customer_email_login);
        String CustEmail = CustEmailLog.getText().toString();

        if(CustEmail.isEmpty()){
            CustEmailLog.setError("Field cannot be empty");
            CustEmailLog.requestFocus();
            return false;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(CustEmail).matches()){
            CustEmailLog.setError("Please enter valid email address");
            CustEmailLog.requestFocus();
            return false;
        }else
        {
            CustEmailLog.setError(null);
            return true;
        }
    }

    public boolean validatePassword() {
        EditText CustPassLog = findViewById(R.id.customer_pass);
        String CustPass = CustPassLog.getText().toString();

        if(CustPass.isEmpty()){
            CustPassLog.setError("Field cannot be empty");
            CustPassLog.requestFocus();
            return false;
        }else{
            CustPassLog.setError(null);
            return true;
        }
    }

    @Override
    public void loginUser(View view) {
        if(!validateEmail() | !validatePassword())
        {
            return;
        }else{
            isCustomer();
        }
    }

    private void isCustomer() {
        EditText CustEmailLog = findViewById(R.id.customer_email_login);
        String CustEmail = CustEmailLog.getText().toString().trim();

        EditText CustPassLog = findViewById(R.id.customer_pass);
        String CustPass = CustPassLog.getText().toString().trim();

        mAuth = FirebaseAuth.getInstance();

        mAuth.signInWithEmailAndPassword(CustEmail, CustPass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Intent intent = new Intent(CustomerLoginActivity.this, HomeActivity.class);
                    startActivity(intent);
                    CustEmailLog.getText().clear();
                    CustPassLog.getText().clear();
                }else{
                    Toast.makeText(CustomerLoginActivity.this, "Failed to login! Try again!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}