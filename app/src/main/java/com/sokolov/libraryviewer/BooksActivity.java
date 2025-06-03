package com.sokolov.libraryviewer;

import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

public class BooksActivity extends AppCompatActivity {
    private ListView listView;
    private BookAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_books);
        listView = findViewById(R.id.booksList);
        Book.loadBooks(this);
        adapter = new BookAdapter(this, UserDataManager.getUser().bookList);
        listView.setAdapter(adapter);
    }
}
