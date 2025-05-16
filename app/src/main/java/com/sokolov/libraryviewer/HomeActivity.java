package com.sokolov.libraryviewer;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Button booksButton = findViewById(R.id.booksButton);
        booksButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, BooksActivity.class);
            startActivity(intent);
        });

        Button barcodeButton = findViewById(R.id.barcodeButton);
        barcodeButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, BarcodeActivity.class);
            startActivity(intent);
        });

        Button logoutButton = findViewById(R.id.logoutButton);
        logoutButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });


    }


}

