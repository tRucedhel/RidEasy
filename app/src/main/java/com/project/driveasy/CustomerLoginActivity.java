package com.project.driveasy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CustomerLoginActivity extends AppCompatActivity
{
    private Button CustomerLoginButton;
    private Button CustomerSignupButton;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_login);

        CustomerLoginButton = (Button) findViewById(R.id.customer_login_btn);
        CustomerSignupButton = (Button) findViewById(R.id.customer_signup_btn);

        CustomerLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent LoginCustomerIntent = new Intent(CustomerLoginActivity.this, DriverLoginActivity.class);
                startActivity(LoginCustomerIntent);
            }
        });

        CustomerSignupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent SignupCustomerIntent = new Intent(CustomerLoginActivity.this, DriverLoginActivity.class);
                startActivity(SignupCustomerIntent);
            }
        });
    }
}