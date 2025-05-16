package com.sokolov.libraryviewer;

import android.content.Intent;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class RegisterActivity extends AppCompatActivity {
    EditText libraryCard, recordNumber;
    Button registerBtn;
    TextView resultText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        libraryCard = findViewById(R.id.libraryCard);
        recordNumber = findViewById(R.id.recordNumber);
        registerBtn = findViewById(R.id.registerButton);
        resultText = findViewById(R.id.resultRegister);

        registerBtn.setOnClickListener(v -> register());
    }

    private void register() {
        new Thread(() -> {
            try {
                Connection conn = DBConnection.getConnection();
                if (conn == null) {
                    runOnUiThread(() -> {
                        resultText.setText("Не удалось подключиться к серверу");
                    });
                } else {

                    String query = "select * from readers where LibraryCard = ?";
                    PreparedStatement stmt = conn.prepareStatement(query);
                    stmt.setString(1, libraryCard.getText().toString());
                    ResultSet rs = stmt.executeQuery();

                    if (rs.next()) {
                        resultText.setText("Пользователь существет");
                    } else {
                        query = "INSERT INTO `readers` (`IDReader`, `RecordNumber`, `LibraryCard`) VALUES (NULL, ?, ?)";
                        stmt = conn.prepareStatement(query);
                        stmt.setString(1, recordNumber.getText().toString());
                        stmt.setString(2, libraryCard.getText().toString());
                        int rows = stmt.executeUpdate();

                        runOnUiThread(() -> {
                            if (rows > 0) {
                                resultText.setText("Регистрация успешна");
                                startActivity(new Intent(this, LoginActivity.class));
                            } else {
                                resultText.setText("Ошибка регистрации");
                            }
                        });
                    }

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }
}

