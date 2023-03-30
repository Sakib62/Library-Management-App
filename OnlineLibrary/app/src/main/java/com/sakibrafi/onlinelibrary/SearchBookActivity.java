package com.sakibrafi.onlinelibrary;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SearchBookActivity extends AppCompatActivity {

    EditText bookName, bookId;
    Button go;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_book);
        bookName = findViewById(R.id.bookName);
        bookId = findViewById(R.id.bookId);
        go = findViewById(R.id.go);

        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(SearchBookActivity.this, "Book is Available", Toast.LENGTH_SHORT).show();
            }
        });

    }
}