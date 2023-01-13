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
import com.google.firebase.database.FirebaseDatabase;
import com.project.RidEasyCustomer.Model.storingdata;
import com.project.RidEasyCustomer.R;

public class CustomerSignUpActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    EditText CustFirstNameReg,CustLastNameReg,CustEmailReg,CustPassReg,CustCopassReg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_sign_up);

        mAuth = FirebaseAuth.getInstance();

        Button CustomerSigninButton = findViewById(R.id.customer_signin_btnreg);
        Button CustomerSignupButton = findViewById(R.id.customer_signup_btnreg);
        CustFirstNameReg = findViewById(R.id.customer_firstname_reg);
        CustLastNameReg = findViewById(R.id.customer_lastname_reg);
        CustEmailReg = findViewById(R.id.customer_email);
        CustPassReg = findViewById(R.id.customer_pass);
        CustCopassReg = findViewById(R.id.customer_copass);

        CustomerSignupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cekCustomer();
            }
        });

        CustomerSigninButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CustomerSignUpActivity.this, CustomerLoginActivity.class);
                startActivity(intent);
            }
        });
    }

    private void cekCustomer(){
        String CustFirstname = CustFirstNameReg.getText().toString();
        String CustLastname = CustLastNameReg.getText().toString();
        String CustEmail = CustEmailReg.getText().toString();
        String CustPass = CustPassReg.getText().toString();
        String CustCopass = CustCopassReg.getText().toString();
        final String CustFullName = CustFirstname+" "+CustLastname;



        if (CustFirstname.isEmpty()){
            CustFirstNameReg.setError("First name is required");
            CustFirstNameReg.requestFocus();
            return;
        }

        if (CustLastname.isEmpty()){
            CustLastNameReg.setError("Last name is required");
            CustLastNameReg.requestFocus();
            return;
        }

        if (CustEmail.isEmpty()){
            CustEmailReg.setError("Email is required");
            CustEmailReg.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(CustEmail).matches()){
            CustEmailReg.setError("Please provide valid email");
            CustEmailReg.requestFocus();
            return;
        }

        if (CustPass.isEmpty()){
            CustPassReg.setError("Password cannot be empty");
            CustPassReg.requestFocus();
            return;
        }

        if (CustPass.length() < 6){
            CustPassReg.setError("Password must at least contain 6 characters");
            CustPassReg.requestFocus();
            return;
        }

        if (!CustPass.equals(CustCopass)){
            CustCopassReg.setError("Password must be identical");
            CustCopassReg.requestFocus();
            return;
        }

        mAuth.createUserWithEmailAndPassword(CustEmail, CustPass)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            storingdata customerData = new storingdata(CustFirstname, CustLastname, CustFullName, CustEmail, CustPass, CustCopass);
                            FirebaseDatabase.getInstance().getReference("InfoCustomer")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(customerData).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful()){
                                                Toast.makeText(CustomerSignUpActivity.this, "Customer successfully registered", Toast.LENGTH_LONG).show();
                                                Intent intent =new Intent(CustomerSignUpActivity.this, HomeActivity.class);
                                                startActivity(intent);
                                                finish();
                                            } else {
                                                Toast.makeText(CustomerSignUpActivity.this, "Failed to register! Try again!", Toast.LENGTH_LONG).show();
                                            }
                                        }
                                    });
                        } else {
                            Toast.makeText(CustomerSignUpActivity.this, "Sorry Something Went Wrong", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
}