package com.example.qrgenerator;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class QRDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_details);

        String qrType = getIntent().getStringExtra("QR_TYPE");
        TextView qrTypeTitle = findViewById(R.id.qrTypeTitle);
        qrTypeTitle.setText("Create " + qrType + " QR");

        LinearLayout qrSpecificFields = findViewById(R.id.qrSpecificFields);

        switch (qrType) {
            case "URL":
                // Inflate URL specific layout
                View urlLayout = getLayoutInflater().inflate(R.layout.qr_url_fields, qrSpecificFields, false);
                qrSpecificFields.addView(urlLayout);
                break;
            case "WiFi":
                // Inflate WiFi specific layout
                View wifiLayout = getLayoutInflater().inflate(R.layout.qr_wifi_fields, qrSpecificFields, false);
                qrSpecificFields.addView(wifiLayout);
                break;
            // Add cases for other QR types
        }
    }
}
