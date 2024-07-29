package com.example.qrgenerator;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private QRCodeTypeAdapter adapter;
    private String[] qrTypes = {"URL", "Text", "Email", "Phone", "SMS", "VCard", "Location", "WiFi", "Event"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new QRCodeTypeAdapter(this, qrTypes);
        recyclerView.setAdapter(adapter);
    }
}