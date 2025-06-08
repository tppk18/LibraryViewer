package com.sokolov.libraryviewer;

import android.content.Context;
import android.graphics.Color;
import android.text.SpannableString;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.android.material.card.MaterialCardView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class BookAdapter extends ArrayAdapter<Book> {
    private final Context context;
    private final List<Book> books;

    public BookAdapter(Context context, List<Book> books) {
        super(context, 0, books);
        this.context = context;
        this.books = books;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Book book = books.get(position);
        DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
        Date now = new Date();

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.book_item, parent, false);
        }

        TextView titleView = convertView.findViewById(R.id.bookTitle);
        TextView issueDate = convertView.findViewById(R.id.issueDate);
        TextView planDate = convertView.findViewById(R.id.planDate);

        titleView.setText(book.getDesc());

        String label, dateStr;
        SpannableString spannable;

        label = "Дата выдачи: ";
        dateStr = df.format(book.getIssueDate());
        spannable = new SpannableString(label + dateStr);

        if (now.after(book.getPlanDate())) {
            spannable.setSpan(
                    new android.text.style.ForegroundColorSpan(Color.parseColor("#e95229")),
                    label.length(),
                    label.length() + dateStr.length(),
                    android.text.Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            );
            issueDate.setText(spannable);

            label = "Требовалось вернуть до: ";
            dateStr = df.format(book.getPlanDate());
            spannable = new SpannableString(label + dateStr);
            spannable.setSpan(
                    new android.text.style.ForegroundColorSpan(Color.parseColor("#e95229")),
                    label.length(),
                    label.length() + dateStr.length(),
                    android.text.Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            );
            planDate.setText(spannable);
            MaterialCardView cardView = (MaterialCardView) convertView;
            cardView.setStrokeColor(Color.parseColor("#e95229"));
        } else {
            spannable.setSpan(
                    new android.text.style.ForegroundColorSpan(Color.parseColor("#96C22B")),
                    label.length(),
                    label.length() + dateStr.length(),
                    android.text.Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            );
            issueDate.setText(spannable);

            label = "Дата возврата: ";
            dateStr = df.format(book.getPlanDate());
            spannable = new SpannableString(label + dateStr);
            spannable.setSpan(
                    new android.text.style.ForegroundColorSpan(Color.parseColor("#96C22B")),
                    label.length(),
                    label.length() + dateStr.length(),
                    android.text.Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            );
            planDate.setText(spannable);
            MaterialCardView cardView = (MaterialCardView) convertView;
            cardView.setStrokeColor(Color.parseColor("#96C22B"));
        }
        return convertView;
    }
}
