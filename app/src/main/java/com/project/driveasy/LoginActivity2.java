package com.project.driveasy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LoginActivity2 extends AppCompatActivity
{
    private Button driverbutton;
    private Button customerbutton;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);

        driverbutton = (Button) findViewById(R.id.button_driver);
        customerbutton = (Button) findViewById(R.id.customer_login_btn);

        driverbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent BeforeLoginDriverIntent = new Intent(LoginActivity2.this, DriverLoginActivity.class);
                startActivity(BeforeLoginDriverIntent);
            }
        });
        customerbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent BeforeLoginCustomerIntent = new Intent(LoginActivity2.this, CustomerLoginActivity.class);
                startActivity(BeforeLoginCustomerIntent);
            }
        });
    }
}