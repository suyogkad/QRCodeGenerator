package com.example.qrgenerator;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class QRDetailsActivity extends AppCompatActivity {

    private static final int REQUEST_STORAGE_PERMISSION = 1;

    private EditText editTextURL;
    private EditText editTextQRName;
    private Button buttonGenerateQR;
    private Button buttonSave;
    private Button buttonShare;
    private ImageView imageViewQRCode;
    private ProgressBar progressBar;
    private Bitmap qrBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_details);

        // Get the QR type from the Intent
        String qrType = getIntent().getStringExtra("QR_TYPE");

        editTextURL = findViewById(R.id.editTextURL);
        editTextQRName = findViewById(R.id.editTextQRName);
        buttonGenerateQR = findViewById(R.id.buttonGenerateQR);
        buttonSave = findViewById(R.id.buttonSave);
        buttonShare = findViewById(R.id.buttonShare);
        imageViewQRCode = findViewById(R.id.imageViewQRCode);
        progressBar = findViewById(R.id.progressBar);

        buttonGenerateQR.setOnClickListener(v -> {
            String url = editTextURL.getText().toString();
            String qrName = editTextQRName.getText().toString();
            if (!url.isEmpty()) {
                progressBar.setVisibility(View.VISIBLE);
                generateQRCode(url, qrName);
            } else {
                Toast.makeText(QRDetailsActivity.this, "Please enter a URL", Toast.LENGTH_SHORT).show();
            }
        });

        buttonSave.setOnClickListener(v -> {
            if (qrBitmap != null) {
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_STORAGE_PERMISSION);
                } else {
                    saveQRCode(qrBitmap, editTextQRName.getText().toString());
                }
            } else {
                Toast.makeText(this, "No QR Code to save", Toast.LENGTH_SHORT).show();
            }
        });

        buttonShare.setOnClickListener(v -> {
            if (qrBitmap != null) {
                shareQRCode(qrBitmap);
            } else {
                Toast.makeText(this, "No QR Code to share", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void generateQRCode(String url, String qrName) {
        BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
        try {
            qrBitmap = barcodeEncoder.encodeBitmap(url, BarcodeFormat.QR_CODE, 400, 400);
            imageViewQRCode.setImageBitmap(qrBitmap);
            progressBar.setVisibility(View.GONE);
            imageViewQRCode.setVisibility(View.VISIBLE);
            buttonSave.setVisibility(View.VISIBLE);
            buttonShare.setVisibility(View.VISIBLE);
        } catch (WriterException e) {
            e.printStackTrace();
            progressBar.setVisibility(View.GONE);
            Toast.makeText(this, "Failed to generate QR Code", Toast.LENGTH_SHORT).show();
        }
    }

    private void saveQRCode(Bitmap bitmap, String qrName) {
        String fileName = qrName.isEmpty() ? "QRCode" : qrName;
        File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File qrFile = new File(path, fileName + ".png");

        try (FileOutputStream out = new FileOutputStream(qrFile)) {
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
            Toast.makeText(this, "QR Code saved to Gallery", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Failed to save QR Code", Toast.LENGTH_SHORT).show();
        }
    }

    private void shareQRCode(Bitmap bitmap) {
        String bitmapPath = MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, "QR Code", null);
        Uri bitmapUri = Uri.parse(bitmapPath);
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("image/png");
        shareIntent.putExtra(Intent.EXTRA_STREAM, bitmapUri);
        startActivity(Intent.createChooser(shareIntent, "Share QR Code"));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_STORAGE_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                saveQRCode(qrBitmap, editTextQRName.getText().toString());
            } else {
                Toast.makeText(this, "Storage permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
