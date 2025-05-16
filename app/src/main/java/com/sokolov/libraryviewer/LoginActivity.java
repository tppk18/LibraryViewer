package com.sokolov.libraryviewer;
import android.content.Intent;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginActivity extends AppCompatActivity {
    EditText libraryCard, recordNumber;
    Button loginBtn, toRegisterBtn;
    TextView resultText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        libraryCard = findViewById(R.id.libraryCard);
        recordNumber = findViewById(R.id.recordNumber);
        loginBtn = findViewById(R.id.loginButton);
        toRegisterBtn = findViewById(R.id.toRegister);
        resultText = findViewById(R.id.resultLogin);

        loginBtn.setOnClickListener(v -> login());
        toRegisterBtn.setOnClickListener(v -> {
            startActivity(new Intent(this, RegisterActivity.class));
        });
    }

    private void login() {
        new Thread(() -> {
            try {
                Connection conn = DBConnection.getConnection();
                if (conn == null) {
                    runOnUiThread(() -> {
                        resultText.setText("Не удалось подключиться к серверу");
                    });
                } else {
                    String query = "SELECT * FROM readers WHERE LibraryCard = ? AND RecordNumber = ?";
                    PreparedStatement stmt = conn.prepareStatement(query);

                    stmt.setString(1, libraryCard.getText().toString());
                    stmt.setString(2, recordNumber.getText().toString());

                    ResultSet rs = stmt.executeQuery();
                    runOnUiThread(() -> {
                        try {
                            if (rs.next()) {
                                resultText.setText("Успешный вход");
                                User.libraryCard = libraryCard.getText().toString();
                                User.recordNumber = recordNumber.getText().toString();
                                startActivity(new Intent(this, HomeActivity.class));
                            } else {
                                resultText.setText("Неверные данные");
                            }
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                    });
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        }).start();
    }
}
