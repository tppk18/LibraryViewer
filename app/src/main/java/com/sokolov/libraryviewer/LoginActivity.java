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
    Button loginBtn;
    TextView resultText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UserDataManager.loadUserData(this);
        if (UserDataManager.getUser() != null) {
            startActivity(new Intent(this, HomeActivity.class));
            finish();
            return;
        }

        setContentView(R.layout.activity_login);

        libraryCard = findViewById(R.id.libraryCard);
        recordNumber = findViewById(R.id.recordNumber);
        loginBtn = findViewById(R.id.loginButton);
        resultText = findViewById(R.id.resultLogin);
        loginBtn.setOnClickListener(v -> login());

        Button becomeReaderButton = findViewById(R.id.becomeReaderButton);
        becomeReaderButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, BecomeActivity.class);
            startActivity(intent);
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
                                UserData user = new UserData(libraryCard.getText().toString(), recordNumber.getText().toString());
                                UserDataManager.saveUserData(this, user);
                                startActivity(new Intent(this, HomeActivity.class));
                                finish();
                                return;
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
