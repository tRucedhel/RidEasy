package com.project.RidEasyDriver;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class PraLoginActivity extends AppCompatActivity {

    private Button driverbutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pralogin);

        driverbutton = (Button) findViewById(R.id.button_driver);

        driverbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PraLoginActivity.this, DriverLoginActivity.class);
                startActivity(intent);
            }
        });
    }
}