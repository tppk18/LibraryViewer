package com.sokolov.libraryviewer;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (checkSelfPermission(android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(this, NotificationPermissionActivity.class);
                startActivity(intent);
            }
        }
        BookReturnNotificationWorker.setupNextWorker(this);
        new Thread(() -> { Book.loadBooks(this); }).start();

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

        Button aboutButton = findViewById(R.id.aboutButton);
        aboutButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, AboutActivity.class);
            startActivity(intent);
        });

        Button logoutButton = findViewById(R.id.logoutButton);
        logoutButton.setOnClickListener(v -> {
            UserDataManager.deleteUserData(this);
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        });

        TextView cardNumberText = findViewById(R.id.cardNumberText);
        String resultText = "Читательский билет №" + UserDataManager.getUser().libraryCard;
        cardNumberText.setText(resultText);

    }
}

