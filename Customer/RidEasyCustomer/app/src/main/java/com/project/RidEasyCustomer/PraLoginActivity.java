package com.project.RidEasyCustomer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.project.RidEasyCustomer.R;

public class PraLoginActivity extends AppCompatActivity {

    private Button customerbutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pralogin);

        customerbutton = (Button) findViewById(R.id.button_customer);

        customerbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PraLoginActivity.this, CustomerLoginActivity.class);
                startActivity(intent);
            }
        });
    }
}