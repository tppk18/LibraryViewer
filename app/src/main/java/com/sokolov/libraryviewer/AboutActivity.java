package com.sokolov.libraryviewer;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        ImageButton vkButton = findViewById(R.id.vkIcon);
        vkButton.setOnClickListener(v -> {
            String url = "https://vk.com/club212898313";
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(intent);
        });

        ImageButton telegramButton = findViewById(R.id.telegramIcon);
        telegramButton.setOnClickListener(v -> {
            String url = "https://t.me/ntbsgugit";
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(intent);
        });
    }
}
