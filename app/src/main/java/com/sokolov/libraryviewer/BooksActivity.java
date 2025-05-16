package com.sokolov.libraryviewer;

import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class BooksActivity extends AppCompatActivity {
    ListView booksList;
    ArrayAdapter<Spanned> adapter;
    ArrayList<Spanned> booksString = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_books);

        booksList = findViewById(R.id.booksList);
        adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                booksString
        );
        booksList.setAdapter(adapter);

        loadBooks();
    }

    private void loadBooks() {
        new Thread(() -> {
            try {
                Connection conn = DBConnection.getConnection();
                if (conn != null) {
                    String query = "SELECT * FROM `bookissuance` WHERE LibraryCard = ? and OkReturnDate is null";
                    PreparedStatement stmt = conn.prepareStatement(query);
                    stmt.setInt(1, Integer.parseInt(User.libraryCard));
                    ResultSet rs = stmt.executeQuery();

                    Date now = new Date();

                    int i = 0;
                    while (rs.next()) {
                        Date issueDate = rs.getDate("IssueDate");
                        Date planReturnDate = rs.getDate("PlanReturnDate");
                        DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
                        String outHtml = "<font color='#4d4149'>" + String.valueOf(++i) + ". Выдан " + df.format(issueDate) + ". ";
                        if (now.after(planReturnDate)) {
                            outHtml += "</font><font color='#BF2232'>Требовалось вернуть до " + df.format(planReturnDate) + ". </font><font color='#4d4149'>";
                        }
                        outHtml += rs.getString("BiblDesc") + "</font><br>";

                        booksString.add(Html.fromHtml(outHtml));
                    }

                    runOnUiThread(() -> adapter.notifyDataSetChanged());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }
}
