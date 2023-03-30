package com.sakibrafi.onlinelibrary;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RemoveBookActivity extends AppCompatActivity {

    EditText bookId;
    Button remove;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remove_book);

        bookId = findViewById(R.id.bookID);
        remove = findViewById(R.id.remove);

        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(RemoveBookActivity.this, "Book has been removed", Toast.LENGTH_SHORT).show();
            }
        });

    }
}