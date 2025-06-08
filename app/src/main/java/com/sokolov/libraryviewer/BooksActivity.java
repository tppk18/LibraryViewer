package com.sokolov.libraryviewer;

import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Collections;
import java.util.List;

public class BooksActivity extends AppCompatActivity {
    private ListView listView;
    private BookAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_books);
        listView = findViewById(R.id.booksList);
        Book.loadBooks(this);
        List<Book> bookList = UserDataManager.getUser().bookList;
        //Сортировка по дате возврата
        Collections.sort(bookList, (v1, v2) -> Long.compare(v1.getPlanDate().getTime(), v2.getPlanDate().getTime()));
        adapter = new BookAdapter(this, bookList);
        listView.setAdapter(adapter);
    }
}
