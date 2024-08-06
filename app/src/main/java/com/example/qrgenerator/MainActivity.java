package com.example.qrgenerator;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView qrTypeRecyclerView;
    private QRCodeTypeAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize RecyclerView for QR Types
        qrTypeRecyclerView = findViewById(R.id.qrTypeRecyclerView);
        qrTypeRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        // List of QR Code Types
        List<QRCodeType> qrTypes = Arrays.asList(
                new QRCodeType("URL", R.drawable.ic_url),
                new QRCodeType("Text", R.drawable.ic_text),
                new QRCodeType("Email", R.drawable.ic_email),
                new QRCodeType("Phone", R.drawable.ic_phone),
                new QRCodeType("SMS", R.drawable.ic_sms),
                new QRCodeType("VCard", R.drawable.ic_vcard),
                new QRCodeType("Location", R.drawable.ic_location),
                new QRCodeType("WiFi", R.drawable.ic_wifi),
                new QRCodeType("Event", R.drawable.ic_event)
        );

        // Set Adapter for RecyclerView
        adapter = new QRCodeTypeAdapter(qrTypes, this);
        qrTypeRecyclerView.setAdapter(adapter);

        // Bottom Navigation View setup
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment = null;
                int itemId = item.getItemId();

                if (itemId == R.id.nav_home) {
                    selectedFragment = new HomeFragment(); // Initialize HomeFragment
                } else if (itemId == R.id.nav_saved) {
                    selectedFragment = new SavedQRFragment(); // Initialize SavedQRFragment
                } else if (itemId == R.id.nav_settings) {
                    selectedFragment = new SettingsFragment(); // Initialize SettingsFragment
                }

                // Replace current fragment with the selected one
                if (selectedFragment != null) {
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container, selectedFragment)
                            .commit();
                }

                return true;
            }
        });

        // Initialize the HomeFragment on startup
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new HomeFragment())
                    .commit();
        }
    }
}
