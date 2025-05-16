package com.sokolov.libraryviewer;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.journeyapps.barcodescanner.BarcodeEncoder;
import com.google.zxing.BarcodeFormat;

public class BarcodeActivity extends AppCompatActivity {
    private Button generateBtn;
    private ImageView barcodeView;
    private TextView titleBarcode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barcode);
        barcodeView = findViewById(R.id.barcodeView);
        try {
            String content = String.valueOf(User.libraryCard);
            if (!content.isEmpty()) {
                BarcodeEncoder encoder = new BarcodeEncoder();
                Bitmap bitmap = encoder.encodeBitmap(
                        content,
                        BarcodeFormat.CODE_128,
                        600,
                        300
                );
                barcodeView.setImageBitmap(bitmap);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        titleBarcode = findViewById(R.id.titleBarcode);
        titleBarcode.setText(String.valueOf(User.libraryCard));
    }
}